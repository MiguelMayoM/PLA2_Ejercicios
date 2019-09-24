package clases.cifo.com;

import java.util.Scanner;
import java.math.BigDecimal;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
//import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;

/*Diminutivo de Utilidades, para que sea corto de llamar. Métodos static para
  no tener que crear ningún objeto*/
class Uti {
  final static String CABGENERAL = "Gestión de empleados y clientes de la empresa\n";
  
  final static String[] CABEZATABLA = {"", "Núm", "Nombre", "DNI", 
                                       "Sec/Depart/email", "S.Bruto", "Dietas",
                                       "StckOpt", "Tipo", "S.Neto"};
  
  final static String[] STRMENUS = {"Añadir un empleado/cliente nuevo",
                                    "Eliminar un empleado/cliente",
                                    "Recibir un saludo de un empleado/cliente",
                                    "Imprimir la tabla de Empleados y Clientes"};

  static Scanner scnEntrada = new Scanner(System.in);

  static void CabeceraGeneral() {
    uyc.impln(CABGENERAL, uyc.Subraya(CABGENERAL.length(),"="));
            
    for(int i = 0; i < STRMENUS.length; i++){uyc.impln(uyc.abc(i+1), ". ", STRMENUS[i]);}
    uyc.impln("S. Salir\n");
    uyc.Subraya(CABGENERAL.length(),"=");
  }  
  
  static void Cabecera(int intEleccion) {
    uyc.impln(STRMENUS[intEleccion], uyc.Subraya(STRMENUS[intEleccion].length(),"="));
  }
  
