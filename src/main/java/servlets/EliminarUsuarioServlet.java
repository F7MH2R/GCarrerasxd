package servlets;

import controladores.UsuariosJpaController;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

import java.io.IOException;

@WebServlet("/EliminarUsuarioServlet")
public class EliminarUsuarioServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    
    @Resource
    private UserTransaction utx;
    private UsuariosJpaController usuariosController;

    @Override
    public void init() throws ServletException {
        usuariosController = new UsuariosJpaController(utx, emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el ID del usuario a eliminar
            Integer id = Integer.parseInt(request.getParameter("id"));

            // Eliminar el usuario de la base de datos
            usuariosController.destroy(id);

            // Redirigir a la lista de usuarios
            response.sendRedirect("UsuariosServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al eliminar el usuario.");
            request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
        }
    }
}
