package ejercicio_03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	// Leer 10 palabras, luego leer una nueva palabra e indicar si la misma ya hab�a sido ingresada en las 10 primeras.
		
	List<String> lista = new ArrayList<>();
	
	Scanner leer = new Scanner(System.in);
	
	for (int i=0; i<10; i++) {
		System.out.print("Ingrese una palabra [" + (i+1) +"]: " );
		lista.add(leer.nextLine());
	}
	
	System.out.print("\nBuscar: ");
	String palabra = leer.nextLine();
	leer.close();
	
	if(lista.contains(palabra)) {
		System.out.println("La palabra est� contenida en la lista");
	}
	else {
		System.out.println("La palabra NO est� contenida en la lista");
	}
	
	}
	
}
