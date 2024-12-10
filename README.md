
# NewsApp: Your Personalized News Companion

**NewsApp** is a modern Android application designed to deliver the latest news from around the world directly to your fingertips. Built entirely in Kotlin and adhering to Android best practices, Newsly offers a seamless user experience, intuitive navigation, and offline functionality. Whether you're catching up on current events or saving articles for later, Newsly has you covered.


## Features

🌐 Real-Time News Updates
- Fetch the latest news articles using the NewsAPI.
- Display articles in a scrollable, user-friendly list with headlines, descriptions, and images.

📰 Article Details
- Tap to Read More button to view the full content in an in-app WebView.
- Save articles locally with a single click for offline access.

💾 Offline Access
- Save your favorite articles to a local database (Room) for easy access without an internet connection.
- View and manage saved articles in the Saved section.
- Delete saved articles when no longer needed.

🎨 Modern UI/UX
- Designed with Jetpack Compose for a sleek and intuitive user interface.
- Enhanced user experience with thoughtful design and smooth animations.

## Architecture

**NewsApp** is built using the MVVM (Model-View-ViewModel) architecture to ensure a clean, maintainable, and scalable codebase.

### Key Components
- Repository: Handles data fetching from the API and local database.
- ViewModel: Manages UI-related data and state.
- Room Database: Provides local storage for offline access.
- Jetpack Compose: For building dynamic, modern UI components.

## Libraries Used
- Retrofit
- Room Database
- Coil: Image loading and caching.
- Jetpack Compose: Declarative UI framework.
- ViewModel, Coroutine, Flow
- Dagger Hilt
- Navigation Compose
- Material 3

## Help: How To Run ? / Installation Guide
- Required **AGP** version - 8.5.2
- Required - JDK 17
- Using latest Android Studio [Ladybug]
- Target API Level 35
 - Enjoy 😍

## Video
https://github.com/user-attachments/assets/9c7ad781-4422-443c-85f8-4a9c1ab1a624

## Screenshots
| Light Mode | Dark Mode |
|-------------|----------------|
![Light Mode](https://github.com/user-attachments/assets/123a155f-b9e5-447a-9304-d0a6fe2fff91) | ![Dark Mode](https://github.com/user-attachments/assets/6e5735ac-e713-4037-928a-fe6deedb38da)

| WebView Screen | After Saved Article |
|-------------|----------------|
![image](https://github.com/user-attachments/assets/4dc9c9e5-2517-4168-921e-94b2054cdc19) | ![image](https://github.com/user-attachments/assets/3b28998b-5b3d-481a-b76d-f25c6e39741f)

| Saved Screen |
|-------------|
![image](https://github.com/user-attachments/assets/062e05e6-7e1d-4bc3-9596-c69197085473)








