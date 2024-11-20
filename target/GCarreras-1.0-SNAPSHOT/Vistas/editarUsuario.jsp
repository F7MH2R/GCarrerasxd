<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Usuario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="mb-4">Editar Usuario</h1>
        <form action="EditarUsuarioServlet" method="post">
            <input type="hidden" name="id" value="${usuario.idUsuario}">
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${usuario.nombre}" required>
            </div>
            <div class="mb-3">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido" value="${usuario.apellido}" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${usuario.email}" required>
            </div>
            <div class="mb-3">
                <label for="edad" class="form-label">Edad</label>
                <input type="number" class="form-control" id="edad" name="edad" value="${usuario.edad}" required>
            </div>
            <div class="mb-3">
                <label for="region" class="form-label">Región</label>
                <input type="text" class="form-control" id="region" name="region" value="${usuario.region}" required>
            </div>
            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            <a href="UsuariosServlet" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>
