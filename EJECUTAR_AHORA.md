# 🚀 EJECUTAR LA APLICACIÓN JAVAFX - PASO A PASO

## ✅ PASO 1: Verificar que MySQL esté corriendo

1. Abre MySQL Workbench o verifica que el servicio MySQL esté activo
2. Verifica que la base de datos `resident_god` exista
3. Si no existe, ejecuta el script: `database/schema.sql`

## ✅ PASO 2: Verificar credenciales de MySQL

El archivo `ConexionBD.java` tiene:
- Usuario: `root`
- Contraseña: `123456`

Si tu contraseña es diferente, cámbiala en:
`src/main/java/com/residencial/util/ConexionBD.java`

## ✅ PASO 3: Ejecutar desde IntelliJ IDEA

### Opción A: Ejecución Directa (MÁS FÁCIL)

1. **Abre IntelliJ IDEA** con tu proyecto

2. **Espera a que Maven descargue dependencias**
   - Verás una barra de progreso abajo
   - Si no aparece, ve a: `File` → `Maven` → `Reload Project`

3. **Abre el archivo:**
   ```
   src/main/java/com/residencial/gui/MainApp.java
   ```

4. **Ejecuta de una de estas formas:**
   - Clic derecho en `MainApp.java` → `Run 'MainApp.main()'`
   - O presiona `Shift + F10`
   - O haz clic en el botón ▶️ verde junto a `public static void main`

### Si aparece ERROR: "JavaFX runtime components are missing"

**Solución Rápida:**

1. Ve a: `Run` → `Edit Configurations...` (o `Alt + Shift + F10` → `0`)

2. Si no existe configuración, crea una nueva:
   - Clic en `+` (arriba a la izquierda)
   - Selecciona `Application`

3. Configura:
   - **Name:** `MainApp JavaFX`
   - **Main class:** `com.residencial.gui.MainApp`
   - **VM options:** Copia y pega esto:
     ```
     --module-path "%USERPROFILE%\.m2\repository\org\openjfx" --add-modules javafx.controls,javafx.fxml
     ```

4. Clic en `Apply` y luego `OK`

5. Ejecuta con `Shift + F10` o el botón ▶️

### Opción B: Usar Maven desde IntelliJ

1. Abre la terminal en IntelliJ (`Alt + F12`)

2. Ejecuta:
   ```bash
   mvn clean javafx:run
   ```

## ✅ PASO 4: Probar la aplicación

1. **Deberías ver una ventana de Login** (600x400 píxeles)

2. **Prueba con estos usuarios:**

   **Administrador:**
   - Correo: `admin@residencial.com`
   - Contraseña: `admin123`

   **Guardia:**
   - Correo: `guardia@residencial.com`
   - Contraseña: `guardia123`

   **Residente:**
   - Correo: `juan.perez@email.com`
   - Contraseña: `residente123`

3. **Después de iniciar sesión**, verás la ventana correspondiente a tu rol con pestañas

## 🔧 SOLUCIÓN DE PROBLEMAS

### Error: "Cannot find resource /fxml/Login.fxml"
**Solución:** 
- Verifica que el archivo exista en: `src/main/resources/fxml/Login.fxml`
- Si no existe, recarga el proyecto: `File` → `Invalidate Caches / Restart`

### Error: "Access denied for user 'root'"
**Solución:**
- Verifica que MySQL esté corriendo
- Verifica la contraseña en `ConexionBD.java`
- Prueba conectarte desde MySQL Workbench con las mismas credenciales

### Error: "Unknown database 'resident_god'"
**Solución:**
- Ejecuta el script `database/schema.sql` en MySQL
- O crea la base de datos manualmente:
  ```sql
  CREATE DATABASE resident_god;
  USE resident_god;
  ```
  Luego ejecuta el resto del script

### La ventana no aparece
**Solución:**
- Revisa la consola de IntelliJ (abajo, pestaña "Run" o "Console")
- Busca errores en rojo
- Presiona `Alt + 4` para ver la consola si no la ves

### Error de compilación
**Solución:**
- Ve a: `File` → `Invalidate Caches / Restart`
- Espera a que se recargue el proyecto
- Intenta compilar: `Build` → `Rebuild Project`

## 📝 NOTAS IMPORTANTES

- La primera vez puede tardar mientras Maven descarga JavaFX
- Si cambias código, recarga el proyecto o recompila
- La aplicación se cierra al hacer clic en "Salir" o cerrar la ventana
- Cada rol tiene su propia interfaz con diferentes pestañas

## 🎯 QUÉ ESPERAR

1. **Ventana de Login** - Interfaz simple con dos campos y botones
2. **Ventana de Residente** - 7 pestañas con todas las funcionalidades
3. **Ventana de Administrador** - 5 pestañas para gestión
4. **Ventana de Guardia** - 3 pestañas para emergencias

¡Listo! Sigue estos pasos y deberías ver tu aplicación funcionando. 🎉

