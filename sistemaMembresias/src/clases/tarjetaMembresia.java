package clases;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import clases.*;
public class tarjetaMembresia {
    private int tarjetaID;
    private String motivo;
    private int duracion = 6;
    private int puntosActuales, puntosAcumulados, puntosGastados;
    private boolean estatus;
    private int empleado;
    private int miembro;
    private String nivelTarj;
    private pago metodosPago;//instancia de la clase pago
    private boolean bandera;
    private double dineroUsuario;
    private int numeroMembresia;
    private int numeroMembresiaAsignada;
    private Date fechaCreacion, fechaActiva, fechaExpiracion, nuevaFechaActiva;
    private int mes, anio;
    
    //Booleano para indicar si el pago se realizo con efectivo. Si es asi, se indica el valor del cambio
    private boolean pagoEfectivo;

    //Scanner
    Scanner r = new Scanner(System.in);

    //////////conexion base de datos
     conexion conexion;

    // Formato decimal para mostrar solo 2 decimales
    DecimalFormat df = new DecimalFormat("0.00");

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

    public tarjetaMembresia(conexion conexion){
        this.metodosPago = new pago();//inicializar la clase pago
        this.conexion=conexion;

        
    }/////////////////////
    public tarjetaMembresia(){}
        //SELECT 
        public void imprimirRegistros() throws SQLException {
            // Comando para conectar a la BD
               Connection con = conexion.conectar();
               Statement st = con.createStatement();
                // Comando para realizar CONSULTAS 
                  ResultSet rs = st.executeQuery("SELECT * FROM tarjetamemb  ");
                  System.out.println("\n\t//////////// Tarjetas de membresias ////////////\n");
                  while (rs.next()) {
                      
                        System.out.println("---------------------------------------------------------------");
                        System.out.println("Numero: "+rs.getInt("numero")+", duracion: "+ rs.getInt("duracion"));
                        System.out.println("Fecha creacion: " + rs.getString("fechaCreacion")+", Fecha activa: " + rs.getString("fechaActiva")+", Fecha Expiracion: "+rs.getString("fechaExpiracion"));
                        System.out.println("El estado de la tarjeta es: " + (rs.getBoolean("estatus") ? "activa":"inactiva"));
                        System.out.println("Puntos actuales: "+rs.getInt("puntosActuales")+", Puntos acumulados: "+ rs.getInt("puntosAcumulados"));
                        System.out.println("Numero de empleado que la asignó: "+rs.getInt("empleado"));
                        System.out.println("Numero del miembro: "+rs.getInt("miembro")+", Nivel de la tarjeta: "+rs.getString("nivelTarjeta"));
                        System.out.println("---------------------------------------------------------------\n\n");
                  }
                  rs.close();
                  st.close();
                  con.close();
           }
        ///////////////////////////////////////
    //SELECT de verificar
    public void verRegistro() throws SQLException{
        try{
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        System.out.println("Ingresa el numero de la membresia a consultar: ");
            do {
                do {
                    try {
                        bandera = false;
                        tarjetaID = r.nextInt();
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                        r.next();
                        bandera = true;
                    }
                } while (bandera);
                bandera = verificarMembresia(tarjetaID);
            } while (bandera == false);
        ResultSet rs = st.executeQuery("SELECT numero, duracion, fechaCreacion, fechaActiva, fechaExpiracion, estatus, puntosActuales, puntosAcumulados, empleado, miembro, nivelTarjeta FROM tarjetamemb where numero="+tarjetaID+"  ");
        //obtiene el boolean de la base de datos, lo envia a la variable que cree aqui y al imprimir le asigna un String dependiendo del true o false
        if(rs.next()){
            estatus = rs.getBoolean("estatus");
                      System.out.println("---------------------------------------------------------------");
                      System.out.println("Numero: "+rs.getInt("numero")+", duracion: "+ rs.getInt("duracion"));
                      System.out.println("Fecha creacion: " + rs.getString("fechaCreacion")+", Fecha activa: " + rs.getString("fechaActiva")+", Fecha Expiracion: "+rs.getString("fechaExpiracion"));
                      System.out.println("El estado de la tarjeta es: " + (rs.getBoolean("estatus") ? "activa":"inactiva"));
                      System.out.println("Puntos actuales: "+rs.getInt("puntosActuales")+", Puntos acumulados: "+ rs.getInt("puntosAcumulados"));
                      System.out.println("Numero de empleado que la asignó: "+rs.getInt("empleado"));
                      System.out.println("Numero del miembro: "+rs.getInt("miembro")+", Nivel de la tarjeta: "+rs.getString("nivelTarjeta"));
                      System.out.println("---------------------------------------------------------------\n\n");
        }else{ System.out.println("No se encontro la tarjeta con el numero proporcionado");}
        rs.close();
        st.close();
        con.close();
    }
    catch(SQLException e){
        System.err.println("Error al ejecutar la consulta: " + e.getMessage());
    }
    }