  /****************************************************************************/
  /*             RELLENAR CAMPOS PARA PASAR A LOS CONSTRUCTORES               */
  /****************************************************************************/  
  /*Esta función llama al constructor indicado en función del tipo de EmplClient,
    según la indicación guardada en el primer campo del archivo "EmplClient.csv".
    El EmplClient creado se retorna a CargarEmplClient, que crea un ArrayList con
    todos los EmplClient ==> Persona al iniciar el programa*/
  static ArrayList<Persona> NuevoEmplClient(ArrayList<Persona> aLstEmplClient) {
    uyc.imp("Tipo de personal o cliente que se puede agregar:\n\n",
            "  1.Director   2.Gerente   3.Administración   4.Cliente\n\n",
            "Escoja el número que desee (1-4): ");
    
    String strEntrada = scnEntrada.nextLine().trim();
    int intEleccion = uyc.Elegir(strEntrada, 4);
    
    String[] strTipoPersona = {"D", "G", "A", "C"}; 
    
    /*Los <> se ponen porque ArrayList forma parte de Collections, que sólo puede
      almacenar objetos. Por eso, si queremos usar primitivos como "int", no se
      puede usar directamente sino que hay que usar una "wraper class", que
      permite usar los primitivos como clases. Por eso, por ejemplo, en vez de
      "int" ponemos <Integer>*/
    ArrayList<String> aLstCampos = new ArrayList(0);
    /*Añado el primer valor, el de la letra*/
    aLstCampos.add(strTipoPersona[intEleccion - 1]);
    
    /* NOMBRE */
    /*--------*/
    uyc.imp("Nombre: ");
    /*Cuando haya más de un espacio, lo reduzco a uno*/
    String strNomApe = scnEntrada.nextLine().trim().replaceAll("\\s+", " ");
    /*A la función que crea los objetos "EmplClient" se lo paso girado en la forma
      Apellido, Nombre, puesto que es así como se guarda en el archivo de texto
      y la función la implementé así para crear los objetos a partir de los
      campos leídos del archivo, pues es donde la usé por primera vez*/
    /*Puedo usar una regex complicada o dividir un split en un máximo de dos 
      partes a partir de la primera coincidencia del espacio. Esta regex parece
      que funciona: cojo (un primer grupo con caracteres sin espacios), (seguido
      de uno o más espacios), (seguido de cualquier grupo de caracteres,
      incluyendo espacios por si por ejemplo se ponen dos apellidos)*/
    //aLstCampos.add(strNombre.replaceAll("^(.+[^\\s])(\\s+)(.+)$", "$3,$1"));
    /*Pero no me arriesgo de algún caso que no haya visto y voy a lo seguro*/
    String strApellido = strNomApe.split(" ", 2)[1];
    String strNombre = strNomApe.split(" ", 2)[0];
    String strApeNom = strApellido + "," + strNombre;
    /*Y ahora, ya sí, añadimos*/
    aLstCampos.add(strApeNom);
    
    /* DNI(Empleados) o eMail(Cliente) */
    /*---------------------------------*/
    /*Dado un DNI o Email, recorreré los registros de Empleados o clientes
      mirando que estos no existan ya, en cuyo caso no se podrán repetir*/
    String strDniEmail;
    boolean blnDniEmailOK = true;
    //Pattern pDNI = Pattern.compile("^[0-9]{3}[A-Z]{1}$", Pattern.CASE_INSENSITIVE);
    //Pattern pEmail = Pattern.compile("^[0-9A-Z]+@[0-9A-Z]+\\.[0-9a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
    do {
      if(intEleccion != 4) {
        uyc.imp("DNI (3 números y una letra): ");
        /*Almaceno la letra en mayúsculas*/
        strDniEmail = scnEntrada.nextLine().trim().toUpperCase();
        /*Comprobar que tenga el formato correcto*/
        do{
          //Matcher mDNI = pDNI.matcher(strDniEmail);
          //if (mDNI.matches()) {
          if(strDniEmail.matches("^[0-9]{3}[A-Z]{1}$")) {
            break;
          }
          uyc.imp("El código no es correcto (ej. 123A). Volver a probar: ");
          strDniEmail = scnEntrada.nextLine().trim().toUpperCase();
        }while(true);
      } else {
        uyc.imp("email (formato letrasY0123456789@buzon.algo): ");
        /*Almaceno el mail en minúsculas*/
        strDniEmail = scnEntrada.nextLine().trim().toLowerCase();
        /*Comprobar que tenga el formato correcto*/
        do{
          //Matcher mEmail = pEmail.matcher(strDniEmail);
          //if (mEmail.matches()) {
          if (strDniEmail.matches("^[\\w\\.]+@[0-9a-z]+\\.[0-9a-z]{2,4}+$")) {
            break;
          }
          uyc.imp("El formato no es correcto (ej. letrasY0123456789@buzon.algo).\n",
                  "Volver a probar: ");
          strDniEmail = scnEntrada.nextLine().trim();
        }while(true);        
      }  
      /*Una vez el formato sea correcto, comprobar que DNI o Email no existan ya
        para otro Empleado o Cliente. Recorremos todos los registros del array
        con el patrón visitante y aprovechamos la visita primera que implementamos
        para otra cosa*/
      IfcPersonaVisitante pv = new PersonaVisitante();
      String strCompruebaDniEmail;
      for (Persona p : aLstEmplClient) {
        strCompruebaDniEmail = p.aceptar1(pv)[1];
        /*En cuanto encuentre una coincidencia, salimos y avisamos del error.
          Primero la ponemos a true para que siga así, si no hay ningún problema,
          y pueda salir del Do*/
        blnDniEmailOK = true;
        /*IgnoreCase no haría falta ya porque he convertido la entrada a lowercase*/
        if(strDniEmail.equalsIgnoreCase(strCompruebaDniEmail)) {
          uyc.impln("El valor introducido ya existe en la base de datos para otra persona. Vuelve a probar.");
          blnDniEmailOK = false;
          break;
        }
      }
    }while(blnDniEmailOK == false); 
    /*Sea DNI o email, es lo siguiente que toca añadir*/
    aLstCampos.add(strDniEmail);
    
    /* Sueldo Bruto */
    /*--------------*/
    /*Para los tres tipos de Empleados, tenemos en común el sueldo bruto. Uso
      BigDecimal para comprobar que sea un número correcto y para redondear, pero
      el dato luego lo paso a String porque es así como los toma la función que
      construye los objetos EmplClient, ya que cuando la construí era para tomar
      los datos tipo String del archivo EmplClient.csv*/
    if(intEleccion != 4) {
      uyc.imp("Sueldo bruto (máximo dos decimales, si no se redondea): ");
      /*La función ya comprueba que sea un BigDecimal válido*/
      BigDecimal bdcSueldoBruto = uyc.NumeroValido();
      String strSueldoBruto = bdcSueldoBruto.toString();
      /*Cuando hay Sueldo Bruto, es lo siguiente que toca añadir al aLstCampos*/
      aLstCampos.add(strSueldoBruto);
    }
    
    /* Campos particulares de cada tipo */
    /*----------------------------------*/
    switch(intEleccion) {
      case 1: /*Direccion*/
        uyc.imp("Stock Options (máximo dos decimales, si no se redondea): ");
        BigDecimal bdcStockOptions = uyc.NumeroValido();
        String strStockOptions = bdcStockOptions.toString();
        /*En el caso de Dirección, ya sólo queda añadir las StockOptions*/
        aLstCampos.add(strStockOptions);
      break;  
      case 2: /*Gerente*/
        uyc.imp("Dietas (máximo dos decimales, si no se redondea): ");
        BigDecimal bdcDietas = uyc.NumeroValido();
        String strDietas = bdcDietas.toString();
        uyc.imp("Departamento: ");
        String strDepartamento = scnEntrada.nextLine().trim();
        /*En el caso de Gerente, hay que añadir en un solo campo el Departamento
          y las Dietas, separados por una coma y sin espacios*/
        aLstCampos.add(strDepartamento + "," + strDietas);
      break;
      case 3: /*Administracion*/
        uyc.imp("Seccion: ");
        String strSeccion = scnEntrada.nextLine().trim();
        aLstCampos.add(strSeccion);
      break;
      case 4: /*Cliente*/
        uyc.imp("Tipo: ");
        String strTipo = scnEntrada.nextLine().trim();
        aLstCampos.add(strTipo);
      break;
    }
  
    /*El ArrayList al que en cada caso añadí un número distinto de campos, lo
      paso ahora a Array, para no trabajar con .get()*/  
    String[] strCampos = aLstCampos.toArray(new String[aLstCampos.size()]);
    /*Paso los campos para crear el nuevo EmplClient*/
    Persona pEmplClient = NuevoEmplClientCampos(strCampos);
    /*Y lo añado al ArrayList de todos*/
    aLstEmplClient.add(pEmplClient);
    /*Ordenamos el ArrayList*/
    OrdenaEmplClient(aLstEmplClient);
    /*Actualizamos el archivo*/
    GuardarEmplClient(aLstEmplClient);
    /*Y devolvemos el ArrayList para seguir trabajando con él*/
    return aLstEmplClient;
  }

  
  /****************************************************************************/
  /*                         CREAR NUEVO EmplClient                           */
  /****************************************************************************/   
  static Persona NuevoEmplClientCampos(String[] strCampos){
    /*Ahora está guardado como "Apellido, Nombre", que no haría falta, porque
      nadie tiene que leer el archivo, pero bueno, intercambio las posiciones
      para mostrar "Nombre Apellido". Lo hago con replaceAll, capturando los
      grupos antes y después de la "," e intercambiándolos*/
    String strNombre = strCampos[1].replaceAll("(.*),(.*)", "$2 $1");
    //uyc.impln(strNombre);
    switch (strCampos[0]) {
      case "A":
        return new Administracion(strNombre, strCampos[2],
                                  new BigDecimal(strCampos[3]), strCampos[4]);
      case "C":
        return new Cliente(strNombre, strCampos[2], strCampos[3]);        
      case "G":
        /*En el caso de gerentes, hemos de dividir el último campo en dos, pues
          nos viene como "Departamento,Dietas"*/
        return new Gerente(strNombre, strCampos[2], 
                           new BigDecimal(strCampos[3]), strCampos[4].split(",")[0],
                           new BigDecimal(strCampos[4].split(",")[1]));
      case "D":  
        return new Direccion (strNombre, strCampos[2], 
                              new BigDecimal(strCampos[3]),
                              new BigDecimal(strCampos[4]));
    }
    /*Esto sólo es porque me daba error al no retornar nada fuera del switch*/
    return null;
  }

