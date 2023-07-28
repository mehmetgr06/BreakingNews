# BreakingNews
The application lists several international news sources and current news that fetched from NewsApi.
The app developed with MVMM Architecture and Clean Architecture approach.
Used Techs
- Jetpack Compose for UI ToolKit.
- ComposeNavigation to navigate between screens.
- Kotlin Coroutines for background processes.
- Retrofit for network requests.
- Dagger Hilt for Dependency Injection.
- Coil for image loading from URL.
- Room for saving articles locally.
- JUnit4 and Espresso for Unit and UI testings.

Optional Parts
- In every 1 minute a network request made for "getNews" to fetch if there is new data via using Coroutines.
- SwipeToRefresh mechanism and manuel error popup implemented in every 1 of 3 request when user pull to refresh.
- Unit and UI Tests implemented.
