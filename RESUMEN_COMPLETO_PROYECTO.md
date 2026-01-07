# RESUMEN COMPLETO DEL PROYECTO
## Sistema Unificado de Gestión para Conjuntos Residenciales y Barrios

---

## 1. DESCRIPCIÓN GENERAL

Sistema de gestión integral desarrollado en **Java** con **Programación Orientada a Objetos (POO)** y **MySQL** para administrar conjuntos residenciales. El sistema permite gestionar usuarios, pagos, pedidos, emergencias y avisos mediante una interfaz de consola interactiva con menús por rol.

**Propósito Académico:** Proyecto final para Programación II que demuestra dominio de POO, herencia, encapsulación, polimorfismo, conexión a base de datos con JDBC y arquitectura en capas.

---

## 2. ARQUITECTURA DEL SISTEMA

### 2.1. Patrón de Arquitectura: Modelo-DAO-Interfaz

```
┌─────────────────────────────────────────┐
│         INTERFAZ (Main.java)            │
│     - Menús interactivos por consola    │
│     - Validación de entrada             │
│     - Navegación por roles              │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│      MODELO (Clases de Negocio)         │
│  - Usuario (clase base)                 │
│  - Residente, Administrador, Guardia    │
│  - Pago, Pedido, Emergencia, Aviso      │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         DAO (Acceso a Datos)            │
│  - UsuarioDAO, PagoDAO, PedidoDAO       │
│  - EmergenciaDAO, AvisoDAO              │
│  - Operaciones CRUD con PreparedStatement│
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│      UTILIDADES (Conexión BD)           │
│  - ConexionBD (JDBC)                    │
│  - ConfiguracionBD                      │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│      BASE DE DATOS (MySQL)              │
│  - resident_god                         │
│  - 5 tablas principales                 │
│  - 2 vistas                             │
└─────────────────────────────────────────┘
```

---

## 3. ESTRUCTURA DETALLADA DE CLASES

### 3.1. PAQUETE: `com.residencial.modelo`

#### **Usuario.java** (Clase Base - Abstracción)
- **Propósito:** Clase padre que representa a todos los usuarios del sistema
- **Atributos:**
  - `idUsuario` (int) - Identificador único
  - `nombre` (String) - Nombre completo
  - `correo` (String) - Correo electrónico (único)
  - `contraseña` (String) - Contraseña de acceso
  - `rol` (String) - RESIDENTE, ADMINISTRADOR o GUARDIA
  - `departamento` (String) - Número de departamento
  - `bloque` (String) - Bloque o torre
  - `telefono` (String) - Número de contacto
- **Constructores:**
  - Constructor vacío (para instanciación desde BD)
  - Constructor con parámetros (nombre, correo, contraseña, rol, departamento, bloque)
- **Métodos:** Getters y Setters para todos los atributos (encapsulación)

#### **Residente.java** (Extiende Usuario - Herencia)
- **Propósito:** Representa a los residentes del conjunto
- **Herencia:** Extiende de `Usuario`
- **Atributos adicionales:** Ninguno (usa los de Usuario)
- **Métodos específicos:**
  - `pagarServicio()` - Registra pagos de servicios (LUZ, AGUA, ALICUOTA, GAS, OTRO)
  - `verHistorialPagos()` - Consulta todos los pagos del residente
  - `crearPedido()` - Crea pedidos/encargos (ENCARGO, SERVICIO, PRODUCTO, OTRO)
  - `verEstadoPedidos()` - Consulta el estado de sus pedidos
  - `activarEmergencia()` - Reporta emergencias con prioridad (BAJA, MEDIA, ALTA, CRITICA)
  - `verAvisos()` - Visualiza avisos publicados por administradores
- **Dependencias DAO:** PagoDAO, PedidoDAO, EmergenciaDAO, AvisoDAO

#### **Administrador.java** (Extiende Usuario - Herencia)
- **Propósito:** Representa a los administradores del conjunto
- **Herencia:** Extiende de `Usuario`
- **Métodos específicos:**
  - `crearUsuario()` - Crea nuevos usuarios (Residentes, Administradores, Guardias)
  - `crearAviso()` - Publica avisos (INFORMATIVO, URGENTE, MANTENIMIENTO, EVENTO, OTRO)
  - `verAvisos()` - Visualiza todos los avisos activos
  - `verReportes()` - Consulta reportes del sistema (en desarrollo)
