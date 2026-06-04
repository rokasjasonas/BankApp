# CLAUDE.md

Guidance for Claude Code when working in this repository.

## Project

BankApp is a Kotlin Multiplatform (KMP) app targeting **Android** and **iOS**, with a shared
UI built in **Compose Multiplatform**. Base package: `com.rokapps.bankapp`.

- `shared/` — shared Kotlin code (the bulk of the app lives here)
  - `commonMain/` — code common to all targets (default home for new code)
  - `androidMain/`, `iosMain/` — platform-specific `actual` implementations only
  - `commonTest/`, `androidHostTest/`, `iosTest/` — tests
- `ui/` — shared design-system module: the app's only UI building blocks
  (`AppButton`, `AppText`, `AppTextField`, `AppImage`, `AppIcon`, …), thin wrappers over Compose primitives
- `androidApp/` — thin Android entry point (`MainActivity`)
- `iosApp/` — thin iOS entry point (SwiftUI host + Xcode project)
- `gradle/libs.versions.toml` — version catalog (single source of truth for dependencies)

## Architecture — MVVM + Clean

Layer new features in `shared/commonMain`, keeping a one-way dependency: **UI → ViewModel → UseCase → Repository**.

- **UI** (Compose) observes immutable state exposed by a ViewModel; no business logic in composables.
- **ViewModel** holds UI state and calls use cases. Use `androidx.lifecycle` viewmodel (already in the catalog).
- **UseCase** — one focused unit of business logic per use case.
- **Repository** — data access behind an interface; implementations can use `expect`/`actual` for platform APIs.

Keep layers in their own packages (e.g. `feature/<name>/{ui,domain,data}`). Depend on interfaces, not implementations.

## Hard rules

1. **Shared-first.** Put code in `commonMain` by default. Only drop to `androidMain`/`iosMain` via
   `expect`/`actual` when a platform API genuinely requires it.
2. **Compose UI in shared.** Build screens with Compose Multiplatform in `shared/commonMain`. Keep
   `androidApp` and `iosApp` as thin entry points only.
3. **Version catalog only.** Add/upgrade dependencies in `gradle/libs.versions.toml` and reference them
   as `libs.*`. Never hardcode a version or coordinate string in a `build.gradle.kts`.
4. **Tests required.** New shared logic (use cases, repositories, utilities) must have `commonTest`
   coverage. Run tests before considering a change done.
5. **UI components from `ui` only.** Build all screens with the wrappers from the `ui` module
   (`AppButton`, `AppText`, `AppTextField`, `AppImage`, `AppIcon`, …). Do **not** call Compose
   Material3/Foundation widgets (`Button`, `Text`, `TextField`, `Image`, …) directly in feature code.
   If a new primitive is needed, add a wrapper to the `ui` module first, then use it.

## Commands

```bash
# Build Android debug APK
./gradlew :androidApp:assembleDebug

# Run tests
./gradlew :shared:testAndroidHostTest        # Android (JVM host) tests
./gradlew :shared:iosSimulatorArm64Test      # iOS simulator tests
```

iOS app: open `iosApp/` in Xcode and run from there.

## Git workflow

- **Never commit to `main` directly.** Create a branch for every change.
- Open a PR with `gh pr create` for review; don't push straight to `main`.
- Commit only when the work is complete and tests pass.
