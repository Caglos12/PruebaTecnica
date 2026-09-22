# Prueba Técnica Android — Listado de Contactos

Aplicación Android que muestra un listado de contactos con filtrado, ordenamiento y llamada directa, desarrollada como parte de una prueba técnica. Los datos se cargan desde un JSON local (sin acceso a base de datos).

## Funcionalidades

- **Listado de contactos** con código, nombre, teléfono, email y estado (visitado / no visitado).
- **Filtro** por *todos*, *visitados* y *no visitados*.
- **Ordenamiento** por *código* o por *nombre*.
- **Llamada directa**: al tocar el teléfono se abre el marcador del sistema con el número cargado.
- **Splash screen** al iniciar (API oficial `core-splashscreen`).
- **Navegación** con `BottomNavigationView` entre dos secciones: *Contactos* y *Preferencias*.

## Arquitectura

El proyecto sigue **MVVM + Repository**, con **inyección de dependencias manual** (sin Hilt, por ser un proyecto de alcance pequeño).

```
UI (Fragment/Activity)  ->  ViewModel  ->  Repository  ->  DataSource
     estado y eventos        LiveData      fuente única     origen abstracto
```

- **`DataSource`** es una interfaz. Hoy la implementa `MockDataSource` (lee un JSON de `assets/`); mañana podría ser SQLite o una API sin tocar el resto de la app.
- **`ContactRepository`** es la única fuente de verdad y oculta de dónde vienen los datos.
- **`ContactsViewModel`** mantiene el estado del filtro y el orden, y expone la lista con `LiveData` (sobrevive rotaciones).
- **`AppContainer`** (en la clase `Application`) arma la cadena de dependencias una sola vez y la entrega al `ViewModel` mediante un `Factory`.

## Stack técnico

- **Lenguaje:** Java 11
- **minSdk:** 27 · **targetSdk / compileSdk:** 37
- **ViewBinding**
- **Navigation Component** (fragments)
- **Lifecycle** (ViewModel + LiveData)
- **Material 3**
- **Core SplashScreen**
- **RecyclerView** con `ListAdapter` + `DiffUtil`
- **Datos mock** desde JSON con `org.json` (incluido en Android)

## Estructura del proyecto

```
com.example.pruebatecnica/
├── PruebaTecnicaApp.java              # Application: inicializa el AppContainer (DI)
├── di/
│   └── AppContainer.java              # Inyección de dependencias manual
├── data/
│   ├── model/
│   │   └── Contact.java               # Modelo inmutable
│   ├── source/
│   │   ├── DataSource.java            # Interfaz (origen de datos abstracto)
│   │   └── MockDataSource.java        # Lee contacts.json desde assets
│   └── repository/
│       └── ContactRepository.java     # Única fuente de verdad
└── ui/
    ├── contacts/
    │   ├── ContactsFragment.java
    │   ├── ContactsViewModel.java          # Filtro y ordenamiento
    │   ├── ContactsViewModelFactory.java
    │   └── adapter/
    │       ├── ContactsAdapter.java        # ListAdapter + DiffUtil
    │       └── ContactViewHolder.java
    ├── home/
    │   └── HomeActivity.java               # BottomNavigation + NavHostFragment
    ├── preferences/
    │   └── PreferencesFragment.java
    └── splash/
        └── MainActivity.java               # Splash screen

app/src/main/assets/contacts.json           # 30 contactos de ejemplo
```

## Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/<usuario>/<repositorio>.git
   ```
2. Ábrelo en Android Studio.
3. Espera el **Gradle Sync**.
4. Ejecuta en un emulador o dispositivo con **API 27 o superior**.

## Decisiones de diseño

- **Sin Hilt:** para el alcance del proyecto, la DI manual con `AppContainer` es suficiente y evita sobre-ingeniería.
- **`ACTION_DIAL` en lugar de `CALL_PHONE`:** abre el marcador con el número cargado y **no requiere permisos**. El usuario confirma la llamada.
- **Datos en JSON (`assets/`):** separa los datos de la lógica y, gracias a la interfaz `DataSource`, la fuente es intercambiable sin tocar el repositorio ni la UI.
- **Filtro y orden en el `ViewModel`:** el estado sobrevive rotaciones y la lista original nunca se muta; siempre se recalcula desde ella.
- **Modelo inmutable + `DiffUtil`:** cambios de filtro/orden animados y comparaciones seguras en el `RecyclerView`.
