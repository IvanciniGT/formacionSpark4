
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

public class IntroSparkSQL {

    public static void main( String[] args ) {
        // PASO 1: Abrir una conexión con el cluster
        SparkSession conexion = SparkSession.builder()
                                            .appName("IntroSQL")
                                            //.master("local[2]")
                                            .getOrCreate();
        // PASO 2: Preparar los datos y cargarlos en Spark
        Dataset<Row> datos = conexion.read()        // Trabaja en modo BATCH
                                   //.readStream()  // Trabaja en modo STREAMING
                                                //.csv
                                                //.parquet
                                                //.json
                                                //.jdbc
                                                //.text
                .json("src/main/resources/personas.json");
        // .json("hdfs://<NOMBRE|IP>:<PUERTO>/ruta/al/fichero.json")
        // .json("s3://<BUCKET>/ruta/al/fichero.json"")

        datos.show();
        datos.printSchema();

        datos.select("nombre", "apellido").show();
        datos.select( col("nombre"),col("apellido"),col("edad").plus(5)).show();
        datos.filter( col("edad").leq(40) )
             .select("nombre", "apellido")
             .show();

        datos.groupBy( "nombre")
                .sum("edad")
                .orderBy( col("sum(edad)").asc() )
                .show();

        // Esto no queda aquí.
        datos.createOrReplaceTempView("personas"); // Alias para poder usarlo desde SQL
        Dataset<Row> resultado = conexion.sql("SELECT nombre, email, sum(edad) FROM personas GROUP BY nombre, email ORDER BY sum(edad) DESC");
        resultado.show();
        //resultado.write()//.csv("src/main/resources/personas.csv");
        //                .parquet("src/main/resources/personas.parquet");

        // EN OCASIONES SQL NO es la mejor opción... preferimos hacerlo con Spark Core
        // Y podemos hacerlo... porque SparkSQL y SparkCore son compatibles
        // Para pasar de un Dataset<Row> a un RDD<Row> usamos el método toJavaRDD()
        //                         Dataset<Row> resultado
        /*
        JavaRDD<Row> datosEnRDD = resultado.toJavaRDD();
        datosEnRDD.filter( fila -> fila.getInt(fila.fieldIndex("edad"))>30)
                .foreach( fila -> System.out.println(fila.getString(fila.fieldIndex("nombre"))));
        Dataset<Row> datosDeVueltaASQL = conexion.createDataFrame(datosEnRDD, Row.class);
        datosDeVueltaASQL.show();
        */


        Dataset<Row> datosEnFormatoSQL = conexion.read()        // Trabaja en modo BATCH
                //.readStream()  // Trabaja en modo STREAMING
                //.csv
                //.parquet
                //.json
                //.jdbc
                //.text
                .json("src/main/resources/personas.json");
        JavaRDD<Row> datosEnFormatoRDD_ROWS = datosEnFormatoSQL.toJavaRDD();
        JavaRDD<Persona> datosEnFormatoRDD_Persona = datosEnFormatoRDD_ROWS.map(fila -> {
            String nombre = fila.getString(fila.fieldIndex("nombre"));
            String apellido = fila.getString(fila.fieldIndex("apellido"));
            int edad = (int) fila.getLong(fila.fieldIndex("edad"));
            String email = fila.getString(fila.fieldIndex("email"));
            String dni = fila.getString(fila.fieldIndex("dni"));
            return new Persona(nombre, apellido, edad, dni, email);
        });
        JavaRDD<Persona> personasEmailValidoRDD = datosEnFormatoRDD_Persona
                .repartition(50)
                .filter(Persona::validarEmail);

        Dataset<Row> personasEmailValidoSQL = conexion.createDataFrame(personasEmailValidoRDD, Persona.class);
        personasEmailValidoSQL.show();
        // PASO 4: Cerrar la conexión con el cluster

        // SparkSession conexion ... objeto de la librería SQL
        // JavaSparkContext conexionCore ... objeto de la librería Core
        SparkContext conexionCore= conexion.sparkContext();
        //JavaSparkContext conexionCoreJava = new JavaSparkContext(conexionCore);

        //conexionCoreJava -> SparkSession
        conexion.close();

    }

}