- **Dependencias DAO:** UsuarioDAO, AvisoDAO

#### **Guardia.java** (Extiende Usuario - Herencia)
- **Propósito:** Representa a los guardias de seguridad
- **Herencia:** Extiende de `Usuario`
- **Métodos específicos:**
  - `verEmergenciasActivas()` - Lista emergencias en estado ACTIVA o EN_ATENCION
  - `atenderEmergencia()` - Cambia el estado de emergencias (EN_ATENCION, RESUELTA)
- **Dependencias DAO:** EmergenciaDAO

#### **Pago.java** (Entidad)
- **Atributos:**
  - `idPago`, `idUsuario`, `tipoServicio` (ENUM), `monto` (BigDecimal)
  - `fechaPago`, `fechaVencimiento`, `estado` (ENUM: PENDIENTE, PAGADO, VENCIDO, CANCELADO)
  - `metodoPago`, `comprobante`, `observaciones`

#### **Pedido.java** (Entidad)
- **Atributos:**
  - `idPedido`, `idUsuario`, `descripcion`, `tipoPedido` (ENUM)
  - `fechaSolicitud`, `fechaEntregaEstimada`, `estado` (ENUM)
  - `costo` (BigDecimal), `observaciones`

#### **Emergencia.java** (Entidad)
- **Atributos:**
  - `idEmergencia`, `idUsuario`, `tipo` (ENUM: MEDICA, INCENDIO, ROBO, etc.)
  - `descripcion`, `ubicacion`, `fechaReporte`
  - `estado` (ENUM: ACTIVA, EN_ATENCION, RESUELTA, CANCELADA)
  - `prioridad` (ENUM: BAJA, MEDIA, ALTA, CRITICA)
  - `idGuardiaAtendiendo`, `fechaResolucion`, `observaciones`

#### **Aviso.java** (Entidad)
- **Atributos:**
  - `idAviso`, `idAdministrador`, `titulo`, `mensaje`
  - `tipo` (ENUM: INFORMATIVO, URGENTE, MANTENIMIENTO, EVENTO, OTRO)
  - `fechaPublicacion`, `fechaExpiracion`, `activo` (boolean)

---

### 3.2. PAQUETE: `com.residencial.dao`

#### **UsuarioDAO.java** (Patrón DAO)
- **Métodos:**
  - `login(String correo, String contraseña)` - Autenticación de usuarios
  - `crear(Usuario usuario)` - INSERT de nuevo usuario
  - `actualizar(Usuario usuario)` - UPDATE de datos del usuario
- **Tecnología:** PreparedStatement para prevenir SQL Injection

#### **PagoDAO.java**
- **Métodos:**
  - `crear(Pago pago)` - Registra nuevo pago
  - `obtenerPorUsuario(int idUsuario)` - Lista pagos de un usuario
  - `marcarComoPagado(int idPago, String metodoPago)` - Actualiza estado a PAGADO

#### **PedidoDAO.java**
- **Métodos:**
  - `crear(Pedido pedido)` - Crea nuevo pedido
  - `obtenerPorUsuario(int idUsuario)` - Lista pedidos de un usuario
  - `actualizarEstado(int idPedido, String nuevoEstado)` - Cambia estado del pedido

#### **EmergenciaDAO.java**
- **Métodos:**
  - `crear(Emergencia emergencia)` - Registra nueva emergencia
  - `obtenerActivas()` - Lista emergencias activas (ACTIVA, EN_ATENCION)
  - `actualizarEstado(int idEmergencia, String nuevoEstado, Integer idGuardia)` - Atiende emergencia

#### **AvisoDAO.java**
- **Métodos:**
  - `crear(Aviso aviso)` - Publica nuevo aviso
  - `obtenerActivos()` - Lista avisos activos
  - `eliminar(int idAviso)` - Desactiva un aviso (soft delete)

**Características comunes de los DAOs:**
- Uso de `PreparedStatement` para seguridad
- Manejo de excepciones `SQLException`
- Cierre explícito de recursos (ResultSet, PreparedStatement)
- Retorno de booleanos para operaciones de escritura
- Retorno de Listas para operaciones de lectura

