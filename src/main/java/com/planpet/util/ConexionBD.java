package com.planpet.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase utilitaria para abrir la conexion JDBC con la base de datos
 * MySQL del proyecto PlanPet, usada por los servlets del modulo de
 * planes (GA7-220501096-AA2-EV02).
 *
 * Los datos de conexion se leen de db.properties (classpath); si el
 * archivo no aparece se usan valores por defecto para desarrollo local.
 * No es necesario registrar el driver manualmente: desde JDBC 4.0
 * mysql-connector-j se auto-registra via META-INF/services siempre
 * que el jar este en el classpath.
 */
public class ConexionBD {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/planpet?useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static final Properties SETTINGS = cargarConfiguracion();

    public static Connection obtenerConexion() throws SQLException {
        String url = SETTINGS.getProperty("db.url", DEFAULT_URL);
        String usuario = SETTINGS.getProperty("db.user", DEFAULT_USER);
        String clave = SETTINGS.getProperty("db.password", DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, usuario, clave);
    }

    private static Properties cargarConfiguracion() {
        Properties propiedades = new Properties();
        try (InputStream entrada = ConexionBD.class.getResourceAsStream("/db.properties")) {
            if (entrada != null) {
                propiedades.load(entrada);
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer db.properties, se usan valores por defecto: " + e.getMessage());
        }
        return propiedades;
    }
}
