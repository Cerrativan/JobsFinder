package com.example.jobsfinder.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.jobsfinder.LoginActivity
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.passwordTextView.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_profile_to_passwordChangeFragment)
        }

        binding.logoutButton.setOnClickListener {
            removeUserData()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.usernameTextView.text = getUsername()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun removeUserData() {
        activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)?.edit()?.remove("userData")
    }

    private fun getUsername(): String {
        val username = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!
        return username?.getString("username", null)!!
    }
}