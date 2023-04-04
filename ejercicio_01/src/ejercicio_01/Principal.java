package ejercicio_01;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
// Mostrar por consola los 10 primeros números enteros y los 10 primeros números impares
		
		for (int i = 0; i < 11; i++) {
			
			System.out.print(i);
			if( !(i % 2 == 0)) {
				System.out.print(" Es Impar");
			}
			System.out.println("\n");
	
		}
		
		
	}

}
