package clases;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;

//      -- Atributos MySQL --
//  Venta (numero, fecha, total, puntosUsados, totalBeneficio, IVA, subtotalBeneficio, tarjetaMemb, nivelTarjeta)
//  TarjetaMemb (numero, duracion, fechaCreacion, fechaActiva, fechaExpiracion, estatus, puntosActuales, puntosAcumulados, empleado, miembro, nivelTarjeta)
//  NivelTarjeta (codigo, nombre, descripcion, color, costo, descuentoPermanente, limitePuntos, gananciaPuntos, valorPunto)

public class ventas extends nivelDeTarjeta {
    Scanner s = new Scanner(System.in);
    private boolean bandera;
    private int puntosUsados;

    //Atributos necesarios de la tabla TARJETAMEMB (para almacenar a quien se le va a registrar la venta)
    private int numeroMembresia;
    private int puntosActuales;
    private int puntosAcumulados;
    private String nivelTarjeta;

    //Atributos necesarios de la tabla VENTA
    private double monto;
    private double subtotal;
    private double IVA;
    private double total;
    private int puntosGanados;
    private int numeroVenta;
    private double dineroUsuario;

    //Booleano para indicar si el pago se realizo con efectivo. Si es asi, se indica el valor del cambio
    private boolean pagoEfectivo;

    //OJO. Objeto para implementar la clase PAGO
    pago pagos = new pago();
    tarjetaMembresia adminTarjeta = new tarjetaMembresia(getConexion());

    //Objeto para implementar la clase CONEXION
    conexion conexion = getConexion();

    // Formato decimal para mostrar solo 2 decimales
    DecimalFormat df = new DecimalFormat("0.00");

    public ventas (conexion conexion) {
        super(conexion);
    }

    public ventas(){

    }
    
