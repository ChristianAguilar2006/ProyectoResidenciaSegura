# CÓMO EJECUTAR LA APLICACIÓN JAVAFX

## Método 1: Desde IntelliJ IDEA (RECOMENDADO)

### Paso 1: Compilar el proyecto
1. Abre IntelliJ IDEA
2. Abre el proyecto `proyectoFinalProgra2yandex`
3. Espera a que Maven descargue las dependencias (verás una barra de progreso)
4. Si no se descargan automáticamente, ve a: `File` → `Maven` → `Reload Project`

### Paso 2: Ejecutar la aplicación
1. Abre el archivo: `src/main/java/com/residencial/gui/MainApp.java`
2. Haz clic derecho en el archivo
3. Selecciona: `Run 'MainApp.main()'`
4. O presiona `Shift + F10`

### Si aparece error "JavaFX runtime components are missing":

**Solución A: Configurar VM Options**
1. Ve a `Run` → `Edit Configurations...`
2. Selecciona la configuración de `MainApp`
3. En `VM options`, agrega:
   ```
   --module-path "C:\Users\Usuario\.m2\repository\org\openjfx\javafx-controls\17.0.2" --add-modules javafx.controls,javafx.fxml
   ```
4. O usa esta ruta completa (ajusta según tu sistema):
   ```
   --module-path "C:\Users\Usuario\.m2\repository\org\openjfx" --add-modules javafx.controls,javafx.fxml
   ```

**Solución B: Usar JavaFX Maven Plugin**
1. Abre la terminal en IntelliJ (Alt + F12)
2. Ejecuta:
   ```bash
   mvn clean javafx:run
   ```

---

## Método 2: Desde la Terminal (Maven)

### Opción A: Usando JavaFX Maven Plugin
```bash
cd "C:\Users\Usuario\OneDrive\Escritorio\APRENDIENDO JAVA CHRISTIAN JR\proyectoFinalProgra2yandex"
mvn clean javafx:run
```

### Opción B: Compilar y ejecutar manualmente
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.residencial.gui.MainApp"
```

---

## Método 3: Ejecutar el JAR (Después de compilar)

```bash
# Compilar y empaquetar
mvn clean package

# Ejecutar (ajusta la ruta según donde esté JavaFX)
java --module-path "ruta-a-javafx/lib" --add-modules javafx.controls,javafx.fxml -jar target/sistema-residencial-1.0.0.jar
```

---

## VERIFICAR QUE TODO ESTÉ BIEN

### 1. Verificar que MySQL esté corriendo
- Abre MySQL Workbench o verifica el servicio MySQL
- La base de datos `resident_god` debe existir

### 2. Verificar credenciales en ConexionBD.java
- Abre: `src/main/java/com/residencial/util/ConexionBD.java`
- Verifica que `USER` y `PASSWORD` sean correctos

### 3. Verificar que Maven haya descargado JavaFX
- Ve a: `File` → `Project Structure` → `Libraries`
- Deberías ver: `javafx-controls-17.0.2` y `javafx-fxml-17.0.2`

---

## QUÉ VERÁS AL EJECUTAR

1. **Ventana de Login** (600x400 píxeles)
   - Campo de correo
   - Campo de contraseña
   - Botones: "Iniciar Sesión" y "Salir"

2. **Después de iniciar sesión:**
   - **Residente**: Ventana con pestañas (Perfil, Pagar Servicio, Mis Pagos, Crear Pedido, Mis Pedidos, Emergencia, Avisos)
   - **Administrador**: Ventana con pestañas (Perfil, Crear Usuario, Crear Aviso, Avisos, Reportes)
   - **Guardia**: Ventana con pestañas (Perfil, Emergencias Activas, Atender Emergencia)

---

## USUARIOS DE PRUEBA

**Administrador:**
- Correo: `admin@residencial.com`
- Contraseña: `admin123`

**Guardia:**
- Correo: `guardia@residencial.com`
- Contraseña: `guardia123`

**Residente:**
- Correo: `juan.perez@email.com`
- Contraseña: `residente123`

---

## SOLUCIÓN DE PROBLEMAS COMUNES

### Error: "Cannot find resource /fxml/Login.fxml"
**Solución:** Verifica que el archivo esté en `src/main/resources/fxml/Login.fxml`

### Error: "JavaFX runtime components are missing"
**Solución:** Usa el Método 1 - Solución A o B (arriba)

### Error: "Access denied for user 'root'"
**Solución:** Verifica las credenciales en `ConexionBD.java`

### Error: "Unknown database 'resident_god'"
**Solución:** Ejecuta el script `database/schema.sql` en MySQL

### La ventana no aparece
**Solución:** Revisa la consola de IntelliJ para ver errores. Presiona `Alt + 4` para ver la consola.

---

## TIPS

- Si cambias algo en los archivos FXML, recarga el proyecto
- Si cambias código Java, recompila antes de ejecutar
- Usa `Ctrl + Shift + F10` para ejecutar rápidamente
- La aplicación se cierra al hacer clic en "Salir" o cerrar la ventana

