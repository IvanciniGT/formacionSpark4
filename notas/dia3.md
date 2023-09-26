
Colección inicial de estimaciones
.reduce( (est1 , est2 ) -> est1 + est2 )))
3.14 \                                          Nodo 1
3.16 /    6.28 \
3.12           / 9.40   \
-----------------------  > 18.77                                Nodo X
3.18           \ 9.37   /                       Nodo 2
3.15 \    6.29 /
3.17 /


---

#SparkSQL:
Lo que era un Stream<T> -> que en Spark core se llama RDD<T> -> en SparkSQL se llama Dataset<Row>
Los Row... tienen columnas

Un Dataset tiene una estructura de Tabla.. com si fuera una tabla de una base de datos relacional
De hecho... que tienen las tablas en las BBDD relacionales? Definir un Schema
    
    CREATE TABLE FEDERICO (
        nombre VARCHAR(100) NOT NULL,
        edad INT,
        altura DECIMAL(5,2)
    );
    Cada columna tiene un tipo de dato... y si puede ser nulo o no

Los Dataset<Row> lo que tienen son métodos muy similares (equivalentes) a los que tenemos en SQL
    .select(columnas)
    .filter(condiciones)
    .groupBy(columnas)
    .orderBy(columnas)
    .join(otroDataset, condiciones)
    .union(otroDataset)
    .distinct()
    .subtract(otroDataset) // AntiJoin

Cuando trabajamos con las columas... 2 formas... que no puedo mezclar... y que admiten todas esas funciones:
- O trabajo con nombres de columnas:
    .select("nombre", "edad")
- O trabajo con una función que nos da SparkSQL, llamada col("nombre de la columna"):
    .select( col("nombre").upper(), col("edad").sum(col("otra columna")) )
    .orderBy( col("nombre").desc(), col("edad").asc() )
    Y direis? pa que?
    Porque col, me devuelve un objeto de tipo Column... y ese objeto tiene un montón de funciones que me permiten hacer cosas con las columnas

---
En general, o leemos de un archivo... o de una BBDD o de una cola de mensajes
Lo que lea, va a tener embebido el formato de los datos
CSV: La primera fila son los nombres de las columnas
CSV... se lee todos los datos... y Spark identifica el tipo de cada columna
JSON: Spark lee todos los datos... y en JSON cada dato tiene su tipo
En las BBDD ni oss cuento... ultra especificado.
Si leo de un KAFKA:
      - JSON ... es un formato de texto
      - AVRO (es un formato que se usa mucho en BigData)... es un formato binario
    Columna con números guardada como texto:
    1234567890 ->> 10 bytes
    Cúanto ocupa esto como texto en el fichero? Depende del juego de caracteres (codificación de caracteres: ASCII, UTF-8, UTF-16, UTF-32, ISO-8859-1)
    UTF-8 es un estandar, cada caracter puede ocupar 1, 2 o 4 bytes... depende del caracter
    Unicode Transformation Format
    Unicode es un estándar que recoge todos los caracteres de la humanidad:  Casi 150.000 caracteres
    a-z , A-Z, 0-9 ,.- -> 1 bytes (256 caracteres)
    á,ô ñ ç -> 2 bytes
    Chinos... 4 bytes

    1234567890 Como número: 2 bytes: 265*256 = 65536
                            3 bytes: 256*256*256 = 16.777.216
                            4 bytes: 256*256*256*256 = 4.294.967.296
    En 4 bytes tengo el número.