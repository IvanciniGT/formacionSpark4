import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CalcularPIStream {

    // Por ejemplo, en una mñaquina como la mía, que tiene 8 cores: 12,5% de uso de CPU
    // Se usa 1 solo core... porque mi código solo usa 1 Thread
    public static void main(String[] args){
/*        final int trabajadores = 4;
        final int totalDeDardos = 1000 * 1000 * 1000;
        //int dardosPorTrabajador = totalDeDardos / trabajadores;
        //
        // IntStream != Stream<Integer>
        // El primero es un Stream de primitivos
        // El segundo es un Stream de Objetos
        //List<Integer> trabajadoresConSusDardos = Arrays.asList(dardosPorTrabajador, dardosPorTrabajador, dardosPorTrabajador, dardosPorTrabajador);

        IntStream trabajadoresConSusDardos = IntStream.range(0, trabajadores)
                 .map( numeroDeTrabajador -> dardosPorTrabajador ); // [250000, 250000, 250000, 250000 ]


        // Stream<T>  Colección de datos de tipo T que se pueden procesar mediante programación funcional
        // Stream<Integer> Colección de datos de tipo Integer que se pueden procesar mediante programación funcional
        // List<Integer>        Objetos de tipo integer ... mientras que un int es un primitivo... que a nivel de la JVM se procesa distinto
        // Caga en JAVA cuando crearon los Genéricos, que no se pueden usar con tipos primitivos
        // El trabajar con Objetos en lugar de sus correspondientes primitivos tiene un coste de memoria ENORME
        // Si voy a meter en una lista 100 cositas... me da igual... el sobrecoste
        // Pero si voy a meter 1000 millones de cositas... el sobrecoste es enorme
        // El los Streams (y el Spark en los RDD) hay subtipos especiales que permiten trabajar con tipos primitivos
        // Y que me dan mucha más eficiencia
        // Java en autom. hace mágia... y si quiero puedo hacer algo como:
        // List<Integer> listaDeNumeros = new ArrayList<>();
        // listaDeNumeros.add(5); // Aquí paso un tipo simple... pero....JAVA lo convierte automáticamente en su correspondiente objeto
                               // Eso es un proceso llamado AUTOBOXING... Y el destrozo en RAM es enorme

        double sumaEstimaciones = trabajadoresConSusDardos //parallelStream()                               //   [250000, 250000, 250000, 250000 ]
                .mapToDouble( CalcularPIStream::calcularPI )                                                //   DoubleStream [3.16, 3.14, 3.15 , 3.14]
                .reduce( Double::sum ).orElse(0.);                                                     //   A un reduce le pasamos un BiFunction<T,T,T>



        IntStream trabajadoresConSusDardos = IntStream.range(0, trabajadores)
                .map( numeroDeTrabajador -> dardosPorTrabajador ); // [250000, 250000, 250000, 250000 ]

*/
        final int totalDeDardos = 100 * 1000 * 1000;
        long numeroDeDardosDentro = Arrays.asList(totalDeDardos).stream()
                .flatMapToInt((numeroDeDardos) -> IntStream.range(0, numeroDeDardos))
                //.parallel()   // 2 opciones
                // Como mucho, tantos como Cores tenga
                // Pero limitado por el número de objetos que tenga el Stream
                .mapToDouble(numeroDeDardo -> Math.sqrt( Math.pow(Math.random(),2) + Math.pow(Math.random(),2)))// De cada dardo calculo la distancia al centro // double distanciaAlCentro
                .filter( distanciaAlCentro -> distanciaAlCentro <= 1)                                             // Me quedo solo con los que están dentro del círculo   // IF
                .count();                                                                                        // Los cuento  // incremental

        double estimacionDePI = 4. * numeroDeDardosDentro  / totalDeDardos;                                      // Estimo PI

        System.out.println("PI es aproximadamente " + estimacionDePI);
    }

}
