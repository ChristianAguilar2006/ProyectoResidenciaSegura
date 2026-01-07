# 🔧 SOLUCIÓN ERROR EN NETBEANS

## ❌ ERROR ENCONTRADO
```
Error: el método principal debe devolver un valor del tipo void en la clase {0}
```

## ✅ SOLUCIÓN

### Problema:
NetBeans está usando `exec-maven-plugin` que NO funciona bien con JavaFX. Necesitas usar `javafx-maven-plugin`.

### Solución Paso a Paso:

#### Paso 1: Configurar NetBeans para usar JavaFX Maven Plugin

1. **Clic derecho en el proyecto** → `Properties`

2. Ve a: `Actions` (en la lista de la izquierda)

3. Busca: `Run project` o `Execute project`

4. En `Goals`, cambia de:
   ```
   exec:java
   ```
   A:
   ```
   javafx:run
   ```

5. O mejor aún, crea una nueva acción:
   - Clic en `Add...`
   - **Display Name:** `Run JavaFX`
   - **Goals:** `javafx:run`
   - **Set Properties:** Deja vacío
   - Clic en `OK`

6. Clic en `OK` para cerrar Properties

#### Paso 2: Ejecutar con la nueva acción

1. Clic derecho en el proyecto → `Custom` → `Run JavaFX`
   - O desde el menú superior: `Run` → `Custom` → `Run JavaFX`

#### Alternativa: Ejecutar desde Terminal de NetBeans

1. Abre la terminal en NetBeans: `Window` → `Output` → `Terminal`
   - O presiona `Alt + 4` y selecciona `Terminal`

2. Ejecuta:
   ```bash
   mvn clean javafx:run
   ```

#### Alternativa 2: Ejecutar el archivo directamente

1. Abre: `Source Packages` → `com.residencial.gui` → `MainApp.java`

2. Clic derecho en `MainApp.java` → `Run File`
   - O presiona `Shift + F6`

3. Si aparece error de JavaFX, configura:
   - Clic derecho en el proyecto → `Properties` → `Run`
   - En `VM Options`, agrega:
     ```
     --module-path "%USERPROFILE%\.m2\repository\org\openjfx" --add-modules javafx.controls,javafx.fxml
     ```

## 🔧 CAMBIOS REALIZADOS EN pom.xml

1. ✅ Eliminado `exec-maven-plugin` (no funciona con JavaFX)
2. ✅ Mantenido solo `javafx-maven-plugin` (correcto para JavaFX)
3. ✅ Actualizado MySQL connector a la nueva ubicación (`com.mysql`)

## ✅ RESULTADO ESPERADO

Después de ejecutar `mvn javafx:run` o usar la acción personalizada, deberías ver:
- ✅ La ventana gráfica de Login (600x400)
- ✅ Sin errores en consola
- ✅ Campos: Correo y Contraseña funcionando

## 🎯 MÉTODO RECOMENDADO PARA NETBEANS

**La forma más fácil:**

1. Abre la terminal de NetBeans (`Alt + 4`)
2. Ejecuta:
   ```bash
   mvn clean javafx:run
   ```

Esto debería funcionar perfectamente y mostrar la ventana gráfica.

