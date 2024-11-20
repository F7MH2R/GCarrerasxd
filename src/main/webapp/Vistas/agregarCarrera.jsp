<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Agregar Carrera</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="nav.jsp" %>
    <div class="container mt-5">
        <h2 class="text-center">Agregar Carrera</h2>
        <form action="AgregarCarreraServlet" method="post">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" name="nombre" id="nombre" class="form-control" required>
            </div>
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea name="descripcion" id="descripcion" class="form-control" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Guardar</button>
            <a href="CarrerasServlet" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>
