# Bienvenido App - Android Kotlin

Una aplicación Android moderna desarrollada con Kotlin y Material Design 3.

## 🚀 Características

### 🎨 Material Design 3
- Esquema de colores completo (light/dark themes)
- Componentes Material modernos (CardView, TextInputLayout, MaterialButton)
- Diseño responsive y accesible

### 🏗️ Arquitectura Moderna
- **ViewBinding**: Acceso type-safe a las vistas
- **Código organizado**: Funciones separadas para mejor mantenibilidad
- **Validación robusta**: Manejo de errores y validación en tiempo real

### 💫 Experiencia de Usuario
- Validación en tiempo real del campo de nombre
- Mensajes de error descriptivos
- Auto-limpieza del campo después de enviar
- Feedback visual inmediato
- Diseño centrado con Material Card

### 🌐 Internacionalización
- Strings externalizados para fácil traducción
- Soporte para múltiples idiomas

## 📱 Funcionalidad

1. El usuario ingresa su nombre en un campo de texto
2. La aplicación valida que el campo no esté vacío
3. Muestra un mensaje de bienvenida personalizado
4. Limpia automáticamente el campo para una nueva entrada

## 🛠️ Tecnologías

- **Kotlin**: Lenguaje principal
- **Android SDK**: API 24+ (Android 7.0+)
- **Material Design 3**: Sistema de diseño moderno
- **ViewBinding**: Binding de vistas type-safe
- **ConstraintLayout**: Layout flexible y responsive

## 📦 Dependencias Principales

```kotlin
// Core Android
implementation("androidx.core:core-ktx")
implementation("androidx.appcompat:appcompat")
implementation("androidx.activity:activity-ktx")

// Material Design
implementation("com.google.android.material:material")

// Layout
implementation("androidx.constraintlayout:constraintlayout")

// Lifecycle Components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx")
implementation("androidx.lifecycle:lifecycle-livedata-ktx")
```

## 🏗️ Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/test/
│   │   └── MainActivity.kt
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml
│   │   ├── values/
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   └── themes.xml
│   │   └── ...
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## 🎯 Mejoras Implementadas

### De la versión básica a la moderna:

1. **UI/UX**: De layout básico a Material Design 3 con cards y componentes modernos
2. **Arquitectura**: De findViewById a ViewBinding type-safe
3. **Validación**: De Toast básico a validación en tiempo real con mensajes de error
4. **Código**: De monolítico a funciones separadas y organizadas
5. **Recursos**: De strings hardcoded a recursos externalizados

## 🚀 Para Ejecutar

1. Clona el repositorio
2. Abre en Android Studio
3. Sincroniza el proyecto (Gradle Sync)
4. Ejecuta en un emulador o dispositivo físico

## 📱 Capturas de Pantalla

*La aplicación presenta un diseño limpio y moderno con un card centrado que contiene el título, campo de entrada y botón de acción.*

---

**Desarrollado con ❤️ usando Kotlin y Material Design 3**
# censa-kotlin
