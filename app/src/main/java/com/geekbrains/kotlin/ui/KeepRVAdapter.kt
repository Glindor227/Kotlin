package com.geekbrains.kotlin.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.geekbrains.kotlin.R
import com.geekbrains.kotlin.common.getColorInt
import com.geekbrains.kotlin.data.entity.Note
import kotlinx.android.synthetic.main.my_note.view.*

class KeepRVAdapter (val onItemViewClick : ((note: Note) -> Unit)? = null): RecyclerView.Adapter<KeepRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.my_note, parent, false)
            )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(notes[pos])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.text

            (this as CardView).setCardBackgroundColor(note.bgrColor.getColorInt(context))
            itemView.setOnClickListener {
                onItemViewClick?.invoke(note)
            }

        }
    }
}