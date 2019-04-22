package com.android.warefly.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warefly.R
import com.android.warefly.db.models.RecipeIngredient

class IngredientAdapter internal constructor(context: Context, private val recipeId: Long) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var recipeIngredients = emptyList<RecipeIngredient>() // Cached copy of ingredients

    inner class IngredientViewHolder(ingredientView: View) : RecyclerView.ViewHolder(ingredientView) {
        val ingredientTitleView: TextView = ingredientView.findViewById(R.id.ingredient_title)
        val ingredientAmountView: TextView = ingredientView.findViewById(R.id.ingredient_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val ingredientView = inflater.inflate(R.layout.recyclerview_recipestep_item, parent, false)

        return IngredientViewHolder(ingredientView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val current = recipeIngredients[position]

        holder.ingredientTitleView.text = current.title
        holder.ingredientAmountView.text = "${current.amount} ${current.unit}"
    }

    internal fun setIngredients(recipeIngredients: List<RecipeIngredient>) {
        this.recipeIngredients = recipeIngredients.filter { it.recipeId == recipeId }
        notifyDataSetChanged()
    }

    override fun getItemCount() = recipeIngredients.size
}