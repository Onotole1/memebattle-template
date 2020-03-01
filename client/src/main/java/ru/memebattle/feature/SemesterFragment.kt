package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_semester.*
import ru.memebattle.ARGS_SEMESTER
import ru.memebattle.R
import ru.memebattle.common.dto.user.SemesterDto
import ru.memebattle.common.dto.user.SemesterSubjectDto

class SemesterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_semester, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val semester = arguments?.get(ARGS_SEMESTER) as SemesterDto

        semesterName.text = semester.name

        val mockSubjects = listOf(
            SemesterSubjectDto(0, 70.0f, 0),
            SemesterSubjectDto(1, 90.0f, 0),
            SemesterSubjectDto(2, 70.0f, 0),
            SemesterSubjectDto(3, 90.0f, 0),
            SemesterSubjectDto(4, 70.0f, 0),
            SemesterSubjectDto(5, 80.0f, 0),
            SemesterSubjectDto(6, 80.0f, 0),
            SemesterSubjectDto(7, 70.0f, 0),
            SemesterSubjectDto(8, 90.0f, 0),
            SemesterSubjectDto(9, 80.0f, 0)
        )

        recyclerViewSubjects?.adapter = SubjectsAdapter(mockSubjects)
    }
}