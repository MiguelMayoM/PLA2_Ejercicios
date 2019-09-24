package clases.cifo.com;

public class Cliente extends Persona implements IfcSaludar {
  private String nombre, email, tipo;
  
  void setNombre(String strNombre) {nombre = strNombre;}
  String getNombre() {return nombre;}
  void setEmail(String strEmail) {email = strEmail;}
  String getEmail() {return email;}
  void setTipo(String strTipo) {tipo = strTipo;}
  String getTipo() {return tipo;}
  
  Cliente(String strNombre, String strEmail, String strTipo){
    nombre = strNombre;
    email = strEmail;
    tipo = strTipo;
  }

  @Override
  public void Saludo() {
    System.out.println("Buenos días, soy un/a cliente");
  }
 
  @Override
  public String[] aceptar1(IfcPersonaVisitante pv) {
    return pv.visita1(this);
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