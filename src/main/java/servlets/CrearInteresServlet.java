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
import modelo.Usuarios;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/CrearInteresServlet")
public class CrearInteresServlet extends HttpServlet {

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
            Usuarios usuario = (Usuarios) session.getAttribute("usuario");

            if (usuario != null) {
                int idCarrera = Integer.parseInt(request.getParameter("idCarrera"));

                // Crear nuevo interés
                Intereses interes = new Intereses();
                interes.setIdUsuario(usuario.getIdUsuario());
                interes.setIdCarrera(idCarrera);
                interes.setFechaSeleccion(java.sql.Date.valueOf(LocalDate.now()));

                // Guardar interés en la base de datos
                interesesController.create(interes);
            }

            // Redirigir a la lista de carreras
            response.sendRedirect("CarrerasServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Vistas/error.jsp");
        }
    }
}
