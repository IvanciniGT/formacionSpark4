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
