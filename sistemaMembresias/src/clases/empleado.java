package clases;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class empleado extends persona {
    Scanner s = new Scanner(System.in);
    private String contrasenia;
    private String puesto;
    boolean exception = false;
    // conexión a la BD
    private conexion conexion;

    public empleado(conexion conexion) {
        this.conexion = conexion;
    }

    public void verRegistros() throws SQLException { // Método para ver todos los registros de empleados
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM empleado");
        System.out.println(colores.CIAN + "\n\t//////////// DATOS DE LOS EMPLEADOS ////////////\n" + colores.RESET);
        while (rs.next()) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Número del empleado: " + rs.getInt("numero"));
            System.out.println("Puesto del empleado: " + rs.getString("puesto"));
            System.out.println(colores.AMARILLO + " - - - DATOS PERSONALES - - - " + colores.RESET);
            System.out.println("Correo: " + rs.getString("correo") + " ");
            System.out.println("Contraseña: " + rs.getString("contrasenia") + " ");
            System.out.println("Nombre: " + rs.getString("nombre") + " " + rs.getString("primerApellido") + " " + rs.getString("segundoApellido"));
            System.out.println("---------------------------------------------------------------\n\n");
        }
    }

    public void verUnRegistros() throws SQLException { // Método para ver un registro de empleado
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        do {
            try {
                System.out.println("Ingrese el número del empleado del cual desea ver su información: ");
                setNumero(s.nextInt());
                exception = true;
            } catch (InputMismatchException e) {
                System.out.println(colores.ROJO + "\n - - INGRESE UN NÚMERO - - \n" + colores.RESET);
                s.nextLine();
            }
        } while (!exception);

        ResultSet rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + getNumero() + "' ");
        if (!rs.next()) {
            System.out.println(colores.ROJO + "\nNO SE ENCONTRÓ UN EMPLEADO CON ESE NÚMERO\n" + colores.RESET);
        } else {
            System.out.println(colores.CIAN + "\n\t//////////// DATOS DE LOS EMPLEADOS ////////////\n" + colores.RESET);
            do {
                System.out.println("---------------------------------------------------------------");
                System.out.println("Número del empleado: " + rs.getInt("numero"));
                System.out.println("Puesto del empleado: " + rs.getString("puesto"));
                System.out.println(colores.AMARILLO + " - - - DATOS PERSONALES - - - " + colores.RESET);
                System.out.println("Correo: " + rs.getString("correo") + " ");
                System.out.println("Contraseña: " + rs.getString("contrasenia") + " ");
                System.out.println("Nombre: " + rs.getString("nombre") + " " + rs.getString("primerApellido") + " " + rs.getString("segundoApellido"));
                System.out.println("---------------------------------------------------------------\n\n");
            } while (rs.next());
        }
    }

    public void agregarEmpld() throws SQLException {
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println(colores.CIAN + "- - - - AÑADIR EMPLEADO - - - - - " + colores.RESET);
        System.out.println("Ingrese el nombre de pila: ");
        setNombre(s.nextLine());
        System.out.println("Ingrese el primer apellido: ");
        setPrimer_AP(s.nextLine());
        System.out.println("Ingrese el segundo apellido: ");
        setSegundo_AP(s.nextLine());
        System.out.println("Ingrese el correo: ");
        setCorreo(s.nextLine());
        System.out.println("Ingrese la contraseña: ");
        contrasenia = s.nextLine();
        do {
            do {
                System.out.println("Ingrese el puesto: ");
                puesto = s.nextLine();
                exception = puesto.length() > 3;
                if (exception) {
                    System.out.println(colores.ROJO + "Error: El puesto debe tener menos de 3 caracteres." + colores.RESET);
                }
            } while (exception);
            ResultSet rs = st.executeQuery("SELECT * FROM puesto WHERE codigo = '" + puesto.toUpperCase() + "' ");
            if (!rs.next()) {
                System.out.println(colores.ROJO + "\nCÓDIGO DE PUESTO INCORRECTO\n" + colores.RESET);
                exception = true;
            }
        } while (exception);
        String comando = "INSERT INTO empleado(correo, contrasenia, nombre, primerApellido, segundoApellido, puesto) values ('" + getCorreo() + "','" + contrasenia + "','" + getNombre() + "','" + getPrimer_AP() + "','" + getSegundo_AP() + "','" + puesto + "')";
        st.executeUpdate(comando);

        System.out.println(colores.VERDE + " - - - EMPLEADO REGISTRADO DE FORMA EXITOSA - - - " + colores.RESET);
    }

    public void updateEmpld() throws SQLException {
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println(colores.CIAN + "- - - - ACTUALIZAR EMPLEADO - - - - - " + colores.RESET);
        do {
            try {
                System.out.println("Ingrese el número del empleado a actualizar: ");
                setNumero(s.nextInt());
                exception = true;
            } catch (InputMismatchException e) {
                System.out.println(colores.ROJO + "FAVOR DE INGRESAR UN NÚMERO" + colores.RESET);
                s.nextLine();
            }
        } while (!exception);
        ResultSet rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + getNumero() + "' ");
        if (!rs.next()) {
            System.out.println(colores.ROJO + "\nNO SE ENCONTRÓ UN EMPLEADO CON ESE NÚMERO\n" + colores.RESET);
        } else {
            s.nextLine();
            System.out.println("Ingrese el nombre de pila: ");
            setNombre(s.nextLine());
            System.out.println("Ingrese el primer apellido: ");
            setPrimer_AP(s.nextLine());
            System.out.println("Ingrese el segundo apellido: ");
            setSegundo_AP(s.nextLine());
            System.out.println("Ingrese el correo: ");
            setCorreo(s.nextLine());
            System.out.println("Ingrese la contraseña: ");
            contrasenia = s.nextLine();
            do {
                do {
                    System.out.println("Ingrese el puesto: ");
                    puesto = s.nextLine();
                    exception = puesto.length() > 3;
                    if (exception) {
                        System.out.println(colores.ROJO + "Error: El puesto debe tener menos de 3 caracteres." + colores.RESET);
                    }
                } while (exception);
                rs = st.executeQuery("SELECT * FROM puesto WHERE codigo = '" + puesto.toUpperCase() + "' ");
                if (!rs.next()) {
                    System.out.println(colores.ROJO + "\nCÓDIGO DE PUESTO INCORRECTO\n" + colores.RESET);
                    exception = true;
                }
            } while (exception);
            String comando = "UPDATE empleado SET " +
                    "correo = '" + getCorreo() + "', " +
                    "contrasenia = '" + contrasenia + "', " +
                    "nombre = '" + getNombre() + "', " +
                    "primerApellido = '" + getPrimer_AP() + "', " +
                    "segundoApellido = '" + getSegundo_AP() + "', " +
                    "puesto = '" + puesto + "' " +
                    "WHERE numero = " + getNumero();
            st.executeUpdate(comando);
            System.out.println(colores.VERDE + " - - - ACTUALIZACIÓN EXITOSA - - - " + colores.RESET);
            setNumero(0);
        }
    }

    public void deleteEmpld() throws SQLException {
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println(colores.ROJO + "- - - - ELIMINAR EMPLEADO - - - - - " + colores.RESET);
        do {
            try {
                System.out.println("Ingrese el número del empleado a eliminar: ");
                setNumero(s.nextInt());
                exception = true;
            } catch (InputMismatchException e) {
                System.out.println(colores.ROJO + "FAVOR DE INGRESAR UN NÚMERO" + colores.RESET);
                s.nextLine();
                exception = false;
            }
        } while (!exception);
        ResultSet rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + getNumero() + "' ");
        if (!rs.next()) {
            System.out.println(colores.ROJO + "\nNO SE ENCONTRÓ UN EMPLEADO CON ESE NÚMERO\n" + colores.RESET);
            s.nextLine();
        } else {
        String comando = "DELETE FROM empleado WHERE numero = '" + getNumero() + "'";
        st.executeUpdate(comando);
        System.out.println(colores.VERDE + " - - - EMPLEADO ELIMINADO DE FORMA EXITOSA - - - " + colores.RESET);
        }
    }

    public void iniciarsesion() throws SQLException {
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        boolean rsnull;
        do {
            do {
                try {
                    System.out.println("Ingrese su número de empleado: ");
                    setNumero(s.nextInt());
                    exception = true;
                } catch (InputMismatchException e) {
                    System.out.println(colores.ROJO + "\n - - INGRESE UN NÚMERO - - \n" + colores.RESET);
                    s.nextLine();
                    exception = false;
                }
            } while (!exception);
            try {
                ResultSet rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + getNumero() + "'");
                if (!rs.next()) {
                    System.out.println(colores.ROJO + "\nNO EXISTE UN EMPLEADO CON ESE NÚMERO \nFavor de ingresar un número válido\n" + colores.RESET);
                    rsnull = true;
                } else {
                    rsnull = false; // Número válido encontrado
                }
            } catch (SQLException e) {
                System.out.println(colores.ROJO + "Error al consultar la base de datos: " + e.getMessage() + colores.RESET);
                rsnull = true; // Para asegurar que se vuelva a pedir el número si ocurre un error en la consulta
            }
        } while (rsnull);
        ResultSet rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + getNumero() + "' ");
        boolean passwrd = false;
        s.nextLine();
        do {
            System.out.println("Ingrese su contraseña: ");
            setContrasenia(s.nextLine());
            rs.beforeFirst(); // Mover el cursor al principio del ResultSet
            if (rs.next()) {
                String passwTemp = rs.getString("contrasenia");
                if (passwTemp.equals(getContrasenia())) {
                    passwrd = true;
                    setPuesto(rs.getString("puesto"));
                    System.out.println(colores.VERDE + "Inicio de sesión exitoso.\n" + colores.RESET);
                } else {
                    System.out.println(colores.ROJO + "Contraseña incorrecta. Intente de nuevo." + colores.RESET);
                }
            }
        } while (!passwrd);
        setPuesto(rs.getString("puesto"));
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getPuesto() {
        return puesto;
    }
}