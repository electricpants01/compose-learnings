## This branch includes retrofit calling an endpoint and displaying the data using MutableStateFlow


### The main branch has a base and includes the following dependencies:
 - Dagger Hilt
 - Retrofit
 - Room
 - Navigation Component
 - Compose viewmodel runtime and Flow

## Steps in this lesson:
1. Create a `network` package for your Network Module
   - create a `PostService` interface
   - create a `PostResponse` data class that represents the response from the API
2. Create a `database` package for your Database Module
   - create a `PostDao`
   - create a `PostEntity`
   - create a `ProductionAppDatabase` abstract class that extends `RoomDatabase`
3. Create a `repository` package for your Repository Module
   - create a `PostDataSource` interface that will be implemented by `PostDataSourceImpl`
4. 