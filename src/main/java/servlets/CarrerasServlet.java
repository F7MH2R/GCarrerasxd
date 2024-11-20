package servlets;

import controladores.InteresesJpaController;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import modelo.Carreras;
import modelo.Intereses;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@WebServlet("/CarrerasServlet")
public class CarrerasServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el usuario de la sesión
            HttpSession session = request.getSession();
            int idUsuario = (int) session.getAttribute("idUsuario");
            System.out.println("ID del usuario en sesión: " + idUsuario);

            // Obtener intereses del usuario
            List<Intereses> intereses = interesesController.findInteresesByUsuario(idUsuario);
            List<Integer> interesesIds = intereses.stream().map(Intereses::getIdCarrera).collect(Collectors.toList());
            System.out.println("IDs de las carreras de interés del usuario: " + interesesIds);

            // Obtener la lista de carreras
            EntityManager em = emf.createEntityManager();
            List<Carreras> listaCarreras = em.createQuery("SELECT c FROM Carreras c", Carreras.class).getResultList();
            em.close();

            // Log de la lista de carreras
            System.out.println("Lista de carreras obtenida:");
            for (Carreras carrera : listaCarreras) {
                System.out.println("ID: " + carrera.getIdCarrera() + ", Nombre: " + carrera.getNombreCarrera());
            }

            // Pasar datos a la vista
            request.setAttribute("listaCarreras", listaCarreras);
            request.setAttribute("interesesUsuario", interesesIds);

            // Redirigir al JSP
            request.getRequestDispatcher("Vistas/carreras.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Vistas/error.jsp");
        }
    }
}
