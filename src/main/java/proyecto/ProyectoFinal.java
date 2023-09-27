package proyecto;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import scala.Tuple2;

import static org.apache.spark.sql.functions.col;

public class ProyectoFinal {

    private static final String FICHERO_PERSONAS_CON_DNI_INVALIDO = "src/main/resources/personasConDNIInvalido.parquet";
    private static final boolean debug = true;

    public static void main(String[] args){

        // PASO 1: Abro conexión
        SparkSession conexion = SparkSession.builder()
                .appName("Proyecto final")
                .master("local[2]")
                .getOrCreate();
        // PASO 2: Leer el fichero de personas
        Dataset<Row> datos = conexion.read()        // Trabaja en modo BATCH
                .json("src/main/resources/personas.json");
        // PASO 3: Los que tengan DNI Inválido, los saco a un fichero
        /*
            // ALTERNATIVA 1: Trabajar con RDDs
             JavaRDD<Row> datosComoRDD = datos.toJavaRDD();                                // Convertir el Dataset a RDD
                // ALTERNATIVA 1.1: Convertir el objeto Row a Persona
                 JavaRDD<PersonaPF> rddPersonas = datosComoRDD.map(fila -> {
                    String nombre = fila.getString(fila.fieldIndex("nombre"));
                    String apellido = fila.getString(fila.fieldIndex("apellido"));
                    String dni = fila.getString(fila.fieldIndex("dni"));
                    String email = fila.getString(fila.fieldIndex("email"));
                    int edad = fila.getInt(fila.fieldIndex("edad"));
                    String cp = fila.getString(fila.fieldIndex("cp"));
                    return new PersonaPF(nombre, apellido, edad, dni, email, cp);
                 });
                JavaPairRDD<PersonaPF, Boolean> personasConDNI_Validado = rddPersonas.mapToPair(persona -> new Tuple2<>(persona, persona.validarDNI()) );
                JavaRDD<PersonaPF> personasConDNI_NOOK = personasConDNI_Validado.filter(tupla -> !tupla._2 ).keys();
                Dataset<Row> personasDNIInvalido_Alternativa1 = conexion.createDataFrame(personasConDNI_NOOK, PersonaPF.class);
                // Alternativa 1.2: Trabajo con RDD pero de tipo Row
                JavaPairRDD<Row, Boolean> personasConDNI_Validado2 = datosComoRDD.mapToPair(fila -> {
                    String dni = fila.getString(fila.fieldIndex("dni"));
                    return new Tuple2<>(fila, !PersonaPF.validarDNI(dni) );
                } );
                Dataset<Row> personasDNIInvalido_Alternativa2 = conexion.createDataFrame(personasConDNI_Validado2.keys(), Row.class);
                // Alternativa 1.3: Trabajo con RDD pero de tipo Row o no... pero aplicando un Filter (y no un map2Pair)
                JavaRDD<Row> personasConDNI_NOOK2 = datosComoRDD.filter( fila -> !PersonaPF.validarDNI(fila.getString(fila.fieldIndex("dni"))) );
                Dataset<Row> personasDNIInvalido_Alternativa3 = conexion.createDataFrame(personasConDNI_NOOK2, Row.class);
                // Luego tengo que hacer otro filer..pesado con la función: validarDNI
            personasDNIInvalido_Alternativa1.write().parquet(FICHERO_PERSONAS_CON_DNI_INVALIDO);
        */

            // Alternativa 2: Trabajo con SQL y Datasets   <<<< En muchos casos esta es GUAY DEL PARAGUAY... como este caso
            conexion.udf().register("validarDNI", PersonaPF::validarDNI, DataTypes.BooleanType); // 1. Registro la función validarDNI en SparkSQL
            Dataset<Row> personasConDNI_Validado3 = datos.withColumn("DNIValido", functions.callUDF("validarDNI", col("dni"))); // 2. Creo una nueva columna llamada DNIValido
            Dataset<Row> personasDNIInvalido_Alternativa4 = personasConDNI_Validado3.filter( col("DNIValido").equalTo(false) ); // 3. Me quedo con los que tengan DNI Inválid
            personasDNIInvalido_Alternativa4.write().mode("overwrite").parquet(FICHERO_PERSONAS_CON_DNI_INVALIDO);// 4. Lo guardo en un fichero
            if(debug)personasDNIInvalido_Alternativa4.limit(10).show();
        // PASO 4: Me quedo con los que tengan DNIs válidos
        Dataset<Row> personasDNIValido = personasConDNI_Validado3.filter( col("DNIValido").equalTo(true) ); // 3. Me quedo con los que tengan DNI Inválid

        // PASO 5: Leer el fichero de CP
        Dataset<Row> cps = conexion.read()
                .option("header",true)     // Trabaja en modo BATCH
                .csv("src/main/resources/cp.csv");
        if(debug)cps.limit(10).show();

        // PASO 6: Hago un join entre personas y CP
        Dataset<Row> personasConInfoExtra = personasDNIValido.join(cps, "cp");

        // PASO 7: El resultado del join, a un fichero
        personasConInfoExtra.write().mode("overwrite").parquet("src/main/resources/personasConInfoExtra.parquet");
        if(debug)personasConInfoExtra.limit(10).show();

        // PASO 8: Aquellas personas que no tengán un CP válido, a otro fichero
        Dataset<Row> personasSinCP = personasDNIValido.except(personasConInfoExtra.select("nombre","apellido","dni","email","edad","cp","DNIValido"));
        personasSinCP.write().mode("overwrite").parquet("src/main/resources/personasSinCP.parquet");
        if(debug)personasSinCP.limit(10).show();

        // PASO 9: Cerrar conexión
        conexion.close();
    }
}
