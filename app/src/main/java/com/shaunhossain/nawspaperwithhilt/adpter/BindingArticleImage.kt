package com.shaunhossain.nawspaperwithhilt.adpter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.shaunhossain.nawspaperwithhilt.R
import kotlinx.android.synthetic.main.item_article_preview.view.*

@BindingAdapter("articleimage")
fun loadImage(view : ImageView,url : String?){
    try {
        Glide.with(view)
            .load(url)
            .override(100, 70)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_loading)
            .centerCrop()
            .into(view)
    } catch (t : Throwable){
        view.setImageResource(R.drawable.ic_loading)
    }
}