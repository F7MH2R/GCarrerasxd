<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Gestión de Usuarios</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
            <%@ include file="nav.jsp" %>

        <div class="container mt-5">
            <h1 class="mb-4">Gestión de Usuarios</h1>

            <!-- Botón para agregar un nuevo usuario -->
            <button class="btn btn-primary mb-3" onclick="location.href = 'RegistroServlet'">Agregar Usuario</button>

            <!-- Tabla para mostrar la lista de usuarios -->
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Email</th>
                        <th>Edad</th>
                        <th>Región</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iteración sobre la lista de usuarios -->
                    <c:forEach var="usuario" items="${usuarios}">
                        <tr>
                            <td>${usuario.idUsuario}</td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.apellido}</td>
                            <td>${usuario.email}</td>
                            <td>${usuario.edad}</td>
                            <td>${usuario.region}</td>
                            <td>
                                <!-- Botón para editar usuario -->
                                <button class="btn btn-warning btn-sm" onclick="location.href = 'EditarUsuarioServlet?id=${usuario.idUsuario}'">Editar</button>

                                <!-- Botón para eliminar usuario -->
                                <button class="btn btn-danger btn-sm" onclick="location.href = 'EliminarUsuarioServlet?id=${usuario.idUsuario}'">Eliminar</button>
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Script para confirmar la eliminación de un usuario -->
        <script>
            function eliminarUsuario(id) {
                if (confirm("¿Está seguro de que desea eliminar este usuario?")) {
                    location.href = `UsuariosServlet?action=delete&id=${id}`;
                }
            }
        </script>
    </body>
</html>
