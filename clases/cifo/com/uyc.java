package clases.cifo.com;

import java.util.Scanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*Interface "UtilidadesYConstantes", nombre demasiado largo para tener que
  escribirlo luego cada vez que llame a estos métodos implementados static de la
  interfaz. No sé si es una buena práctica, sé que es posible hacerlo desde JDK 8.
  Así como desde JDK 9 es posible incluir métodos privados. El caso es que lo he
  hecho para practicar y para descargar la clase principal "Programa" de todos
  estos métodos que siempre repito. Esto por una parte, pero luego, para practicar
  la otra posibilidad de sacar métodos repetitivos y generales de la clase
  "Programa", y para que para una próxima vez no tenga que copiar y pegar estos
  métodos desde un ejercicio anterior, sino que simplemente agregue un archivo
  .java al paquete de trabajo, también he creado una clase para hacer esto mismo
  pero de otra manera, tal vez más correcta que con la interfaz. De momento, allí
  he puesto métodos más relacionados, por así decirlo, con la clase "Programa", 
  como las cabeceras de lo que se imprime por pantalla. Y aquí, en la interface,
  he puesto cosas más genéricas, como los "alias" para System.out.print. Para
  practicar, iba a poner también unas constantes con las cadenas de texto del
  programa, pero como he dicho, como es algo que está más relacionado con el
  programa, casi que lo voy a poner en esta otra clase que he dicho, y que le
  llamo "Uti" como diminutivo de Utilidades. Además, he leído que, aunque a veces
  se usan las interfaces para albergar constantes del programa, dado que las
  variables son final (además de public y static), es una práctica un tanto
  controvertida*/

public interface uyc {
  //String[] STRPROGRAMA = {"Añadir un empleado/cliente nuevo",
  //                        "Eliminar un empleado/cliente"};
  
  /*Como digo, estos métodos los podría mover  a la clase Uti.java*/
  static void imp(String... args) {for (String arg : args) {System.out.print(arg);}}
  static void impln(String... args) {for (String arg : args) {System.out.print(arg);} System.out.print("\n");}
  static String abc(int intN) {return Integer.toString(intN);}

  static String Subraya(int intLongitud, String strCaracter) {
    return new String(new char[intLongitud]).replace("\0", strCaracter);
  }  
  
  static Scanner scnEntrada = new Scanner(System.in);
    
  static int CompruebaEntero(String strEntrada) {
    int intEntero = 0;
    do {
      try {
        intEntero = Integer.parseInt(strEntrada);
        break;
      } catch (Exception e) {
        imp("No ha escrito un número entero. Vuelva a probar: ");
        strEntrada = scnEntrada.nextLine().trim();
      }
    }while(true);
    return intEntero;
  }
 
  static int Elegir(String strEntrada, int intNumMax) {
    int intNumero;
    do{
      intNumero = CompruebaEntero(strEntrada);
      if ((intNumero >= 1) && (intNumero <= intNumMax)) {
        break;
      }
      imp("El número entero ha de estar entre 1 y ", abc(intNumMax), ". Vuelva a probar: ");
      strEntrada = scnEntrada.nextLine().trim();
    }while(true);
    return intNumero;
  }  

  /*Comprobar que lo que se introduce sea un número, se podría comprobar en los
    setters, pero también habría que hacerlo en el constructor.*/
  static BigDecimal NumeroValido() {
    /*La inicializamos, si no no mantiene el valor de dentro del while*/
    BigDecimal numero = null ;
    boolean numValido = false;
    /*Creamos un bucle hasta que se introduzca un número correcto*/
    while (!numValido) {
      /*Leo la línea entera para evitar que sea válida una entrada del tipo
        "7 a" si utilizara simplemente el método .next(). Quito los espacios
        exteriores con String.trim()
        El BigDecimal va con . en vez de ,
        Si se introduce , la cambiamos a . para que eso no sea un problema*/
      String strEntrada = scnEntrada.nextLine().trim().replace(",",".");

      /*Al Intentar convertir la línea a BigDecimal, puede dar error si se han
        introducido números incorrectos, caracteres o más de un número*/
      try {
        /*Instrucción diferente para BigDecimal o los tipos primitivos(Float,
          Int,...)*/
        //numero = Float.parseFloat(strEntrada);
        numero = new BigDecimal(strEntrada).setScale(2, RoundingMode.HALF_EVEN);   
        /*Si hay error, irá al Catch; pero si no lo hay, irá directamente a las
        siguientes líneas, y para salir del bucle pongo:*/
        numValido = true;
      } catch (Exception e) {
        System.out.print("Número no correcto. Vuelve a probar: ");
      }
    }
    return numero;
  } 
}