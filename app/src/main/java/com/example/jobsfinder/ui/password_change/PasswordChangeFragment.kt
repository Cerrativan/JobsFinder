package com.example.jobsfinder.ui.password_change

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.MyAdsFragmentBinding
import com.example.jobsfinder.databinding.PasswordChangeFragmentBinding
import com.example.jobsfinder.ui.my_ads.MyAdsViewModel
import io.ktor.http.*

class PasswordChangeFragment : Fragment(R.layout.password_change_fragment) {

    companion object {
        fun newInstance() = PasswordChangeFragment()
    }

    private var _passwordChangeBinding: PasswordChangeFragmentBinding? = null

    private val passwordChangeBinding get() = _passwordChangeBinding!!

    private lateinit var viewModel: PasswordChangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val passwordChangeViewModel= ViewModelProvider(this).get(PasswordChangeViewModel::class.java)
        viewModel = passwordChangeViewModel
        _passwordChangeBinding = PasswordChangeFragmentBinding.inflate(inflater, container, false)

        val view: View = passwordChangeBinding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordChangeBinding.changePasswordButton.setOnClickListener {
            if(viewModel.validation(passwordChangeBinding.changePasswordPasswordEditText, passwordChangeBinding.changePasswordConfirmEditText))
                viewModel.changePassword(getUserEmail(),
                                        passwordChangeBinding.changePasswordPasswordEditText.text.toString(),
                                        passwordChangeBinding.changePasswordConfirmEditText.text.toString())
            else
                Toast.makeText(context, "Error changing password", Toast.LENGTH_SHORT).show()
        }

        viewModel.changePswSuccess.observe(viewLifecycleOwner, Observer {
            if(it == HttpStatusCode.OK)
                Toast.makeText(context, "Password successfully changed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Password change failed", Toast.LENGTH_SHORT).show()
        })

        viewModel.changePswFailed.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Password change failed", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _passwordChangeBinding = null
    }

    private fun getUserEmail(): String {
        val shared = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!
        return shared.getString("email", null)!!
    }

}