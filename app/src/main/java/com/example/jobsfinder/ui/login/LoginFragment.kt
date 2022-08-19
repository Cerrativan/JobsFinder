package com.example.jobsfinder.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.jobsfinder.MainActivity
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.LoginFragmentBinding
import com.example.jobsfinder.ui.model.User
import com.example.jobsfinder.ui.register.RegisterViewModel
import com.example.jobsfinder.ui.service.UserRepository


class LoginFragment : Fragment(R.layout.login_fragment) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var _loginBinding: LoginFragmentBinding? = null

    private val loginbinding get() = _loginBinding!!

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel = loginViewModel
        _loginBinding = LoginFragmentBinding.inflate(inflater, container, false)
        val view : View = loginbinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginbinding.loginLoginButton.setOnClickListener {
            val email = loginbinding.loginEmailEditText.text.toString()
            val password = loginbinding.loginPasswordEditText.text.toString()
            viewModel.login(email, password)
        }
        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer {
                saveUser(it)
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
        })

        viewModel.loginFailed.observe(viewLifecycleOwner, Observer {
            if(!it)
            Toast.makeText(context, "Email or password incorrect", Toast.LENGTH_SHORT).show()
        })

        loginbinding.loginSignUpTextView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.loginToSignUp_action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _loginBinding = null
    }

    fun saveUser(user: User) {
        val shared = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE) ?: return
        with (shared.edit()) {
            putString("id", user.id)
            putString("username", user.username)
            putString("email", user.email)
            putString("password", user.password)
            putString("type", user.type)
            apply()
        }
    }

}