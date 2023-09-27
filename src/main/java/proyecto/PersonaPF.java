package proyecto;

import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class PersonaPF {
    private String nombre;
    private String apellido;
    private int edad;
    private String dni;
    private String email;
    private String cp;

    public static PersonaPF crearPersona(String nombre, String apellido, int edad, String dni, String email){
        PersonaPF p = new PersonaPF();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setEdad(edad);
        p.setDni(dni);
        p.setEmail(email);
        return p;
    }
    private static final String LETRAS_DNI= "TRWAGMYFPDXBNJZSQVHLCKE";

    public boolean validarDNI(){
        return PersonaPF.validarDNI(this.dni);
    }


    public boolean validarEmail(){
        return this.email.matches("^[a-zA-Z0-9.!#$%&'*+=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }

    public static Boolean validarDNI(Object odni){
        String dni = odni.toString();
        boolean formatoValido= dni.matches("^[0-9]{1,8}[A-Za-z]$");
        if(!formatoValido) return false;
        String parteNumerica = dni.substring(0, dni.length() - 1);
        int resto = Integer.parseInt(parteNumerica) % 23; // Me quedo con el resto al dividir entre 23, que estará entre: 0-22
        char miLetra = dni.toUpperCase().charAt(dni.length() - 1);
        return LETRAS_DNI.charAt(resto) == miLetra;
    }
}
// Convertir en un POJO -> BEAN
// Una clase que tenga los getter y los setter = GRAN CAGADA DE LA SINTAXIS DE JAVA