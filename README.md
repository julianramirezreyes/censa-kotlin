# Censa App - Android Kotlin

Una aplicación Android educativa moderna desarrollada con Kotlin y Material Design 3 que ofrece herramientas académicas útiles.

## 🚀 Características

### 🎨 Material Design 3
- Esquema de colores completo (light/dark themes)
- Componentes Material modernos (CardView, TextInputLayout, MaterialButton)
- Diseño responsive y accesible

### 🏗️ Arquitectura Moderna
- **ViewBinding**: Acceso type-safe a las vistas
- **SharedPreferences**: Persistencia de datos de usuario
- **Código organizado**: Funciones separadas para mejor mantenibilidad
- **Validación robusta**: Manejo de errores y validación en tiempo real

### 💫 Experiencia de Usuario
- Flujo de navegación intuitivo: Login → Menú → Funcionalidades
- Mensajes de error descriptivos
- Feedback visual inmediato con popups informativos
- Diseño centrado con Material Cards
- Navegación automática entre pantallas

### 🌐 Internacionalización
- Strings externalizados para fácil traducción
- Soporte para múltiples idiomas

## 📱 Funcionalidades

### 🔐 Pantalla de Bienvenida
- Formulario de ingreso de nombre con validación en tiempo real
- Mensaje de bienvenida personalizado
- Almacenamiento del nombre del usuario
- Redirección automática al menú principal

### 📋 Menú Principal
- Interfaz con cards clickeables para cada funcionalidad
- Bienvenida personalizada con el nombre del usuario
- Navegación intuitiva a cada módulo

### 📝 Calculadora de Notas
- Sistema de calificaciones con ponderaciones:
  - **Conocimiento**: 33%
  - **Desempeño**: 33% 
  - **Producto**: 34%
- Almacenamiento de nombre del estudiante, materia y nota definitiva
- Popup emergente con resultado detallado
- Validación completa de campos y rangos (0-5)
- Criterio de aprobación: promedio ≥ 4.0

### 🔢 Generador de Números Primos
- Algoritmo optimizado para detección de primos
- Generación de los primeros X números primos solicitados
- Validación de entrada (límite de 1000 para rendimiento)
- Presentación clara y formateada de resultados

### 💱 Conversor de Divisas
- Soporte para 10 monedas principales:
  - USD, EUR, GBP, JPY, COP, MXN, ARS, CAD, AUD, CHF
- Conversión precisa usando USD como base
- Muestra tasa de cambio utilizada
- Validación de montos y selección de monedas

## 🛠️ Tecnologías

- **Kotlin**: Lenguaje principal
- **Android SDK**: API 24+ (Android 7.0+)
- **Material Design 3**: Sistema de diseño moderno
- **ViewBinding**: Binding de vistas type-safe
- **ConstraintLayout**: Layout flexible y responsive
- **SharedPreferences**: Almacenamiento persistente

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
implementation("androidx.fragment:fragment-ktx")
```

## 🏗️ Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/test/
│   │   ├── MainActivity.kt          # Pantalla de bienvenida/login
│   │   ├── MainMenuActivity.kt      # Menú principal
│   │   ├── NotasActivity.kt         # Calculadora de notas
│   │   ├── PrimosActivity.kt        # Generador de números primos
│   │   └── DivisasActivity.kt       # Conversor de divisas
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_main_menu.xml
│   │   │   ├── activity_notas.xml
│   │   │   ├── activity_primos.xml
│   │   │   └── activity_divisas.xml
│   │   ├── values/
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   └── themes.xml
│   │   └── ...
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## 🎯 Arquitectura de Navegación

```
MainActivity (Login)
    ↓ (2 segundos después)
MainMenuActivity
    ├── NotasActivity
    ├── PrimosActivity
    └── DivisasActivity
```

## 🚀 Para Ejecutar

1. Clona el repositorio
2. Abre en Android Studio
3. Sincroniza el proyecto (Gradle Sync)
4. Ejecuta en un emulador o dispositivo físico

## 📱 Flujo de Uso

1. **Inicio**: Ingresa tu nombre en la pantalla de bienvenida
2. **Menú**: Selecciona una de las 3 herramientas disponibles
3. **Calculadora**: Ingresa datos académicos y obtén resultado con popup
4. **Primos**: Especifica cantidad y genera números primos
5. **Divisas**: Convierte entre 10 monedas diferentes

## 🎨 Características de Diseño

- **Cards interactivas** con iconos representativos
- **Colores semánticos** para feedback visual (verde/rojo)
- **Popups informativos** para resultados importantes
- **ScrollViews** para contenido extenso
- **Spinners** para selección de opciones

---

**Desarrollado con ❤️ usando Kotlin y Material Design 3**
