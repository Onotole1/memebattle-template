package ru.memebattle.feature

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.android.ext.android.get
import ru.memebattle.PREFS_TOKEN
import ru.memebattle.R
import ru.memebattle.common.dto.AuthenticationRequestDto
import ru.memebattle.common.dto.AuthenticationResponseDto
import ru.memebattle.core.api.AuthApi
import ru.memebattle.core.utils.putString
import ru.memebattle.core.utils.toast

class AuthFragment : Fragment() {

    private val prefs: SharedPreferences = get()
    private val authApi: AuthApi = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            val authenticationRequestDto = getAuthDto() ?: return@setOnClickListener

            prepareAuth()

            authApi.signIn(authenticationRequestDto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onAuthSuccess(it)
                }, {
                    onAuthError(it)
                })
        }

        signUpButton.setOnClickListener {
            val authenticationRequestDto = getAuthDto() ?: return@setOnClickListener

            prepareAuth()

            authApi.signUp(authenticationRequestDto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onAuthSuccess(it)
                }, {
                    onAuthError(it)
                })
        }
    }

    private fun getAuthDto(): AuthenticationRequestDto? {
        val email = emailInput.text?.toString()
        val password = passwordInput.text?.toString()

        if (email == null || email == "") {
            toast("Заполните поле почты")
            return null
        }
        if (password == null || password == "") {
            toast("Заполните поле пароля")
            return null
        }

        return AuthenticationRequestDto(email, password)
    }

    private fun prepareAuth() {
        progressBar.visibility = View.VISIBLE
        signUpButton.isEnabled = false
        signInButton.isEnabled = false
    }

    private fun onAuthSuccess(authenticationResponseDto: AuthenticationResponseDto) {
        prefs.putString(PREFS_TOKEN, authenticationResponseDto.token)
        progressBar.visibility = View.GONE
        signUpButton.isEnabled = true
        signInButton.isEnabled = true
        findNavController().navigate(R.id.action_authFragment_to_mainFragment)
    }

    private fun onAuthError(throwable: Throwable) {
        progressBar.visibility = View.GONE
        signUpButton.isEnabled = true
        signInButton.isEnabled = true
        toast(throwable.localizedMessage!!)
    }
}