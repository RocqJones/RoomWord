package com.intoverflown.roomword

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.intoverflown.roomword.databinding.ActivityNewWordBinding

/**
 * 11. Add NewWordActivity
 */
class NewWordActivity : AppCompatActivity() {

    private var binding: ActivityNewWordBinding? = null

    companion object {
        const val EXTRA_REPLY = "NEW.WORD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(binding!!.editWord.text.toString().trim())) {
                binding!!.editWord.error = getString(R.string.empty_not_saved)
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = binding!!.editWord.text.toString().trim()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}