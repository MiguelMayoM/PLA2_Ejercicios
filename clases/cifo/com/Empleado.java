package clases.cifo.com;

/*
  ENUNCIADOS
  ==========
  + Ej A: CLASES. Crear una clase para guardar los empleados de la empresa.
          Tiene que tener las propiedades nombre, dni y sueldo.
          Añadiremos un método sueldoNeto que nos devolverá el sueldo
          multiplicado por 0.85
  + Ej B: CONSTRUCTORES. A la clase anterior de empleado añadir un constructor
          que pida obligatoriamente el nombre y el dni.
  + Ej C: MODIFICADORES ACCESO. Cambiar los modificadores de la clase empleado
          para que sean privados.
          Añadir getters y setters para poder acceder a las propiedades.
          Añadir un método privado getIRPF que devuelva 0.85 si el sueldo es
          menor de 3000 y 0.75 en caso contrario. Modificar el método sueldoNeto
          para que multiplique por getIRPF en vez de por 0.85
  + Ej D: PAQUETES. En el proyecto donde hayas creado la clase empleado crea un
          proyecto llamado: clases.cifo.com
          Arrastra ahí la clase empleado y añade una clase program con main para
          poder usar y probar la clase empleado.
  + Ej E: HERENCIA. Crear de la clase empleado las siguientes clases derivadas:
            - Administracion: Incluye la propiedad 'Seccion' 
            - Gerente: Incluye la propiedad Departamento y Dietas (entero).
                       Modifica el método sueldoNeto llamando a la función
                       sueldoNeto de la clase madre y sumándole las dietas
            - Direccion: Incluye la propiedad StockOptions (entero)
                         Modifica el método sueldoNeto llamando a la función
                         sueldoNeto de la clase madre y sumándole las StockOption
                         multiplicadas por 0.1
  + Ej F: INTERFACES 
          - En el proyecto que ya tenemos vamos a crear una clase nueva 
            'Clientes' con propiedades:
            nombre, email, tipo (todas de tipo string, privadas y con getters y
            setters)
          - Vamos a crear una Interfaz que obligue a implementar el método 'Saludo'.
            Todas las clases deben implementar ese interfaz y por lo tanto escribir
            código para el método. Simplemente que devuelvan un string con
            diferentes saludos.
*/

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/*Antes había creado la clase Empleado en el mismo archivo que la clase con el
  nombre del ejercicio, como vengo haciendo (PLA2_ejA_Clases.java). Esta segunda
  clase, la del ejercicio, tenía el "main", que en este ejercicio hemos de llamar
  en realidad como "Programa", ya que tiene el sentido que allí tendremos el "main"
  desde el cual ejecutar el programa.
  En principio se pone una clase en cada archivo (como es obvio para tener bien
  ordenado y estructurado el código, para una buena manutención y reutilización),
  pero, si es necesario, puedo poner más de una, siempre y cuando sólo haya una
  clase pública "externa" (la de mayor nivel de jerarquía) por archivo .java,
  la que tendrá el main, si es que quiero tenerlo (aquí por ejemplo no uso)*/
/*Puedo quitar public y dejar sin modificador (o sea, default) porque la clase
  main que utiliza esta clase Empleado está en el mismo paquete. Si no se pone
  nada, cuenta como "default", que permite accesibilidad dentro de un mismo
  paquete. En principio, todo lo que no sea private o protected, lo voy a dejar
  default, pues no necesito que sea accesible desde fuera del paquete de trabajo
  en el que se hallan estas dos clases, para así seguir el:
  "Principio de acceso más restrictivo que tenga sentido"*/
/*No obstante, las interfaces son públicas por defecto y cuando se sobreescribe
  un método no se puede hacer con un método que tenga un modificador que sea más
  restrictivo que el de la interface, así que voy a ir con cuidado y a usar
  muchos public*/

