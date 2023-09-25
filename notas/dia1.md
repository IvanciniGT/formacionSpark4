# BigData

# Apache Spark / Apache Storm

Herramienta para el procesamiento de datos en el mundo BigData.
Principalmente su uso es montar ETLs.

ETL: Extraer, Transformar y Cargar.
^
Se realiza mediante programación funcional. EXCLUSIVAMENTE

---

# Programación funcional

Es un paradigma de programación (es sólo una forma de expresar unos conceptos).

## Paradigmas de programación

- Programación imperativa:      Escribimos una serie de instrucciones que deben ejecutarse secuencialmente.
  A veces me interesa romper la secuencialidad: if, for, while, switch
- Programación procedural:      Cuando el lenguaje me da la opción de crear funciones (procedimientos, métodos, rutinas)
  Y me permite invocar a esas funciones posteriormente.
  Esto me interesa porque me permite:
  - Generar un código más limpio / organizado
  - Reutilizar código
- Programación funcional:       Cuando el lenguaje me permite que una variable referencie una función.
      Y posteriormente invocar esa función desde la variable.
      Para qué sirve.. o qué me permite?
      Desde el momento que puedo hacer esas cosas de arriba...
      - Puedo crear funciones que reciben funciones como parámetros  ** COMPLEJO
      - Puedo crear funciones que devuelven funciones como resultado ** COMPLEJO
      Y aquí es donde TODO se convierte en caos.
- Programación orientada a objetos:     Cuando el lenguaje me permite definir mis propios Tipos de Datos.
  - Con sus propiedades (atributos)
  - Con sus métodos (funciones)
  + Herencia, Polimorfismo, Sobreescribir métodos, etc.

                                        Tipos de datos      Propiedades                         Métodos
                                        String              secuencia de caracteres             length(), toUpperCase(), toLowerCase(), etc.
                                        Date                día, mes, año                       getDay(), getMonth(), getYear(), etc.
                                        List                secuencia de elementos              add(), remove(), get(), etc.
                                        Usuario             nombre, apellidos, edad, etc.       eresMayorDeEdad(), etc.

- Programación declarativa <<<< Spring, Ansible, Terraform, Kubernetes, Docker-compose

  Felipe, pon una silla debajo de la ventana.         <<<< Imperativa
  Felipe, debajo de la venta ha de haber una silla.   <<<< Declarativa

---

String texto = "hola";

- "hola"            Crear un objeto de tipo String con el contenido "hola" en Memoria RAM (= CUADERNO)
- String texto      Crear una variable que puede apuntar a objetos(datos) de tipo String (Variable = POST-IT)
- =                 Asignar la variable de la izquierda al valor de la derecha

texto = "Adios"
- "Adios"           Crear un objeto de tipo String con el contenido "Adios" en Memoria RAM (= CUADERNO)
  Dónde? En el mismo sitio que el anterior? En otro sitio? En otro sitio
  Y en ese momento tengo 2 objetos de tipo String en Memoria RAM. El primero con el valor "hola" y el segundo con el valor "Adios"
- texto             Arranca el postit
- =                 Asignar la variable de la izquierda al valor de la derecha. Pegas la variable al lado del "adios"
  Y el hola queda  huérfano. No hay ninguna variable que lo apunte.
  Se convierte en basura. Y el recolector de basura lo elimina de Memoria RAM.

Java es un lenguaje que hace un destructivo uso de la memoria RAM.
Eso es malo? No. Es bueno? No. Es lo que hay. FEATURE ! CARACTERÍSTICA del lenguaje.

```java
public class Operaciones {
    public static int doble(int numero) {
        return numero * 2;
    }
    public int triple(int numero) { // Definiendo una función
        return numero * 3;
    }
}

...

    String                      texto     = "hola";                 // STATEMENT: Sentencia, Enunciado = Oración, Frase
    // Esto es programación funcional. PUNTO PELOTA
    Function<Integer,Integer>   miFuncion = Operaciones::doble;     // El operador :: de Java 1.8, me permite referencia una función

    Operacion op = new Operaciones();
    Function<Integer,Integer>   miFuncion2 = op::triple;  

    System.out.println( miFuncion.apply(5) );    // 10
    // Esto es programación funcional. PUNTO PELOTA. NO HAY MAS

    // En Java 1.8 aparece un segundo operador: -> (flecha), que me permite usar expresiones lambda.
    // Qué es una expresión lambda? Es trozo de código que devuelve una función anónima creada dentro de un statement.
    // Qué es una expresión? Una expresión es trozo de código que devuelve un valor.
    int numero = 5 + 7;                                           // Otro STATEMENT
                /////    Esa parte, es una expresión

    Function<Integer,Integer> miFuncion3 = (int numero) -> {
                                                                return numero * 3;
                                                            };
    miFuncion3.apply(5);

    Function<Integer,Integer> miFuncion4 = (int numero) -> {    return numero * 3;   };
    Function<Integer,Integer> miFuncion5 = (numero) -> {    return numero * 3;   };
    Function<Integer,Integer> miFuncion6 = numero -> {    return numero * 3;   };
    Function<Integer,Integer> miFuncion6 = numero -> numero * 3;

```

