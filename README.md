## This branch includes retrofit calling an endpoint and displaying the data using MutableStateFlow

https://github.com/user-attachments/assets/bcefe9ca-0f7c-41e6-8785-d34556248d2b


### The main branch has a base and includes the following dependencies:
 - Dagger Hilt
 - Retrofit
 - Room
 - Navigation Component
 - Compose viewmodel runtime and Flow

## Steps in this lesson:
1. Create a `network` package for your Network Module
2. Create a generic sealed class for your `DataResult` that will handle the success and error states
3. Create a `PostService` interface for your API calls
4. Create your model class, in this case, `Post`
5. Create a `PostRepository` class to handle your API calls that will return a `Flow<DataResult<List<Post>>>`
6. Create a `PostViewModel` class that will handle your UI logic 
   - use `repository.getPosts.onEach` to collect the flow and update the UI
   - do not forget to `launchIn` the viewModelScope
   - create a MainScreenState to handle the UI states
7. Create a `PostScreen` composable that will display the data
   - create your `viewModel` inside your composable and call `hiltViewModel()` to get the viewModel
   - use `viewModel.mainScreenState.collectAsStateWithLifecycle()` to collect the state
   - use `when` to handle the UI states
