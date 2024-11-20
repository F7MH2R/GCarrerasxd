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




@WebServlet("/EditarCarreraServlet")
public class EditarCarreraServlet extends HttpServlet {

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
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Carreras carrera = carrerasController.findCarreras(id);

            if (carrera != null) {
                request.setAttribute("carrera", carrera);
                request.getRequestDispatcher("Vistas/editarCarrera.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Carrera no encontrada.");
                request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar la carrera.");
            request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("idCarrera"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            Carreras carrera = carrerasController.findCarreras(id);
            carrera.setNombreCarrera(nombre);
            carrera.setDescripcion(descripcion);

            carrerasController.edit(carrera);

            response.sendRedirect("CarrerasServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al editar la carrera.");
            request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
        }
    }
}
