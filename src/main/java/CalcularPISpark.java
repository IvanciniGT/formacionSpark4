import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CalcularPISpark {

    public static void main(String[] args) throws Exception {
        SparkConf configuracion = new SparkConf()
                .setAppName("ProcesarHashtagsConSpark")
                .setMaster("local[2]");

        JavaSparkContext conexion = new JavaSparkContext(configuracion);

        final int totalDeDardos = 10 * 1000 * 1000;

        long numeroDeDardosDentro = conexion.parallelize(Arrays.asList(totalDeDardos)/*, 50*/) // Particiones del conjunto de datos (PAQUETES DE TRABAJO)
                .flatMap(numeroDeDardos-> IntStream.range(0, numeroDeDardos).iterator())
                .repartition(50)   // Particiones
                .mapToDouble(numeroDeDardo -> Math.sqrt( Math.pow(Math.random(),2) + Math.pow(Math.random(),2)))
                .filter( distanciaAlCentro -> distanciaAlCentro <= 1)
                .count();


        // El cluster tendrá npi nodos... verdad?
        // Me aseguraré (o lo intentaré) de poner un número más alto que el de cores totales de cálculo en el cluster
        // Ya Spark irá repartiendo el trabajo.
        // En un cluster tiene 2 ejecutores... Pues a cada uno le mandará 25 paquetes de trabajo
        // En otro cluster tiene 4 ejecutores... Pues a cada uno le mandará 12 paquetes de trabajo... a uno de ellos le mandará 14
        // En otro cluster tengo 25 ejecutores... Pues a cada uno le mandará 2 paquetes de trabajo

        double estimacionDePI = 4. * numeroDeDardosDentro  / totalDeDardos;

        System.out.println("PI es aproximadamente " + estimacionDePI);
        conexion.close();
    }

}













