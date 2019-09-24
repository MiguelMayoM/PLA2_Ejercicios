package clases.cifo.com;

import java.util.Scanner;
import java.util.ArrayList;

/*"Principio de acceso más restrictivo que tenga sentido", quito...*/
/*public*/ /*Pero digo yo que tampoco es cuestión de ir poniendo private a todo
             lo que sé que sólo se utiliza dentro de la clase*/
/*Para poder acceder a los métodos static de la interfaz uyc*/
class Programa implements uyc {
  static Scanner scnEntrada = new Scanner(System.in);
     
  public static void main(String[] args) {
    boolean bolSalir = false, bolValida = false;
    String strEleccion;
    do {
      Uti.CabeceraGeneral();
      ArrayList<Persona> aLstEmplClient = Uti.CargaEmplClient();
      do {
        /*Ya le he puesto un nombre corto a la interfaz, y sin mayúsculas ("uyc"),
        porque si no, al final, seria lo mismo que escribir System.out.print. De
        todas formas, este "alias" ocupa una línea en el archivo. Lo podría poner
        sin problema en cada clase que contenga el main de cada proyecto que
        hagamos: una línea no molesta, me evitaría tener que poner "uyc." y podría
        elaborar una plantilla para que no tuviera que copiar esa línea cada vez
        desde otros proyectos. Pero ahora lo he hecho así, con una interfaz, por
        practicar y aprender*/        
        uyc.imp("Elija una opción: ");
        /*Para no obligar a poner la "s" mayúscula...*/
        strEleccion = scnEntrada.nextLine().trim().toUpperCase();
        uyc.impln("");
        
        switch(strEleccion){
          case "1":
            bolValida = true;
            Uti.NuevoEmplClient(aLstEmplClient);
            break;
          case "2":
            bolValida = true;
            Uti.BorrarEmplClient(aLstEmplClient);
            break;
          case "3":
            bolValida = true;
            Uti.SaludoEmplClient(aLstEmplClient);
            break;
          case "4":
            bolValida = true;
            Uti.ImprimeEmplClient(aLstEmplClient);
            break;
          case "S":
            bolValida = true;
            bolSalir = true;
            break;
          default:
            uyc.impln("Opcion no válida.");
        }
      } while(!bolValida);
      
      if(!strEleccion.equals("S")){
        uyc.impln("Pulse Enter para volver al Menú Inicial.");
      }else{
        break;
      }
      scnEntrada.nextLine();
    } while(!bolSalir);
  }
}