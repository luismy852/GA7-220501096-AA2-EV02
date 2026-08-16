package com.planpet.util;

/**
 * Clase utilitaria para escapar texto antes de imprimirlo en HTML.
 * Se usa en los JSP del modulo de planes para evitar XSS almacenado
 * al mostrar datos que vienen de la base de datos o del usuario
 * (nombre y descripcion del plan).
 */
public final class Escape {

    private Escape() {
        // Clase utilitaria: no se instancia.
    }

    /**
     * Escapa los caracteres especiales de HTML (& < > " ') en el texto
     * recibido, para que se muestre como texto plano y no como marcado
     * o script ejecutable.
     *
     * @param texto texto a escapar, puede ser null
     * @return texto escapado, o cadena vacia si texto es null
     */
    public static String html(String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder resultado = new StringBuilder(texto.length());
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            switch (caracter) {
                case '&':
                    resultado.append("&amp;");
                    break;
                case '<':
                    resultado.append("&lt;");
                    break;
                case '>':
                    resultado.append("&gt;");
                    break;
                case '"':
                    resultado.append("&quot;");
                    break;
                case '\'':
                    resultado.append("&#39;");
                    break;
                default:
                    resultado.append(caracter);
            }
        }
        return resultado.toString();
    }
}
