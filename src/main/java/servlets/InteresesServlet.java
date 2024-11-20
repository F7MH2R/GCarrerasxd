/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.CarrerasJpaController;
import controladores.InteresesJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import modelo.Carreras;

/**
 *
 * @author black
 */

@WebServlet("/InteresesServlet")
public class InteresesServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private InteresesJpaController interesesController;
    private CarrerasJpaController carrerasController;

    @Override
    public void init() throws ServletException {
        interesesController = new InteresesJpaController(null, emf);
        carrerasController = new CarrerasJpaController(null, emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener ID del usuario de sesión (puede ser modificado según lógica de autenticación)
            Integer idUsuario = (Integer) request.getSession().getAttribute("userId");

            // Obtener lista de carreras
            List<Carreras> listaCarreras = carrerasController.findCarrerasEntities();
            request.setAttribute("listaCarreras", listaCarreras);

            // Obtener lista de intereses del usuario
            List<Integer> interesesUsuario = interesesController.findInteresesByUsuario(idUsuario);
            request.setAttribute("interesesUsuario", interesesUsuario);

            // Redirigir al JSP
            request.getRequestDispatcher("Vistas/carrerasIntereses.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
