package clases.cifo.com;

/*Por defecto los métodos de una interfaz son públicos, ya que la idea de la
  interfaz es comunicarse con otras clases e interfaces. Así que, poner public o
  no poner nada a los métodos, es lo mismo. Lo mismo con abstract, aunque no se
  ponga nada, los métodos ya son abtract. No sólo es que sea lo mismo, es que se
  recomienda no ser redundante y, por lo tanto, no poner ni abstract ni public a
  los métodos.
  Por otra parte, si la inteface se declara public, como aquí, ha de vivir en un
  archivo específico para ella, como es el caso y como ocurre con cualquier clase.
  Sin public, podría estar en otro archivo como en el de Empleados. Como las
  clases, las interfaces no pueden ser private o protected, a menos que sean 
  internas, que estén anidadas*/
/*Si hubiera variables en la interfaz, estas no son de instancia puesto que no
  se pueden crear objetos a partir de una interfaz. Implícitament son public,
  final y static y han de inicializarse, por ello. Y por eso a veces se usan las
  interfaces para guardar constantes, aunque su uso a tal efecto es controvertido*/
public interface IfcSaludar {
  void Saludo();
}
