# Sistema Unificado de Gestion para Conjuntos Residenciales

Sistema desarrollado en Java con Programacion Orientada a Objetos (POO) y MySQL para la gestion integral de conjuntos residenciales y barrios.

## Descripcion

Sistema que permite gestionar de manera centralizada:
- Usuarios (Residentes, Administradores, Guardias)
- Pagos de servicios (luz, agua, alicuota)
- Pedidos y encargos
- Emergencias y seguridad
- Avisos y notificaciones

## Tecnologias

- Java (JDK 8 o superior)
- MySQL (5.7 o superior)
- JDBC para conexion a base de datos
- Maven para gestion de dependencias

## Requisitos Previos

1. Java JDK 8 o superior instalado
2. MySQL instalado y corriendo
3. Maven instalado (opcional, si usas IDE puede manejar las dependencias)

## Instalacion y Configuracion

### Paso 1: Configurar MySQL

1. Iniciar MySQL

2. Crear la base de datos ejecutando el script SQL:
   - Abre database/schema.sql y ejecuta todo el script
   - O desde linea de comandos: mysql -u root -p < database/schema.sql

3. Verificar la creacion:
   ```sql
   SHOW DATABASES;
   USE resident_god;
   SHOW TABLES;
   ```

### Paso 2: Configurar Credenciales

Edita el archivo src/main/java/com/residencial/util/ConexionBD.java:

```java
private static final String USER = "root";
private static final String PASSWORD = "";
```

### Paso 3: Ejecutar la Aplicacion

Desde tu IDE:
- Ejecuta la clase `com.residencial.gui.MainApp`

O desde Maven:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.residencial.gui.MainApp"
```

## Estructura del Proyecto

```
proyectoFinalProgra2yandex/
├── database/
│   └── schema.sql
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── residencial/
│                   ├── Main.java (consola - opcional)
│                   ├── gui/
│                   │   ├── MainApp.java
│                   │   ├── controladores/
│                   │   │   └── LoginController.java
│                   │   ├── ventanas/
│                   │   │   ├── VentanaResidente.java
│                   │   │   ├── VentanaAdministrador.java
│                   │   │   └── VentanaGuardia.java
│                   │   └── util/
│                   │       └── DialogosUtil.java
│                   ├── dao/
│                   ├── modelo/
│                   ├── util/
│                   └── test/
├── lib/
│   └── mysql-connector-j-9.5.0.jar
├── USUARIOS_PRUEBA.md
├── SCRIPT_BASE_DATOS.md
└── README.md
```

## Base de Datos

### Tablas Principales

1. usuarios: Almacena informacion de todos los usuarios
2. pagos: Registra pagos de servicios
3. pedidos: Gestiona pedidos y encargos
4. emergencias: Registra emergencias reportadas
5. avisos: Almacena avisos del administrador

### Usuarios por Defecto

Ver archivo USUARIOS_PRUEBA.md para las credenciales de prueba.

## Uso

Ejecuta la clase Main.java para iniciar el sistema por consola.

El sistema muestra un menu interactivo donde puedes iniciar sesion con los usuarios de prueba.

## Solucion de Problemas

### Error: "Driver no encontrado"
- Verifica que el JAR de MySQL Connector este en el classpath

### Error: "Access denied for user"
- Verifica usuario y contrasena en ConexionBD.java

### Error: "Unknown database 'resident_god'"
- Ejecuta el script database/schema.sql para crear la base de datos

### Error: "Communications link failure"
- Verifica que MySQL este corriendo
- Verifica que el puerto 3306 este disponible


## Notas Importantes

- Este proyecto esta disenado para Programacion II con enfoque en POO
- La base de datos MySQL permite persistencia de datos
- El sistema utiliza JDBC para la conexion (sin frameworks avanzados)
- Interfaz por consola con menus interactivos
- Cada clase del sistema se relaciona con una tabla en la base de datos
