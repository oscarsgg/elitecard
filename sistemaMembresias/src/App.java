import java.sql.*;
import java.util.Scanner;
import clases.*;
import java.time.*;
//Hola, saquen a dani del equipo
public class App{
    public static void main(String[] args) {
        conexion conexion = new conexion("jdbc:mysql://localhost:3306/elitecard", "root", "");
        nivelDeTarjeta adminNiveles = new nivelDeTarjeta(conexion);
        empleado admEmpleado = new empleado(conexion);
        empleado adminEmpld = new empleado(conexion);
        miembro adMiembro = new miembro(conexion);
        ventas adminVentas = new ventas(conexion);
        tarjetaMembresia adminTarjetas = new tarjetaMembresia(conexion);
        String cond;
        Scanner s = new Scanner(System.in);
        boolean bandera;
        boolean banderadmin;
        boolean banderalogin;
        LocalDate fechaHoy = LocalDate.now();
        Date fecha = Date.valueOf(fechaHoy);

        // Primer trycatch para verificar la conexion con la BD
        try {
            System.out.println(colores.AMARILLO + "BIENVENIDO A ELITE CARD" + colores.RESET + "\nFecha actual: "+ fecha);
            do{
                    System.out.println(colores.AMARILLO + "\n¿Que desea hacer?"
                                        + colores.CIAN + "\n    a.Iniciar sesion"
                                        + colores.PURPURA +"\n    b.Cerrar el programa" + colores.RESET
                    );
                    cond = s.nextLine();
                    switch (cond.toLowerCase()) {
                        case "a":
                        do{
                            banderalogin = false;
                            System.out.println(colores.AZUL + "INICIO DE SESION");
                            adminEmpld.iniciarsesion();
                            if(adminEmpld.getPuesto().equals("PT4")){
                                System.out.println(colores.CIAN + "Bienvenido al sistema de membresías. ¡Gracias por iniciar sesión!" + colores.RESET);
                                do {
                                System.out.println("-----------------------------\nIngrese una de nuestras opciones");
                                System.out.println(  colores.PURPURA + "a. Ingresar al apartado de gestión\n"
                                                    +"b. Ingresar al apartado de venta\n"
                                                    +"c. Mostrar estadisticas del sistema\n"
                                                    +"d. Cerrar la sesion" + colores.RESET
                                );
                                cond = s.nextLine();
                                switch (cond.toLowerCase()) {
                                    case "a":
                                        boolean gestionBandera;
                                        do {
                                            gestionBandera = false;
                                            System.out.println(colores.AZUL +"\nSeleccione alguna de las opciones: \n" 
                                                                + "a. Gestionar membresías\n"
                                                                + "b. Gestionar empleados\n"
                                                                + "c. Gestionar miembros\n"
                                                                + "d. Gestionar niveles de membresías\n"
                                                                + "r. Regresar");
                                            cond = s.nextLine();
                                            switch (cond.toLowerCase()) {
                                                case "a":
                                                    boolean membresiasBandera;
                                                    do {
                                                        membresiasBandera = false;
                                                        System.out.println(colores.VERDE +"\nSeleccione alguna de las opciones: \n" 
                                                                    + "a. Ver información de todas las membresías\n"
                                                                    + "b. Ver información de una membresía en específico\n"
                                                                    + "c. Ver actividad de la membresía\n"
                                                                    + "d. Ver el balance de puntos\n"
                                                                    + "r. Regresar" + colores.AZUL);
                                                        cond = s.nextLine();
                                                        switch (cond.toLowerCase()) {
                                                            case "a":
                                                                adminTarjetas.imprimirRegistros();
                                                                break;
                                                            case "b":
                                                                adminTarjetas.verRegistro();
                                                                break;
                                                            case "c":
                                                                adminTarjetas.verActividadMembresia();
                                                                break;
                                                            case "d":
                                                                adminTarjetas.verBalancePuntos();
                                                                break;
                                                            case "r":
                                                                membresiasBandera = true;
                                                                break;
                                                            default:
                                                                System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                                break;
                                                        }
                                                    } while (!membresiasBandera);
                                                    break;
                                                
                                                case "b":
                                                    boolean empleadosBandera;
                                                    do {
                                                        empleadosBandera = false;
                                                        System.out.println(colores.CIAN + "\nSeleccione alguna de las opciones: \n" 
                                                                + "a. Ver datos de un empleado\n"
                                                                + "b. Ver datos de todos los empleados\n"
                                                                + "c. Modificar un empleado\n"
                                                                + "d. Registar un empleado\n"
                                                                + "e. Eliminar un empleado\n"
                                                                + "r. Regresar" + colores.AZUL);
                                                        cond = s.nextLine();
                                                        switch (cond.toLowerCase()) {
                                                            case "a":
                                                                admEmpleado.verUnRegistros();
                                                                break;
                                                            case "b":
                                                                admEmpleado.verRegistros();
                                                                break;
                                                            case "c":
                                                                admEmpleado.updateEmpld();
                                                                break;
                                                            case "d":
                                                                admEmpleado.agregarEmpld();
                                                                break;
                                                            case "e":
                                                                admEmpleado.deleteEmpld();
                                                                break;
                                                            case "f":
                                                                adminEmpld.iniciarsesion();
                                                            case "r":
                                                                empleadosBandera = true;
                                                                break;
                                                            default:
                                                            System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                                break;
                                                        }
                                                    } while (!empleadosBandera);
                                                    break;
                                                
                                                case "c":
                                                    boolean miembrosBandera;
                                                    do {
                                                        miembrosBandera = false;
                                                        System.out.println(colores.PURPURA + "\nSeleccione alguna de las opciones: \n" 
                                                                + "a. Ver datos de un miembro\n"
                                                                + "b. Ver datos de todos los miembros\n"
                                                                + "c. Modificar un miembro\n"
                                                                + "d. Registar un miembro\n"
                                                                + "e. Eliminar un miembro\n"
                                                                + "f. Mostrar la membresia del miembro\n"
                                                                + "r. Regresar" + colores.AZUL);
                                                        cond = s.nextLine();
                                                        switch (cond.toLowerCase()) {
                                                            case "a":
                                                                adMiembro.verUnRegistros();
                                                                break;
                                                            case "b":
                                                                adMiembro.verRegistros();
                                                                break;
                                                            case "c":
                                                                adMiembro.updateMiembro();
                                                                break;
                                                            case "d":
                                                                adMiembro.agregarMiemb();
                                                                break;
                                                            case "e":
                                                                adMiembro.deleteMiemb();
                                                                break;
                                                            case "f":
                                                                adMiembro.mostrarMembCliente();
                                                                break;
                                                            case "r":
                                                                miembrosBandera = true;
                                                                break;
                                                            default:
                                                                System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                                break;
                                                        }
                                                    } while (!miembrosBandera);
                                                    break;
                                                
                                                case "d":
                                                    boolean nivelesBandera;
                                                    do {
                                                        nivelesBandera = false;
                                                        System.out.println(colores.AMARILLO + "\nSeleccione alguna de las opciones: \n" 
                                                                + "a. Costo\n"
                                                                + "b. Descuento permanente del nivel\n"
                                                                + "c. Valor de puntos\n"
                                                                + "d. Límite de puntos\n"
                                                                + "r. Regresar" + colores.AZUL);
                                                        cond = s.nextLine();
                                                        switch (cond.toLowerCase()) {
                                                            case "a":
                                                                adminNiveles.actualizarCosto();
                                                                break;
                                                            case "b":
                                                                adminNiveles.actualizarDescPermanente();
                                                                break;
                                                            case "c":
                                                                adminNiveles.actualizarValorPunto();
                                                                break;
                                                            case "d":
                                                                adminNiveles.actualizarLimitePuntos();
                                                                break;
                                                            case "r":
                                                                nivelesBandera = true;
                                                                break;
                                                            default:
                                                                System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                                break;
                                                        }
                                                    } while (!nivelesBandera);
                                                    break;
                                                
                                                case "r":
                                                    gestionBandera = true;
                                                    break;
                                                
                                                default:
                                                    System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                    break;
                                            }
                                        } while (!gestionBandera);
                                        System.out.println("" + colores.RESET);
                                        banderadmin = true;
                                        break;
                                        
                                    case "b":
                                        boolean ventasBandera;
                                        do {
                                            ventasBandera = false;
                                            System.out.println(colores.VERDE + "Seleccione alguna de las opciones: \n" 
                                                                + "a. Ver los benefecios de un nivel de tarjeta\n"
                                                                + "b. Ver precios de cada nivel de tarjeta\n"
                                                                + "c. Imprimir la cantidad de beneficios por nivel\n"
                                                                + "d. Registrar una nueva compra\n"
                                                                + "e. Asignar tarjeta\n"
                                                                + "g. Renovar tarjeta\n"
                                                                + "g. Cancelar tarjeta\n"
                                                                + "r. Regresar" + colores.RESET);
                                            cond = s.nextLine();
                                            switch (cond.toLowerCase()) {
                                                case "a":
                                                    adminNiveles.imprimirBeneficios();
                                                    break;
                                                case "b":
                                                    adminNiveles.imprimirPrecios();
                                                    break;  
                                                case "c":
                                                    adminNiveles.imprimirCantBeneficios();
                                                    break;
                                                case "d":
                                                    adminVentas.registrarCompra();
                                                    break;
                                                case "e":
                                                    adminTarjetas.asignarTarjeta();
                                                    break;
                                                case "f":
                                                    adminTarjetas.renovarTarjeta();
                                                    break;
                                                case "g":
                                                    adminTarjetas.cancelarTarjeta();
                                                    break;
                                                
                                                case "r":
                                                    ventasBandera = true;
                                                    break;
                                                default:
                                                    System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                    break;
                                            }
                                        }while(!ventasBandera);
                                        banderadmin = true;
                                        break; 

                                    case "c":
                                        boolean estadisticaBandera;
                                        do {
                                            estadisticaBandera = false;
                                            System.out.println(colores.VERDE + "Seleccione alguna de las opciones: \n" 
                                                                + "a. Ver la cantidad de membresias asocidadas por nivel\n"
                                                                + "b. Cantidad de membresias vendidas en un mes determinado\n"
                                                                + "c. Cantidad de membresias vendidas por fecha\n"
                                                                + "r. Regresar" + colores.RESET);
                                            cond = s.nextLine();
                                            switch (cond.toLowerCase()) {
                                                case "a":
                                                    adminNiveles.imprimirCantidad();
                                                    break;
                                                case "b":
                                                    adminTarjetas.verVentasPorMes();
                                                    break;
                                                case "c":
                                                    adminTarjetas.verVentasPorDia();
                                                    break;
                                                case "r":
                                                    estadisticaBandera = true;
                                                    break;
                                                default:
                                                    System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                    break;
                                            }
                                        }while(!estadisticaBandera);
                                        banderadmin = true;
                                        break; 
                                    case "d":
                                        banderalogin = false;
                                        banderadmin = false;
                                        break;
                                    
                                    default:
                                    System.out.println(colores.ROJO + "OPCION INVALIDA");
                                    banderadmin = true;
                                    break;
                                }
                            } while (banderadmin);
                            }else{
                                System.out.println(colores.AMARILLO +"\nBIENVENIDO AL NODO DE VENTAS\n" + colores.RESET);
                                boolean ventasBandera;
                                        do {
                                            ventasBandera = false;
                                            System.out.println(colores.VERDE + "Seleccione alguna de las opciones: \n" 
                                                                + "a. Ver los benefecios\n"
                                                                + "b. Ver precios\n"
                                                                + "c. Registrar una nueva compra\n"
                                                                + "d. Asignar tarjeta\n"
                                                                + "e. Renovar tarjeta\n"
                                                                + "f. Cancelar tarjeta\n"
                                                                + "r. Cerrar sesion" + colores.RESET);
                                            cond = s.nextLine();
                                            switch (cond.toLowerCase()) {
                                                case "a":
                                                    adminNiveles.imprimirBeneficios();
                                                    break;
                                                case "b":
                                                    adminNiveles.imprimirPrecios();
                                                    break;
                                                case "c":
                                                    adminVentas.registrarCompra();
                                                    break;
                                                case "d":
                                                    adminTarjetas.asignarTarjeta();
                                                    break;
                                                case "e":
                                                    adminTarjetas.renovarTarjeta();
                                                    break;
                                                case "f":
                                                    adminTarjetas.cancelarTarjeta();
                                                    break;
                                                case "r":
                                                    banderalogin = false;
                                                    ventasBandera = true;
                                                    break;
                                                default:
                                                    System.out.println(colores.ROJO + "\nOpción inválida \nFavor de ingresar una de las opciones\n" + colores.RESET);
                                                    break;
                                            }
                                        }while(!ventasBandera);
                            }
                            }while(banderalogin);
                            bandera = true;
                            break;
                        case "b":
                            System.out.println(colores.AZUL + "Cerrando el programa\nAdioos...");
                            bandera = false;
                            break;
                        default:
                            System.out.println(colores.ROJO + "OPCION INVALIDA \nFavor de ingresar alguna de nuestras opciones" + colores.RESET);
                            bandera = true;
                            break;
                    }
            }while(bandera);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        s.close();
    }
}
