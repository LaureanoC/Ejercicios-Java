package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Persona;
import logic.Login;
/**
 * Servlet implementation class SignIn
 */
@WebServlet("/signin")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Login ctrl = new Login();
		
		Persona p = new Persona();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// validar email y password
		
		p.setEmail(email);
		p.setPassword(password);
		
		p = ctrl.validate(p);
		LinkedList<Persona> personas = ctrl.getAll();
		
		// Guardamos el usuario en la session (permanece mientras dure la session del usuario)
		// Ocupa memoria en lado del servidor
		
		request.getSession().setAttribute("usuario", p);
		
		// registramos el listado de personas en la request (permanece en la pagina redirigida)
		
		request.setAttribute("listaPersonas", personas);
		
		// Forward envia a traves del propio servlet la trae y la devuelve en la misma url
		// Redirect dirigite a esta otra pagina por lo que hay que pasar la info
		request.getRequestDispatcher("WEB-INF/UserManagement.jsp").forward(request, response);;
		
		
		
	}

}
