package com.cristh.amazondemostefanini.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cristh.amazondemostefanini.R
import com.cristh.amazondemostefanini.model.Comment

class CommentsAdapter(private val commentList: List<Comment>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userIcon: ImageView = itemView.findViewById(R.id.userIcon)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val userComment: TextView = itemView.findViewById(R.id.userComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_comment_item, parent, false)

        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.apply {
            //userIcon.load("") { placeholder(R.drawable.user_icon) }
            userName.text = commentList[position].username
            userComment.text = commentList[position].comemnt_text
        }

    }


}