  /****************************************************************************/
  /*                            CARGAR LISTA                                  */
  /****************************************************************************/  
  /*Cargar datos Empleados y Clientes desde fichero. Al principio lo había hecho
    sólo para Empleados, así que el ArrayList era para Empleados. Luego, cuando
    tenía que imprimir los campos de un tipo de Empleado, si era una variable
    específica de un tipo de Empleado, e.g. Dietas en Gerentes, tenía que hacer
    un cast del Objeto tipo Empleados: en el bucle, recorriendo cada Empleado
    con "e", tenía que hacer ((Gerente) e).getDietas(). No sé si era la manera
    más correcta de guardar todos los Empleados en un único ArrayList. Ahora,
    he decidido, para hacer plenamente funcional el programa también con los
    clientes, y dado que estos no se derivan de Empleado, hacer un ArrayList
    general de Objetos para poder guardarlos todos, lo cual me obliga a hacer
    un cast para todas las variables que quiera imprimir. Y crear una superclase
    Persona con la que poder hacer un patrón visitante y no tener que comprobar
    con instanceof. Pero esto lo haré en otro sitio...*/
  static ArrayList<Persona> CargaEmplClient () {
    /*Para cada línea, leemos los datos y creamos el objeto de tipo de Empleado
      o Cliente, tipo superclase Persona, aunque al objeto le suelo llamar 
      EmplClient. Seguidamente, lo añadimos a un ArrayList*/
    ArrayList<Persona> aLstEmplClient = new ArrayList();
    //uyc.impln(new File("").getAbsolutePath());
    try {
      /*La ubicación de los archivos se cuenta desde la carpeta del Proyecto:
        PLA2-Ejercicios
        Con Programa.class tengo la dirección de la clase, que es donde también
        he puesto el archivo. Darse cuenta que yo lo he puesto en la carpeta /src
        pero, al ejecutarlo, se copia a la carpeta /build
      */
      File filEmplClient = new File(Programa.class.getResource("EmplClient.csv").getPath());
      /*Para leer uso un Scanner con input desde el fichero, aunque también
        podría haber usado otro recurso, como BufferedReader...*/
      Scanner scnLeeFichero = new Scanner(filEmplClient);
      String[] strCampos;
      
      while (scnLeeFichero.hasNextLine()) {
        /*Reemplazo las " que se encuentren a principio o final de línea y
          divido por el separador ","*/
        strCampos = scnLeeFichero.nextLine().replaceAll("^[\"]|[\"]$", "").split("\",\"");
        //System.out.println(Arrays.toString(strCampos));
        
        Persona pEmplClient = NuevoEmplClientCampos(strCampos);
        //empEmpleado.Saludo();
        //System.out.println(empEmpleado.getClass());
        aLstEmplClient.add(pEmplClient);
      }
      scnLeeFichero.close();
    } catch(Exception e) {
      uyc.impln("Ocurrió un error leyendo el archivo empleados.csv");
    }
    
    /*Esto sólo hace falta la primera vez en el ejemplo de archivo que presento
      desordenado para que se vea que la función de ordenar funciona*/
    OrdenaEmplClient(aLstEmplClient);
    /*Guardar en realidad no hace falta a no ser que añada o borre algún registro,
      para que se conserve al salir del programa, porque con él abierto, utilizo
      siempre el ArrayList para todo. De hecho, podría simplemente guardar al
      salir (S). Por otra parte, aquí lo había puesto al incio del programa para
      que así ya se guardara ordenado el archivo que he presentado desordenado
      para ver como funciona la función de ordenar el ArrayList*/
    //GuardarEmplClient(aLstEmplClient);
    return aLstEmplClient;
  }


