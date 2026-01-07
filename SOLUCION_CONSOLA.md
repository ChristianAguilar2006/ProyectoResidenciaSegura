# 🔧 SOLUCIÓN: Solo aparece en consola

## ❌ PROBLEMA
Estás ejecutando `Main.java` (versión consola) en lugar de `MainApp.java` (versión JavaFX gráfica).

## ✅ SOLUCIÓN PASO A PASO

### Paso 1: Cerrar la ejecución actual
- Si hay una ejecución corriendo, deténla (botón rojo de stop)

### Paso 2: Abrir el archivo correcto
1. En IntelliJ, ve a la carpeta: `src/main/java/com/residencial/gui/`
2. Abre el archivo: **`MainApp.java`** (NO `Main.java`)

### Paso 3: Ejecutar MainApp.java
**Opción A: Clic derecho**
- Clic derecho en `MainApp.java`
- Selecciona: `Run 'MainApp.main()'`

**Opción B: Atajo de teclado**
- Con `MainApp.java` abierto, presiona: `Shift + F10`

**Opción C: Botón verde**
- Haz clic en el botón ▶️ verde que aparece junto a `public static void main`

### Paso 4: Si aparece error "JavaFX runtime components are missing"

1. Ve a: `Run` → `Edit Configurations...`
   - O presiona: `Alt + Shift + F10` → `0`

2. Clic en `+` (arriba izquierda) → Selecciona `Application`

3. Configura:
   - **Name:** `MainApp JavaFX`
   - **Main class:** `com.residencial.gui.MainApp`
   - **VM options:** Copia y pega esto:
     ```
     --module-path "%USERPROFILE%\.m2\repository\org\openjfx" --add-modules javafx.controls,javafx.fxml
     ```

4. Clic en `Apply` y luego `OK`

5. Ejecuta con `Shift + F10`

## 🎯 DIFERENCIA ENTRE LOS DOS ARCHIVOS

- **`Main.java`** → Versión CONSOLA (texto, menús por teclado)
- **`MainApp.java`** → Versión JAVAFX (ventanas gráficas, botones)

## ✅ RESULTADO ESPERADO

Al ejecutar `MainApp.java` correctamente, deberías ver:
- Una **ventana gráfica** (no texto en consola)
- Título: "Sistema de Gestión Residencial"
- Campos: Correo y Contraseña
- Botones: "Iniciar Sesión" y "Salir"

## 🔍 VERIFICAR QUE ESTÁS EJECUTANDO LO CORRECTO

En la parte superior de IntelliJ, donde dice "Run", debería aparecer:
- ✅ Correcto: `MainApp` o `MainApp.main()`
- ❌ Incorrecto: `Main` o `Main.main()`

Si aparece `Main`, estás ejecutando el archivo incorrecto.

## 📝 NOTA IMPORTANTE

Si quieres usar la versión de consola (texto), ejecuta `Main.java`.
Si quieres usar la versión gráfica (ventanas), ejecuta `MainApp.java`.

