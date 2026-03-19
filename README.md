# FormCompse
Formulario de registro en Kotlin con Jetpack Compose. Incluye validación en tiempo real, diseño con Material 3, animaciones al enfocar, modo oscuro, accesibilidad con TalkBack mediante semantics, navegación entre pantallas y retroalimentación con Snackbar.

# Formulario de Registro en Jetpack Compose

Aplicación móvil desarrollada en Kotlin con Jetpack Compose, enfocada en la creación de un formulario de registro moderno, accesible y con validación en tiempo real.

## Características principales

* Formulario con múltiples campos:

  * Nombre
  * Edad
  * Correo electrónico
  * Teléfono
  * Contraseña

* Validación en tiempo real:

  * Edad válida (1–120)
  * Formato correcto de correo electrónico
  * Contraseña con longitud mínima

* Interfaz moderna:

  * Diseño basado en Material 3
  * Campos estilizados en color azul
  * Botón personalizado con estados activo y deshabilitado

* Experiencia de usuario (UX):

  * Animaciones al enfocar los campos
  * Retroalimentación visual de errores
  * Mensaje de confirmación mediante Snackbar

* Modo oscuro:

  * Activación dinámica mediante botón flotante

* Accesibilidad (a11y):

  * Compatible con TalkBack
  * Uso de Modifier.semantics para describir los campos
  * Navegación lógica y estructurada

* Navegación:

  * Implementada con Navigation Compose
  * Pantalla de éxito después del registro

## Tecnologías utilizadas

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose

## Objetivo

Implementar un formulario aplicando principios de ergonomía cognitiva, prevención de errores y accesibilidad universal.

## Evidencia

Incluye capturas del funcionamiento del formulario y estados de error.

## Notas

Se realizaron pruebas con TalkBack para validar la accesibilidad del sistema, asegurando que los campos sean correctamente identificados por lectores de pantalla.

Proyecto académico enfocado en buenas prácticas de desarrollo Android moderno.