  /****************************************************************************/
  /*                           ORDENAR LISTA                                  */
  /****************************************************************************/  
  static ArrayList<Persona> OrdenaEmplClient (ArrayList<Persona> aLstEmplClient) {
    /*Ordeno primero: por Direccion, Gerente, Empleado, Cliente;
             segundo: Apellido alfabéticamente
             tercero: dni o email*/
    /*Para el primer orden, que es personalizado, creo una lista de como quiero
      que queden, y que utilizaré como criterio*/
    ArrayList<String> Orden1 = new ArrayList<String>(
      Arrays.asList(new String[]{"Direccion","Gerente","Administracion","Cliente"}));
    
    Comparator<Persona> CompOrden1 = (Persona obj1, Persona obj2) -> {
      int intIndex1 = Orden1.indexOf(obj1.getClass().toString().replaceAll(".+[.]{1}(.+)","$1"));
      int intIndex2 = Orden1.indexOf(obj2.getClass().toString().replaceAll(".+[.]{1}(.+)","$1"));
      return intIndex1 - intIndex2;
    };
    
    /*No vale hacer un casting dinámico (en tiempo de ejecución) con la clase
      que haya obtenido con getClass, NO. Así que utilizo un patrón Visitante*/
    /*Para el segundo orden, tomo el apellido del campo "nombre apellido"*/
    IfcPersonaVisitante pv = new PersonaVisitante(); 
    Comparator<Persona> CompOrden2 = (Persona p1, Persona p2) -> {  
      String str1 = p1.aceptar1(pv)[0].replaceAll("(.+)\\s(.+)","$2");
      String str2 = p2.aceptar1(pv)[0].replaceAll("(.+)\\s(.+)","$2");
      return str1.compareToIgnoreCase(str2);
    };    
    
    /*Y si hay dos nombres iguales, ordenamos alfabéticamente por el DNI*/
    Comparator<Persona> CompOrden3 = (Persona p1, Persona p2) -> {
      String str1 = p1.aceptar1(pv)[1].replaceAll("(.+)\\s(.+)","$2");
      String str2 = p2.aceptar1(pv)[1].replaceAll("(.+)\\s(.+)","$2");
      return str1.compareToIgnoreCase(str2);
    };
    
    /*Y ordeno concatenando todos los filtros uno tras otro en el orden deseado*/
    Collections.sort(aLstEmplClient, CompOrden1.thenComparing(CompOrden2).thenComparing(CompOrden3));
    return aLstEmplClient;
  }