    //INSERT METODO LISTO-------------------------------------------
    public void asignarTarjeta() {
        try {
            Connection con = conexion.conectar();
            Statement st = con.createStatement();
            // Instanciar objeto metodos de pago
            double monto = 0.00;
            // Instanciar fechas
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaActive = fechaActual.plusYears(1);
            LocalDate fechaExp = fechaActual.plusYears(6);
            Date fechaCreacion = Date.valueOf(fechaActual);
            Date fechaActiva = Date.valueOf(fechaActive);
            Date fechaExpiracion = Date.valueOf(fechaExp);

            System.out.println("Ingresa tu numero de empleado: ");
            do {
                do {
                    try {
                        bandera = false;
                        empleado = r.nextInt();
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                        r.next();
                        bandera = true;
                    }
                } while (bandera);
                bandera = verificarEmpleado(empleado);
            } while (bandera == false);

            ResultSet rs = st.executeQuery("SELECT numero,correo,contrasenia,nombre,primerApellido,segundoApellido,puesto from empleado where numero="+empleado+"");
            if(rs.next()){
                System.out.println("Ingresa el numero del miembro");
                do {
                    do {
                        try {
                            bandera = false;
                            miembro = r.nextInt();
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                            r.next();
                            bandera = true;
                        }
                    } while (bandera);
                    bandera = verificarMiembro(miembro);
                } while (bandera == false);
                
                ResultSet rsm = st.executeQuery("SELECT  numero,fechaRegistro,numTel,nombre,primerApellido,segundoApellido,correo,empleado FROM miembro WHERE numero="+miembro+" ");
                if(rsm.next()){ 
                    nivelTarj = seleccionarNivel();

                    //Obtener el costo de la membresia que se va a asignar
                    rs = st.executeQuery("SELECT * FROM nivelTarjeta WHERE codigo = \"" +nivelTarj+"\"");
                    while (rs.next()){
                        monto = rs.getInt("costo");
                    }

                    System.out.println("El monto a pagar por la membresia es: "+monto+"$\n");

                    //Metodo de pago
                    System.out.println("/////////////////////////////");
                    System.out.println("Seleccione el metodo de pago:");
                    System.out.println("a. Efectivo \nb. Tarjeta \n *Seleccione otra opcion para cancelar la compra*");
                    System.out.println("/////////////////////////////");

                    String cond = r.nextLine().toLowerCase();
                    switch (cond) {
                        case "a":
                            dineroUsuario = metodosPago.pagarConEfectivo(monto);
                            bandera = true;
                            pagoEfectivo = true;
                            break;

                        case "b":
                            metodosPago.pagarConTarjeta(monto);
                            bandera = true;
                            pagoEfectivo = false;
                            break;
                    
                        default:
                            System.out.println("La venta ha sido cancelada");
                            bandera = false;
                            break;
                        }

                    if (bandera == true){
                        String comando = "INSERT INTO tarjetamemb (numero, duracion, fechaCreacion, fechaActiva, "
                                     +"fechaExpiracion, estatus, puntosActuales, puntosAcumulados, empleado, miembro, nivelTarjeta) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement ps = con.prepareStatement(comando);
                        ps.setInt(1, duracion);
                        ps.setDate(2, fechaCreacion);
                        ps.setDate(3, fechaActiva);
                        ps.setDate(4, fechaExpiracion);
                        ps.setBoolean(5, true);
                        ps.setInt(6, 0);
                        ps.setInt(7, 0);
                        ps.setInt(8, empleado);//falta que agregue el empleado
                        ps.setInt(9, miembro);
                        ps.setString(10, nivelTarj);
                        ps.executeUpdate();
                        System.out.println("Se asignó la tarjeta correctamente! \n");

                        //Codigo para imprimir los datos de la ultima venta realizada
        
                        comando = "SELECT LAST_INSERT_ID() AS numero";
                        rs = st.executeQuery(comando);
                        if (rs.next()) {
                            System.out.println("---------------------------------------");
                            System.out.println("  COMPROBANTE DE COMPRA DE TARJETA");
                            numeroMembresiaAsignada = rs.getInt("numero");
                        }
                
                        rs = st.executeQuery("select*from TARJETAMEMB WHERE numero = " +numeroMembresiaAsignada+"");
                        while (rs.next()) {
                            System.out.println("Numero de Tarjeta: " + numeroMembresiaAsignada);
                            nivelTarj = rs.getString("nivelTarjeta");
                            switch (nivelTarj) {
                                case "NV1":
                                    System.out.println(" - Tarjeta nivel basica  ");
                                    break;

                                case "NV2":
                                    System.out.println(" - Tarjeta nivel intermedio  ");
                                    break;
                                
                                case "NV3":
                                    System.out.println(" - Tarjeta nivel premium  ");
                                    break;
                            
                                default:
                                    break;
                            }
                            fechaCreacion = rs.getDate("fechaCreacion");
                            System.out.println("Fecha de compra de la tarjeta: " + fechaCreacion);
                            fechaExpiracion = rs.getDate("fechaExpiracion");
                            System.out.println("Fecha de compra: " + fechaExpiracion);
                            if (pagoEfectivo == false) {
                                System.out.println("Total pagado con tarjeta: "+df.format(monto));
                            } else {
                                System.out.println("Total a pagar: "+df.format(monto));
                                System.out.println("Efectivo: "+df.format(dineroUsuario));
                                System.out.println("Cambio: "+df.format(dineroUsuario - monto));
                                System.out.println("---------------------------------------\n");
                            }
                            ps.close();
                            }
                        }
                    st.close();
                    con.close();
                    }
                
            }else{System.out.println("No se encontro el empleado con el numero proporcionado");}
        } catch (SQLException e) {
            System.err.println("Error al ejecutar: " + e.getMessage());
        }
    }

