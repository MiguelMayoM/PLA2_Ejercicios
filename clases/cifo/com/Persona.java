package clases.cifo.com;

/*Superclase de Empleado y Cliente, para implementar un PATRÓN VISITANTE*/
public abstract class Persona {/*implements IfcSaludar { /*Opción descrita en línea 533 de Uti.java*/
  public abstract String[] aceptar1(IfcPersonaVisitante pv);
  public abstract String aceptar2(IfcPersonaVisitante pv);
  public abstract void aceptar3(IfcPersonaVisitante pv);
}

/*                    _________________________________
/*                    | interface IfcPersonaVisitante |
/*                    |     |--> visita()             |
/*                    ---------------------------------
/*                                   ^
/*                                   |
/*                   ________________|________________
/*                  |                                 |
/*       ___________|_____________       _____________|_________________________________________
/*       |class abstract Persona |       |class PersonaVisitante implements IfcPersonaVisitante |
/*       |      |-> aceptar()    |       |      @Override visita()                              |
/*       |-----------------------|       |------------------------------------------------------|
/*           |               |
/*           |               |
/*           V               V  
/* |----------------| |----------------|
/* | class Empleado | |class Cliente   |
/* | |->@Override   | | |->@Override   |
/* |    aceptar()   | |    aceptar     |
/* |----------------| |----------------|
/*
*/
