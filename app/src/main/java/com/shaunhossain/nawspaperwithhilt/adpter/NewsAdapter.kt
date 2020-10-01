package com.shaunhossain.nawspaperwithhilt.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shaunhossain.nawspaperwithhilt.databinding.ItemArticlePreviewBinding
import com.shaunhossain.nawspaperwithhilt.model.ArticleX



class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner  class ArticleViewHolder(
        val itemArticlePreviewBinding: ItemArticlePreviewBinding
    ): RecyclerView.ViewHolder(itemArticlePreviewBinding.root)

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
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticlePreviewBinding.inflate(inflater)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemArticlePreviewBinding.articleItem = article

        /*holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .into(articleImage)
            newsSource.text = article.source.name
            newsTitle.text = article.title
            publishedAt.text = changeDateFormat(article.publishedAt)
            setOnClickListener{
                 onItemClickListener?.let {it(article)}
            }
        }*/
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}