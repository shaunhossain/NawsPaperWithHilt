package com.shaunhossain.nawspaperwithhilt.ui.main

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.shaunhossain.nawspaperwithhilt.R
import com.shaunhossain.nawspaperwithhilt.databinding.FragmentViewArticleBinding
import java.text.SimpleDateFormat
import java.util.*


class ViewArticleFragment : Fragment() {

    val args : MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentViewArticleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_view_article,container,false)
        binding.newsTiteView.text = args.article?.title.toString()
        loadImage(binding.contentImage,args.article?.urlToImage.toString())
        loadDate(binding.publishedAt,args.article?.publishedAt.toString())
        binding.newsSource.text = args.article?.source?.name.toString()
        binding.newsDescriptionView.text = args.article?.content.toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = args.article?.title.toString()
    }


    fun loadImage(view : ImageView, url : String?){
        try {
            Glide.with(view)
                .load(url)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_loading)
                .centerCrop()
                .into(view)
        } catch (t : Throwable){
            view.setImageResource(R.drawable.ic_loading)
        }
    }

    fun loadDate(view : TextView, date : String){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val currentDate = dateFormat.parse(date)
        val formatter = SimpleDateFormat("dd MMMM", Locale.ENGLISH)
        val dateNow = formatter.format(currentDate)
        view.text = dateNow
    }

}