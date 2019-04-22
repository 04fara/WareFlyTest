package com.android.warefly

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import com.android.warefly.adapters.RecipeListAdapter
import com.android.warefly.viewmodels.RecipeViewModel
import com.android.warefly.db.models.Recipe

class MainActivity : AppCompatActivity() {
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = RecipeListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        // Add an observer on the LiveData returned by getAllRecipes.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        recipeViewModel.allRecipes.observe(this, Observer { recipes ->
            // Update the cached copy of the Recipes in the adapter.
            recipes?.let { adapter.setRecipes(it) }
        })
    }
}
