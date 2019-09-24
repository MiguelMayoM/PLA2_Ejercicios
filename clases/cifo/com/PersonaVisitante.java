package clases.cifo.com;

/*PATRÓN VISITANTE*/
public class PersonaVisitante implements IfcPersonaVisitante{

  /*----------------*/
  /* Visita tipo 1: */
  /*----------------*/
  /*Casting para llamar al getter específico de Empleado o Cliente para
    realizar la comparación de datos y ordenar*/
  @Override
  public String[] visita1 (Empleado pv) {
    return new String[] {pv.getNombre(),pv.getDni()};
  }
  @Override
  public String[] visita1 (Cliente pv) {
    return new String[] {pv.getNombre(),pv.getEmail()};
  }
  
  /*----------------*/
  /* Visita tipo 2: */
  /*----------------*/
  /*Casting para obtener los getters particulares de cada objeto y poder
    sacar la información necesaria para guardarla al archivo*/
  @Override
  public String visita2 (Direccion pv) {
    System.out.println("D: " + pv.getNombre());
    return "\"D\",\"" + pv.getNombre().split(" ",2)[1] + "," + pv.getNombre().split(" ",2)[0] +
           "\",\"" + pv.getDni() + 
           "\",\"" + pv.getSueldoBruto() + "\",\"" + pv.getStockOptions() + "\"\n";
  }  
  @Override
  public String visita2 (Gerente pv) {
    System.out.println("G: "+ pv.getNombre());
    return "\"G\",\"" + pv.getNombre().split(" ",2)[1] + "," + pv.getNombre().split(" ",2)[0] +
           "\",\"" + pv.getDni() + "\",\"" + pv.getSueldoBruto() + 
           "\",\"" + pv.getDepartamento()  + "," + pv.getDietas() + "\"\n";
  }   
  @Override
  public String visita2 (Administracion pv) {
    return "\"A\",\"" + pv.getNombre().split(" ",2)[1] + "," + pv.getNombre().split(" ",2)[0] + 
           "\",\"" + pv.getDni() + "\",\"" + pv.getSueldoBruto() +
           "\",\"" + pv.getSeccion() + "\"\n";
  }   
  @Override
  public String visita2 (Cliente pv) {
    return "\"C\",\"" + pv.getNombre().split(" ",2)[1] + "," + pv.getNombre().split(" ",2)[0] +
           "\",\"" + pv.getEmail() +
           "\",\"" + pv.getTipo() + "\"\n";
  }
  //@Override
  //public String visita2(Empleado pv) {
  //  return "";
  //}
  
  /*----------------*/
  /* Visita tipo 3: */
  /*----------------*/
  /*Casting para llamar al método Saludo() específico de cada objeto*/
  @Override
  public void visita3 (Direccion pv) {pv.Saludo();}
  public void visita3 (Gerente pv) {pv.Saludo();}
  public void visita3 (Administracion pv) {pv.Saludo();}
  public void visita3 (Cliente pv) {pv.Saludo();}
  //public void visita3 (Empleado pv) {pv.Saludo();}
}
