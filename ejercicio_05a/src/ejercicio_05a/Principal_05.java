package ejercicio_05a;

public class Principal_05 {

	public static void main(String[] args) {
		
/*Enunciado
  *Cargar una lista de empleados (máximo 20) preguntado en cada uno si son administrativos o 
  *vendedores y cargar todos los datos respectivos.
  *Luego listar los empleados indicando: dni, nombre, apellido y sueldo.
*/	
		Empresa e = new Empresa();
		e.cargarEmpleados();
		
		
	}
	
}

/*Consulta
 * 
 * Mi duda es con respecto a un método de la clase Empresa que llamé cargarEmpleados().
 * 
   Debo pedir que quien ingrese los datos sea por teclado o directamente pongo yo unos valores predeterminados? 
   Es decir, cuál de los dos constructores uso? (el constructor de empleado está sobrecargado)
   Uno con parametros y el otro te pide ingresar los datos por teclado sin parametros
 * 
 * */
