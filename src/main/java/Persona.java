import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Persona {
    private String nombre;
    private String apellido;
    private int edad;
    private String dni;
    private String email;

    public static Persona crearPersona(String nombre, String apellido, int edad, String dni, String email){
        Persona p = new Persona();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setEdad(edad);
        p.setDni(dni);
        p.setEmail(email);
        return p;
    }

    public boolean validarEmail(){
        return this.email.matches("^[a-zA-Z0-9.!#$%&'*+=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }
}
// Convertir en un POJO -> BEAN
// Una clase que tenga los getter y los setter = GRAN CAGADA DE LA SINTAXIS DE JAVA