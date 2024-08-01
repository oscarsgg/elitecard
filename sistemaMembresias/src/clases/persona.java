package clases;
public class persona {
    private int numero;
    private String nombre;
    private String primer_AP;
    private String segundo_AP;
    private String correo;

    // Constructor por defecto
    public persona() {}

    // Constructor con conexión a la base de datos

    // Getters y Setters

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_AP() {
        return primer_AP;
    }

    public void setPrimer_AP(String primer_AP) {
        this.primer_AP = primer_AP;
    }

    public String getSegundo_AP() {
        return segundo_AP;
    }

    public void setSegundo_AP(String segundo_AP) {
        this.segundo_AP = segundo_AP;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Método para imprimir los datos de la persona
    public void imprimirDatos() {
        System.out.println("Número: " + numero);
        System.out.println("Nombre: " + nombre);
        System.out.println("Primer Apellido: " + primer_AP);
        System.out.println("Segundo Apellido: " + segundo_AP);
        System.out.println("Correo: " + correo);
    }
}
