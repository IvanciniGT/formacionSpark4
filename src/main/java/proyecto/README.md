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


12345678 LETRA
          ||
 % 23 -> LETRA

0	1	2	3	4	5	6	7	8	9	10	11
LETRA	T	R	W	A	G	M	Y	F	P	D	X	B
RESTO	12	13	14	15	16	17	18	19	20	21	22
LETRA	N	J	Z	S	Q	V	H	L	C	K	E

letrasDNI= "TRWAGMYFPDXBNJZSQVHLCKE"