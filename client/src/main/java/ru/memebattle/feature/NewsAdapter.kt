package ru.memebattle.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import ru.memebattle.R
import ru.memebattle.common.dto.PostResponseDto

class NewsAdapter(
    private val posts: List<PostResponseDto>,
    private val onItemClickListener: (PostResponseDto) -> Unit
) : RecyclerView.Adapter<NewsAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)

        return PostViewHolder(itemView)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position] ?: return

        holder.itemView.apply {
            content.text = post.content
            setOnClickListener {
                onItemClickListener(post)
            }
        }
    }

}