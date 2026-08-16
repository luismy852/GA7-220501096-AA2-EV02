<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.planpet.plan.Plan" %>
<%@ page import="com.planpet.util.Escape" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PlanPet - Planes de salud</title>
</head>
<body>
<h1>PlanPet - Planes de salud registrados</h1>

<table border="1" cellpadding="6" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Descripcion</th>
        <th>Precio</th>
    </tr>
    <%
        List<Plan> planes = (List<Plan>) request.getAttribute("planes");
        if (planes != null) {
            for (Plan plan : planes) {
    %>
    <tr>
        <td><%= plan.getIdPlan() %></td>
        <td><%= Escape.html(plan.getNombre()) %></td>
        <td><%= Escape.html(plan.getDescripcion()) %></td>
        <td>$<%= plan.getPrecio() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<br>
<a href="registrarPlan.jsp">Registrar nuevo plan</a>

</body>
</html>
