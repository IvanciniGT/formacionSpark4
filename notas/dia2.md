# Tipos de funciones dentro del paquete java.util.function.*;

- Function<T,R>      Funciones que reciben un objeto (de tipo T) y devuelven otro (de tipo R)... doble
- Consumer<T>        Funciones que reciben un objeto (de tipo T) y no devuelven nada (void)... set
- Predicate<T>       Funciones que reciben un objeto (de tipo T) y devuelven un boolean... is, has
- Supplier<T>        Funciones que no reciben nada y devuelven un objeto (de tipo T)... getter

- BiFunction<T,U,R>  Funciones que reciben dos objetos (de tipo T y U) y devuelven otro (de tipo R)... doble
- BiConsumer<T,U>    Funciones que reciben dos objetos (de tipo T y U) y no devuelven nada (void)... set
- IntFunction<R>     Funciones que reciben un int y devuelven un objeto (de tipo R)... doble
- IntConsumer        Funciones que reciben un int y no devuelven nada (void)... set
---


# Ejercicio 1: Trending Topics de Twitter(X)



Colección inicial de Tweets (textos)

    "En la playa con mis amigos!!! (#goodVibes)"                            String      
    "Amig@s de mierda#goodvibes #summerLove"
    "Amigos guays #goodvibes#friendsLove"
    "Amigos de mierda que te cagas #badvibes#CacaFriends"

Colección final

    goodvibes
    goodvibes
    summerlove
    goodvibes
    friendslove
    badvibes
    cacafriends         # Éste hay que eliminarlo

"caca", "culo", "pedo", "pis"

Todo el trabajo lo queremos con 1 sola línea de código.

PASO 0:     filter  Por los que contienen # (hashtag) 
PASO 1:     map             .replaceAll("#"," #")                           

    "En la playa con mis amigos!!! (#goodVibes)"                            String
    "Amig@s de mierda #goodvibes #summerLove"
    "Amigos guays  #goodvibes  #friendsLove"
    "Amigos de mierda que te cagas  #badvibes #CacaFriends"

PASO 2:     map             .split("[ ,.+()_@|&%$¿?¡!-]+")

    ["En", "la", "playa", "con", "mis", "amigos", "#goodVibes"]             String[]
    ["Amig@s", "de", "mierda", "#goodvibes", "#summerLove"]
    ["Amigos", "guays", "#goodvibes", "#friendsLove"]
    ["Amigos", "de", "mierda", "que", "te", "cagas", "#badvibes", "#CacaFriends"]

PASO 3:     flatMap         Cada Array lo transforme en un Stream

    "En"
    "la"
    "playa"
    "con"
    "mis"
    "amigos"
    "#goodVibes"
    "Amig@s"
    "de"
    "mierda"
    "#goodvibes"
    "#summerLove"
    "Amigos"
    "guays"
    "#goodvibes"
    "#friendsLove"
    "Amigos"
    "de"
    "mierda"
    "que"
    "te"
    "cagas"
    "#badvibes"
    "#CacaFriends"

PASO 4: filter          Por los que empiezan por #

    "#goodVibes"
    "#goodvibes"
    "#summerLove"
    "#goodvibes"
    "#friendsLove"
    "#badvibes"
    "#CacaFriends"

PASO 5: Quito las almohadillas      map         .substring(1)

    "goodVibes"
    "goodvibes"
    "summerLove"
    "goodvibes"
    "friendsLove"
    "badvibes"
    "CacaFriends"

PASO 6: A minúsculas         map         .toLowerCase()

    "goodvibes"
    "goodvibes"
    "summerlove"
    "goodvibes"
    "friendslove"
    "badvibes"
    "cacafriends"

PASO 7: Quito las que tengan ruina por ahí dentro!

    List<String prohibidas = Arrays.asList("caca", "culo", "pedo", "pis");

hashtags.filter(  FUNCION QUE DADO UN HASHTAG, devuelve true si no contiene ninguna de las palabras prohibidas)

    "goodvibes"
    "goodvibes"
    "summerlove"
    "goodvibes"
    "friendslove"
    "badvibes"

// FUNCION QUE DADO UN HASHTAG, devuelve true si no contiene ninguna de las palabras prohibidas

    public boolean filtradorDeHashtagRuinosos(String hashtag){
        /*
        for(String prohibida: prohibidas){
            if(hashtag.contains(prohibida)){
                return false;
            }
        }
        return true;
        */

        /*
        prohibidas.filter( palabraRuinosa -> hashtag.contains(palabraRuinosa) )     // Se queda, de entre las palabras prohibidas, con las que están contenidas en el hashtag
            // "#cacafriends"
            // "caca", "culo", "pedo", "pis" -> "caca"
            // true    false   false   false
                                                   .count() == 0; // Mi hashtag no tiene palabras guarrindongas
        */
        // return prohibidas.filter( palabraRuinosa -> hashtag.contains(palabraRuinosa) ).count() == 0;

        return prohibidas.noneMatch(palabraRuinosa -> hashtag.contains(palabraRuinosa))
    } 

