package ejercicio_05a;

import javax.swing.JOptionPane;

public class Empresa {
	
	Empleado[] empleados = new Empleado[20];
	
	
	public void getEmpleados() {
		
		// Listar los empleados indicando: dni, nombre, apellido y sueldo.
		
		String lista = "";
		for(Empleado empleado : empleados) {
			
				if(empleado != null) {
					lista = lista + "DNI: " + empleado.getDni() + " Nombre: " + empleado.getNombre() + " Apellido: " + empleado.getApellido() + " Sueldo: " + empleado.getSueldo() + "\n\n";
				} 
				else {
					break;
				}
		}
		
		System.out.println(lista);
	}
	
	public void cargarEmpleados() {
		
		// Cargar como máx 20 empleados
		
		// int result = JOptionPane.showOptionDialog
		//(parentComponent, message, title, optionType, messageType, icon, options, null);
		int i=0;
		while (i<20) {
		
		String [] botones = {"Administrativo", "Vendedor"};
			
		int resultado = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Agregar Empleado", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, botones, null);
		
		System.out.println(resultado);
		
		switch(resultado) {
	
			// Administrativo
			case 0: 
				System.out.println("Administrativo");
				Administrativo administrativo = new Administrativo();
				this.empleados[i] = administrativo;
				break;
			
			// Vendedor
			case 1: 
				System.out.println("Vendedor");
				Vendedor vendedor = new Vendedor();
				this.empleados[i] = vendedor;
				break;
		}
		
		//0 si 1 no 2 cancelar
		int	opcion = JOptionPane.showConfirmDialog(null, "Agregar otro empleado"); 
		System.out.println(opcion);

		if(opcion != 0) {
			i = 20;
		}
		
		i++;
	    }
	}

}


