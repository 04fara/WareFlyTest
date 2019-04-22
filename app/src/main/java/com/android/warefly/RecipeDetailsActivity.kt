package com.android.warefly

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.warefly.adapters.IngredientAdapter
import com.android.warefly.adapters.RecipeStepAdapter
import com.android.warefly.viewmodels.RecipeDetailsViewModel
import com.bumptech.glide.Glide

class RecipeDetailsActivity : AppCompatActivity() {
    private lateinit var recipeDetailsViewModel: RecipeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent

        setContentView(R.layout.activity_recipe_details)

        val thumbnail = findViewById<ImageView>(R.id.thumbnail)
        Glide
                .with(applicationContext)
                .load(intent.getIntExtra("recipe_thumbnail", R.mipmap.thumbnail))
                .into(thumbnail)

        val title = findViewById<TextView>(R.id.title)
        title.text = intent.getStringExtra("recipe_title")
        supportActionBar!!.title = title.text

        val addToList = findViewById<Button>(R.id.add_to_list_button)
        addToList.setOnClickListener {
            val text = "Added to your list!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
        }


        val recyclerViewIngredients = findViewById<RecyclerView>(R.id.recyclerview_ingredients)
        val ingredientsAdapter = IngredientAdapter(this, intent.getLongExtra("recipe_id", -1))
        recyclerViewIngredients.adapter = ingredientsAdapter
        recyclerViewIngredients.layoutManager = LinearLayoutManager(this)

        val recyclerViewImages = findViewById<RecyclerView>(R.id.recyclerview_images)
        val stepsAdapter = RecipeStepAdapter(intent.getIntArrayExtra("recipe_images"))
        recyclerViewImages.adapter = stepsAdapter
        recyclerViewImages.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)

        recipeDetailsViewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel::class.java)
        recipeDetailsViewModel.allRecipeIngredients.observe(this, Observer { ingredients ->
            // Update the cached copy of the RecipeIngredients in the adapter.
            ingredients?.let { ingredientsAdapter.setIngredients(it) }
        })
    }
}