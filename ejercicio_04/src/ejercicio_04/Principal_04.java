package ejercicio_04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal_04 {

	public static void main(String[] args) {
		
// Leer un entero y luego una lista de 20 enteros. 
//Guardar los mayores al número inicial y mostrarlos al final. Usar arrays, no otras colecciones.		
		
		System.out.print("Ingrese un numero entero: " );
		Scanner leer = new Scanner(System.in);
		
		
		int[] numeros = new int[20];
		int[] numeros_mayores = new int[0];
		
		int num = Integer.parseInt(leer.nextLine());
		
		for (int i=0; i<20; i++) {
			System.out.print("Ingrese un [" + (i+1) +"]: " );
			numeros[i] = Integer.parseInt(leer.nextLine());
			
			if(numeros[i] > num) {
				System.out.println("El length es: " + numeros_mayores.length);
				numeros_mayores = Arrays.copyOf(numeros_mayores, numeros_mayores.length + 1);
				System.out.println("El length es: " + numeros_mayores.length);
				numeros_mayores[numeros_mayores.length-1] = numeros[i];
				
			}
		}
		
		System.out.println("Numeros ingresados mayores a " + num + ": ");
		
		for(int i=0; i<numeros_mayores.length; i++) {
			System.out.println(numeros_mayores[i]);
		}
		
		
	}
}