---

### 3.3. PAQUETE: `com.residencial.util`

#### **ConexionBD.java** (Singleton simplificado)
- **Propósito:** Gestiona la conexión a MySQL
- **Método estático:**
  - `getConexion()` - Retorna Connection a la base de datos
- **Configuración:**
  - URL: `jdbc:mysql://localhost:3306/resident_god`
  - Usuario: `root`
  - Contraseña: Configurable (vacía por defecto)
- **Características:** Conexión directa sin pool (simple para explicar)

#### **ConfiguracionBD.java**
- **Propósito:** Centraliza configuración de BD
- **Constantes:** HOST, PUERTO, BASE_DATOS, USUARIO, CONTRASEÑA
- **Método:** `obtenerURL()` - Construye URL de conexión JDBC

---

### 3.4. PAQUETE: `com.residencial`

#### **Main.java** (Clase Principal - Interfaz de Usuario)
- **Propósito:** Punto de entrada del sistema, maneja la interfaz de consola
- **Atributos estáticos:**
  - `Scanner scanner` - Entrada del usuario
  - `UsuarioDAO usuarioDAO` - Acceso a datos de usuarios
  - `Usuario usuarioActual` - Usuario logueado actualmente
- **Flujo principal:**
  1. Muestra menú principal (Login o Salir)
  2. Si no hay usuario: opción de login
  3. Si hay usuario: muestra menú según rol
  4. Procesa opciones con `switch`
  5. Maneja errores de entrada con `try-catch`
- **Métodos principales:**
  - `main(String[] args)` - Método principal
  - `mostrarMenuPrincipal()` - Menú de login
  - `mostrarMenuUsuario()` - Menú según rol
  - `iniciarSesion()` - Autenticación
  - `verPerfil()` - Muestra datos del usuario
  - `actualizarPerfil()` - Actualiza datos del usuario
  - `cerrarSesion()` - Cierra sesión
  - `gestionarUsuarios(Administrador)` - Submenú de administración
  - `menuResidente(int, Residente)` - Procesa opciones de residente
  - `menuAdministrador(int, Administrador)` - Procesa opciones de administrador
  - `menuGuardia(int, Guardia)` - Procesa opciones de guardia
  - `convertirAResidente/Administrador/Guardia()` - Conversión de tipos (polimorfismo)

---

## 4. BASE DE DATOS MYSQL

### 4.1. Base de Datos: `resident_god`
- **Motor:** InnoDB
- **Charset:** utf8mb4 (soporte Unicode completo)
- **Collation:** utf8mb4_unicode_ci

### 4.2. Tablas Principales

#### **usuarios**
```sql
- id_usuario (PK, AUTO_INCREMENT)
- nombre (VARCHAR(100), NOT NULL)
- correo (VARCHAR(100), UNIQUE, NOT NULL)
- contraseña (VARCHAR(255), NOT NULL)
- rol (ENUM: 'RESIDENTE', 'ADMINISTRADOR', 'GUARDIA', DEFAULT 'RESIDENTE')
- departamento (VARCHAR(50))
- bloque (VARCHAR(50))
- telefono (VARCHAR(20))
- fecha_registro (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
- activo (BOOLEAN, DEFAULT TRUE)
- Índices: idx_correo, idx_rol
```

#### **pagos**
```sql
- id_pago (PK, AUTO_INCREMENT)
- id_usuario (FK → usuarios.id_usuario, ON DELETE CASCADE)
- tipo_servicio (ENUM: 'LUZ', 'AGUA', 'ALICUOTA', 'GAS', 'OTRO')
- monto (DECIMAL(10,2), NOT NULL)
- fecha_pago (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
- fecha_vencimiento (DATE)
- estado (ENUM: 'PENDIENTE', 'PAGADO', 'VENCIDO', 'CANCELADO', DEFAULT 'PENDIENTE')
- metodo_pago (VARCHAR(50))
- comprobante (VARCHAR(255))
- observaciones (TEXT)
- Índices: idx_usuario, idx_estado, idx_fecha
```

