# 🚀 EJECUTAR JAVAFX EN NETBEANS - SOLUCIÓN DEFINITIVA

## ❌ PROBLEMA
NetBeans está usando `exec-maven-plugin` que NO funciona con JavaFX. Necesitas usar `javafx-maven-plugin`.

## ✅ SOLUCIÓN PASO A PASO

### Método 1: Usar Terminal de NetBeans (MÁS FÁCIL Y FUNCIONA SIEMPRE)

1. **Abre la terminal en NetBeans:**
   - `Window` → `Output` → `Terminal`
   - O presiona `Alt + 4` y selecciona `Terminal`

2. **Ejecuta este comando:**
   ```bash
   mvn clean javafx:run
   ```

3. **Deberías ver la ventana gráfica de Login**

### Método 2: Configurar NetBeans para usar JavaFX Plugin

1. **Cierra NetBeans completamente**

2. **Abre el archivo `nbactions.xml`** que acabo de crear en la raíz del proyecto

3. **Verifica que tenga este contenido:**
   ```xml
   <goals>
       <goal>javafx:run</goal>
   </goals>
   ```

4. **Abre NetBeans de nuevo**

5. **Recarga el proyecto:**
   - Clic derecho en el proyecto → `Reload POM`

6. **Ejecuta el proyecto:**
   - Clic derecho en el proyecto → `Run`
   - O presiona `F6`

### Método 3: Crear Acción Personalizada en NetBeans

1. **Clic derecho en el proyecto** → `Properties`

2. Ve a: `Actions` (en la lista izquierda)

3. **Busca:** `Run project` o `Execute project`

4. **En `Goals`, cambia de:**
   ```
   exec:java
   ```
   **A:**
   ```
   javafx:run
   ```

5. **Clic en `OK`**

6. **Ejecuta el proyecto con `F6`**

### Método 4: Ejecutar Archivo Directamente (SIN Maven)

1. **Abre:** `Source Packages` → `com.residencial.gui` → `MainApp.java`

2. **Clic derecho** → `Run File`
   - O presiona `Shift + F6`

3. **Si aparece error de JavaFX:**
   - Clic derecho en el proyecto → `Properties` → `Run`
   - En `VM Options`, agrega:
     ```
     --module-path "%USERPROFILE%\.m2\repository\org\openjfx" --add-modules javafx.controls,javafx.fxml
     ```

## 🎯 RECOMENDACIÓN

**USA EL MÉTODO 1** (Terminal de NetBeans):
```bash
mvn clean javafx:run
```

Es el más confiable y siempre funciona.

## 🔧 VERIFICAR QUE FUNCIONA

Después de ejecutar `mvn clean javafx:run`, deberías ver:
- ✅ La ventana gráfica de Login (600x400 píxeles)
- ✅ Título: "Sistema de Gestión Residencial"
- ✅ Campos: Correo y Contraseña
- ✅ Botones: "Iniciar Sesión" y "Salir"
- ✅ NO deberías ver texto en consola (solo la ventana gráfica)

## 📝 NOTA IMPORTANTE

El archivo `nbactions.xml` que creé le dice a NetBeans que use `javafx:run` en lugar de `exec:java`. Esto debería solucionar el problema permanentemente.

## 🐛 SI SIGUE SIN FUNCIONAR

1. **Verifica que JavaFX esté descargado:**
   ```bash
   mvn dependency:resolve
   ```

2. **Limpia y reconstruye:**
   ```bash
   mvn clean compile
   ```

3. **Ejecuta de nuevo:**
   ```bash
   mvn javafx:run
   ```

