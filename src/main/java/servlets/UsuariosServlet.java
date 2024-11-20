package servlets;

import controladores.UsuariosJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import modelo.Usuarios;

@WebServlet("/UsuariosServlet")
public class UsuariosServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private UsuariosJpaController usuariosController;

    @Override
    public void init() throws ServletException {
        // Inicializar el controlador JPA con el EntityManagerFactory
        usuariosController = new UsuariosJpaController(null, emf);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener la lista de usuarios desde el controlador
            List<Usuarios> usuarios = usuariosController.findUsuariosEntities();

            // Pasar la lista de usuarios como atributo para la vista
            request.setAttribute("usuarios", usuarios);

            // Redirigir al JSP para mostrar los usuarios
            request.getRequestDispatcher("Vistas/usuarios.jsp").forward(request, response);
        } catch (Exception e) {
            // Manejar errores y redirigir a una página de error
            e.printStackTrace();
            request.setAttribute("error", "No se pudo cargar la lista de usuarios.");
            request.getRequestDispatcher("vistas/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Determinar la acción a realizar (add, edit, delete)
            String action = request.getParameter("action");

            if ("add".equalsIgnoreCase(action)) {
                agregarUsuario(request, response);
            } else if ("edit".equalsIgnoreCase(action)) {
                editarUsuario(request, response);
            } else if ("delete".equalsIgnoreCase(action)) {
                eliminarUsuario(request, response);
            } else {
                // Redirigir a la lista de usuarios por defecto
                response.sendRedirect("UsuariosServlet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al procesar la solicitud.");
            request.getRequestDispatcher("vistas/error.jsp").forward(request, response);
        }
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Crear un nuevo usuario a partir de los datos del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        Integer edad = Integer.parseInt(request.getParameter("edad"));
        String region = request.getParameter("region");

        Usuarios nuevoUsuario = new Usuarios(nombre, apellido, email, edad, region);

        // Guardar el usuario en la base de datos
        usuariosController.create(nuevoUsuario);

        // Redirigir a la lista de usuarios
        response.sendRedirect("UsuariosServlet");
    }

    private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Obtener los datos del formulario para actualizar
        Integer id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        Integer edad = Integer.parseInt(request.getParameter("edad"));
        String region = request.getParameter("region");

        // Buscar el usuario existente y actualizar sus datos
        Usuarios usuarioExistente = usuariosController.findUsuarios(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(nombre);
            usuarioExistente.setApellido(apellido);
            usuarioExistente.setEmail(email);
            usuarioExistente.setEdad(edad);
            usuarioExistente.setRegion(region);

            // Guardar los cambios en la base de datos
            usuariosController.edit(usuarioExistente);

            // Redirigir a la lista de usuarios
            response.sendRedirect("UsuariosServlet");
        } else {
            throw new Exception("Usuario no encontrado para editar.");
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Obtener el ID del usuario a eliminar
        Integer id = Integer.parseInt(request.getParameter("id"));

        // Eliminar el usuario en la base de datos
        usuariosController.destroy(id);

        // Redirigir a la lista de usuarios
        response.sendRedirect("UsuariosServlet");
    }
}