    ///////////////////
    //UPDATE RENOVARTARJETA METODO INCOMPLETO---------------------------
    public void renovarTarjeta() throws SQLException {
        try {
            Connection con = conexion.conectar();
            Statement st = con.createStatement();
            ResultSet rs;
            // Instanciar objeto metodos de pago
            double monto = 0.00;
            // Instanciar fechas

            /* 
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaActive = fechaActual.plusYears(1);
            LocalDate fechaExp = fechaActual.plusYears(6);
            Date fechaCreacion = Date.valueOf(fechaActual);
            Date fechaActiva = Date.valueOf(fechaActive);
            Date fechaExpiracion = Date.valueOf(fechaExp);
            */

            System.out.println("Seleccione una tarjeta de membresia a renovar: ");
            do {
                do {
                    try {
                        bandera = false;
                        numeroMembresia = r.nextInt();
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                        r.next();
                        bandera = true;
                    }
                } while (bandera);
                bandera = verificarMembresia(numeroMembresia);
            } while (bandera == false);

            //Obtener el nivel de la membresia que se va a asignar
            rs = st.executeQuery("SELECT * FROM tarjetamemb WHERE numero = \"" +numeroMembresia+"\"");
            while (rs.next()){
                nivelTarj = rs.getString("nivelTarjeta");
            }

            //Obtener el costo de la membresia que se va a asignar
            rs = st.executeQuery("SELECT * FROM nivelTarjeta WHERE codigo = \"" +nivelTarj+"\"");
            while (rs.next()){
                monto = rs.getInt("costo");
            }

            //Instanciar fechas
            LocalDate fechaHoy = LocalDate.now();
            Date fechaActual = Date.valueOf(fechaHoy);

            //Obtener las fechas de las consultas
            rs = st.executeQuery("select*from tarjetaMemb WHERE numero = " +numeroMembresia+"");
            while (rs.next()) {
            fechaCreacion = rs.getDate("fechaCreacion");
            fechaActiva = rs.getDate("fechaActiva");
            fechaExpiracion = rs.getDate("fechaExpiracion");
            }

            Date fechaTest = new Date(fechaActiva.getTime() + 31536000000L);
            Date fechaTest2 = new Date(fechaActual.getTime() + 31536000000L);
            Boolean renovar;

            if (fechaExpiracion.before(fechaActual) || fechaExpiracion.equals(fechaActual)) {
                System.out.println("No se puede renovar la membresía, su tiempo de vida ha expirado/ha sido cancelada");
                renovar = false;
                return;
            } else if (fechaExpiracion.before((fechaTest))) {
                // Si la suma de la fecha activa + 1 año es mayor que la fecha de expiracion, no se puede cancelar
                System.out.println("No se puede renovar la membresía, expira pronto");
                System.out.println("Debe solicitar una nueva membresia");
                renovar = false;
            } else if (fechaExpiracion.before((fechaTest2))) {
                // Si la suma de la fecha actual + 1 año es mayor que la fecha de expiracion, no se puede cancelar
                System.out.println("No se puede renovar la membresía, expira pronto");
                System.out.println("Debe solicitar una nueva membresia");
                renovar = false;
            } else if (fechaActiva.after(fechaActual)) {
                // Si la fechaActiva es mayor que hoy, sumar un año
                nuevaFechaActiva = new Date(fechaActiva.getTime() + 31536000000L); // 31536000000L = 1 año en milisegundos
                renovar = true;
            } else {
                // Si la fechaActiva es menor que hoy, la nueva fechaActiva es la fecha de hoy + 1 año
                nuevaFechaActiva = new Date(fechaActual.getTime() + 31536000000L);
                renovar = true;
            }


            //Si se puede renovar, entonces procede a hacer los pagos
            if (renovar == true){

                //Pagar la renovacion
                System.out.println("El monto a pagar por la membresia es: "+monto+"$\n");

                //Metodo de pago
                System.out.println("/////////////////////////////");
                System.out.println("Seleccione el metodo de pago:");
                System.out.println("a. Efectivo \nb. Tarjeta \n *Seleccione otra opcion para cancelar la compra*");
                System.out.println("/////////////////////////////");

                r.nextLine();
                String cond = r.nextLine().toLowerCase();
                switch (cond) {
                    case "a":
                        dineroUsuario = metodosPago.pagarConEfectivo(monto);
                        bandera = true;
                        pagoEfectivo = true;
                        break;

                    case "b":
                        metodosPago.pagarConTarjeta(monto);
                        bandera = true;
                        pagoEfectivo = false;
                        break;
                
                    default:
                        System.out.println("La venta ha sido cancelada");
                        bandera = false;
                        break;
                    }

            }

            if (renovar == true & bandera == true){
                // Actualizar la fechaActiva en la BD
                PreparedStatement ps = con.prepareStatement("UPDATE tarjetamemb SET fechaActiva = ? WHERE numero = ?");
                ps.setDate(1, nuevaFechaActiva);
                ps.setInt(2, numeroMembresia);
                ps.executeUpdate();

                System.out.println("Membresía renovada con éxito");
                ps.close();
            }
            
            st.close();
            con.close();
            
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }   
    }