#### **pedidos**
```sql
- id_pedido (PK, AUTO_INCREMENT)
- id_usuario (FK → usuarios.id_usuario, ON DELETE CASCADE)
- descripcion (TEXT, NOT NULL)
- tipo_pedido (ENUM: 'ENCARGO', 'SERVICIO', 'PRODUCTO', 'OTRO', DEFAULT 'ENCARGO')
- fecha_solicitud (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
- fecha_entrega_estimada (DATE)
- estado (ENUM: 'PENDIENTE', 'EN_PROCESO', 'COMPLETADO', 'CANCELADO', DEFAULT 'PENDIENTE')
- costo (DECIMAL(10,2), DEFAULT 0.00)
- observaciones (TEXT)
- Índices: idx_usuario, idx_estado, idx_fecha
```

#### **emergencias**
```sql
- id_emergencia (PK, AUTO_INCREMENT)
- id_usuario (FK → usuarios.id_usuario, ON DELETE CASCADE)
- tipo (ENUM: 'MEDICA', 'INCENDIO', 'ROBO', 'ACCIDENTE', 'FALLA_ELECTRICA', 'FALLA_PLOMERIA', 'OTRA')
- descripcion (TEXT, NOT NULL)
- ubicacion (VARCHAR(255))
- fecha_reporte (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
- estado (ENUM: 'ACTIVA', 'EN_ATENCION', 'RESUELTA', 'CANCELADA', DEFAULT 'ACTIVA')
- prioridad (ENUM: 'BAJA', 'MEDIA', 'ALTA', 'CRITICA', DEFAULT 'MEDIA')
- id_guardia_atendiendo (FK → usuarios.id_usuario, ON DELETE SET NULL)
- fecha_resolucion (TIMESTAMP, NULL)
- observaciones (TEXT)
- Índices: idx_usuario, idx_estado, idx_prioridad, idx_fecha
```

#### **avisos**
```sql
- id_aviso (PK, AUTO_INCREMENT)
- id_administrador (FK → usuarios.id_usuario, ON DELETE CASCADE)
- titulo (VARCHAR(200), NOT NULL)
- mensaje (TEXT, NOT NULL)
- tipo (ENUM: 'INFORMATIVO', 'URGENTE', 'MANTENIMIENTO', 'EVENTO', 'OTRO', DEFAULT 'INFORMATIVO')
- fecha_publicacion (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
- fecha_expiracion (DATE)
- activo (BOOLEAN, DEFAULT TRUE)
- Índices: idx_activo, idx_fecha, idx_tipo
```

### 4.3. Vistas (Views)

#### **vista_pagos_usuario**
- **Propósito:** Resumen de pagos por usuario
- **Columnas:** id_usuario, nombre, correo, total_pagos, total_pagado, total_pendiente
- **Lógica:** Agrupa pagos por usuario, suma montos según estado

#### **vista_emergencias_activas**
- **Propósito:** Lista emergencias en curso con información de usuario y guardia
- **Columnas:** id_emergencia, tipo, descripcion, ubicacion, prioridad, fecha_reporte, nombre_usuario, departamento, bloque, guardia_atendiendo
- **Lógica:** Filtra emergencias ACTIVA o EN_ATENCION, ordena por prioridad y fecha

### 4.4. Relaciones (Foreign Keys)
- **pagos.id_usuario** → usuarios.id_usuario (CASCADE)
- **pedidos.id_usuario** → usuarios.id_usuario (CASCADE)
- **emergencias.id_usuario** → usuarios.id_usuario (CASCADE)
- **emergencias.id_guardia_atendiendo** → usuarios.id_usuario (SET NULL)
- **avisos.id_administrador** → usuarios.id_usuario (CASCADE)

---

## 5. FLUJO DEL SISTEMA

### 5.1. Inicio de Sesión
```
1. Usuario ejecuta Main.java
2. Sistema muestra menú principal
3. Usuario selecciona "Iniciar Sesión"
4. Usuario ingresa correo y contraseña
5. UsuarioDAO.login() consulta BD
6. Si credenciales válidas → carga objeto Usuario
7. Sistema identifica rol (RESIDENTE/ADMINISTRADOR/GUARDIA)
8. Muestra menú específico del rol
```

