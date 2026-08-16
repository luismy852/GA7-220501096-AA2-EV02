package com.planpet.plan;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * PlanServlet: servlet del modulo de planes de salud de PlanPet.
 * Evidencia GA7-220501096-AA2-EV02.
 *
 * - doGet: lista los planes registrados y los envia al JSP planes.jsp.
 * - doPost: recibe los datos del formulario HTML (registrarPlan.jsp)
 *   y registra un nuevo plan en la base de datos.
 */
@WebServlet(name = "PlanServlet", urlPatterns = {"/planes"})
public class PlanServlet extends HttpServlet {

    private final PlanDAO planDAO = new PlanDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Plan> planes = planDAO.listarPlanes();
            request.setAttribute("planes", planes);
            request.getRequestDispatcher("planes.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al consultar los planes", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String precioTexto = request.getParameter("precio");

        // Se conservan los valores recibidos para repoblar el formulario
        // si alguna validacion falla, en vez de devolverlo vacio.
        request.setAttribute("nombre", nombre);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("precio", precioTexto);

        // Validaciones basicas de los datos recibidos por el formulario.
        if (nombre == null || nombre.trim().isEmpty()) {
            request.setAttribute("error", "El nombre del plan es obligatorio.");
            request.getRequestDispatcher("registrarPlan.jsp").forward(request, response);
            return;
        }

        String nombreLimpio = nombre.trim();
        if (nombreLimpio.length() > 80) {
            request.setAttribute("error", "El nombre del plan no puede superar los 80 caracteres.");
            request.getRequestDispatcher("registrarPlan.jsp").forward(request, response);
            return;
        }

        if (descripcion != null && descripcion.length() > 255) {
            request.setAttribute("error", "La descripcion no puede superar los 255 caracteres.");
            request.getRequestDispatcher("registrarPlan.jsp").forward(request, response);
            return;
        }

        if (precioTexto == null || precioTexto.trim().isEmpty()) {
            request.setAttribute("error", "El precio es obligatorio.");
            request.getRequestDispatcher("registrarPlan.jsp").forward(request, response);
            return;
        }

        BigDecimal precio;
        try {
            precio = new BigDecimal(precioTexto.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El precio debe ser un valor numerico valido.");
            request.getRequestDispatcher("registrarPlan.jsp").forward(request, response);
            return;
        }

        if (precio.signum() < 0) {
            request.setAttribute("error", "El precio no puede ser negativo.");
            request.getRequestDispatcher("registrarPlan.jsp").forward(request, response);
            return;
        }

        try {
            Plan nuevoPlan = new Plan(nombreLimpio, descripcion, precio, 1);
            planDAO.crearPlan(nuevoPlan);
            response.sendRedirect("planes");
        } catch (SQLException e) {
            throw new ServletException("Error al registrar el plan", e);
        }
    }
}
