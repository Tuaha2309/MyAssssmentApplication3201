# Fitness Tracker App

A simple Android fitness tracking application built for the NIT3213 assignment. The app displays a list of fitness exercises with detailed information about each exercise.

## Features

- **Login Screen**: Simple authentication using username and password
- **Dashboard Screen**: Displays a list of fitness exercises in a RecyclerView
- **Details Screen**: Shows detailed information about a selected exercise
- **Material 3 Design**: Modern, light-themed UI with rounded corners and subtle elevation
- **Dependency Injection**: Uses Hilt for clean architecture
- **MVVM Architecture**: Follows Model-View-ViewModel pattern with Repository pattern
- **Unit Tests**: Comprehensive test coverage including MockWebServer integration tests

## Architecture

The app follows a clean architecture pattern with the following layers:

- **UI Layer**: Fragments, ViewModels, and Adapters
- **Domain Layer**: Models and Repository contracts
- **Data Layer**: Repository implementations, API services, and DTOs
- **DI Layer**: Hilt modules for dependency injection

## API Endpoints

- **Base URL**: `https://nit3213api.onrender.com/`
- **Authentication**: `POST /sydney/auth`
- **Dashboard**: `GET /dashboard/{keypass}`

## Credentials Format

For login, use:
- **Username**: Your first name
- **Password**: Your student ID (numeric part only, without the 's')

Example: If your student ID is `s12345678`, use `12345678` as the password.

## Screens

### 1. Login Screen
- Two input fields for username and password
- Sign In button with loading state
- Error handling with user-friendly messages
- Credentials hint for users

### 2. Dashboard Screen
- RecyclerView displaying fitness exercises
- Each item shows:
  - Exercise name
  - Muscle group and difficulty (as chips)
  - Equipment required
  - Calories burned per hour
- Pull-to-refresh functionality
- Error state with retry option

### 3. Details Screen
- Complete exercise information
- Large title with chips for key details
- Equipment section
- Detailed description
- Scrollable layout for long content

## Technical Stack

- **Language**: Kotlin
- **UI**: Material 3, ViewBinding
- **Architecture**: MVVM + Repository Pattern
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp + Moshi
- **Navigation**: Navigation Component
- **Async**: Kotlin Coroutines + Flow
- **Testing**: JUnit, Mockito, Truth, MockWebServer

## Project Structure

```
app/src/main/java/com/example/myassssmentapplication/
├── data/
│   ├── remote/
│   │   ├── api/          # API service interfaces
│   │   └── dto/          # Data transfer objects
│   └── repo/             # Repository implementations
├── domain/
│   ├── model/            # Domain models
│   └── repo/             # Repository contracts
├── ui/
│   ├── login/            # Login screen
│   ├── dashboard/        # Dashboard screen
│   ├── details/          # Details screen
│   └── common/           # Shared UI components
└── di/                   # Dependency injection modules
```

## Setup and Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MyAssssmentApplication
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder and select it

3. **Sync Project**
   - Android Studio will automatically sync the project
   - Wait for Gradle sync to complete

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## Testing

### Running Unit Tests

```bash
./gradlew test
```

### Running Instrumented Tests

```bash
./gradlew connectedAndroidTest
```

### Test Coverage

The project includes:
- **ViewModel Tests**: Testing business logic and state management
- **Repository Tests**: Testing data layer with mocked dependencies
- **API Integration Tests**: End-to-end testing with MockWebServer

## Dependencies

### Core Dependencies
- `androidx.core:core-ktx`
- `androidx.appcompat:appcompat`
- `com.google.android.material:material`
- `androidx.constraintlayout:constraintlayout`

### Architecture Components
- `androidx.lifecycle:lifecycle-viewmodel-ktx`
- `androidx.lifecycle:lifecycle-livedata-ktx`
- `androidx.navigation:navigation-fragment-ktx`
- `androidx.navigation:navigation-ui-ktx`

### Networking
- `com.squareup.retrofit2:retrofit`
- `com.squareup.retrofit2:converter-moshi`
- `com.squareup.okhttp3:okhttp`
- `com.squareup.okhttp3:logging-interceptor`
- `com.squareup.moshi:moshi`
- `com.squareup.moshi:moshi-kotlin`

### Dependency Injection
- `com.google.dagger:hilt-android`
- `com.google.dagger:hilt-compiler`

### Async Programming
- `org.jetbrains.kotlinx:kotlinx-coroutines-android`

### Testing
- `junit:junit`
- `org.mockito:mockito-core`
- `org.mockito.kotlin:mockito-kotlin`
- `com.google.truth:truth`
- `com.squareup.okhttp3:mockwebserver`

## Git History

The project follows meaningful commit conventions:

- `chore: add deps & setup hilt` - Dependencies and Hilt setup
- `feat: login screen & api` - Login functionality implementation
- `feat: dashboard list with recycler view` - Dashboard screen with RecyclerView
- `feat: details screen + nav` - Details screen and navigation
- `test: add vm tests + mockwebserver` - Unit tests and integration tests
- `docs: add readme` - Documentation

## Error Handling

The app includes comprehensive error handling:

- **Network Errors**: Displayed with retry options
- **Authentication Errors**: Clear error messages for invalid credentials
- **Loading States**: Progress indicators during API calls
- **Empty States**: Appropriate handling for empty data

## Theme and Design

The app uses a light Material 3 theme with:
- **Primary Color**: Green (#4CAF50)
- **Secondary Color**: Blue (#2196F3)
- **Background**: Light gray (#FAFAFA)
- **Cards**: White with subtle elevation
- **Typography**: Material 3 text styles
- **Shapes**: Rounded corners (16-24dp)

## Future Enhancements

Potential improvements for the app:
- Offline support with Room database
- Exercise favorites functionality
- Search and filtering capabilities
- Exercise timer and tracking
- User profiles and progress tracking
- Dark theme support

## License

This project is created for educational purposes as part of the NIT3213 assignment.