### 5.2. Flujo para RESIDENTE
```
Menú Residente:
├── 1. Ver Perfil → Muestra datos del usuario
├── 2. Actualizar Perfil → Modifica nombre/teléfono en BD
├── 3. Pagar Servicio → Crea registro en tabla pagos
├── 4. Ver Mis Pagos → Consulta pagos del usuario
├── 5. Crear Pedido → Crea registro en tabla pedidos
├── 6. Ver Mis Pedidos → Consulta pedidos del usuario
├── 7. Activar Emergencia → Crea registro en tabla emergencias
├── 8. Ver Avisos → Consulta avisos activos
├── 9. Cerrar Sesión → Vuelve a menú principal
└── 10. Salir → Termina aplicación
```

### 5.3. Flujo para ADMINISTRADOR
```
Menú Administrador:
├── 1. Ver Perfil → Muestra datos del administrador
├── 2. Gestionar Usuarios → Submenú:
│   └── 1. Crear Usuario → Inserta nuevo usuario en BD
├── 3. Crear Aviso → Publica aviso (se guarda en BD)
├── 4. Ver Avisos → Lista todos los avisos activos
├── 5. Ver Reportes → Funcionalidad en desarrollo
├── 6. Cerrar Sesión → Vuelve a menú principal
└── 7. Salir → Termina aplicación
```

### 5.4. Flujo para GUARDIA
```
Menú Guardia:
├── 1. Ver Perfil → Muestra datos del guardia
├── 2. Ver Emergencias Activas → Lista emergencias ACTIVA/EN_ATENCION
├── 3. Atender Emergencia → Actualiza estado y asigna guardia
├── 4. Cerrar Sesión → Vuelve a menú principal
└── 5. Salir → Termina aplicación
```

---

## 6. CONCEPTOS DE POO APLICADOS

### 6.1. Herencia
- **Clase Base:** `Usuario`
- **Clases Derivadas:** `Residente`, `Administrador`, `Guardia`
- **Beneficio:** Reutilización de código, atributos comunes en clase padre
- **Ejemplo:** Todos heredan nombre, correo, contraseña, rol, etc.

### 6.2. Encapsulación
- **Atributos privados:** Todos los atributos de las clases modelo son `private`
- **Acceso controlado:** Getters y Setters para modificar atributos
- **Ejemplo:** `usuario.getNombre()`, `usuario.setNombre(String)`

### 6.3. Polimorfismo
- **Métodos específicos por clase:** Cada clase derivada tiene métodos únicos
- **Ejemplo:** `Residente.pagarServicio()`, `Administrador.crearUsuario()`, `Guardia.atenderEmergencia()`
- **Conversión de tipos:** `convertirAResidente()`, `convertirAAdministrador()`, `convertirAGuardia()`

### 6.4. Abstracción
- **Clase abstracta implícita:** `Usuario` actúa como abstracción (aunque no es abstract)
- **Separación de responsabilidades:** Modelo, DAO, Interfaz separados

### 6.5. Patrón DAO (Data Access Object)
- **Separación de capas:** Lógica de negocio separada de acceso a datos
- **Ventajas:** Facilita mantenimiento, permite cambiar BD sin afectar modelo
- **Implementación:** Cada entidad tiene su DAO correspondiente

---

## 7. SEGURIDAD Y VALIDACIÓN

### 7.1. Prevención de SQL Injection
- **PreparedStatement:** Todos los DAOs usan PreparedStatement con parámetros
- **Ejemplo:** `pstmt.setString(1, correo)` en lugar de concatenación de strings

### 7.2. Validación de Entrada
- **Try-Catch:** Manejo de errores en entrada numérica
- **Validación de formato:** Fechas en formato YYYY-MM-DD
- **Validación de campos:** Verificación de campos vacíos

### 7.3. Autenticación
- **Login:** Validación de credenciales contra BD
- **Sesión:** Control de usuario actual en memoria
- **Roles:** Acceso diferenciado según rol

---

## 8. TECNOLOGÍAS Y HERRAMIENTAS

### 8.1. Lenguaje y Versión
- **Java:** JDK 8 o superior
- **Características usadas:** Clases, herencia, interfaces implícitas, generics (List<T>)