    //CANCELAR
    public void cancelarTarjeta() throws SQLException {
        try{
            LocalDate fechaActual = LocalDate.now();//instanciar e incializar variables a string a fecha
            Date fechaCancelacion = Date.valueOf(fechaActual);

        // Comando para conectar a la BD
           Connection con = conexion.conectar();
           Statement st = con.createStatement();
           System.out.println("Ingresa el codigo de la tarjeta a cancelar: ");
           do {
                do {
                    try {
                        bandera = false;
                        numeroMembresia = r.nextInt();
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                        r.next();
                        bandera = true;
                    }
                } while (bandera);
                bandera = verificarMembresia(numeroMembresia);
            } while (bandera == false);
           
            if(verificarEstatus(numeroMembresia)==true){
                System.out.println("Ingresa el motivo de cancelacion: ");
                System.out.println(" * Ingrese 'r' para cancelar la cancelacion *");
                r.nextLine();
                motivo = r.nextLine();
                switch (motivo) {
                    case "r":
                        System.out.println("Se cancelo la operacion exitosamente.");
                    break;
                
                    default:
                    try {
                        // Actualiza el estado a cancelado de la tarjeta seleccionada
                        String updateComando = "UPDATE tarjetamemb SET estatus = false, fechaExpiracion = ?, fechaActiva = ? WHERE numero = ?";
                        PreparedStatement ps = con.prepareStatement(updateComando);
                        ps.setDate(1, fechaCancelacion);
                        ps.setDate(2, fechaCancelacion);
                        ps.setInt(3, numeroMembresia);
                        ps.executeUpdate();
                        // Agrega la tarjeta a la tabla de cancelación
                        String comando = "INSERT INTO cancelacion (numero, fechaCancelacion, motivo, tarjetaMemb) VALUES (DEFAULT, ?, ?, ?)";
                        ps = con.prepareStatement(comando);
                        ps.setDate(1, fechaCancelacion);
                        ps.setString(2, motivo);
                        ps.setInt(3, numeroMembresia);
                        ps.executeUpdate();           
                        System.out.println("Se canceló correctamente.");
    
                    } catch (SQLException e) {System.err.println("Error al ejecutar consulta: "+ e.getMessage());}
                    break;
                } 
            } else {
                System.out.println("La membresia no existe o ya fue cancelada.");
            }
           st.close();
           con.close();
        }catch(SQLException e){System.err.println("Error al ejecutar consulta: "+ e.getMessage());}
    }



