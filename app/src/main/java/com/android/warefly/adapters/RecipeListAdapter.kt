package com.android.warefly.adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.warefly.R
import com.android.warefly.RecipeDetailsActivity
import com.android.warefly.db.models.Recipe


class RecipeListAdapter internal constructor(context: Context) : RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var recipes = emptyList<Recipe>() // Cached copy of recipes

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeItemView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RecipeDetailsActivity::class.java)

                val current = recipes[adapterPosition]
                val images: IntArray = intArrayOf(
                        R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4
                )

                intent.putExtra("recipe_id", current.id)
                intent.putExtra("recipe_title", current.title)
                intent.putExtra("recipe_thumbnail", current.thumbnailPath)
                intent.putExtra("recipe_images", images)

                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_recipelist_item, parent, false)

        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val current = recipes[position]
        holder.recipeItemView.text = current.title
    }

    internal fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    override fun getItemCount() = recipes.size
}


