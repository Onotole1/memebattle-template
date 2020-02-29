package ru.memebattle.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_semester.view.*
import ru.memebattle.R
import ru.memebattle.common.dto.user.SemesterDto

class SemestersAdapter(
    private val semesters: List<SemesterDto>,
    private val onItemClickListener: (SemesterDto) -> Unit
) : RecyclerView.Adapter<SemestersAdapter.SemesterViewHolder>() {

    class SemesterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemesterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_semester, parent, false)

        return SemesterViewHolder(itemView)
    }

    override fun getItemCount(): Int = semesters.size

    override fun onBindViewHolder(holder: SemesterViewHolder, position: Int) {
        val semester = semesters[position]

        holder.itemView.apply {
            semesterName.text = semester.name

            setOnClickListener {
                onItemClickListener(semester)
            }
        }
    }

}