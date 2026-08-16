# PlanPet — Módulo de planes de salud

Evidencia **GA7-220501096-AA2-EV02**, módulo de **Gestión de planes de salud (Plan)**.
Aprendiz: Luis Miguel Murillo — Ficha 3186584.

Este es el módulo que desarrollé para esa evidencia. La idea de fondo es simple: una veterinaria necesita registrar los planes de salud que le ofrece a sus clientes —nombre, descripción y precio— y poder consultarlos en un listado. Lo resolví con un formulario HTML, un servlet y dos páginas JSP, todo sobre una base de datos MySQL, siguiendo el enfoque que pide la guía (HTML + Servlets + JSP).

**Historia de usuario cubierta:** Como administrador de la veterinaria, quiero registrar
un plan de salud (nombre, descripción y precio) y consultar el listado de planes
existentes, para poder ofrecérselos a mis clientes.

## Cómo está organizado

- `sql/planpet_plan.sql`: crea la base de datos `planpet`, la tabla `plan` y deja dos registros de ejemplo (Plan Básico y Plan Premium) para no arrancar con la tabla vacía.
- `src/main/java/com/planpet/plan/Plan.java`: la entidad del plan (id, nombre, descripción, precio, id de la empresa).
- `src/main/java/com/planpet/plan/PlanDAO.java`: el acceso a datos con JDBC. Ya tiene el CRUD completo (`crearPlan`, `listarPlanes`, `buscarPorId`, `actualizarPlan`, `eliminarPlan`) usando `PreparedStatement`, aunque por ahora el servlet solo usa crear y listar; lo dejé listo para las siguientes evidencias del módulo.
- `src/main/java/com/planpet/util/ConexionBD.java`: abre la conexión JDBC contra MySQL, leyendo la URL, el usuario y la clave desde `db.properties` (con valores por defecto si no lo encuentra).
- `src/main/resources/db.properties`: configuración de conexión a la base de datos, fuera del código fuente.
- `src/main/java/com/planpet/util/Escape.java`: escapa caracteres especiales de HTML antes de imprimir datos en los JSP, para evitar XSS.
- `src/main/java/com/planpet/plan/PlanServlet.java`: el servlet, con `doGet` para listar los planes y `doPost` para registrar uno nuevo, validando nombre, longitudes y precio.
- `src/main/webapp/planes.jsp`: la vista con la tabla de planes, recorrida con un scriptlet.
- `src/main/webapp/registrarPlan.jsp`: el formulario HTML para registrar un plan nuevo.
- `src/main/webapp/WEB-INF/web.xml`: configuración del proyecto web; deja `planes` como página de bienvenida.
- `pom.xml`: proyecto Maven tipo `war`, con `javax.servlet-api` y el driver de MySQL.

El flujo va así: `registrarPlan.jsp` envía el formulario por POST a `PlanServlet`, que valida los datos, llama a `PlanDAO` para insertarlos en MySQL y redirige de vuelta a `/planes`; esa misma ruta, por GET, lista los planes y los muestra en `planes.jsp`.

## Cómo ponerlo a correr

1. Ejecuta `sql/planpet_plan.sql` en tu servidor MySQL.
2. Revisa usuario y clave en `src/main/resources/db.properties` si los tuyos no son `root` con clave vacía.
3. Empaqueta con Maven: `mvn clean package`. Esto genera `target/planpet-aa2-ev02-plan.war`.
4. Despliega ese `.war` en **Apache Tomcat 9**. El proyecto usa `javax.servlet-api` (el paquete `javax.*`, no `jakarta.*`), así que en Tomcat 10 no va a desplegar sin cambios.
5. Abre `http://localhost:8080/planpet-aa2-ev02-plan/planes` para ver el listado (GET) y registra un plan nuevo desde el enlace "Registrar nuevo plan" (POST).

## Detalles de la implementación

- Si el nombre viene vacío, supera 80 caracteres, la descripción supera 255, el precio falta o es negativo, `PlanServlet` no inserta nada: reenvía a `registrarPlan.jsp` con un mensaje de error, conservando lo que el usuario ya había escrito.
- Después de registrar un plan con éxito, el servlet redirige a `/planes` en vez de reenviar directamente, para evitar que al recargar la página el navegador vuelva a enviar el mismo POST.
- Todas las consultas usan `PreparedStatement`, para no armar el SQL concatenando texto.
- `planes.jsp` y `registrarPlan.jsp` pasan el nombre, la descripción y el mensaje de error por `Escape.html(...)` antes de imprimirlos, para que un plan con `<script>` no se ejecute en el navegador.

## Requisitos de la guía que cubre esta evidencia

- Formularios HTML conectados a servlets: `registrarPlan.jsp` envía los datos a `PlanServlet`.
- Los dos métodos HTTP principales: `doGet` para listar, `doPost` para insertar.
- Elementos de JSP: `planes.jsp` y `registrarPlan.jsp` usan scriptlets y expresiones JSP.
- Estándares de codificación del proyecto: paquetes en minúsculas, clases en PascalCase, métodos en camelCase.
- Se creó el proyecto utilizando herramientas de versionamiento (Git); ver `enlace_repositorio.txt`.

## Repositorio

El enlace al repositorio remoto queda en `enlace_repositorio.txt`.
