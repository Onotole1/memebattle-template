package ru.memebattle.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.get
import ru.memebattle.ARGS_SEMESTER
import ru.memebattle.R
import ru.memebattle.common.dto.user.SemesterDto
import ru.memebattle.core.BaseFragment
import ru.memebattle.core.api.ProfileApi
import ru.memebattle.core.utils.toast

class ProfileFragment : BaseFragment() {

    private val profileApi: ProfileApi = get()

    private val onItemClickListener: (SemesterDto) -> Unit = { semester ->
        Navigation.findNavController(requireActivity(), R.id.host_global).navigate(
            R.id.action_mainFragment_to_semesterFragment,
            bundleOf(ARGS_SEMESTER to semester)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.VISIBLE

        profileApi.getProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                username.text = it.username

                rate.text = 10.toString()
                average.text = 80.203.toString()

                val mockSemesters = listOf(
                    SemesterDto(0, "1 семестр", listOf()),
                    SemesterDto(1, "2 семестр", listOf()),
                    SemesterDto(2, "3 семестр", listOf()),
                    SemesterDto(3, "4 семестр", listOf()),
                    SemesterDto(4, "5 семестр", listOf()),
                    SemesterDto(5, "6 семестр", listOf())
                )

                recyclerViewSemesters?.adapter =
                    SemestersAdapter(mockSemesters, onItemClickListener)

                progressBar.visibility = View.GONE
            }, {
                toast(it.localizedMessage)

                progressBar.visibility = View.GONE
            })
            .addTo(compositeDisposable)
    }
}