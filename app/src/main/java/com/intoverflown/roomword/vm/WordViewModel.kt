package com.intoverflown.roomword.vm

import androidx.lifecycle.*
import com.intoverflown.roomword.entity.Word
import com.intoverflown.roomword.repository.WordRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * Step 6. ViewModel
 * - provide data to the UI and survive configuration changes.
 * - A ViewModel acts as a communication center between the Repository and the UI.
 * LiveData and ViewModel
 * - LiveData is an observable data holder - you can get notified every time the data changes.
 * @viewModelScope
 * - All coroutines run inside a CoroutineScope. A scope controls the lifetime of coroutines through its job.
 * - When you cancel the job of a scope, it cancels all coroutines started in that scope.
 */
class WordViewModel(private val repository: WordRepository) : ViewModel() {
    /**
     * Using LiveData and caching what allWords returns has several benefits:
     * 1. We can put an observer on the data and only update the UI when the data actually changes.
     * 2. Repository is completely separated from the UI through the ViewModel.
     */
    val allWords : LiveData<List<Word>> = repository.allWords.asLiveData()

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

///**
// * Step 7. WordViewModelFactory
// * - VM are not independent hence they require VM Factory to survive configuration changes and even
//    if the Activity is recreated.
// * - This "ViewModelProvider.Factory" framework will take care of the lifecycle of the ViewModel.
// */
//class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return WordViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}