En Java 1.8 aparece un paquete nuevo en el API de JAVA: java.util.function
Lleno de Interfaces. Interfaces que llamamos Funcionales. Porque nos permiten identificar funciones.

    Consumer<T>         Función/Método que recibe un parámetro de tipo T y no devuelve nada (void)      set
        .accept(T)
    Supplier<T>         Función/Método que no recibe nada y devuelve un parámetro de tipo T             get
        .get()
    Predicate<T>        Función/Método que recibe un parámetro de tipo T y devuelve un boolean          (is, has)
        .test(T)
    Function<T,R>       Función/Método que recibe un parámetro de tipo T y devuelve un parámetro de tipo R (doble)
        .apply(T)     
    Además de éstas hay otras 50 interfaces funcionales más:
        - BiConsumer<T,U>
        - BiFunction<T,U,R>
        - BiPredicate<T,U>
        - UnaryOperator<T>
        - BinaryOperator<T>
        - etc.

En Java 1.8 sale la clase Stream... pensada para trabajar con datos (es un equivalente a la clase List) mediante programación FUNCIONAL. EXCLUSIVAMENTE
En el curso vamos a jugar con esa clase Stream, ya que:
- Nos va a ayudar a entender la programación funcional
- Nos va a ayudar a entender el patrón de programación MAP-REDUCE, que es el que se usa en Apache Spark y Apache Storm.
- Tiene su clase EQUIVALENTE en Apache Spark, llamada JavaRDD (Resilient Distributed Dataset)

---

# Map-Reduce

Una forma de resolver programas de programación para procesar conjuntos de datos.

En Map Reduce, tenemos 2 tipos de funciones:
- Funciones de tipo MAP         Una función de tipo MAP es una función que al aplicarse sobre un conjunto de datos (STREAM, RDD)
  devuelve otro conjunto de datos (STREAM, RDD).
  Las funciones tipo MAP se ejecutan en modo LAZY (vago). Solo cuando el dato es requerido, se calculan.
- Funciones de tipo REDUCE      Una función de tipo REDUCE es una función que al aplicarse sobre un conjunto de datos (STREAM, RDD)
  devuelve algo que no es un conjunto de datos (STREAM, RDD).
  Las funciones tipo REDUCE se ejecutan en modo EAGER (ansioso). S
  e ejecutan en el momento que se definen... pero no solo ellas... todo lo que haya antes.. que va a hacer falta

Al trabajar con Map Reduce, lo normal es coger una colección de datos (Stream, RDD, Dataset), y aplicarles en cadena 57 operaciones MAP
Y acabar siempre con 1 operación REDUCE

## Funciones tipo MAP

Hay un HUEVO DE FUNCIONES TIPO MAP, entre ellas:
- map

  La función map me permite aplicar una FUNCION a una colección de datos, de forma que genero otra colección de datos que contiene el resultado de aplicar la FUNCION sobre los datos originales.

                                     doble(numero) -> numero * 2
  COLECCION ORIGINAL   -> map(     FUNCION(parametro)-> Devolver algo       )  ->  COLECCION DE DATOS GENERADA
    1                                                                                       2
    2                                                                                       4
    3                                                                                       6
    4                                                                                       8

  Resumiendo: ME PERMITE TRANSFORMAR UN CONJUNTO DE DATOS AL APLICARLES A CADA UNO UNA DETERMINA FUNCION

- flatMap
- filter
- distinct
- sorted
- (de hecho hay veces que encontramos funciones que en el nombre tienen la palabra REDUCE... y son de tipo map - reduceByKey)

## Funciones tipo REDUCE

Hay un HUEVO DE FUNCIONES TIPO REDUCE.
- reduce
- forEach
- collect
- count
- sum

---

COLECCION INICIAL
"Buenos días"                               [ "Buenos", "días" ]                                         Stream("Buenos","días")
"Buenas tardes"         =>> map(split)  =>> [ "Buenas", "tardes" ]            => map(Arrays.stream ) =>> Stream("Buenas","tardes")
"Buenas noches amigos"                      [ "Buenas", "noches", "amigos" ]                             Stream("Buenas","noches","amigos")


COLECCION INICIAL
"Buenos días"                               [ "Buenos", "días" ]                                             "Buenos"
"Buenas tardes"         =>> map(split)  =>> [ "Buenas", "tardes" ]            => flatMap(Arrays.stream ) =>> "días"
"Buenas noches amigos"                      [ "Buenas", "noches", "amigos" ]                                 "Buenas"
"tardes"
"Buenas"
"noches"
"amigos"


La función map devuelve una colección de datos, del mismo tamaño que la original.
La función .filter devuelve una colección de datos, de tamaño igual o menor que la original.
La función .flatMap devuelve una colección de datos, de tamaño npi. Más, igual o menos.