    // CONSULTA #4
    public void verActividadMembresia() throws SQLException{
        try{
            Connection con = conexion.conectar();
            Statement st = con.createStatement();
            ResultSet rs;
            System.out.println("Ingresa el numero de la membresia a consultar: ");
            do {
                do {
                    try {
                        bandera = false;
                        tarjetaID = r.nextInt();
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                        r.next();
                        bandera = true;
                    }
                } while (bandera);
                bandera = verificarMembresia(tarjetaID);
            } while (bandera == false);
            try {
                // Consulta para obtener los datos de la tarjeta
                st = con.createStatement();
                rs = st.executeQuery("SELECT tm.numero AS membresia, m.numero AS cliente, "
                                        + "CONCAT(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) AS nombre_cliente "
                                        + "FROM TarjetaMemb as tm "
                                        + "INNER JOIN Miembro as m on tm.miembro = m.numero "
                                        + "where tm.numero="+tarjetaID+"  ");
                if (rs.next()) {
                    System.out.println("\n\t//////////// ACTIVIDAD DE MEMBRESIA ////////////\n");
                    System.out.println("Membresía: " + rs.getInt("membresia"));
                    System.out.println("Cliente: " + rs.getInt("cliente"));
                    System.out.println("Nombre del cliente: " + rs.getString("nombre_cliente"));
                    System.out.println();
                    rs.close();
                    // Consulta para obtener las ventas
                    rs = st.executeQuery("SELECT tm.numero AS membresia, "
                                        +"m.numero AS cliente, CONCAT(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) AS nombre_cliente, "
                                        + "'Venta' AS actividad, v.fecha AS fecha_actividad "
                                        + "FROM TarjetaMemb as "
                                        + "tm INNER JOIN Miembro m ON tm.miembro = m.numero "
                                        + "INNER JOIN Venta v ON tm.numero = v.tarjetaMemb "
                                        + "where tm.numero="+tarjetaID+"  ");
                    System.out.println("---------------------------\n");
                    while (rs.next()) {
                        System.out.println("Actividad: " + rs.getString("actividad"));
                        System.out.println("Fecha actividad: " + rs.getDate("fecha_actividad"));
                        System.out.println();
                    }
                    rs.close();
                    // Consulta para obtener las renovaciones
                    rs = st.executeQuery("SELECT tm.numero AS membresia, "
                                            + "m.numero AS cliente, "
                                            + "CONCAT(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) AS nombre_cliente, "
                                            + "'Renovacion' as actividad, r.fechaRenovacion as fecha_actividad "
                                            + "FROM TarjetaMemb tm "
                                            + "INNER JOIN Miembro m ON tm.miembro = m.numero "
                                            + "INNER JOIN Renovacion r ON tm.numero = r.tarjetaMemb "
                                            + "where tm.numero="+tarjetaID+"  ");
                    System.out.println("---------------------------\n");
                    while (rs.next()) {
                        System.out.println("Actividad: " + rs.getString("actividad"));
                        System.out.println("Fecha actividad: " + rs.getDate("fecha_actividad"));
                        System.out.println();
                    }
                    rs.close();
                    // Consulta para obtener las cancelaciones
                    rs = st.executeQuery("SELECT tm.numero AS membresia, m.numero AS cliente, "
                                            + "CONCAT(m.nombre, ' ', m.primerApellido, ' ', m.segundoApellido) AS nombre_cliente, "
                                            + "'Cancelacion' AS actividad, c.fechaCancelacion AS fecha_actividad "
                                            + "FROM TarjetaMemb tm "
                                            + "INNER JOIN Miembro m ON tm.miembro = m.numero "
                                            + "INNER JOIN Cancelacion c ON tm.numero = c.tarjetaMemb "
                                            + "where tm.numero="+tarjetaID+"  ");
                    System.out.println("---------------------------\n");
                    while (rs.next()) {
                        System.out.println("Actividad: " + rs.getString("actividad"));
                        System.out.println("Fecha actividad: " + rs.getDate("fecha_actividad"));
                        System.out.println();
                    }
                    rs.close();
                    st.close();
                    con.close();
                } else {
                    System.out.println("No se encontraron datos para la membresía proporcionada");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // CONSULTA #5
    public void verBalancePuntos() throws SQLException{
        try{
            Connection con = conexion.conectar();
            Statement st = con.createStatement();
            
            System.out.println("Ingresa el numero de la membresia a consultar: ");
            do {
                do {
                    try {
                        bandera = false;
                        tarjetaID = r.nextInt();
                    } catch (Exception e) {
                        System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                        r.next();
                        bandera = true;
                    }
                } while (bandera);
                bandera = verificarMembresia(tarjetaID);
            } while (bandera == false);
            
            ResultSet rs = st.executeQuery("select t.numero as numero, "
            +"t.puntosActuales as puntosActuales, "
            + "t.puntosAcumulados - t.puntosActuales as puntosGastados, "
            + "t.puntosAcumulados as puntosGenerados "
            + "from tarjetamemb as t "
            + "where t.numero = "+tarjetaID+" ");
            
            if(rs.next()){
                System.out.println("\n\t//////////// BALANCE DE PUNTOS ////////////\n");
                System.out.println("Numero de la membresia: " + tarjetaID);
                puntosAcumulados = rs.getInt ("puntosGenerados");
                System.out.println("Puntos acumulados desde la creacion de la tarjeta: "+ puntosAcumulados);
                puntosGastados = rs.getInt ("puntosGastados");
                System.out.println("Puntos gastados: "+puntosGastados);
                puntosActuales = rs.getInt ("puntosActuales");
                System.out.println("Puntos actuales: "+puntosActuales);
                System.out.println("\n");
            }else{ System.out.println("No se encontro la tarjeta con el numero proporcionado");}
            rs.close();
            st.close();
            con.close();
        }
        catch(SQLException e){
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    //Metodo para verificar el estatus de una tarjeta sea valida
    public Boolean verificarEstatus(int numeroMembresia) throws SQLException{
        Connection con = conexion.conectar();
        Statement st = con.createStatement();
        ResultSet rs;

        //Instanciar fechas
        LocalDate fechaHoy = LocalDate.now();
        Date fechaActual = Date.valueOf(fechaHoy);

        //Obtener las fechas de las consultas
        rs = st.executeQuery("select*from tarjetaMemb WHERE numero = " +numeroMembresia+"");
        while (rs.next()) {
            fechaActiva = rs.getDate("fechaActiva");
        }

        boolean estatus = fechaActiva.after(fechaActual);

        PreparedStatement ps = con.prepareStatement("UPDATE tarjetamemb SET estatus = ? WHERE numero = ?");
        ps.setBoolean(1, estatus);
        ps.setInt(2, numeroMembresia);
        ps.executeUpdate();

        rs.close();
        st.close();
        con.close();

        if (estatus == true){
            return true;
        } else {
            return false;
        }
    }

    //Codigo para verificar que la membresia ingresada exista. Se utiliza dentro del metodo registrarCompra
    public boolean verificarMembresia(int numeroMembresia){
        try {
            Connection con = conexion.conectar();
            // Verificar si el número de tarjeta existe en la base de datos
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM TarjetaMemb WHERE numero =?");
            pstmt.setInt(1, numeroMembresia);
            ResultSet resultado = pstmt.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                System.out.println("Error: El número de tarjeta no existe, ingreselo de nuevo");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
            return false;
        }
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

    //Codigo para verificar que el empleado ingresado exista. Se utiliza dentro del metodo registrarCompra
    public boolean verificarEmpleado(int empleado){
        try {
            Connection con = conexion.conectar();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM empleado WHERE numero =?");
            pstmt.setInt(1, empleado);
            ResultSet resultado = pstmt.executeQuery();

            if (resultado.next()) {
                return true;
            } else {
                System.out.println("Error: El número de empleado no existe, ingreselo de nuevo");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la base de datos: " + e.getMessage());
            return false;
        }
    }

    //Seleccionar un nivel de tarjeta
    public String seleccionarNivel(){
        System.out.println("Seleccione el nivel de tarjeta: ");
        System.out.println("a. Nivel basico \nb. Nivel intermedio \nc. Nivel premium");
        do { //Switch que verifica que se selecciona uno de los 3 niveles de la tarjeta.
                r.nextLine();
                String cond = r.nextLine().toLowerCase();
                switch (cond) {
                case "a":
                    bandera = true;
                    nivelTarj = "NV1";
                    break;
                    
                case "b":
                    bandera = true;
                    nivelTarj = "NV2";
                    break;
                
                case "c":
                    bandera = true;
                    nivelTarj = "NV3";
                    break;
            
                default:
                    bandera = false;
                    System.out.println("El valor ingresado es incorrecto. Volver a ingresar.");
                    break;
            }
        } while (bandera == false);
        return nivelTarj;
    }

    // CONSULTA #3
    // Ventas de membresías en un mes determinado
    public void verVentasPorMes() throws SQLException{
        
        //Verificar que se ingrese un mes correcto
        do {
            try {
                System.out.print("Ingrese el mes (1-12): ");
                mes = r.nextInt();
                if (mes < 1 || mes > 12) {
                    System.out.println("Error: El mes debe ser un número entre 1 y 12.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                r.next(); // Limpiar el buffer de entrada
            }
        } while (mes < 1 || mes > 12);

        // Validación del año
        do {
            try {
                System.out.print("Ingrese el año a consultar: ");
                anio = r.nextInt();
                if (anio < 0 || anio > 2024) {
                    System.out.println("Error: El año debe ser un número entre 0 y 2024.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                r.next(); // Limpiar el buffer de entrada
            }
        } while (anio < 0 || anio > 2024);

        Connection con = conexion.conectar();

        String comando= "SELECT " +
                        "DATE_FORMAT(t.fechaCreacion, \"%d/%m/%Y\") AS fecha, " +
                        "t.numero AS NumeroMembresia, " +
                        "m.numero AS NumeroCliente, " +
                        "CONCAT(m.nombre, ' ', " +
                        "m.primerApellido, ' ', " +
                        "IFNULL(CONCAT(m.segundoApellido, ' '), '')" +
                        ") AS Miembro " +
                        "from tarjetamemb as t " +
                        "INNER JOIN miembro AS m ON t.miembro = m.numero " +
                        "WHERE MONTH(fechaCreacion) = ? AND YEAR(fechaCreacion) = ?";

        // Verificar si el número de tarjeta existe en la base de 

        PreparedStatement ps = con.prepareStatement(comando);
        ps.setInt(1, mes);
        ps.setInt(2, anio);
        ResultSet rs = ps.executeQuery();
        boolean bandera = false;

            while (rs.next()) {
                bandera = true;
                System.out.println("\n----------------------------------------------");
                System.out.println("Fecha: " + rs.getString("fecha"));
                System.out.println("Numero de membresia: " + rs.getString("NumeroMembresia"));
                System.out.println("Numero del cliente: " + rs.getString("NumeroCliente"));
                System.out.println("Miembro: " + rs.getString("Miembro"));
                System.out.println();
                System.out.println(); // Salto de línea entre registros
            }

            if (!bandera) {
                System.out.println("No hay registros para mostrar");
            }
        System.out.println("");
    }

    // CONSULTA #10
    // Ventas de membresías por dia
    public void verVentasPorDia() throws SQLException{
        String comando = "SELECT " +
            "DATE_FORMAT(t.fechaCreacion, \"%d/%m/%Y\") AS fecha, " +
            "COUNT(t.numero) AS cantidad " +
            "FROM tarjetamemb AS t " +
            "GROUP BY t.fechaCreacion";


            Connection con = conexion.conectar();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(comando);

            while (rs.next()) {
            bandera = true;
            System.out.println("\n----------------------------------------------");
            System.out.println("Fecha: " + rs.getString("fecha"));
            System.out.println("Cantidad de ventas: " + rs.getString("cantidad"));
            System.out.println(); // Salto de línea entre registros
        }
    }
}
