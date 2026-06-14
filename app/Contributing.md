# 📋 HMIF Ukrida Mobile — Team Rules & Guidelines

> **Project:** HMIF-U Mobile (Informatics Student Union Management System)
> **Team Size:** 3 people
> **Timeline:** 2 Weeks MVP
> **Stack:** Android (Kotlin + Jetpack Compose + MVVM) · Laravel REST API · MySQL

---

## 👥 Role Summary

| Role | Responsibilities |
|------|-----------------|
| **Role 1 – Backend Engineer** | MySQL schema design, Laravel REST API, server deployment |
| **Role 2 – Android UI Architect** | Jetpack Compose screens, Navigation, ViewModel & StateFlow |
| **Role 3 – Integration & Repository Manager** | Retrofit interfaces, Repository layer, connecting data to ViewModels |

---

## 🌿 1. Git Workflow

### Branch Naming Convention

```
main              → stable, deployable code only
dev               → integration branch (merge here first, not main)
feature/<name>    → new feature
fix/<name>        → bug fix
chore/<name>      → config, setup, tooling

# Examples:
feature/login-screen
feature/event-api-endpoints
fix/token-expiry-crash
chore/retrofit-setup
```

### Rules

- **Never push directly to `main`.**
- All work goes to a `feature/` or `fix/` branch first.
- Merge into `dev` via Pull Request (PR). At least **1 teammate must review** before merging.
- `main` is only updated from `dev` when a working milestone is confirmed.
- Pull from `dev` at the start of every work session to stay up to date:
  ```bash
  git checkout dev
  git pull origin dev
  git checkout feature/your-branch
  git merge dev
  ```

### Commit Message Format

Use this format for all commits:

```
<type>(<scope>): <short description>

# Types: feat | fix | chore | refactor | docs | style
# Scope: auth | event | announcement | db | api | ui | repo

# Examples:
feat(auth): add JWT login endpoint
feat(ui): build HomeScreen with announcement feed
fix(repo): handle null response from events API
chore(db): add migrations for users and events tables
docs(api): update Postman collection with event endpoints
```

### Pull Request Checklist

Before opening a PR, confirm:
- [ ] Code compiles/runs without errors
- [ ] Branch is up to date with `dev`
- [ ] PR title matches the feature/fix
- [ ] Brief description of what changed and why

---

## 🧱 2. Coding Conventions

### Android (Kotlin)

**Naming**

| Element | Convention | Example |
|---------|-----------|---------|
| Classes | PascalCase | `AnnouncementViewModel` |
| Functions & variables | camelCase | `fetchAnnouncements()`, `isLoading` |
| Constants | SCREAMING_SNAKE_CASE | `BASE_URL`, `TOKEN_KEY` |
| Layout/Screen files | PascalCase + "Screen" | `HomeScreen.kt`, `LoginScreen.kt` |
| API response models | PascalCase + "Dto" | `EventDto`, `UserDto` |
| Database/domain models | PascalCase | `Event`, `Announcement` |
| Retrofit interfaces | PascalCase + "ApiService" | `EventApiService` |
| Repository classes | PascalCase + "Repository" | `EventRepository` |

**Package Structure**

```
com.hmif.ukrida/
├── data/
│   ├── model/          # Data classes (Dto & domain models)
│   ├── remote/         # Retrofit API service interfaces
│   └── repository/     # Repository classes
├── ui/
│   ├── screens/        # Composable screen files
│   ├── components/     # Reusable UI components
│   └── theme/          # Colors, Typography, Shapes
└── viewmodel/          # ViewModel classes
```

**General Rules**

- Every screen has its own ViewModel. No shared ViewModels between screens unless it's a shared session/auth state.
- StateFlow is the only allowed state holder in ViewModels. No `LiveData`.
- All network calls must be wrapped in a `try-catch` inside a `viewModelScope.launch`.
- Never call a Repository directly from a Composable. Always go through a ViewModel.
- Use `sealed class` for UI state:

```kotlin
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### Laravel (Backend)

**Naming**

| Element | Convention | Example |
|---------|-----------|---------|
| Controllers | PascalCase + "Controller" | `EventController` |
| Models | PascalCase (singular) | `Event`, `User` |
| Database tables | snake_case (plural) | `events`, `event_registrations` |
| Migration files | Laravel default | `2024_01_01_create_events_table` |
| Route names | snake_case, dot-separated | `events.index`, `auth.login` |
| API routes | kebab-case | `/api/event-registrations` |

**General Rules**

- All API logic lives in controllers. No business logic in routes.
- Use Laravel Form Request classes for validation — never validate inside the controller directly.
- Return consistent JSON responses using a standard wrapper:

```php
// Success
return response()->json(['status' => 'success', 'data' => $data], 200);

// Error
return response()->json(['status' => 'error', 'message' => 'Not found'], 404);
```

- All routes that require authentication must use the `auth:sanctum` middleware.
- Sensitive config (DB credentials, app key, JWT secret) must be in `.env`, never hardcoded.

### MySQL / Database

- All table names are **plural** and **snake_case**: `users`, `events`, `announcements`, `event_registrations`.
- Every table must have `id` (primary key, auto-increment), `created_at`, and `updated_at`.
- Use foreign keys with proper constraints (e.g., `event_registrations.user_id` → `users.id`).
- No storing passwords in plain text. Use Laravel's `bcrypt` hashing.

---

## 🔗 3. API Contract Rules

These rules ensure the Android and Backend teams stay in sync without blocking each other.

### The Golden Rule: API Contract First

> **Before writing any code for a feature, the Backend Engineer must first define the endpoint in the shared Postman collection.** Android devs (Role 2 & 3) work against this contract using mock data until the real endpoint is ready.

### API Response Format

All endpoints **must** return JSON in this exact wrapper format:

```json
// Success (single object)
{
  "status": "success",
  "data": { ... }
}

// Success (list)
{
  "status": "success",
  "data": [ ... ]
}

// Error
{
  "status": "error",
  "message": "Human-readable error description"
}
```

### Authentication

- Auth is handled with **Laravel Sanctum** (token-based).
- After login, the API returns a `token` string. Android stores this securely using `EncryptedSharedPreferences`.
- All protected routes require the header:
  ```
  Authorization: Bearer <token>
  ```
- Token field in JSON response must always be named `token`.

### Core Endpoint Reference (MVP)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|:---:|
| `POST` | `/api/auth/register` | Register new user | ❌ |
| `POST` | `/api/auth/login` | Login, returns token | ❌ |
| `POST` | `/api/auth/logout` | Revoke token | ✅ |
| `GET` | `/api/announcements` | List all announcements | ✅ |
| `POST` | `/api/announcements` | Create announcement (admin) | ✅ |
| `GET` | `/api/events` | List all events | ✅ |
| `GET` | `/api/events/{id}` | Get single event details | ✅ |
| `POST` | `/api/events` | Create event (admin) | ✅ |
| `POST` | `/api/events/{id}/register` | Register for event | ✅ |
| `GET` | `/api/events/{id}/registrants` | List registrants (admin) | ✅ |

### Naming Rules for JSON Fields

- All JSON keys must be `snake_case`: `event_id`, `created_at`, `is_admin`.
- Dates must follow ISO 8601 format: `"2024-08-01T09:00:00Z"`.
- Boolean fields must be actual booleans, not `0`/`1` integers: `"is_admin": true`.

### Versioning

- All routes are prefixed with `/api/`. No version prefix is needed for the MVP (we only have one version).
- If a breaking change is needed mid-sprint, discuss in Discord **before** changing the endpoint — not after.

---

## 📌 4. Task Tracking

---

## 🏁 2-Week Milestone Targets

| Day | Target |
|-----|--------|
| Day 1–2 | ERD finalized, project repos set up, Compose navigation scaffold done |
| Day 3–4 | Auth endpoints live in Postman, Login/Register screens built with mock data |
| Day 5–7 | Announcements & Events endpoints done, Repository + Retrofit layer connected |
| Day 8–10 | Event registration flow end-to-end, Admin views functional |
| Day 11–12 | Integration testing, bug fixing |
| Day 13–14 | Final polish, README updated, demo prep |

---

*Last updated: June 2026 · HMIF Ukrida Mobile Team*