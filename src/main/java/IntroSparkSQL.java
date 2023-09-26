
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class IntroSparkSQL {

    public static void main( String[] args ) {
        // PASO 1: Abrir una conexión con el cluster
        SparkSession conexion = SparkSession.builder()
                                            .appName("IntroSQL")
                                            .master("local[2]")
                                            .getOrCreate();
        // PASO 2: Preparar los datos y cargarlos en Spark
        Dataset<Row> datos = conexion.read() // Trabaja en modo BATCH
                .json("src/main/resources/personas.json");




        // PASO 3: Procesar los datos
        // PASO 5: Hago lo que quiera con los resultados
        // PASO 4: Cerrar la conexión con el cluster
        conexion.close();
    }

}
