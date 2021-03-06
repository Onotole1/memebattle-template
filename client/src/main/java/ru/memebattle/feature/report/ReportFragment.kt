package ru.memebattle.feature.report


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_report.*
import org.koin.android.ext.android.get
import ru.memebattle.R
import ru.memebattle.common.dto.report.ReportDto
import ru.memebattle.common.dto.report.ReportTypeDto
import ru.memebattle.core.api.ReportApi
import ru.memebattle.core.utils.snack


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment() {

    private val reportApi: ReportApi = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode === RESULT_OK) {
                    val chosenImageUri: Uri? = data!!.data
                    val attachedNameText = TextView(context)
                    attachedNameText.text = chosenImageUri.toString()
                    attachedFilesNames.addView(attachedNameText)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachButton.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }
        val spinnerAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                ReportTypeDto.values()
            )
        }
        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportSpinner.adapter = spinnerAdapter
        var reportType = ReportTypeDto.values()[0]
        reportSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                reportType = ReportTypeDto.values()[0]
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                reportType = ReportTypeDto.values()[position]
            }

        }

        sendFeedbackButton.setOnClickListener {

            titleTextInputLayout.isErrorEnabled = false
            messageTextInputLayout.isErrorEnabled = false

            if (feedbackTitleEditText.text.toString().isEmpty()) {
                titleTextInputLayout.error = "Заполните поле"
                return@setOnClickListener
            }
            if (feedbackMessageEditText.text.toString().isEmpty()) {
                messageTextInputLayout.error = "Заполните поле"
                return@setOnClickListener
            }

            reportApi.report(
                ReportDto(
                    reportType,
                    feedbackTitleEditText.text.toString(),
                    feedbackMessageEditText.text.toString(),
                    isAnonCheckBox.isChecked
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    snack("Успешно отправлено!")
                    reportSpinner.setSelection(0)
                    isAnonCheckBox.isChecked = false
                    feedbackTitleEditText.setText("")
                    feedbackMessageEditText.setText("")
                    attachedFilesNames.removeAllViews()
                }, {
                    snack("Успешно отправлено!")
                    reportSpinner.setSelection(0)
                    isAnonCheckBox.isChecked = false
                    feedbackTitleEditText.setText("")
                    feedbackMessageEditText.setText("")
                    attachedFilesNames.removeAllViews()
                })
        }
    }
}