### 8.2. Base de Datos
- **MySQL:** 5.7 o superior
- **Motor:** InnoDB (soporte transaccional, foreign keys)
- **Charset:** utf8mb4 (soporte completo Unicode)

### 8.3. Conexión
- **JDBC:** Java Database Connectivity
- **Driver:** MySQL Connector/J 8.0.33
- **URL:** `jdbc:mysql://localhost:3306/resident_god`

### 8.4. Gestión de Dependencias
- **Maven:** Gestión de dependencias y compilación
- **Dependencias:** mysql-connector-java 8.0.33

---

## 9. USUARIOS DE PRUEBA

### Administrador
- **Correo:** admin@residencial.com
- **Contraseña:** admin123
- **Rol:** ADMINISTRADOR
- **Funciones:** Crear usuarios, publicar avisos, ver reportes

### Guardia
- **Correo:** guardia@residencial.com
- **Contraseña:** guardia123
- **Rol:** GUARDIA
- **Funciones:** Ver y atender emergencias

### Residente
- **Correo:** juan.perez@email.com
- **Contraseña:** residente123
- **Rol:** RESIDENTE
- **Departamento:** 101
- **Bloque:** A
- **Funciones:** Pagar servicios, crear pedidos, reportar emergencias, ver avisos

---

## 10. ESTRUCTURA DE ARCHIVOS DEL PROYECTO

```
proyectoFinalProgra2yandex/
├── database/
│   └── schema.sql                    # Script completo de BD
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── residencial/
│                   ├── Main.java                    # Clase principal
│                   ├── dao/                          # Capa de acceso a datos
│                   │   ├── UsuarioDAO.java
│                   │   ├── PagoDAO.java
│                   │   ├── PedidoDAO.java
│                   │   ├── EmergenciaDAO.java
│                   │   └── AvisoDAO.java
│                   ├── modelo/                       # Clases del modelo
│                   │   ├── Usuario.java             # Clase base
│                   │   ├── Residente.java            # Extiende Usuario
│                   │   ├── Administrador.java        # Extiende Usuario
│                   │   ├── Guardia.java             # Extiende Usuario
│                   │   ├── Pago.java
│                   │   ├── Pedido.java
│                   │   ├── Emergencia.java
│                   │   └── Aviso.java
│                   ├── util/                        # Utilidades
│                   │   ├── ConexionBD.java          # Conexión MySQL
│                   │   ├── ConfiguracionBD.java    # Configuración
│                   │   └── Conexion.java           # (Alternativa)
│                   └── test/
│                       └── TestConexion.java       # Prueba de conexión
├── lib/
│   └── mysql-connector-j-9.5.0.jar  # Driver JDBC
├── target/                           # Archivos compilados
├── pom.xml                           # Configuración Maven
├── README.md                         # Documentación principal
├── USUARIOS_PRUEBA.md               # Credenciales de prueba
├── SCRIPT_BASE_DATOS.md             # Script SQL completo
└── RESUMEN_COMPLETO_PROYECTO.md     # Este documento
```

---

## 11. CARACTERÍSTICAS DESTACADAS

### 11.1. Simplicidad para Explicación
- **Código limpio:** Sin comentarios excesivos, fácil de leer
- **Sintaxis básica:** Uso de `switch`, `if-else`, `try-catch` estándar
- **Sin frameworks complejos:** Solo JDBC, sin Hibernate ni Spring

### 11.2. Persistencia de Datos
- **Base de datos relacional:** MySQL con relaciones bien definidas
- **Integridad referencial:** Foreign keys con CASCADE y SET NULL
- **Índices:** Optimización de consultas frecuentes

### 11.3. Manejo de Errores
- **Try-Catch:** Captura de excepciones SQLException
- **Validación de entrada:** Prevención de InputMismatchException
- **Mensajes claros:** Errores descriptivos para el usuario

### 11.4. Interfaz de Usuario
- **Menús interactivos:** Navegación clara por opciones numéricas
- **Feedback inmediato:** Mensajes de éxito/error en cada operación
- **Sesión persistente:** Usuario logueado mantiene sesión hasta cerrar

---

## 12. CASOS DE USO PRINCIPALES

