package com.planpet.plan;

import com.planpet.util.ConexionBD;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PlanDAO: capa de acceso a datos del modulo de planes de salud.
 * Utilizada por PlanServlet para insertar, consultar, actualizar
 * y eliminar registros de la tabla plan.
 */
public class PlanDAO {

    public int crearPlan(Plan plan) throws SQLException {
        String sql = "INSERT INTO plan (nombre, descripcion, precio, id_company) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, plan.getNombre());
            statement.setString(2, plan.getDescripcion());
            statement.setBigDecimal(3, plan.getPrecio());
            statement.setInt(4, plan.getIdCompany());
            statement.executeUpdate();

            try (ResultSet llaves = statement.getGeneratedKeys()) {
                if (llaves.next()) {
                    return llaves.getInt(1);
                }
            }
        }
        return -1;
    }

    public List<Plan> listarPlanes() throws SQLException {
        String sql = "SELECT id_plan, nombre, descripcion, precio, id_company FROM plan ORDER BY id_plan";
        List<Plan> planes = new ArrayList<>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {
                planes.add(mapearPlan(resultado));
            }
        }
        return planes;
    }

    public Plan buscarPorId(int idPlan) throws SQLException {
        String sql = "SELECT id_plan, nombre, descripcion, precio, id_company FROM plan WHERE id_plan = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idPlan);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return mapearPlan(resultado);
                }
            }
        }
        return null;
    }

    public boolean actualizarPlan(Plan plan) throws SQLException {
        String sql = "UPDATE plan SET nombre = ?, descripcion = ?, precio = ? WHERE id_plan = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, plan.getNombre());
            statement.setString(2, plan.getDescripcion());
            statement.setBigDecimal(3, plan.getPrecio());
            statement.setInt(4, plan.getIdPlan());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminarPlan(int idPlan) throws SQLException {
        String sql = "DELETE FROM plan WHERE id_plan = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, idPlan);
            return statement.executeUpdate() > 0;
        }
    }

    private Plan mapearPlan(ResultSet resultado) throws SQLException {
        return new Plan(
                resultado.getInt("id_plan"),
                resultado.getString("nombre"),
                resultado.getString("descripcion"),
                resultado.getBigDecimal("precio"),
                resultado.getInt("id_company")
        );
    }
}