---

# Bigdata

Llevamos décadas trabajando con datos(más de 1 siglo)... eso no es nuevo.
El problema es que hoy en día, debido a:
- Un gran volumen de datos
- A la velocidad en la que se generan los datos (y su ventana de disponibilidad)
- A la complejidad de los mismo (videos, fotos, sonido, paginas web)
  La técnicas tradicionales no me sirven en algunos casos.

Ejemplo 1: Lista de cosas

Compra: 200 cosas: Bloc de notas: Excel
50000 cosas: MsAccess
500.000 cosas: MySQL / MariaDB
5.000.000 cosas: Ms SQL Server
100.000.000 cosas: Oracle / exadata
100.000.000.000: ???

Ejemplo 2: Tengo un amigo de un primo de un sobrino que descargó una peli por internet
Ocupa 5 Gbs.
Tengo un USB recién comprado de 16Gbs ... vacio... limpio de polvo y paja. Me entra?
Depende del tipo de formato (sistema de archivos /filesystem) fat16/fat32 -> Archivos limitados a 2Gbs
Otra cosa es que tenga aquello formateado en NTFS.. Ahí si.
Y ... si quiero guardar un archivo de 5Pb? Me sirve el NTFS? Ya no.

Ejemplo 3: Clash royale
2v2
Arqueras
Dragón de fuego
Mago de hielo
Tengo una app en un teléfono. Y el pollo hace un movimiento: ese mov tiene que llegar a los otros 3 teléfonos. 3 mensajes
Si  hago 2 mov x seg = 6 mensajes que hay que transmitir
Somos 4 jugando: 6x4 = 24 x 50.000 partidas = 1.200.000 mensajes por segundo

Bigdata es un conjunto de técnicas que aplicamos cuando las técnicas tradicionales fallan, no me sirven ya.

Los primeros en encontrarse con estos problemas fueron los de GOOGLE.
- Sistema de archivos propio GFS
- Estructura / Modelo de programación Map-Reduce

Hicieron un software implementando esto: BigTable de Google

Sale una implementación de esa forma de trabajo, en un producto llamado Hadoop que se distribuye por la fundación Apache.
Hadoop ofrece 3 cosas:
- Su propio sistema de archivos: DISTRIBUIDO: HDFS
- Su propia implementación de MAP-REDUCE
- Un componente llamado YARN

Básicamente es el equivalente a un SO distribuido.

Cuando trabajamos en el mundo BIGDATA, en lugar de tener una máquina enorme, tenemos muchas mierda-maquinas (i7. 16 Gbs de RAM) x 200-6000
Y las pongo a trabajar como si fueran 1 sola.
De todas esas máquinas 1 es la maestra, la que organiza.
Yo le pido un trabajo:
- Guarda un archivo. El archivo se parte en trozos (60Mbs) y cada trozo se guarda en al menos 3 computadoras diferentes. (HDFS)

        1234|5678

        1234 -> 3 máquinas
        5678 -> 3 máquinas

        Qué me ofrece esto? Poder guardar archivos enormes: Los hdd de 200 máquinas como si fuera un único HDD
                            Rendimiento. Tengo 200 sitios a los que escribir o de donde leer simultaneamenta la información
- Procesa una coleccion de Tweets
  Recibe 1.000.000 tweets y los parte en trozos de 20.000 tweets -> Cada trozo a una máquina... junto con el código que deje ejecutar sobre cada trozo. MAP REDUCE

Además Hadoop tiene un programa que monitoriza los nodos del cluster, para asegurar que todos están funcionando.. Detectar problemas, etc: YARN

### Situaciones

1. La implementación MapReduce que lleva por defecto Hadoop no es nada buena... es muy ineficiente: Ya que todo movimiento de datos entre máquinas SIEMPRE PASA PRIMERO por HDD
2. Hadoop per se... es bastante inútil.
   La gracia es sobre ese "SO" poder montar apps que aporten valor.

Hay todo un ecosistema de apps para correr sobre Hadoop, en clusters de maquinas-de-mierda.
- BBDD: Cassandra, HBase, Hive
- Herramientas para transformar y procesar datos:
    - Spark(Se parten los datos y el trabajo se hace sobre distintos nodos(cada uno su parte... y todos el mismo trabajo))
        - Paralelizo los datos >>>> ACORDAROS DE ESTA FRASE !!!!!!!
    - Storm: Los datos viajan de una máquina a otra... Cada máquina hace una parte del trabajo.
        - Paralelizo el trabajo
- Análisis de datos: Mahout

# Cluster Hadoop (bigdata)

# Spark

Framework escrito en Scala (aunque se puede usar también desde: JAVA, PYTHON y R) para el procesamiento de datos...
dentro de un cluster HADOOP (BIGDATA) mediante un modelo de programación MAP-REDUCE.
Spark me da una reimplementación del MAP-REDUCE de Hadoop que trabaja en RAM (evitando los problemas de rendimiento que tenía al usar el MAP-REDUCE de hadoop)

