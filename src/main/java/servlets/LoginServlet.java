package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import java.io.IOException;
import modelo.Usuarios;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirigir al login.jsp si se accede directamente al servlet
        RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EntityManager em = emf.createEntityManager();
        try {
            // Consulta para encontrar al usuario por email
            Query query = em.createNamedQuery("Usuarios.findByEmail");
            query.setParameter("email", email);
            Usuarios usuario = (Usuarios) query.getSingleResult();

            // Validar credenciales del usuario
            if (usuario != null && password.equals("1234")) { // Cambia esta lógica según tus necesidades
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setAttribute("idUsuario", usuario.getIdUsuario()); // Guardar el ID del usuario en la sesión

                // Redirigir al CarrerasServlet
                response.sendRedirect("CarrerasServlet");
            } else {
                // Credenciales inválidas
                request.setAttribute("error", "Credenciales inválidas.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NoResultException e) {
            // Usuario no encontrado
            request.setAttribute("error", "Usuario no encontrado.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/login.jsp");
            dispatcher.forward(request, response);
        } finally {
            em.close();
        }
    }
}
