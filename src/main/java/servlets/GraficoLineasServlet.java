package servlets;

import com.google.gson.Gson;
import controladores.InteresesJpaController;
import controladores.CarrerasJpaController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.transaction.UserTransaction;
import modelo.Intereses;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/GraficoLineasServlet")
public class GraficoLineasServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InteresesJpaController interesesController = new InteresesJpaController(utx, emf);
        CarrerasJpaController carrerasController = new CarrerasJpaController(utx, emf);

        try {
            // Obtener todos los intereses
            List<Intereses> intereses = interesesController.findInteresesEntities();

            // Agrupar por fecha y carrera
            Map<Date, Map<Integer, Long>> agrupados = intereses.stream()
                    .collect(Collectors.groupingBy(Intereses::getFechaSeleccion,
                            Collectors.groupingBy(Intereses::getIdCarrera, Collectors.counting())));

            // Convertir a un formato manejable por el JSP
            List<String> fechas = new ArrayList<>();
            List<Map<String, Object>> datos = new ArrayList<>();

            agrupados.forEach((fecha, conteoPorCarrera) -> {
                fechas.add(fecha.toString());
                conteoPorCarrera.forEach((carreraId, total) -> {
                    Map<String, Object> entrada = new HashMap<>();
                    entrada.put("fecha", fecha.toString());
                    entrada.put("carrera", carrerasController.findCarreras(carreraId).getNombreCarrera());
                    entrada.put("total", total);
                    datos.add(entrada);
                });
            });

            // Enviar datos al JSP
            request.setAttribute("fechas", gson.toJson(fechas));
            request.setAttribute("datos", gson.toJson(datos));

            request.getRequestDispatcher("Vistas/graficoLineas.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar los datos para la gráfica.");
        }
    }
}
