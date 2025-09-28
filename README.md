# MoviesDbApp

Android app that lists high-rated popular movies from TMDB with:
- Two tabs: **Films** (all) and **Favorites**
- **Add/remove favorites** with instant sync between tabs
- **Pull-to-refresh**
- **Pagination**
- **Offline first-page cache** (shows cached list when you open the app offline)
- **Share** a movie (title + TMDB link)
- **Edge-to-edge** layout (portrait & landscape)
- **Optional** legacy Google Sign-In (GSI) for showing a simple account chip

---

## Tech stack

- **UI:** Jetpack Compose (Material 3), Accompanist SwipeRefresh, Coil
- **State:** Orbit MVI (`container`, `intent`, immutable state)
- **DI:** Hilt
- **Data:** Retrofit + OkHttp (logging), Room (cache), DataStore (profile)
- **Lang:** Kotlin/Coroutines/Flow
- **Auth:** Legacy Google Sign-In (Play Services) – **deprecated** (see below)

---

I deliberately keep legacy GSI to avoid web client setup.
	1.	Add an Android OAuth client in Google Cloud (Credentials → OAuth client ID → Android)
	•	Package: com.task.moviesdbapp
	•	SHA-1: from your keystore (debug or release)
	2.	We only call .requestEmail() (no ID token). If you add .requestIdToken(...), you must create a Web client ID and use it there.

⚠️ Deprecated: Google recommends Credential Manager + Sign in with Google.
Migration guide: https://developer.android.com/identity/sign-in/legacy-gsi-migration
