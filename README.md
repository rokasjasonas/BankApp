# BankApp

A Kotlin Multiplatform banking app with a shared Compose Multiplatform UI, targeting **Android** and **iOS**.

## Tech specs

### Languages & platforms
| | |
|---|---|
| Language | Kotlin `2.3.21` |
| Targets | Android, iOS (`iosArm64`, `iosSimulatorArm64`) |
| Shared UI | Compose Multiplatform `1.11.0` |
| Async | kotlinx-coroutines `1.10.2` |

### Build
| | |
|---|---|
| Build system | Gradle `9.1.0` (wrapper) |
| Android Gradle Plugin | `9.0.1` |
| JDK toolchain | `21` (Amazon Corretto, via `gradle-daemon-jvm.properties`) |
| Module JVM target | `21` |
| Dependencies | Centralised in the [version catalog](gradle/libs.versions.toml) |
| Config cache / build cache | Enabled |

### Android
| | |
|---|---|
| compileSdk | `36` |
| targetSdk | `36` |
| minSdk | `24` |
| Application id | `com.rokapps.bankapp` |

### Key libraries
| Library | Version |
|---|---|
| Compose Multiplatform (runtime, foundation, ui, resources) | `1.11.0` |
| Compose Material 3 | `1.11.0-alpha07` |
| AndroidX Lifecycle (viewmodel-compose, runtime-compose) | `2.11.0-beta01` |
| AndroidX Activity Compose | `1.13.0` |
| kotlinx-coroutines (core, test) | `1.10.2` |
| Koin (core, compose, compose-viewmodel) | `4.1.1` |
| kotlin-test | bundled with Kotlin |

## Architecture

**MVVM + Clean**, with a one-way dependency: **UI → ViewModel → UseCase → Repository**. Almost all
code lives in `shared/commonMain`; platform code is added via `expect`/`actual` only when a platform
API requires it. See [AGENTS.md](AGENTS.md) for the full conventions.

## Modules

| Module | Description |
|---|---|
| [`shared`](shared/src) | Shared Kotlin: features (`feature/login`, `feature/accounts`), built in `commonMain` |
| [`ui`](ui/src) | Design-system module — the app's only UI building blocks (`AppButton`, `AppText`, `AppTextField`, `AppImage`, `AppIcon`), thin wrappers over Compose primitives, each with a required `testId` |
| [`androidApp`](androidApp) | Thin Android entry point (`MainActivity`) |
| [`iosApp`](iosApp) | Thin iOS entry point (SwiftUI host + Xcode project) |

`shared` source sets: `commonMain` (shared code), `androidMain` / `iosMain` (platform `actual`s),
`commonTest` / `androidHostTest` / `iosTest` (tests). The iOS build produces a static `Shared` framework.

## Features

- **Login** — `feature/login`: validates credentials against a (fake) auth backend. Demo account: `demo` / `password`.
- **Accounts (home)** — `feature/accounts`: lists the user's bank accounts with formatted balances.

## Feature flags

`core/featureflags` provides a small, reactive feature-flag mechanism: flags are declared in the
`FeatureFlag` enum (key + default), fetched from a `RemoteFeatureFlagSource`, and exposed by
`FeatureFlagRepository` as observable values. The source is currently a **fake remote**
(`FakeRemoteFeatureFlagSource`) — swap it for a real backend later without touching callers. The
repository is refreshed once on startup.

## Dependency injection

Dependencies are wired with **Koin** (`4.1.1`) — the modules are defined in `di/AppGraph` and
started via `KoinApplication` at the app root. ViewModels use `koinViewModel()` from
`koin-compose-viewmodel` and are scoped per-screen. Use cases and repositories are registered as
`factory` / `single` respectively so that stateful dependencies (repositories, sources) are shared
and stateless ones (use cases) are created fresh on each injection.

## Running the apps

Use the run configurations in your IDE's run widget, or:

- **Android:** `./gradlew :androidApp:assembleDebug`
- **iOS:** open [`iosApp`](iosApp) in Xcode and run from there.

## Running tests

- **Android (JVM host):** `./gradlew :shared:testAndroidHostTest`
- **iOS simulator:** `./gradlew :shared:iosSimulatorArm64Test`

## Testing & accessibility

Every `ui` component requires a `testId`, declared per feature in a `<Feature>TestIds` object. In
**debug builds** these ids are published as accessibility content descriptions (via `UiDebug.exposeTestIds`)
so UI tests and accessibility tooling can locate nodes; release builds add no extra semantics.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
