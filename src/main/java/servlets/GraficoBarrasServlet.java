package servlets;

import com.google.gson.Gson;
import controladores.InteresesJpaController;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GraficoBarrasServlet")
public class GraficoBarrasServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InteresesJpaController interesesController = new InteresesJpaController(utx, emf);

        try {
            // Obtener los datos de intereses por carrera
            List<Object[]> interesesPorCarrera = interesesController.countInteresesByCarrera();
            List<String> nombresCarreras = new ArrayList<>();
            List<Integer> valores = new ArrayList<>();

            for (Object[] fila : interesesPorCarrera) {
                nombresCarreras.add((String) fila[0]); // Nombre de la carrera
                valores.add(((Number) fila[1]).intValue()); // Cantidad de usuarios interesados
            }

            // Convertir datos a JSON para enviarlos al JSP
            Gson gson = new Gson();
            request.setAttribute("nombresCarreras", gson.toJson(nombresCarreras));
            request.setAttribute("valores", gson.toJson(valores));

            // Redirigir al JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/graficoBarras.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