### Caso 1: Residente paga servicio de luz
1. Residente inicia sesión
2. Selecciona "Pagar Servicio"
3. Ingresa tipo: LUZ, monto: 50000, fecha vencimiento: 2026-02-15
4. Sistema crea registro en tabla `pagos` con estado PENDIENTE
5. Residente puede ver el pago en "Ver Mis Pagos"

### Caso 2: Residente reporta emergencia
1. Residente inicia sesión
2. Selecciona "Activar Emergencia"
3. Ingresa tipo: FALLA_PLOMERIA, descripción, ubicación, prioridad: ALTA
4. Sistema crea registro en tabla `emergencias` con estado ACTIVA
5. Guardia puede ver la emergencia en "Ver Emergencias Activas"

### Caso 3: Guardia atiende emergencia
1. Guardia inicia sesión
2. Selecciona "Ver Emergencias Activas"
3. Ve lista de emergencias ordenadas por prioridad
4. Selecciona "Atender Emergencia"
5. Ingresa ID de emergencia y nuevo estado: EN_ATENCION
6. Sistema actualiza emergencia y asigna guardia

### Caso 4: Administrador crea usuario
1. Administrador inicia sesión
2. Selecciona "Gestionar Usuarios" → "Crear Usuario"
3. Ingresa datos: nombre, correo, contraseña, rol, departamento, bloque
4. Sistema valida que correo sea único
5. Sistema crea registro en tabla `usuarios`

### Caso 5: Administrador publica aviso
1. Administrador inicia sesión
2. Selecciona "Crear Aviso"
3. Ingresa título, mensaje, tipo: URGENTE
4. Sistema crea registro en tabla `avisos` con activo=TRUE
5. Todos los residentes pueden ver el aviso

---

## 13. MÉTRICAS DEL PROYECTO

- **Clases Java:** 18 clases
  - 8 clases modelo
  - 5 clases DAO
  - 3 clases util
  - 1 clase principal (Main)
  - 1 clase test
- **Líneas de código:** ~2,500 líneas (estimado)
- **Tablas MySQL:** 5 tablas principales
- **Vistas MySQL:** 2 vistas
- **Relaciones:** 5 foreign keys
- **ENUMs:** 8 tipos enumerados
- **Métodos públicos:** ~40 métodos

---

## 14. JUSTIFICACIÓN ACADÉMICA

### ¿Por qué este proyecto demuestra POO?
1. **Herencia:** Jerarquía Usuario → Residente/Administrador/Guardia
2. **Encapsulación:** Atributos privados con getters/setters
3. **Polimorfismo:** Métodos específicos por clase derivada
4. **Abstracción:** Separación Modelo-DAO-Interfaz

### ¿Por qué usar MySQL?
1. **Persistencia:** Datos no se pierden al cerrar aplicación
2. **Integridad:** Foreign keys garantizan consistencia
3. **Escalabilidad:** Base de datos relacional profesional
4. **Realismo:** Sistemas reales usan bases de datos

### ¿Por qué JDBC y no frameworks?
1. **Simplicidad:** Fácil de explicar y entender
2. **Control:** Acceso directo a SQL
3. **Aprendizaje:** Comprende cómo funcionan los frameworks
4. **Académico:** Enfoque en conceptos, no en herramientas

---

## 15. MEJORAS FUTURAS (Opcional)

- Interfaz gráfica con JavaFX o Swing
- Hash de contraseñas (BCrypt)
- Reportes con gráficos
- Notificaciones por email
- Historial de cambios (auditoría)
- Búsqueda avanzada
- Exportación a PDF/Excel

---

## 16. CONCLUSIÓN

Este proyecto es un **sistema completo y funcional** que demuestra:
- Dominio de **Programación Orientada a Objetos**
- Uso de **herencia, encapsulación y polimorfismo**
- Conexión a **base de datos MySQL con JDBC**
- Arquitectura en **capas (Modelo-DAO-Interfaz)**
- Implementación de **patrón DAO**
- **CRUD completo** para todas las entidades
- **Manejo de errores** y validación de entrada
- **Código limpio y fácil de explicar**

Ideal para **defensa académica** en Programación II, ya que cubre todos los conceptos fundamentales de POO y bases de datos de manera práctica y comprensible.

---

**Fecha de creación:** 2026
**Versión:** 1.0.0
**Autor:** Proyecto Final Programación II


