package com.android.warefly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.warefly.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions


class RecipeStepAdapter(private val images: IntArray) : RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder>() {
    inner class RecipeStepViewHolder(stepView: View) : RecyclerView.ViewHolder(stepView) {
        val recipeStepView: ImageView = stepView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeStepViewHolder {
        val stepView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_recipestep_image, parent, false)

        return RecipeStepViewHolder(stepView)
    }

    override fun onBindViewHolder(holder: RecipeStepViewHolder, position: Int) {
        val current = images[position]
        val context = holder.recipeStepView.context

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(RoundedCorners(20))
        Glide
                .with(context)
                .load(current)
                .apply(requestOptions)
                .into(holder.recipeStepView)
    }

    override fun getItemCount() = images.size
}