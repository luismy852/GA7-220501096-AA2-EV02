<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.planpet.util.Escape" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PlanPet - Registrar plan</title>
</head>
<body>
<h1>Registrar nuevo plan de salud</h1>

<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= Escape.html((String) request.getAttribute("error")) %></p>
<% } %>

<form action="planes" method="post">
    <label for="nombre">Nombre del plan:</label><br>
    <input type="text" id="nombre" name="nombre"
           value="<%= Escape.html((String) request.getAttribute("nombre")) %>" required><br><br>

    <label for="descripcion">Descripcion:</label><br>
    <textarea id="descripcion" name="descripcion" rows="3"><%= Escape.html((String) request.getAttribute("descripcion")) %></textarea><br><br>

    <label for="precio">Precio:</label><br>
    <input type="number" id="precio" name="precio" step="0.01" min="0"
           value="<%= Escape.html((String) request.getAttribute("precio")) %>" required><br><br>

    <button type="submit">Guardar plan</button>
</form>

<br>
<a href="planes">Volver al listado</a>

</body>
</html>
