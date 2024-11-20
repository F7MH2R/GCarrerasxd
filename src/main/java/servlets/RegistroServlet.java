package servlets;

import controladores.UsuariosJpaController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.UserTransaction;
import modelo.Usuarios;

import java.io.IOException;

@WebServlet("/RegistroServlet")
public class RegistroServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cargar la vista de registro.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/registro.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String edadParam = request.getParameter("edad");
        String region = request.getParameter("region");

        Integer edad = null;
        if (edadParam != null && !edadParam.isEmpty()) {
            edad = Integer.parseInt(edadParam);
        }

        Usuarios nuevoUsuario = new Usuarios(nombre, apellido, email, edad, region);

        UsuariosJpaController usuariosController = new UsuariosJpaController(utx, emf);

        try {
            System.out.println("Intentando persistir el usuario: " + nuevoUsuario);
            usuariosController.create(nuevoUsuario);
            System.out.println("Usuario persistido con éxito");

            // Redirigir al LoginServlet tras un registro exitoso
            response.sendRedirect("LoginServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al registrar usuario.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/registro.jsp");
            dispatcher.forward(request, response);
        }
    }
}
