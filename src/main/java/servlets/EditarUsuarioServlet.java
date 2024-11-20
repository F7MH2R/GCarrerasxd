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
import modelo.Usuarios;

import java.io.IOException;

@WebServlet("/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private UsuariosJpaController usuariosController;
    
    @Resource
    private UserTransaction utx;

    @Override
    public void init() throws ServletException {
        usuariosController = new UsuariosJpaController(utx, emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el ID del usuario a editar
            Integer id = Integer.parseInt(request.getParameter("id"));

            // Buscar el usuario en la base de datos
            Usuarios usuario = usuariosController.findUsuarios(id);

            if (usuario != null) {
                // Pasar el usuario como atributo a la vista
                request.setAttribute("usuario", usuario);

                // Redirigir al JSP para editar el usuario
                request.getRequestDispatcher("Vistas/editarUsuario.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Usuario no encontrado.");
                request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al cargar el usuario.");
            request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
        }
    }


    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        // Obtener los datos del formulario
        Integer id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        Integer edad = Integer.parseInt(request.getParameter("edad"));
        String region = request.getParameter("region");

        // Log para depuración: mostrar los datos en la consola
        System.out.println("Datos recibidos para actualizar:");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Email: " + email);
        System.out.println("Edad: " + edad);
        System.out.println("Región: " + region);

        // Buscar y actualizar el usuario
        Usuarios usuario = usuariosController.findUsuarios(id);
        if (usuario != null) {
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            usuario.setEdad(edad);
            usuario.setRegion(region);

            usuariosController.edit(usuario);

            // Redirigir a la lista de usuarios
            response.sendRedirect("UsuariosServlet");
        } else {
            throw new Exception("Usuario no encontrado.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Ocurrió un error al editar el usuario.");
        request.getRequestDispatcher("Vistas/error.jsp").forward(request, response);
    }
}

}
