package ejercicio_01;

import java.util.Iterator;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
// Mostrar por consola los 10 primeros n�meros enteros y los 10 primeros n�meros impares
		
		for (int i = 0; i < 11; i++) {
			
			System.out.print(i);
			if( !(i % 2 == 0)) {
				System.out.print(" Es Impar");
			}
			System.out.println("\n");
			
		}
		
		
		
	}

}
