package servlets;

import controladores.InteresesJpaController;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import modelo.Intereses;

import java.io.IOException;

@WebServlet("/EliminarInteresServlet")
public class EliminarInteresServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private InteresesJpaController interesesController;

    @Override
    public void init() throws ServletException {
        interesesController = new InteresesJpaController(utx, emf);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el usuario de la sesión
            HttpSession session = request.getSession();
            int idUsuario = (int) session.getAttribute("idUsuario");

            int idCarrera = Integer.parseInt(request.getParameter("idCarrera"));

            // Buscar interés para eliminar
            Intereses interes = interesesController.findInteresByUsuarioAndCarrera(idUsuario, idCarrera);

            if (interes != null) {
                // Eliminar interés
                interesesController.destroy(interes.getIdInteres());
            }

            // Redirigir a la lista de carreras
            response.sendRedirect("CarrerasServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Vistas/error.jsp");
        }
    }
}
