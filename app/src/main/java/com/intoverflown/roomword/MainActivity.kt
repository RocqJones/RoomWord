package com.intoverflown.roomword

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.intoverflown.roomword.adapters.WordListAdapter
import com.intoverflown.roomword.databinding.ActivityMainBinding
import com.intoverflown.roomword.entity.Word
import com.intoverflown.roomword.vm.WordViewModel
import com.intoverflown.roomword.vm.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null


    /**
     * 12.0 Connect with the data: create the ViewModel.
     * - To create the ViewModel you used the viewModels delegate, passing in an instance of our WordViewModelFactory.
     * - This is constructed based on the repository retrieved from the WordsApplication.
     */
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        /**
         * Step 8. Add a RecyclerView
         * - Have WordListAdapter class that extends ListAdapter.
         * - The ViewHolder inside WordListAdapter will display each word in our list.
         */
        val adapter = WordListAdapter()
        binding!!.recyclerview.adapter = adapter
        binding!!.recyclerview.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is in the foreground.
        wordViewModel.allWords.observe(this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }

        // Call add New word Activity
        binding!!.fab.setOnClickListener {
            addNewWordForResult.launch(Intent(this@MainActivity, NewWordActivity::class.java))
        }
    }

    /**
     * 12.1 Connect with the data: Add the onActivityResult() code for the NewWordActivity.
     * - If the activity returns with RESULT_OK, insert the returned word into the database by
     * calling the insert() method of the WordViewModel
     */
    private val addNewWordForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(0, it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}