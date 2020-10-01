package com.shaunhossain.nawspaperwithhilt.adpter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("articledate")
fun loadDate(view : TextView, date : String){
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val currentDate = dateFormat.parse(date)
    val formatter = SimpleDateFormat("dd MMMM", Locale.ENGLISH)
    val dateNow = formatter.format(currentDate)
    view.text = dateNow
}