package com.intoverflown.roomword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.intoverflown.roomword.adapters.WordListAdapter
import com.intoverflown.roomword.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

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
    }
}