package clases;
import java.util.Scanner;

public class pago {

    private boolean bandera;
    private double dineroUsuario;
    private double cambio;
    private String tarjeta = " "; 

    public pago(){

    }

    Scanner s = new Scanner(System.in);

    public void pagarConTarjeta(double monto) {
        System.out.println("Inserte su tarjeta (16 dígitos): ");
        do {
            tarjeta = s.nextLine();

            if (tarjeta.length() != 16) {
                System.out.println("Error: La tarjeta debe tener 16 dígitos.");
                bandera = true;
            } else {
                System.out.println("Verificando tarjeta . . . ");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Pago realizado con tarjeta por $" + monto);
                bandera = false;
            }
        } while (bandera);
    }

    public double pagarConEfectivo(double total) {
        System.out.println("Ha seleccionado pagar con efectivo. Seleccione la cantidad a pagar");
        do{
            try {
              bandera=false;
              dineroUsuario = s.nextDouble();
              if (dineroUsuario>=total) {
                cambio = dineroUsuario - total;
              }else {
                System.out.println("Pague con un monto igual o mayor al total");
                bandera=true;
              } 
              
              } catch (Exception e) {
                  bandera=true;
                  s.next();
                  System.out.println("Error. Debe ingresar obligatoriamente un valor numérico");
              }
        }while (bandera);

        //Retorna el dineroUsuario para luego poder imprimirlo en otra clase.
        //Osea, que en otra clase puedas declarar un dineroUsuario = pagos.pagarConEfectivo(total) y ya lo imprimes
        return dineroUsuario;
    }

}
