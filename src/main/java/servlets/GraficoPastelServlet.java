package servlets;

import com.google.gson.Gson;
import controladores.CarrerasJpaController;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Carreras;

@WebServlet("/GraficoPastelServlet")
public class GraficoPastelServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InteresesJpaController interesesController = new InteresesJpaController(utx, emf);

        try {
            // Obtener los datos de interés por carrera
            List<Object[]> interesesPorCarrera = interesesController.countInteresesByCarrera();
            List<String> nombresCarreras = new ArrayList<>();
            List<Integer> valores = new ArrayList<>();
            List<Double> porcentajes = new ArrayList<>();

            // Calcular totales y porcentajes
            int totalIntereses = 0;
            for (Object[] fila : interesesPorCarrera) {
                totalIntereses += ((Number) fila[1]).intValue();
            }

            for (Object[] fila : interesesPorCarrera) {
                String carrera = (String) fila[0];
                int count = ((Number) fila[1]).intValue();
                double porcentaje = (double) count / totalIntereses * 100;

                // Redondear a dos decimales
                porcentaje = Math.round(porcentaje * 100.0) / 100.0;

                nombresCarreras.add(carrera);
                valores.add(count);
                porcentajes.add(porcentaje);
            }

            // Imprimir datos en consola para verificar
            System.out.println("Datos de Intereses por Carrera:");
            System.out.println("Nombres de Carreras: " + nombresCarreras);
            System.out.println("Valores (Cantidad de Intereses): " + valores);
            System.out.println("Porcentajes: " + porcentajes);

            // Pasar los datos al JSP
            Gson gson = new Gson();
            request.setAttribute("nombresCarreras", gson.toJson(nombresCarreras));
            request.setAttribute("valores", gson.toJson(valores));
            request.setAttribute("porcentajes", gson.toJson(porcentajes));

            // Redirigir al JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("Vistas/graficoPastel.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