  /****************************************************************************/
  /*                          GUARDAR ARCHIVO                                 */
  /****************************************************************************/  
  static void GuardarEmplClient (ArrayList<Persona> aLstEmplClient) {
    try {
      /*El original lo renombro para tenerlo como backup*/
      File filEmplClient = new File(Programa.class.
        getResource("EmplClient.csv").getPath());
      
      File filEmplClientBckp = new File(Programa.class.
        getResource("EmplClient.csv").getPath().replace(".csv", "Bckp.csv"));
      
      /*Realizo una copia de backup del archivo original*/
      Path pthOriginal = Paths.get(filEmplClient.getAbsolutePath());
      Path pthDestino = Paths.get(filEmplClientBckp.getAbsolutePath());
   
      Files.copy(pthOriginal, pthDestino, StandardCopyOption.REPLACE_EXISTING);
        
      /*Escribo en uno nuevo con nombre igual al que tendrá finalmente*/
      filEmplClient.delete();
      FileWriter filWEmplClient = new FileWriter(filEmplClient);

      /*Preparo un visitante nuevo (tipo 2) para saber de qué tipo es el objeto
        que se recorra en cada posición del array y obrar en consecuencia*/
      IfcPersonaVisitante pv = new PersonaVisitante();
      for(Persona p : aLstEmplClient) {
        filWEmplClient.write(p.aceptar2(pv));
      }
      filWEmplClient.close();
      
      /*Y, si todo ha ido bien, llego aquí y borro el bckp o lo podría dejar
        también.*/
      filEmplClientBckp.delete();
    }catch (Exception e){uyc.impln("Ocurrió un error");}
    uyc.impln("Archivo guardado con éxito");
  }
  

