# Script Completo de Base de Datos

```sql
-- =====================================================
-- SISTEMA UNIFICADO DE GESTIÓN PARA CONJUNTOS RESIDENCIALES
-- Base de Datos MySQL - Script de Creación
-- =====================================================

-- Crear la base de datos
DROP DATABASE IF EXISTS resident_god;
CREATE DATABASE resident_god CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE resident_god;

-- =====================================================
-- TABLA: usuarios
-- =====================================================
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('RESIDENTE', 'ADMINISTRADOR', 'GUARDIA') NOT NULL DEFAULT 'RESIDENTE',
    departamento VARCHAR(50),
    bloque VARCHAR(50),
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    INDEX idx_correo (correo),
    INDEX idx_rol (rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: pagos
-- =====================================================
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo_servicio ENUM('LUZ', 'AGUA', 'ALICUOTA', 'GAS', 'OTRO') NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento DATE,
    estado ENUM('PENDIENTE', 'PAGADO', 'VENCIDO', 'CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    metodo_pago VARCHAR(50),
    comprobante VARCHAR(255),
    observaciones TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    INDEX idx_usuario (id_usuario),
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha_pago)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: pedidos
-- =====================================================
CREATE TABLE pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    descripcion TEXT NOT NULL,
    tipo_pedido ENUM('ENCARGO', 'SERVICIO', 'PRODUCTO', 'OTRO') NOT NULL DEFAULT 'ENCARGO',
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega_estimada DATE,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'COMPLETADO', 'CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    costo DECIMAL(10, 2) DEFAULT 0.00,
    observaciones TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    INDEX idx_usuario (id_usuario),
    INDEX idx_estado (estado),
    INDEX idx_fecha (fecha_solicitud)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: emergencias
-- =====================================================
CREATE TABLE emergencias (
    id_emergencia INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo ENUM('MEDICA', 'INCENDIO', 'ROBO', 'ACCIDENTE', 'FALLA_ELECTRICA', 'FALLA_PLOMERIA', 'OTRA') NOT NULL,
    descripcion TEXT NOT NULL,
    ubicacion VARCHAR(255),
    fecha_reporte TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ACTIVA', 'EN_ATENCION', 'RESUELTA', 'CANCELADA') NOT NULL DEFAULT 'ACTIVA',
    prioridad ENUM('BAJA', 'MEDIA', 'ALTA', 'CRITICA') NOT NULL DEFAULT 'MEDIA',
    id_guardia_atendiendo INT,
    fecha_resolucion TIMESTAMP NULL,
    observaciones TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_guardia_atendiendo) REFERENCES usuarios(id_usuario) ON DELETE SET NULL,
    INDEX idx_usuario (id_usuario),
    INDEX idx_estado (estado),
    INDEX idx_prioridad (prioridad),
    INDEX idx_fecha (fecha_reporte)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABLA: avisos
-- =====================================================
CREATE TABLE avisos (
    id_aviso INT AUTO_INCREMENT PRIMARY KEY,
    id_administrador INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    tipo ENUM('INFORMATIVO', 'URGENTE', 'MANTENIMIENTO', 'EVENTO', 'OTRO') NOT NULL DEFAULT 'INFORMATIVO',
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion DATE,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_administrador) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    INDEX idx_activo (activo),
    INDEX idx_fecha (fecha_publicacion),
    INDEX idx_tipo (tipo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- DATOS INICIALES
-- =====================================================

INSERT INTO usuarios (nombre, correo, contraseña, rol, departamento, bloque) VALUES
('Administrador Principal', 'admin@residencial.com', 'admin123', 'ADMINISTRADOR', 'ADMIN', 'ADMIN');

INSERT INTO usuarios (nombre, correo, contraseña, rol, departamento, bloque) VALUES
('Guardia Principal', 'guardia@residencial.com', 'guardia123', 'GUARDIA', 'SEGURIDAD', 'ENTRADA');

INSERT INTO usuarios (nombre, correo, contraseña, rol, departamento, bloque, telefono) VALUES
('Juan Pérez', 'juan.perez@email.com', 'residente123', 'RESIDENTE', '101', 'A', '3001234567');

INSERT INTO avisos (id_administrador, titulo, mensaje, tipo) VALUES
(1, 'Bienvenida al Sistema', 'Bienvenidos al Sistema Unificado de Gestión. Este es un aviso de prueba.', 'INFORMATIVO');

-- =====================================================
-- VISTAS
-- =====================================================

CREATE OR REPLACE VIEW vista_pagos_usuario AS
SELECT 
    u.id_usuario,
    u.nombre,
    u.correo,
    COUNT(p.id_pago) AS total_pagos,
    SUM(CASE WHEN p.estado = 'PAGADO' THEN p.monto ELSE 0 END) AS total_pagado,
    SUM(CASE WHEN p.estado = 'PENDIENTE' THEN p.monto ELSE 0 END) AS total_pendiente
FROM usuarios u
LEFT JOIN pagos p ON u.id_usuario = p.id_usuario
WHERE u.rol = 'RESIDENTE'
GROUP BY u.id_usuario, u.nombre, u.correo;

CREATE OR REPLACE VIEW vista_emergencias_activas AS
SELECT 
    e.id_emergencia,
    e.tipo,
    e.descripcion,
    e.ubicacion,
    e.prioridad,
    e.fecha_reporte,
    u.nombre AS nombre_usuario,
    u.departamento,
    u.bloque,
    g.nombre AS guardia_atendiendo
FROM emergencias e
INNER JOIN usuarios u ON e.id_usuario = u.id_usuario
LEFT JOIN usuarios g ON e.id_guardia_atendiendo = g.id_usuario
WHERE e.estado IN ('ACTIVA', 'EN_ATENCION')
ORDER BY 
    CASE e.prioridad 
        WHEN 'CRITICA' THEN 1 
        WHEN 'ALTA' THEN 2 
        WHEN 'MEDIA' THEN 3 
        WHEN 'BAJA' THEN 4 
    END,
    e.fecha_reporte DESC;
```

