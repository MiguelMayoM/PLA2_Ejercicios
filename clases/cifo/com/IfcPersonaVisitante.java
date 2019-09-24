package clases.cifo.com;

public interface IfcPersonaVisitante {
  /*---------------*/
  /*Visita tipo 1: */
  /*---------------*/
  /*En el caso de Empleado, el cast no ha de ser a las subclases, porque aquí
    estoy usando el getNombre y getDNI, que están definidos en Empleado, no en
    las subclases*/
  public String[] visita1 (Empleado pv);
  public String[] visita1 (Cliente pv);

  /*----------------*/
  /* Visita tipo 2: */
  /*----------------*/
  /*Casting para obtener los getters particulares de cada objeto y poder
    sacar la información necesaria para guardarla al archivo*/
  public String visita2 (Direccion pv);  
  public String visita2 (Gerente pv);  
  public String visita2 (Administracion pv);
  public String visita2 (Cliente pv);  
  //public String visita2(Empleado pv);
  
  /*----------------*/
  /* Visita tipo 3: */
  /*----------------*/
  /*Casting para llamar al método Saludo() específico de cada objeto*/
  public void visita3 (Direccion pv);  
  public void visita3 (Gerente pv);  
  public void visita3 (Administracion pv);
  public void visita3 (Cliente pv);  
  //public String visita3(Empleado pv);
}