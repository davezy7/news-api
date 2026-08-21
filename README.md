# News Api

An Android application built with Jetpack Compose that displays news articles using the [NewsAPI.org](https://newsapi.org/) service.

## Tech Stack

- **UI:** Jetpack Compose (Material 3)
- **Navigation:** Compose Navigation
- **Networking:** Retrofit, OkHttp, Kotlinx Serialization
- **Dependency Injection:** Hilt
- **Image Loading:** Coil
- **Pagination:** Paging 3
- **Asynchronous Programming:** Kotlin Coroutines & Flow

## Setup Instructions

This project uses an `env.properties` file to manage sensitive information like API keys, which is not included in version control.

To run the project locally, follow these steps:

1.  **Create an `env.properties` file** in the root directory of the project.
2.  **Add your configuration** to the file. You will need an API key from [NewsAPI.org](https://newsapi.org/register).

The file should contain the following properties:

```properties
BASE_URL=https://newsapi.org/
API_KEY=your_api_key_here
```

3.  **Sync the project with Gradle files** in Android Studio.
4.  **Run the app.**

## License

This project is for demonstration purposes.