  /****************************************************************************/
  /*                            IMPRIMIR LISTA                                */
  /****************************************************************************/  
  /*Antes de imprimir, ya estarán ordenados, tanto si hemos cargado el archivo
    al principio de la ejecución como si hemos añadido un nuevo Empleado*/
  static void ImprimeEmplClient (ArrayList<Persona> aLstEmplClient) {
    if (aLstEmplClient.isEmpty()) {
      uyc.impln("No hay ningún empleado/cliente en la lista\n",
                "Añada alguno primero");
      return;
    }
    
    System.out.format("%7s%5s%25s%7s%25s%12s%12s%12s%15s%12s\n", CABEZATABLA);
    uyc.impln(uyc.Subraya(132,"="));
    int intContador = 0;
    String strCabecera = "", strCabeceraV = "";
   
    for (Persona e : aLstEmplClient) {
      intContador++;
      /*Miramos de qué tipo es para ver qué campos imprimimos y con qué separación.
        Aquí, en esta función, lo he hecho de la forma cutre comprobando cada
        objeto con "instanceof" para escoger un casting diferente en cada
        situación
        Si en el ArrayList sólo hubiera Empleados, podría haber utilizado
        polimorfismo: hacer la clase Empleado abstracta y rellenar los métodos
        en las subclases. Y luego, sin hacer ningún casting, llamar a la clase
        Empleados con el método en cuestión. Pero todo esto si solo usara clase
        Empleado.
        En el método que ordena los objetos según el nombre, para lo cual tengo
        que consultar getNombre, voy a usar otra forma de hacerlo (aquí la dejo
        tal cual, ya está hecha), usando un PATRÓN VISITANTE.
      */
      if(e instanceof Administracion) {
        strCabecera = "Admin.";
        if (strCabecera == strCabeceraV) {strCabecera = "";}
        else {strCabeceraV = strCabecera;}
                         
        System.out.format("%7s%5s%25s%7s%25s%12s%51s\n", strCabecera, intContador,
        /*Hay que hacer un (cast) para poder llamar a los métodos de cada
          objeto específico. Para las variables de Empleado (nombre, dni,
          sueldoBruto), tal vez debería hacer el cast a Empleado aunque dé el
          mismo resultado, por formalidad...*/
        /*Cuando haya un campo muy largo y que sobresaldría del espacio designado
          en la tabla, uso una regex para que corte en el carácter máximo - 3 y
          ponga 3 puntos en los últimos caracteres. Por ejemplo, en la primera
          regex del getNombre, cuyo campo tiene 25 espacios en la tabla, le doy
          como máximo una longitud de 23 antes de quitarle los 3 últimos
          caracteres y sustituirlos con puntos. Esta regex sólo actúa si hay más
          de 23 caracteres, lo cual queda reflejado con el {20} y {3,}. Este es
          un punto importante que permite manejar la regex, porque si la regla
          fuera simplemente "corta a partir del 20", se podría dar el caso de un
          string de 21, 22 o 23 caracteres, que cabrían perfectamente, pero que,
          con la regla formulada de alguna otra forma no tan precisa como la
          reex permite, podría llevar a sustituir los caracteres que caben por
          puntos suspensivos*/
          ((Administracion) e).getNombre().replaceAll("(.{20})(.{3,})","$1..."),
          ((Administracion) e).getDni(),
          ((Administracion) e).getSeccion().replaceAll("(.{20})(.{3,})","$1..."),
          ((Administracion) e).getSueldoBruto(), ((Administracion) e).SueldoNeto());
        
      } else if(e instanceof Direccion) {
        strCabecera = "Direc.";
        if (strCabecera == strCabeceraV) {strCabecera = "";}
        else {strCabeceraV = strCabecera;}
        
        System.out.format("%7s%5s%25s%7s%37s%24s%27s\n", strCabecera, intContador,
          ((Direccion) e).getNombre().replaceAll("(.{20})(.{3,})","$1..."),
          ((Direccion) e).getDni(), ((Direccion) e).getSueldoBruto(),
          ((Direccion) e).getStockOptions(), ((Direccion) e).SueldoNeto());
        
      } else if(e instanceof Gerente) {
        strCabecera = "Geren.";
        if (strCabecera == strCabeceraV) {strCabecera = "";}
        else {strCabeceraV = strCabecera;}

        System.out.format("%7s%5s%25s%7s%25s%12s%12s%39s\n", strCabecera, intContador,
          ((Gerente) e).getNombre().replaceAll("(.{20})(.{3,})","$1..."),
          ((Gerente) e).getDni(), 
          ((Gerente) e).getDepartamento().replaceAll("(.{20})(.{3,})","$1..."), 
          ((Gerente) e).getSueldoBruto(), ((Gerente) e).getDietas(),
          ((Gerente) e).SueldoNeto());
        
      } else if(e instanceof Cliente) {
        strCabecera = "Clien.";
        if (strCabecera == strCabeceraV) {
          strCabecera = "";}
        else {
          strCabeceraV = strCabecera;
          uyc.impln(uyc.Subraya(132,"-"));
        }
        
        System.out.format("%7s%5s%25s%32s%51s\n", strCabecera, intContador,
          ((Cliente) e).getNombre().replaceAll("(.{20})(.{3,})","$1..."),
          ((Cliente) e).getEmail().replaceAll("(.{20})(.{3,})","$1..."),
          ((Cliente) e).getTipo().replaceAll("(.{13})(.{3,})","$1..."));
      }
    }
    uyc.impln(uyc.Subraya(132, "="));
  }

  
  /****************************************************************************/
  /*                         ELEGIR DE LA LISTA                               */
  /****************************************************************************/  
  /*Función para elegir un EmplClient, tanto para borrarlo como para que nos
    salude*/
  static int ElegirEmplClient(ArrayList<Persona> aLstEmplClient) {
    int intIndiceMax = aLstEmplClient.size();
    if (intIndiceMax == 0) return -1;
    String strEleccion; int intEleccion = 0;
    
    uyc.imp("Elija un número de empleado/cliente (1-", uyc.abc(intIndiceMax), ") o S para salir: ");
    strEleccion = scnEntrada.nextLine().trim();
    if (strEleccion.equals("S")) return -1;
    /*Si no es "S", habrá de ser un entero. Utilizamos funciones ya creadas*/
    intEleccion = uyc.Elegir(strEleccion, intIndiceMax);
    return intEleccion;
  }
  
  
  /****************************************************************************/
  /*                                SALUDAR                                   */
  /****************************************************************************/
  static void SaludoEmplClient(ArrayList<Persona> aLstEmplClient) {
    /*Imprimimos por pantalla la lista de Empleados y trabajadores para ver
      quien deseamos elegir*/
    ImprimeEmplClient(aLstEmplClient);    
    boolean bolSalir = false;
    do {
      int intEleccion = ElegirEmplClient(aLstEmplClient) - 1;
      
      /*Para saber quien es (Cliente o Empleado, y tipo), usamos nuevamente el
        patrón visitante, en un tercer tipo de visita*/
      if (intEleccion < 0) break;
      
      /*AQUÍ TENGO 2 OPCIONES*/
      /*OPCIÓN 1*/
      /*+ Que la clase Persona.java implemente la interfaz IfcSaludar,
        + con lo cual he de @Override Saludo() en la l.138 de Empleado.java
        |--> Como resultado, puedo llamar a Saludo desde la superclase Persona
             y cada miembro saludará de su forma particular. Para ello, mientras
             voy recorriendo el ArrayList<Persona>, declaro cada elemento como
             lo que es, un miembro de la clase Persona.
             Obvio. Lo he probado pero lo dejo comentado.
      */
      //Persona persona = aLstEmplClient.get(intEleccion);
      //persona.Saludo();    
     
      /*OPCIÓN 2*/
      /*+ Llamar al método desde el elemento de la clase, lo que en principio
          se pedía. No obstante, como yo he "mezclado" Empleados y Clientes en
          un mismo array, definiendo una superclase Persona, para saber degradar
          esa clase a cada clase hija para cada elemento en particular del
          ArrayList, o sea, para saber si es, no sólo un Empleado (ya que no
          llamo a Saludo() desde Empleado), sino un objeto de la subclase
          Direccion, Gerente o Administracion, o un objeto de la clase Cliente,
          habré de aplicar un PATRÓN VISITANTE para obtener cada tipo de objeto
          particular que llamará a su método sobreescrito particularmente. Así,
          la llamada será directa. Procedo así, con un patrón de visita definido
          como tipo 3
      */
      IfcPersonaVisitante pv = new PersonaVisitante();
      aLstEmplClient.get(intEleccion).aceptar3(pv);      
  
      uyc.impln("");
    }while(!bolSalir);
  }    

  
  /****************************************************************************/
  /*                      BORRAR EMPLEADO/CLIENTE                             */
  /****************************************************************************/  
  static ArrayList<Persona> BorrarEmplClient (ArrayList<Persona> aLstEmplClient) {
   /*Imprimimos por pantalla la lista de Empleados y trabajadores para ver
     quien deseamos elegir*/
    ImprimeEmplClient(aLstEmplClient);
    boolean bolSalir = false;
    do {
      int intEleccion = ElegirEmplClient(aLstEmplClient) - 1;
      if (intEleccion < 0) break;
      aLstEmplClient.remove(intEleccion);
      /*Después de borrar el registro del ArrayList, hemos de guardar los
        cambios en el archivo*/
      uyc.imp("Registro borrado y "); 
      GuardarEmplClient(aLstEmplClient);
      /*Hay que volver a imprimir, porque los números de registros ya no son
        los mismos*/
      uyc.impln("");
      ImprimeEmplClient(aLstEmplClient);
    }while(!bolSalir);
      
  /*No hay que ordenar nada pues los índices del ArrayList se desplazan en orden
    para eliminar ese hueco. Debido a este desplazamiento, muchos índices habrán
    cambiado de lugar y hace falta volver a presentar la lista por si se quiere
    seguir borrando registros*/
    return aLstEmplClient;
  }
}