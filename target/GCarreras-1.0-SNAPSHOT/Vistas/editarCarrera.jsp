<%-- 
    Document   : editarCarrera
    Created on : 19 nov. 2024, 2:55:18 p. m.
    Author     : black
--%>
<%@page import="modelo.Carreras"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Carrera</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="nav.jsp" %>
    <div class="container mt-5">
        <h2 class="text-center">Editar Carrera</h2>
        <form action="EditarCarreraServlet" method="post">
            <input type="hidden" name="idCarrera" value="<%= ((Carreras) request.getAttribute("carrera")).getIdCarrera() %>">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" id="nombre" class="form-control" value="<%= ((Carreras) request.getAttribute("carrera")).getNombreCarrera() %>" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea name="descripcion" id="descripcion" class="form-control" required><%= ((Carreras) request.getAttribute("carrera")).getDescripcion() %></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            <a href="CarrerasServlet" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>
