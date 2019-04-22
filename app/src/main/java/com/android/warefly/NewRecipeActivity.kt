package com.android.warefly

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

/**
 * Activity for creating a Recipe.
 */

class NewRecipeActivity : AppCompatActivity() {
    private lateinit var editRecipeView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_recipe)
        editRecipeView = findViewById(R.id.edit_recipe)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editRecipeView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val recipe = editRecipeView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, recipe)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

