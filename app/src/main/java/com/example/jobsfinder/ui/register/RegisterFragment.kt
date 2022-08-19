package com.example.jobsfinder.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.RegisterFragmentBinding
import com.example.jobsfinder.ui.model.User
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterFragment() : Fragment(R.layout.register_fragment) {

    /*companion object {
        fun newInstance() = RegisterFragment()
    }*/

    private var _registerBinding: RegisterFragmentBinding? = null
    private val registerBinding get() = _registerBinding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel = registerViewModel

        _registerBinding = RegisterFragmentBinding.inflate(inflater, container, false)
        val view: View = registerBinding.root


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        registerBinding.signUpButton.setOnClickListener {
            val checkedButton = getType()
            val username = registerBinding.usernameEditText
            val email = registerBinding.emailEditText
            val password = registerBinding.passwordEditText
            val confirmPassword = registerBinding.confirmEditText
            if(validation(username, email, password, confirmPassword, checkedButton))
                viewModel.signUp(username.text.toString(), email.text.toString(), password.text.toString(), checkedButton)
        }

        viewModel.signUpResult.observe(viewLifecycleOwner, Observer {
            if(it == true)
                Toast.makeText(context, "Sign Up Succesful", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Email already taken", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _registerBinding = null
    }

    private fun validation(username: EditText, email: EditText, password: EditText, confirmPassword: EditText, checkedButton: String): Boolean {
        val regex: String = "^(.+)@(.+)$"

        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(email.text.toString())

        if(username.text.toString().isEmpty()) {
            username.setError("Enter a valid username")
            return false
        }
        if(!matcher.matches()) {
            email.setError("Enter a valid email")
            return false
        }
        if(password.text.toString().length < 8 || password.text.toString().length > 16 || password.text.toString().isEmpty()) {
            password.setError("Password not valid")
            return false
        }else if(!(confirmPassword.text.toString().equals(password.text.toString()))) {
            confirmPassword.setError("Passwords must match")
            return false
        }
        if(checkedButton.equals("error")) {
            Toast.makeText(context, "Select private or company user", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun getType(): String{
        val checkedButton = registerBinding.radioGroup4.checkedRadioButtonId
        val privateUser = registerBinding.privateUserRadioButton.id
        val companyUser = registerBinding.companyUserRadioButton.id
        if(checkedButton == privateUser)
            return "user"
        if(checkedButton == companyUser)
            return "company"
        return "error"
    }

}