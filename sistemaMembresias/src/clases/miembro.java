package clases;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class miembro extends persona{
    Scanner s = new Scanner(System.in);
    private String fechaRegistro;
    private String numTel;
    private int empleado;
    boolean exception = false;
    boolean bandera;
    boolean rsnull = false;
    LocalDate fecha;
    //conexion a la BD
    private conexion conexion;

    tarjetaMembresia adminTarj = new tarjetaMembresia(conexion);

    public miembro(conexion conexion) {
        this.conexion = conexion;
    }
    public void verRegistros() throws SQLException{
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM miembro");
        System.out.println("\n\t//////////// DATOS DE LOS MIEMBROS ////////////\n");
        while (rs.next()) {
            System.out.println("---------------------------------------------------------------");
            System.out.println("Numero: "+rs.getInt("numero"));
            System.out.println("Fecha de registro: "+rs.getDate("fechaRegistro"));
            System.out.println("Numero de telefono: "+rs.getString("numTel")+" ");
            System.out.println("Nombre: "+rs.getString("nombre")+ " "+rs.getString("primerApellido")+ " "+rs.getString("segundoApellido"));
            System.out.println("Correo: "+rs.getString("correo")+" ");
            System.out.println("Empleado que lo registro: "+rs.getInt("empleado"));

            System.out.println("---------------------------------------------------------------\n\n");
        }
    }

    public void verUnRegistros() throws SQLException{ 
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        do {
            try {
                System.out.println("Ingrese el numero del miembro del cual desea ver su informacion");
                setNumero(s.nextInt());
                exception = true;
            } catch (InputMismatchException e) {
                System.out.println("\n - - INGRESE UN NUMERO - - \n");
                s.nextLine();
            }
        } while (exception == false);
        ResultSet rs = st.executeQuery("SELECT * FROM miembro WHERE numero = '"+getNumero()+"' ");
        if (!rs.next()) {
            System.out.println("\nNO SE ENCONTRO UN EMPLEADO CON ESE NUMERO\n");
        } else {
            System.out.println("\n\t//////////// DATOS DE LOS MIEMBROS ////////////\n");  
            do {
                System.out.println("---------------------------------------------------------------");
                System.out.println("Numero: "+rs.getInt("numero"));
                System.out.println("Fecha de registro: "+rs.getDate("fechaRegistro"));
                System.out.println("Numero de telefono: "+rs.getString("numTel")+" ");
                System.out.println("Nombre: "+rs.getString("nombre")+ " "+rs.getString("primerApellido")+ " "+rs.getString("segundoApellido"));
                System.out.println("Correo: "+rs.getString("correo")+" ");
                System.out.println("Empleado que lo registro: "+rs.getInt("empleado"));

                System.out.println("---------------------------------------------------------------\n\n");
            } while (rs.next()); 
        }
    }
    
    public void agregarMiemb() throws SQLException{
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println("- - - - AÑADIR MIEMBRO - - - - - ");
        System.out.println("Ingrese el nombre de pila: ");
        setNombre(s.nextLine());
        System.out.println("Ingrese el primer apellido: ");
        setPrimer_AP(s.nextLine());
        System.out.println("Ingrese el segundo apellido: ");
        setSegundo_AP(s.nextLine());
        System.out.println("Ingrese el correo: ");
        setCorreo(s.nextLine());
        do {
            try {
                System.out.println("Ingrese el número de teléfono: ");
                numTel = s.next();  
                s.nextLine(); // Limpiar el buffer del Scanner
        
                // Validar que la longitud del número de teléfono sea de al menos 10 dígitos
                if (numTel.length() < 10) {
                    System.out.println("El número de teléfono debe tener al menos 10 dígitos. Inténtalo de nuevo.");
                    exception = false;
                } else {
                    // Validar que todos los caracteres sean dígitos
                    if (numTel.matches("\\d+")) {
                        exception = true;
                    } else {
                        System.out.println("El número de teléfono solo debe contener dígitos. Inténtalo de nuevo.");
                        exception = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("\n - - INGRESE UN NÚMERO VÁLIDO - - \n");
                s.nextLine(); // Limpiar el buffer del Scanner
                exception = false;
            }
        } while (!exception);
        do {
            do {
                try {
                    System.out.println("Ingrese el numero del empleado que lo afilio: ");
                    empleado = s.nextInt();
                    exception = true;
                } catch (InputMismatchException e) {
                    System.out.println("\n - - INGRESE UN NUMERO - - \n");
                    s.nextLine();
                    exception = false;
                }
            } while (!exception);
            try {
                ResultSet rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + empleado + "'");
                if (!rs.next()) {
                    System.out.println("\nNO EXISTE UN EMPLEADO CON ESE NÚMERO \nFavor de ingresar un número válido\n");
                    rsnull = true;
                } else {
                    rsnull = false; // Número válido encontrado
                }
            } catch (SQLException e) {
                System.out.println("Error al consultar la base de datos: " + e.getMessage());
                rsnull = true; // Para asegurar que se vuelva a pedir el número si ocurre un error en la consulta
            }
        } while (rsnull);
        s.nextLine();
        LocalDate dateNow = LocalDate.now();
        Date fechaRegistro = Date.valueOf(dateNow);
        String comando = "INSERT INTO miembro(fechaRegistro, numTel, nombre, primerApellido, segundoApellido, correo, empleado) values ('" +fechaRegistro+ "','" + numTel + "','"+getNombre()+"','"+getPrimer_AP()+"','"+getSegundo_AP()+"','"+getCorreo()+"', '"+empleado+"')";
        st.executeUpdate(comando);
        
        System.out.println(" - - - MIEMBRO REGISTRADO DE FORMA EXITOSA - - - ");
        comando = "SELECT LAST_INSERT_ID() AS numero";
                ResultSet rs = st.executeQuery(comando);
                int numeroCliente;
                if (rs.next()) {
                    System.out.println("---------------------------------------");
                    System.out.println("TICKET DE COMPRA");
                    numeroCliente = rs.getInt("numero");
                    System.out.println("Número de cliente asignado: " + numeroCliente);
                    System.out.println("---------------------------------------");
                }
        /*
        System.out.println("Desea adquirir una membresia para el miembro registrado?");
        System.out.println("a. Si");
        System.out.println("b. No");
        String respuesta = s.nextLine();
        if (respuesta.toLowerCase(null).equals("a")){
            adminTarj.asignarTarjeta();
        }
         */

    }

    public void updateMiembro() throws SQLException{
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println("- - - - ACTUALIZAR MIEMBRO - - - - - ");
        do {
            try {
                System.out.println("Ingrese el numero del empleado a actualizar: ");
                setNumero(s.nextInt());
                exception = true;
            } catch (InputMismatchException e) {
                System.out.println("FAVOR DE INGRESAR UN NUMERO");
                s.nextLine();
            }
        } while (!exception);
        ResultSet rs = st.executeQuery("SELECT * FROM miembro WHERE numero = '"+getNumero()+"' ");
        if(!rs.next()){
            System.out.println("\nNO SE ENCONTRO UN EMPLEADO CON ESE NUMERO\n");
        }else{
            s.nextLine();
            System.out.println("Ingrese el nombre de pila: ");
            setNombre(s.nextLine());
            System.out.println("Ingrese el primer apellido: ");
            setPrimer_AP(s.nextLine());
            System.out.println("Ingrese el segundo apellido: ");
            setSegundo_AP(s.nextLine());
            System.out.println("Ingrese el correo: ");
            setCorreo(s.nextLine());
            do {
                try {
                    System.out.println("Ingrese el número de teléfono: ");
                    numTel = s.next();  
                    s.nextLine(); // Limpiar el buffer del Scanner
            
                    // Validar que la longitud del número de teléfono sea de al menos 10 dígitos
                    if (numTel.length() < 10) {
                        System.out.println("El número de teléfono debe tener al menos 10 dígitos. Inténtalo de nuevo.");
                        exception = false;
                    } else {
                        // Validar que todos los caracteres sean dígitos
                        if (numTel.matches("\\d+")) {
                            exception = true;
                        } else {
                            System.out.println("El número de teléfono solo debe contener dígitos. Inténtalo de nuevo.");
                            exception = false;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("\n - - INGRESE UN NÚMERO VÁLIDO - - \n");
                    s.nextLine(); // Limpiar el buffer del Scanner
                    exception = false;
                }
            } while (!exception);
            do {
                do {
                    try {
                        System.out.println("Ingrese el numero del empleado que lo afilio: ");
                        empleado = s.nextInt();
                        exception = true;
                    } catch (InputMismatchException e) {
                        System.out.println("\n - - INGRESE UN NUMERO - - \n");
                        s.nextLine();
                        exception = false;
                    }
                } while (!exception);
                try {
                    rs = st.executeQuery("SELECT * FROM empleado WHERE numero = '" + empleado + "'");
                    if (!rs.next()) {
                        System.out.println("\nNO EXISTE UN EMPLEADO CON ESE NÚMERO \nFavor de ingresar un número válido\n");
                        rsnull = true;
                    } else {
                        rsnull = false; // Número válido encontrado
                    }
                } catch (SQLException e) {
                    System.out.println("Error al consultar la base de datos: " + e.getMessage());
                    rsnull = true; // Para asegurar que se vuelva a pedir el número si ocurre un error en la consulta
                }
            } while (rsnull);
            s.nextLine();
            do {
                try {
                    System.out.println("Introduce la fecha (formato yyyy-MM-dd): ");
                    fechaRegistro = s.nextLine();
                    // Convertir la fecha ingresada por el usuario a LocalDate
                    fecha = LocalDate.parse(fechaRegistro, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    exception = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto. Por favor usa el formato yyyy-MM-dd.");
                    exception = false;
                }
            } while (!exception);
            fecha = LocalDate.parse(fechaRegistro, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //String comando = "INSERT INTO miembro(fechaRegistro, numTel, nombre, primerApellido, segundoApellido, correo, empleado) values ('" +fecha+ "','" + numTel + "','"+getNombre()+"','"+getPrimer_AP()+"','"+getSegundo_AP()+"','"+getCorreo()+"', '"+empleado+"')";
            String comando = "UPDATE miembro SET " +
                            "fechaRegistro = '" + fecha + "', " +
                            "numTel = '" + numTel + "', " +
                            "nombre = '" + getNombre() + "', " +
                            "primerApellido = '" + getPrimer_AP() + "', " +
                            "segundoApellido = '" + getSegundo_AP() + "', " +
                            "correo = '" + getCorreo() + "', " +
                            "empleado = '" + empleado + "' " +
                            "WHERE numero = " + getNumero();
            st.executeUpdate(comando);
            System.out.println(" - - - MIEMBRO REGISTRADO DE FORMA EXITOSA - - - ");
            }
    }

    public void deleteMiemb() throws SQLException{
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println(colores.ROJO + "- - - - ELIMINAR MIEMBRO - - - - - " + colores.AZUL);
        do {
            try {
                System.out.println("Ingrese el numero del miembro a eliminar: ");
                setNumero(s.nextInt());
                exception = true;
            } catch (InputMismatchException e) {
                System.out.println("FAVOR DE INGRESAR UN NUMERO");
                s.nextLine();
            }
        } while (!exception);
        ResultSet rs = st.executeQuery("SELECT * FROM miembro WHERE numero = '"+getNumero()+"' ");
        if(!rs.next()){
            System.out.println("\nNO SE ENCONTRO UN EMPLEADO CON ESE NUMERO\n");
            s.nextLine();
        }else{
            System.out.println("- - - - ELIMINAR MIEMBRO - - - - - ");
            System.out.println("Ingrese el numero de Miembro a eliminar.");
            setNumero(s.nextInt());
            String comando = "DELETE FROM miembro WHERE numero = '"+getNumero()+"'";
            st.executeUpdate(comando);
            System.out.println(" - - - MIEMBRO ELIMINADO DE FORMA EXITOSA - - - ");
        }
    }

    // CONSULTA #1
    public void mostrarMembCliente() throws SQLException {
        // Comando para conectar a la BD
        Connection con = conexion.conectar();
        Statement st = con.createStatement();

        System.out.println("Ingresa el numero del miembro");
                do {
                    do {
                        try {
                            bandera = false;
                            setNumero(s.nextInt());
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                            s.next();
                            bandera = true;
                        }
                    } while (bandera);
                    bandera = verificarMiembro(getNumero());
                } while (bandera == false);

        // Comando para realizar CONSULTAS
        ResultSet rs = st.executeQuery("SELECT m.numero AS NumeroMiembro, "
        + "CONCAT(m.nombre, ' ', m.primerApellido, ' ', IFNULL(CONCAT(m.segundoApellido, ' '), ' ')) AS Miembro, "
        + "t.numero AS NumeroMemb, "
        + "DATE_FORMAT(t.fechaCreacion, \"%d/%m/%Y\") AS fechaInicio, "
        + "DATE_FORMAT(t.fechaExpiracion, \"%d/%m/%Y\") AS fechaFinal, "
        + "n.descripcion AS descripcion "
        + "FROM miembro AS m "
        + "INNER JOIN tarjetamemb AS t ON t.miembro = m.numero "
        + "INNER JOIN niveltarjeta AS n ON t.niveltarjeta = n.codigo "
        + "where m.numero="+getNumero()+"  ");

        System.out.println("----------------------------------------");
        if (rs.next()) {
            System.out.println("Número del cliente: " + rs.getInt("NumeroMiembro"));
            System.out.println("Nombre del cliente: " + rs.getString("Miembro"));
            System.out.println("\n\t//////////// TARJETAS ASOCIADAS ////////////\n");
            do {
                System.out.println("  - Número de membresía: " + rs.getInt("NumeroMemb"));
                System.out.println("    Fecha de inicio: " + rs.getString("FechaInicio"));
                System.out.println("    Fecha final: " + rs.getString("FechaFinal"));
                System.out.println("    Descripción del nivel de membresía: " + rs.getString("descripcion"));
                System.out.println(" ");
            } while (rs.next());
        } else {
            System.out.println("No se encontraron resultados para el cliente " /*+ numeroCliente*/);
        }
        System.out.println("----------------------------------------");

        rs.close();
        st.close();
        con.close();   
    }

    //Codigo para verificar que el miembro ingresado exista. Se utiliza dentro del metodo registrarCompra
    public boolean verificarMiembro(int miembro){
        try {
            Connection con = conexion.conectar();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM miembro WHERE numero =?");
            pstmt.setInt(1, miembro);
            ResultSet resultado = pstmt.executeQuery();

            if (resultado.next()) {
                System.out.println("Cliente encontrado!");
                return true;
            } else {
                System.out.println("Error: El número de miembro no existe, ingreselo de nuevo");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
            return false;
        }
    }
}