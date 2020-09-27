package com.shaunhossain.nawspaperwithhilt.adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shaunhossain.nawspaperwithhilt.R
import com.shaunhossain.nawspaperwithhilt.model.ArticleX
import kotlinx.android.synthetic.main.item_article_preview.view.*


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner  class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<ArticleX>() {
        override fun areItemsTheSame(oldItem: ArticleX, newItem: ArticleX): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleX, newItem: ArticleX): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .into(articleImage)
            newsSource.text = article.source?.name
            newsTitle.text = article.title
            newsDescription.text = article.description.toString()
            publishedAt.text = article.publishedAt
            setOnClickListener{
                 onItemClickListener?.let {it(article)}
            }
        }
    }

    private var onItemClickListener: ((ArticleX) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleX) -> Unit){
        onItemClickListener = listener
    }
}