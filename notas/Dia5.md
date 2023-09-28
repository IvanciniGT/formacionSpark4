

Tabla 1: Facturas
| id | fecha | clienteId | importe |

Tabla 2: Clientes
| id | fecha | cliente | importe |

ESCENARIO 1: ETL
BBDD_Producción                     ---->           DatawareHouse (Consulta) DESNORMALIZACION 100%
Facturas De los últimos 12 meses                    FacturasConClientes (Duplicas datos a cascoporro)
Facturas >- Clientes

Quiero un proceso que las facturas viejas me las lleve al Datawarehouse y las quite de la BBDD Producción
La query la tiro a la BBDD ... y que me dé ya 1 solo tabla con los datos que quiero


ESCENARIO 2: ETL

Carga facturas desde ficheros a mi BBDD Producción... dode tengo los clientes
                                        v
FACTURAS                       BBDD_Producción_Clientes

SPARK Es para cuando quiero un tratamiento fuerte de los datos eTl.

Amazon y tengo 500 servidores WEB que generan log -> Los quiero tratar para llevarlos a una BBDD centralizada
- Filtrar los datos (errores), que vienen en medio de textos

Leyendo los mensajes que se ponen en un chat, facebook... y quiero procesarlos para saber si son ofensivos
o si hablan bien de mi empresa

ETLs custom:
- COBOL
- SpringBatch
- Spark -> Puedo escalar los procesos por estar creados con programación MAP-REDUCE

ETL
ETLT
ELT

En los JOINS, dentro de SPARK no importa que la tabla 1 sea gigante y la tabla 2 pequeña
Pero si la tabla 1 es pequeña y la tabla 2 es gigante... entonces es mejor hacer un JOIN de la tabla 2 con la tabla 1
Y si tienes 2 tablas gigantes... entonces es mejor hacer un JOIN en otro sitio que no sea aquí
Que la tabla 2 me entre en memoria

TABLA 1 -> la distribuyo entre 500 máquinas... me da igual cómo sea de grande!
TABLA 2 -> la necesito entera en las 500 máquinas

a       11
b       33
c       44
d       55
---------------
e       11
f
g
h
i
j
k

------

Esto es proceso lo hemos pensado en modo BATCH
Llevarlos ahora a modo Streaming. Se simplifica mucho
Los joins se hacen con la tabla 1 muy pequeña