public class Empleado extends Persona {/*implements IfcSaludar { /*Aquí no sirve
    de nada y obligaría a implementar el método en esta clase sin que se vaya a
    usar, puesto que lo iba a sobreescribir en cada una de las clases, pues
    quiero que sean ellas las que me saluden y no la Empleado con un saludo
    genérico "Hola soy un Emplead@".
    Nomenclautra variables clase: Pongo los nombres sin las tres primeras letras
    con abreviatura del tipo ("str", "dbl"...) para cuando cree los getters y
    setter, que sigan la convención de  get+"nombreVariable" y que se vea
    getNombre y no getstrNombre ni getStrNombre*/
  /*"private" accesible sólo dentro de esta clase (excluye también a las clases
    hijas), para métodos o miembros de datos. Clases e interfaces no pueden ser
    "private" si son principales, si no están anidadas/interiores a otra*/
  /*Pensaba que tendría que usar protected para poder ser heredadas, pero tengo
    los setters y getters, que no son privados*/
  private String nombre, dni;
  private BigDecimal sueldoBruto;
  
  /*Método no "private" porque se supone que se usará desde la clase "progama",
    ya que el enunciado dice que ha de devolver un valor y aquí no se almacena
    en esta clase. Si fuera "void y sólo se imprimiera el valor por pantalla,
    entonces podría ser private. Como lo dejo "default", tiene alcance de paquete,
    que para lo que hacemos ya está bien. Con "public", alcanzaría fuera del
    paquete*/
  BigDecimal SueldoNeto() {
    return sueldoBruto.multiply(this.getIRPF()).setScale(2, RoundingMode.HALF_EVEN);
  }
  
  private BigDecimal getIRPF() {
    BigDecimal bdcTramoIRPF = new BigDecimal("3000");
    if (sueldoBruto.compareTo(bdcTramoIRPF) == -1) {
      return new BigDecimal("0.75");
    } else {
      return new BigDecimal("0.85");
    }
  }
  
  /*Setters y getters public o default, para que sean accesibles desde la clase
    "progama"*/
  /*"this" es para desambiguar si variable de instancia y variable del valor que
    se le da se llaman igual, e.g. nombre = nombre, daría error. En este caso
    habría que hacer:*/
  //void setNombre(String strNombre) {this.nombre = nombre;}
  /*Yo le pongo nombre diferente y así no hay ambigüedad y vale sin el this*/
  void setNombre (String strNombre){nombre = strNombre;}
  String getNombre() {return nombre;}
  void setDni (String strDni) {dni = strDni;} 
  String getDni () {return dni;}
  
  void setSueldoBruto (BigDecimal bdcSueldoBruto) {sueldoBruto = bdcSueldoBruto;}
  BigDecimal getSueldoBruto() {return sueldoBruto.setScale(2, RoundingMode.HALF_EVEN);}
  
  Empleado(String strNombre, String strDNI, BigDecimal bdcSueldoBruto) {
    nombre = strNombre;
    dni = strDNI;
    sueldoBruto = bdcSueldoBruto;
  }
  
  /*Los métodos de una interfaz son por defecto públicos, así que no puedo
    implementarlos con un modificador de acceso más restrictivo aquí, como
    default. Por otra parte, una interfaz de jerarquía superior (que dé nombre
    a su archivo .java) no puede ser ni private ni protected, como ocurre con
    las clases. Como aquellas, sólo puede tener estos modificadores de acceso
    si están anidadas/si son internas a otras*/
  /*Si no voy a usar directamente la clase Empleado, sino que esta es un nexo
    común para no repetir código y poder crear de forma limpia y cómoda las
    subclases Administrativo, Gerente y Director, entonces no haría falta
    implementar aquí el método saludo porque no se iba a utilizar. Por eso no
    ponemos implements en la clase Empleado sino en las hijas*/
  
  /*Opción descrita en línea 533 de Uti.java*/
  //@Override 
  //public void Saludo(){}

  @Override
  public String[] aceptar1(IfcPersonaVisitante pv) {
    return pv.visita1(this);
  } 

  @Override
  public String aceptar2(IfcPersonaVisitante pv) {
    //return pv.visita2(this);
    return "";
  }
  /*Empleado is not abstract and does not override abstract method aceptar3 (IfcPersonaVisitante) in Persona*/
  @Override
  public void aceptar3(IfcPersonaVisitante pv) {
    //pv.visita3(this);
  }  
}

/***********/
/*SUBCLASES*/
/***********/
/*Las pongo en el mismo archivo Java, por la relación existente. Sin embargo, la
  clase Clientes tiene su propio archivo dentro del mismo paquete*/