    //Metodo para registrar una compra
    public void registrarCompra() throws SQLException {

        // Comando para conectar a la BD
        Connection con = conexion.conectar();
        Statement st = con.createStatement();

        // Comando para realizar CONSULTAS (en este caso es para consultar los datos de la tabla del nivel de la tarjeta)

        System.out.println("Ingrese el numero de la tarjeta de membresia: ");
        do {
            do {
                try {
                    bandera = false;
                    numeroMembresia = s.nextInt();
                } catch (Exception e) {
                    System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                    s.next();
                    bandera = true;
                }
            } while (bandera);
            bandera = verificarMembresia(numeroMembresia);
        } while (bandera == false);

        if(adminTarjeta.verificarEstatus(numeroMembresia)==true){
            System.out.println("Ingrese el monto de la compra: ");
            do {
                try {
                    bandera = true;
                    monto = s.nextDouble();
                    if (monto <= 0) {
                        System.out.println("El monto debe ser mayor a cero. Por favor, ingreselo de nuevo");
                        bandera = false;
                    }
                } catch (Exception e) {
                    System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                    s.next();
                    bandera = false;
                }
            } while (bandera == false);
    
            //Obtener el total de puntos del usuario
            ResultSet rs = st.executeQuery("SELECT * FROM tarjetaMemb where numero = \"" +numeroMembresia+"\"");
    
            while (rs.next()) {
                nivelTarjeta = rs.getString("nivelTarjeta");
                puntosActuales = rs.getInt("puntosActuales");
                puntosAcumulados = rs.getInt("puntosAcumulados");
            }
    
            System.out.println("El usuario cuenta con " +puntosActuales+  " puntos");
            do {
                try {
                    bandera = true;
                    System.out.println("Ingrese la cantidad de puntos a usar:");
                    puntosUsados = s.nextInt(); 
                    s.nextLine();
                    if (puntosUsados > puntosActuales) {
                        System.out.println("No tiene suficientes puntos para realizar la compra");
                        bandera = false;
                    } else if (puntosUsados < 0){
                            System.out.println("No puede usar puntos negativos");
                            bandera = false;
                        }
                    }
                catch (Exception e) {
                    bandera = false;
                    s.next();
                    System.out.println("Debe ingresar un valor numerico. Por favor, ingreselo de nuevo");
                }
            } while (bandera == false);
            
            total = monto - (monto * (super.obtenerDescuentoPermanente(nivelTarjeta) / 100)) - (puntosUsados * (super.obtenerValorPunto(nivelTarjeta) / 100));
            if (total < 0){
                total = 0;
            }
            IVA = total * 0.16;
            subtotal = total - IVA;
            puntosGanados = (int) (total * (super.obtenerGananciaPuntos(nivelTarjeta) / 100));
    
            // Validar que los puntos ganados no excedan el limite de puntos de la tarjeta
            if (puntosActuales - puntosUsados + puntosGanados > super.obtenerLimitePuntos(nivelTarjeta)){
                puntosGanados = super.obtenerLimitePuntos(nivelTarjeta) - puntosActuales + puntosUsados;
            }
    
            puntosActuales = puntosActuales - puntosUsados + puntosGanados;
    
            // Verificar que la suma de los puntos actuales no supere el limite de puntos
            if (puntosActuales > super.obtenerLimitePuntos(nivelTarjeta)){
                puntosActuales = super.obtenerLimitePuntos(nivelTarjeta);
            }
    
            // Verificar que no haya puntos negativos para la tarjeta
            puntosAcumulados = puntosAcumulados + puntosUsados;
            if (total < 0){
                total = 0;
            }
            System.out.println("El total de la venta se ha reducido a "+df.format(total)+"$");
            if (total > 0){
                System.out.println("/////////////////////////////");
            System.out.println("Seleccione el metodo de pago:");
            System.out.println("a. Efectivo \nb. Tarjeta \n *Seleccione otra opcion para cancelar la compra*");
            System.out.println("/////////////////////////////");
            char menu = s.next().charAt(0);
            switch (menu) {
                case 'a':
                    dineroUsuario = pagos.pagarConEfectivo(total);
                    bandera = true;
                    pagoEfectivo = true;
                    break;
    
                case 'b':
                    pagos.pagarConTarjeta(total);
                    bandera = true;
                    pagoEfectivo = false;
                    break;
            
                default:
                    System.out.println("La venta ha sido cancelada");
                    bandera = false;
                    break;
                }
            } else {
                System.out.println("Sus beneficios hicieron que la compra sea gratis ! ! !");
                bandera = true;
            }
    
            if (bandera == true){
    
                String comando =  "insert into Venta (numero, fecha, total, puntosUsados, puntosGanados, totalBeneficio, IVA, subtotalBeneficio, tarjetaMemb, nivelTarjeta) VALUES (DEFAULT, DATE(NOW()), '"+monto+"', '"+puntosUsados+"', '"+puntosGanados+"', '"+total+"', '"+IVA+"', '"+subtotal+"', '"+numeroMembresia+"', '"+nivelTarjeta+"')";
                st.executeUpdate(comando);
                st.executeUpdate("UPDATE tarjetaMemb SET puntosActuales = " + puntosActuales + " WHERE numero = " +numeroMembresia+"");
                st.executeUpdate("UPDATE tarjetaMemb SET puntosAcumulados = " + puntosAcumulados + " WHERE numero = " +numeroMembresia+"");
                System.out.println("La venta se realizo correctamente ! ! !");
        
                //Codigo para imprimir los datos de la ultima venta realizada
        
                comando = "SELECT LAST_INSERT_ID() AS numero";
                rs = st.executeQuery(comando);
                if (rs.next()) {
                    System.out.println("---------------------------------------");
                    System.out.println("TICKET DE COMPRA");
                    numeroVenta = rs.getInt("numero");
                    System.out.println("Número de venta: " + numeroVenta);
                }
        
                rs = st.executeQuery("select*from venta WHERE numero = " +numeroVenta+"");
                while (rs.next()) {
                    System.out.println("Numero de Tarjeta: " + numeroMembresia);
                    Date fechaVenta = rs.getDate("fecha");
                    System.out.println("Fecha de compra: " + fechaVenta);
                    System.out.println("Monto Total (sin beneficios): " + monto);
                    System.out.println("Subtotal: "+df.format(subtotal));
                    System.out.println("IVA: "+df.format(IVA));
                    if (pagoEfectivo == false) {
                        System.out.println("Total pagado con tarjeta: "+df.format(total));
                    } else {
                        System.out.println("Total a pagar: "+df.format(total));
                        System.out.println("Efectivo: "+df.format(dineroUsuario));
                        System.out.println("Cambio: "+df.format(dineroUsuario - total));
    
                    }
                    
                    System.out.println("-----------------------");
                    System.out.println("Beneficios aplicados: ");
                    System.out.println("Descuento permanente del "+super.obtenerDescuentoPermanente(nivelTarjeta)+"%");
                    System.out.println("Puntos usados: "+puntosUsados+" puntos");
                    System.out.println("Puntos ganados por la compra: "+puntosGanados+ " puntos");
                    System.out.println( "Puntos actuales: "+puntosActuales);
                    System.out.println("En total ahorraste "+ df.format((monto - total))+" pesos\n");
                }
            }
        } else {
            System.out.println("La operacion no se ha podido realizar.\nLa tarjeta no esta activa.");
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
                // Si número de tarjeta existe, se procede con el registro de la compra
                System.out.println("Tarjeta encontrada. Procediendo con el registro de la compra...");
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

    
}