 Tenemos datos de personas... que nos llegan en un fichero (JSON)
 Tenemos datos de cps.... en otro ficherito (CSV)
 
Leemos las personas y:
1- Validamos el DNI
    Las que no -> A un fichero parquet de personas con DNI incorrecto
2- Para las que tengan DNI guay... 
    Generamos un fichero parquet, con la información de las personas, enriquecida con el municipio y provincia (1)
    Si una persona con DNI Valido, tiene un CP que no existe... a un fichero parquet de personas con CP incorrecto (2)

Este ejemplo, además de practicar con las cositas de SparkSQL,
nos va a permitir trabajar con JOINS y ANTIJOINS

(1) personasDataset.join( cpDataset, "cp" )
(2) substract, para quitar del dataset de personas totales, las que tienen CP correcto (1)
