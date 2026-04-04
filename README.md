# Gello

Gello is a full-stack application for managing scientific journal entries with a focus on gel image analysis. It leverages the power of **Kotlin Multiplatform** to share logic across mobile and desktop platforms.

* **Frontend**: Compose Multiplatform (Android, iOS, and Desktop)
* **Backend**: Django + Django REST Framework
* **Image Processing**: Gel analysis service

---

## Compose Multiplatform Capabilities

This project demonstrates a modern, production-ready KMP setup using the following ecosystem:

*   **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Jetpack Compose for iOS, Android, and Desktop).
*   **Dependency Injection**: [Koin](https://insert-koin.io/) (with BOM support, Compose, and ViewModel integration).
*   **Database**: [Room](https://developer.android.com/kotlin/multiplatform/room) (SQLite) for cross-platform local persistence.
*   **Networking & Architecture**: Powered by the **Forge** framework for:
    *   Type-safe API clients (Ktor-based).
    *   Structured UseCase patterns.
    *   Unified Navigation (Nav2).
    *   Multiplatform Settings/DataStore.
*   **Image Handling**:
    *   [FileKit](https://github.com/vinceglb/FileKit) for cross-platform file picking and Coil integration.
    *   [Krop](https://github.com/m-ayoub/Krop) for native-feel image cropping.
*   **Design System**: Material 3 with custom components and [Compose ColorPicker](https://github.com/skydoves/colorpicker-compose).
*   **Concurrency & Serialization**: Kotlinx Coroutines, Serialization (JSON), and Datetime.

---

## Features

### 🔐 Authentication & Profile
*   Secure Registration and Login.
*   JWT-based session management shared across platforms.

### 📚 Journal Management
*   **Create/Delete**: Manage scientific journals.
*   **Search**: Full-text search across all journal entries.
*   **Organization**: Nested entry management within journals.

### 🧪 Entry Creation & Gel Analysis
*   **Multi-step Flow**: A guided wizard for creating complex scientific entries.
*   **Image Processing**:
    *   Upload gel images directly from device/desktop.
    *   Built-in **Image Cropping** (Krop) for precise analysis areas.
    *   **Automatic Lane Detection**: Integrated with a specialized backend service.
*   **Data Editing**:
    *   Manual correction of detected lane counts.
    *   Interactive Lane Table to specify probes and volumes.
    *   JSON-based storage for flexible content schemas.

---

## Architecture

### Frontend (Compose Multiplatform)

The project follows a modularized **Clean Architecture** approach:

*   `:composeApp` → UI layer. Screens, ViewModels (MVI), and Navigation logic.
*   `:core:domain` → Pure business logic. UseCases and Repository interfaces.
*   `:core:data` → Implementation detail. API clients, Room Database, and Mappers.
*   `:core:designsystem` → The "Gello" look & feel. Shared M3 components and theming.

**Pattern: MVI (Model-View-Intent)**
1.  **UI** sends an **Intent** to the **ViewModel**.
2.  **ViewModel** interacts with **UseCases**.
3.  **ViewModel** updates a single, immutable **State**.
4.  **UI** observes the state and re-renders.

---

## Setup

### Backend Setup
1.  **Clone the project**.
2.  **Install Python 3.12**.
3.  `make setup` → `make migrate` → `make run`.

### Frontend Setup
1.  **Open in Android Studio** (or IntelliJ IDEA for Desktop/iOS).
2.  **Update IP**: In `core:data` (build.gradle), set your machine's local IP (don't use localhost for mobile emulators).
3.  **Sync Gradle** and select your target:
    *   `androidApp`
    *   `iosApp` (Requires macOS & Xcode)
    *   `desktopApp` (Runs via JVM)