El uso principal de Spark es montar ETLs... y variantes: TETL ELT

ETLs: Procesos de Extracción / Transformación  / Carga
Me quiero llevar los datos de unos usuarios que me mandan todas las noches en CSVs a Mi BBDD  (SCRIPT-ETL)

Alimentar un DatawareHouse
BBDD Producción -> ETL -> Datawarehouse (BBDD)

## Spark tiene dentro 3 librerías (antes 4)

- Spark core, que es tooodo lo que os he contado
  Pero... el Spark core es duro de manejar... bastante (MAP-REDUCE). 
  Poca gente que sea capaz de escribir ese tipo de código. Mucha formación y mucha experiencia
- Spark Streaming: Que nos permite montar procesos no BATCH (eso es Spark CORE) sino procesos que quedan corriendo en un cluster
  procesando datos en tiempo real. Esta librería esta DEPRECATED ahora mismo (Solo queda legacy)
  Toda la funcionalidad que tenía se ha llevado a SparkSQL vvv
- SparkSQL, que ofrece una reimplementación de la librería SparkCore para trabajar con otra sintaxis diferente a la de MapReduce... inspirada en SQL
  Que en general es más amigable para los informáticos. MENOS FORMACION y MENOS EXPERIENCIA en su uso
- SparkML. Ofrece algunos algoritmos de MachineLearning(cluster, arboles de decisión... muy poco de redes neuronales...) que están implementadas haciendo uso de SparkCore
  DE ESTO NO VAMOS A VER NADA EN LA FORMACION

## Cómo se usa esto en la realidad

### Escenario típico 1

Proceso batch que corre todas las noches a la 0:00: ETLs... simples sobre conjuntos de datos medianos: 100k datos o 1 Millón de datos

Una ventaja de la arquitectura BigData es la ESCALABILIDAD.:
Dia n   : 1M de datos  16 cores y 128 Gbs de ram
Dia n+1 : 10 Millones de datos
Dia n+2: 100k datos 4 cores y 32 Gbs de ram
Dia n+3 : 20 Millones
Además por el día no lo uso. Como compre máquina... estoy tirando el dinero durante el día.

Tenemos un cluster.. y le pongo y quito máquinas según necesite <- CLOUDs (GCP, AWS)

Por un lado:
- El almacenamiento en un CLOUD se contrata de forma independiente a las máquinas
  Máquina - Volumen principal con EL SO y software
  Volumen externo de almacenamiento: 15 Tbs S3
  (Hadoop además de soporte HDFS hoy en día tiene soporte S3)

### Escenario típico 2

Hay veces que SI tengo volumenes de datos más grandes.. y además continuos en el tiempo:
Streaming: Voy a ir procesando datos, según se reciben... Esto es una mentira en Spark. Realmente lo que hace Spark es estar ejecutando el programa una y otra vez
con los datos que se reciben en una ventana de tiempo: Un montón de mini-batch

        De donde saco datos?
                Ficheros (linux tail -f) POCO HABITUAL
                Leer de una cola de mensajería (KAFKA)

        tweets -> Se deja en un Sistema de mensajería:
                        En una COLA (KAFKA)     <- Un programa que lo publica en mi perfil
                                                <- Un programa que inspecciona el mensaje.. para ver que no haya contenido ofensivo (Objetivamente... XDXDXD)
                                                <- Un programa que preprocesa los hashtags (según llegan mensajes a la cola, los va procesando)
                                                        |       STREAMING
                                                        v
                                                        OTRA COLA (hashtags) <- Otro programa que va leyendo cada hora... lo que hay... y genera los trending topics
                                                                 

                                                <- Un programa que prprocesa las menciones
                                                <- Programa que manda las fotos a reconocimiento facial

Sistema de mensajería: Whatsapp
- Comunicaciones asíncronas: Una comunicación entre 2 intervinientes que se realiza a traves de un mediador (el sistema de mensajería)
  que garantiza la entrega de mensajes.


MERCADONA .. quiero pagar con tarjeta
TPV (computadora) programa de pago ----> Mensaje -> BANCO
Síncrona o asíncrona? Síncrona... ya que mercadona, para dejarme salir por la puerta necesita RESPUESTA en el momento...
ESPERANDO RESPUESTA!!!!
PEAJE ... quiero pagar con tarjeta (débito / crédito) NO PREPAGO                      
TPV (computadora) programa de pago ----> Mensaje -> Sistema de Mensajería -> En otro momento -> BANCO
Síncrona o asíncrona? Asíncrona.
El problema en este caso es: Qué pasa si el BANCO no está disponible (como mi madre)

2 formatos de fichero que se usan mucho en Bigdata:
- AVRO: orientado a filas
- PARQUET: Orientado a columnas

# MAVEN

Herramienta de automatización de tareas de un proyecto (JAVA):
- Compilar
- Empaquetar
- Ejecutar tests
- Generar documentación
- Mandar el código a SonarQube

Entre ellas... gestiona las dependencias