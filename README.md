# RoomWord
This focuses on a subset of the components, namely LiveData, ViewModel and Room.

### 1. LiveData
A data holder class that can be observed. Always `holds/caches` the latest version of data, and notifies its observers when data has changed. `LiveData` is *lifecycle aware*. UI components just observe relevant data and don't stop or resume observation. LiveData automatically manages all of this since it's aware of the relevant lifecycle status changes while observing.

### 2. ViewModel.
Acts as a communication center between the **Repository (data) and the UI**. The UI no longer needs to worry about the origin of the data. **ViewModel instances survive Activity/Fragment recreation.**

### 3. Repository. 
A class that you create that is primarily used to manage multiple data sources.

### 4. Entity.
Annotated class that describes a database table when working with Room.

### 5. Room database.
Simplifies database work and serves as an access point to the underlying SQLite database (hides `SQLiteOpenHelper`). The Room database uses the DAO to issue queries to the SQLite database.
* **SQLite database:** On device storage. The Room persistence library creates and maintains this database for you.
* **DAO:** Data access object. A mapping of SQL queries to functions. When you use a DAO, you call the methods, and Room takes care of the rest.

## Flow of Data for Automatic UI Updates (Reactive UI)
- The automatic update is possible because you are using LiveData. In the `MainActivity`, there is an `Observer` that observes the words LiveData from the database and is notified when they change. When there is a change, the observer's `onChange()` method is executed and updates `mWords` in the `WordListAdapter`.
- The `WordViewModel` hides everything about the backend from the UI layer. It provides methods for accessing the data layer, and it returns `LiveData so that `MainActivity` can set up the observer relationship. `Views` and `Activities` (and `Fragments`) only interact with the data through the `ViewModel`. As such, it doesn't matter where the data comes from.
- In this case, the data comes from a `Repository`. The `ViewModel` does not need to know what that Repository interacts with. It just needs to know how to interact with the `Repository`, which is through the methods exposed by the `Repository`.
