# Gello

Gello is a full-stack application for managing scientific journal entries with a focus on gel image analysis. It consists of:

* **Frontend**: Compose Multiplatform (Android, iOS and Desktop)
* **Backend**: Django + Django REST Framework
* **Image Processing**: Gel analysis service

---

## Features

### Registration

### Login

### Journals

* Create, delete journals
* Search journals
* Organize entries inside journals
* Create, delete entries
* Search entries

### Entries

* Multi-step entry creation flow
* Upload gel images
* Automatic lane detection
* Manual correction of lane count
* Editable lane table (probe + volume)
* JSON-based content storage

### Image Processing

* Upload gel image
* Receive processed image
* Lane detection and table prefill

---

## Architecture

### Frontend (Compose Multiplatform)

Structure:

* `composeApp` → ui layer built with Jetpack Compose. Contains screens, view models, navigation, state handling, and feature-specific presentation logic.
* `core` → shared modules that contain the business logic. data, domain, designsystem, util.
* `core:data` → contains API clients, request/response models, repository implementations, mappers, and remote/local data handling.
* `core:domain` → contains business models, repository interfaces, and use cases. This module defines the application logic independently from UI and network details.
* `core:designsystem` → reusable UI components, theming, typography, spacing, colors, and shared Compose building blocks used across the app.
* `core:util` → shared utilities and helpers such as enums, extensions, formatting helpers, date handling, color parsing.

Architecture pattern:

* MVI (Model-View-Intent)
* ViewModel-driven state
* Unidirectional data flow

Key concepts:

* `State` → UI state
* `Intent` → user actions
* `ViewModel` → handles logic

Example flow:

```
UI → Intent → ViewModel → UseCase → Repository → API
```

---

### Backend (Django)

* Django REST Framework
* JWT Authentication
* Project ↔ Entry relationship

## Setup

## Backend Setup

### 1. Clone the project

### 2. Install Python

Make sure you have the correct python version installed → 3.12.

```python --version```

### 3. Setup the project

```make setup```

### 4. Run migrations

```make migrate```

### 5. Start the backend

```make run```

## Frontend Setup

### 1. Open Project

### 2. Update IP

In the ```core:data``` module (build.gradle), replace the URL with your IP.

You cannot use ```localhost```. You have to use your machines local IP address.

### 3. Sync gradle

After changing the IP click ```Sync gradle``` in Android Studio.

### 4. Run the project

Select your desired target and press ```Play```. Note: If you want to run Android/iOS you need either
an actual device or you have to have simulator/emulator device ready.