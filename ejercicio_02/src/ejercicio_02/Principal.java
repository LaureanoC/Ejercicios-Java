package ejercicio_02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	// Leer 10 palabras y mostrarlas en orden inverso al que fueron ingresadas.
		
	List<String> lista = new ArrayList<>();
	
	Scanner leer = new Scanner(System.in);
	
	for (int i=0; i<10; i++) {
		System.out.print("Ingrese una palabra [" + (i+1) +"]: " );
		lista.add(leer.nextLine());
	}
	leer.close();
	Collections.reverse(lista); // Doy vuelta la lista (modifica la var)
	System.out.println(lista);
	
	}
	
}