/*ADMINISTRACIÓN*/
/*--------------*/
class Administracion extends Empleado implements IfcSaludar {
  private String seccion;
  /*setters y getters para la nueva variable privada. Las variables privadas
    de la superclase no se heredan pero son accesibles por sus getters y setters,
    que no son privados*/
  void setSeccion (String strNombre){seccion = strNombre;}
  String getSeccion() {return seccion;}
  
  /*Una subclase hereda variables, métodos y clases anidadas (que no sean privados),
    pero no los constructores, que deben ser por ello invocados desde la subclase*/
  Administracion(String strNombre, String strDni, BigDecimal bdcSueldoBruto, String strSeccion) {
    /*Cuando la superclase tiene un constructor con argumentos, es obligatorio
      llamar explícitamente a super con argumentos*/
    super(strNombre, strDni, bdcSueldoBruto);
    seccion = strSeccion;
  }
  
  @Override
  public void Saludo() {
    System.out.println("Buenos días, trabajo en Administración");
  }

  @Override
  public String aceptar2(IfcPersonaVisitante pv) {
    return pv.visita2(this);
  }  

  @Override
  public void aceptar3(IfcPersonaVisitante pv) {
    pv.visita3(this);
  }
}

/*GERENTE*/
/*-------*/
class Gerente extends Empleado implements IfcSaludar{ //, IfcPersona {
  private String departamento;
  /*Yo he usado BigDecimal para las cantidades*/
  private BigDecimal dietas;

  /*Setters y getteres para las nuevas variables privadas*/
  void setDepartamento (String strNombre){departamento = strNombre;}
  String getDepartamento() {return departamento;}  
  void setDietas (BigDecimal strDietas){dietas = strDietas;}
  BigDecimal getDietas() {return dietas.setScale(2, RoundingMode.HALF_EVEN);}  
  
  Gerente(String strNombre, String strDni, BigDecimal bdcSueldoBruto, String strDepartamento, BigDecimal bdcDietas) {
    super(strNombre, strDni, bdcSueldoBruto);
    departamento = strDepartamento;
    dietas = bdcDietas;
  }
  
  /*Modifica el método sueldoNeto llamando a la función sueldoNeto de la clase
    madre y sumándole las dietas. Entiendo que se le suman al sueldo neto, no
    antes al sueldo bruto*/
  @Override
  BigDecimal SueldoNeto() {
    return super.SueldoNeto().add(dietas).setScale(2, RoundingMode.HALF_EVEN);
  }
  
  @Override
  public void Saludo() {
    System.out.println("Buenos días, soy un/a Gerente");
  }

  @Override
  public String aceptar2(IfcPersonaVisitante pv) {
    return pv.visita2(this);
  }  
  
  @Override
  public void aceptar3(IfcPersonaVisitante pv) {
    pv.visita3(this);
  }  
}

/*DIRECCIÓN*/
/*---------*/
class Direccion extends Empleado implements IfcSaludar {//, IfcPersona {
  private BigDecimal stockOptions;

  /*Setter y getter para las nuevas variables privadas*/
  void setStockOptions (BigDecimal bdcStockOptions) {stockOptions = bdcStockOptions;}
  BigDecimal getStockOptions() {return stockOptions.setScale(2, RoundingMode.HALF_EVEN);}  
  
  Direccion(String strNombre, String strDni, BigDecimal bdcSueldoBruto, BigDecimal bdcStockOptions) {
    super(strNombre, strDni, bdcSueldoBruto);
    stockOptions = bdcStockOptions;
  }
  
  /*Modifica el método sueldoNeto llamando a la función sueldoNeto de la clase
    madre y sumándole las StockOption multiplicadas por 0.1*/
  @Override
  BigDecimal SueldoNeto() {
    return super.SueldoNeto().add(stockOptions.multiply(new BigDecimal(0.1))).setScale(2, RoundingMode.HALF_EVEN);
  }
  
  @Override
  public void Saludo() {
    System.out.println("Buenos días, soy el/la Director/a");
  }
  
  @Override
  public String aceptar2(IfcPersonaVisitante pv) {
    return pv.visita2(this);
  }
  
  @Override
  public void aceptar3(IfcPersonaVisitante pv) {
    pv.visita3(this);
  }
}