<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Carreras"%>
<%@page import="modelo.Intereses"%>
<!DOCTYPE html>
<html>
<head>
    <title>Carreras e Intereses</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script>
        // Función de confirmación para eliminar una carrera
        function confirmarEliminacion() {
            return confirm("¿Está seguro de que desea eliminar esta carrera?");
        }
    </script>
</head>
<body>
    <%@ include file="nav.jsp" %>

    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center">
            <h2 class="text-center">Lista de Carreras</h2>
            <!-- Botón para agregar nueva carrera -->
            <a href="AgregarCarreraServlet" class="btn btn-primary">Agregar Carrera</a>
        </div>

        <table class="table table-striped mt-4">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Interés</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Carreras> listaCarreras = (List<Carreras>) request.getAttribute("listaCarreras");
                    List<Integer> interesesUsuario = (List<Integer>) request.getAttribute("interesesUsuario");
                    if (listaCarreras != null) {
                        for (Carreras carrera : listaCarreras) {
                            boolean interesado = interesesUsuario != null && interesesUsuario.contains(carrera.getIdCarrera());
                %>
                <tr>
                    <td><%= carrera.getIdCarrera() %></td>
                    <td><%= carrera.getNombreCarrera() %></td>
                    <td><%= carrera.getDescripcion() %></td>
                    <td>
                        <form action="<%= interesado ? "EliminarInteresServlet" : "CrearInteresServlet" %>" method="post" style="display:inline;">
                            <input type="hidden" name="idCarrera" value="<%= carrera.getIdCarrera() %>">
                            <button class="btn <%= interesado ? "btn-danger" : "btn-success" %>">
                                <%= interesado ? "Quitar Interés" : "Agregar Interés" %>
                            </button>
                        </form>
                    </td>
                    <td>
                        <!-- Botones para Editar y Eliminar Carrera -->
                        <a href="EditarCarreraServlet?id=<%= carrera.getIdCarrera() %>" class="btn btn-warning btn-sm">Editar</a>
                        <form action="EliminarCarreraServlet" method="post" style="display:inline;" onsubmit="return confirmarEliminacion();">
                            <input type="hidden" name="idCarrera" value="<%= carrera.getIdCarrera() %>">
                            <button class="btn btn-danger btn-sm">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
