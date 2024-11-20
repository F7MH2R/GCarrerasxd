/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.CarrerasJpaController;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
import modelo.Carreras;

/**
 *
 * @author black
 */


@WebServlet("/AgregarCarreraServlet")
public class AgregarCarreraServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    private CarrerasJpaController carrerasController;

    @Override
    public void init() throws ServletException {
        carrerasController = new CarrerasJpaController(utx, emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Vistas/agregarCarrera.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener datos del formulario
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            // Crear nueva carrera
            Carreras carrera = new Carreras();
            carrera.setNombreCarrera(nombre);
            carrera.setDescripcion(descripcion);

            // Guardar en la base de datos
            carrerasController.create(carrera);

            // Redirigir al listado
            response.sendRedirect("CarrerasServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al agregar la carrera.");
            request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
        }
    }
}
