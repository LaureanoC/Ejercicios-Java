package ui;

import java.util.LinkedList;
import java.util.Scanner;

import entities.*;
import logic.Login;

public class Menu {
	Scanner s = null;
	Login ctrlLogin = new Login();

	public void start() {
		s = new Scanner(System.in);
		Persona p = login();
		System.out.println("Bienvenido " + p.getNombre() + " " + p.getApellido());
		System.out.println();

		String command;
		do {
			command = getCommand();
			executeCommand(command);
			System.out.println();

		} while (!command.equalsIgnoreCase("exit"));

		s.close();
	}

	private void executeCommand(String command) {
		switch (command) {
		case "list":
			System.out.println(ctrlLogin.getAll());
			break;
		case "find":
			System.out.println(find());
			break;
		case "search":
			System.out.println(ctrlLogin.getAllBySurname());
			break;
		case "new":
			crearUsuario();
			break;
		case "edit":
			actualizarUsuario();
			break;
		case "delete":
			borrarUsuario();
			break;
		default:
			break;
		}
	}

	private String getCommand() {
		System.out.println("Ingrese el comando según la opción que desee realizar");
		System.out.println("list\t\tlistar todos");
		System.out.println("find\t\tbuscar por tipo y nro de documento"); // solo debe devolver 1
		System.out.println("search\t\tlistar por apellido"); // puede devolver varios
		System.out.println("new\t\tcrea una nueva persona y asigna un rol existente");
		System.out.println("edit\t\tbusca por tipo y nro de documento y actualiza todos los datos");
		System.out.println("delete\t\tborra por tipo y nro de documento");
		System.out.println();
		System.out.print("comando: ");
		return s.nextLine();
	}

	public Persona login() {
		Persona p = new Persona();

		System.out.print("Email: ");
		p.setEmail(s.nextLine());

		System.out.print("password: ");
		p.setPassword(s.nextLine());

		p = ctrlLogin.validate(p);

		return p;

	}

	private Persona find() {
		System.out.println();
		Persona p = new Persona();
		Documento d = new Documento();
		p.setDocumento(d);
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());

		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());

		return ctrlLogin.getByDocumento(p);
	}

	private void crearUsuario() {

		System.out.println("-------- CREAR UN NUEVO USUARIO ----------");
		Persona p = new Persona();
		p = setearPersona(p);
		ctrlLogin.newUser(p);
		System.out.println("El usuario ha sido registrado.");

	}

	private Persona setearPersona(Persona p) {

		Documento d = new Documento();
		p.setDocumento(d); // Le asigno la posición de memoria del documento al doc de persona

		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());

		System.out.print("Nombre: ");
		p.setNombre(s.nextLine());
		System.out.print("Apellido: ");
		p.setApellido(s.nextLine());
		System.out.print("Telefono ");
		p.setTel(s.nextLine());

		System.out.print("Email: ");
		p.setEmail(s.nextLine());

		System.out.print("Contraseña: ");
		p.setPassword(s.nextLine());

		System.out.print("Habilitado True/False: ");
		p.setHabilitado(Boolean.parseBoolean(s.nextLine()));

		// Roles existentes para agregarle al usuario

		LinkedList<Rol> roles = ctrlLogin.getAllRol();

		System.out.println("Introducir id rol -- exit para salir");
		System.out.println(roles);
		String i = "";
		while (!i.equals("exit")) {
			switch (i) {
			case "1":
				// Si no lo tiene lo agrego
				if (!p.hasRol(roles.get(0))) {
					p.addRol(roles.get(0));
				}

				break;

			case "2":
				if (!p.hasRol(roles.get(1))) {
					p.addRol(roles.get(1));
				}
				break;
			}

			i = s.nextLine();

		}
		return p;
	}

	private void actualizarUsuario() {
		Persona p = find();
		p.removeRoles();
		System.out.println(p);
		p = setearPersona(p);
		ctrlLogin.updateUser(p);
		System.out.println("El usuario ha sido actualizado");
	}

	private void borrarUsuario() {
		System.out.println("------- ELIMINAR USUARIO ---------");
		Persona p = find();
		ctrlLogin.deleteUser(p);
		System.out.println("El usuario ha sido eliminado.");
	}

}