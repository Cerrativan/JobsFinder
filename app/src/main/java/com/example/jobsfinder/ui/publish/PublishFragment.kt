package com.example.jobsfinder.ui.publish

import android.content.Context
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
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.LoginFragmentBinding
import com.example.jobsfinder.databinding.PublishFragmentBinding
import com.example.jobsfinder.ui.register.RegisterViewModel
import io.ktor.http.*

class PublishFragment : Fragment(R.layout.publish_fragment) {

    companion object {
        fun newInstance() = PublishFragment()
    }

    private var _publishBinding: PublishFragmentBinding? = null

    private val publishBinding get() = _publishBinding!!

    private lateinit var viewModel: PublishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val publishViewModel = ViewModelProvider(this).get(PublishViewModel::class.java)
        viewModel = publishViewModel


        _publishBinding = PublishFragmentBinding.inflate(inflater, container, false)

        val view : View = publishBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publishBinding.publishMyAdsEditText.setOnClickListener { view ->
            if(getUserType().equals("user"))
                Toast.makeText(context, "Only company users can check published ads", Toast.LENGTH_SHORT).show()
            else
                view.findNavController().navigate(R.id.publishToMyAds_action)
        }

        publishBinding.publishButton.setOnClickListener {
            val title = publishBinding.titleEditText
            val city = publishBinding.cityEditText
            val salary = publishBinding.publishSalaryEditText
            val description = publishBinding.descriptionEditText
            if(getUserType().equals("user"))
                Toast.makeText(context, "Only company can publish", Toast.LENGTH_SHORT).show()
            else if(getUserType().equals("company"))
                if(viewModel.validation(title, city, salary, description))
                    viewModel.publishAd(title.text.toString(), city.text.toString(), salary.text.toString(), description.text.toString(), getUser())
        }

        viewModel.publishResult.observe(viewLifecycleOwner, Observer {
            if(it.equals(HttpStatusCode.OK))
                Toast.makeText(context, "Published", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Publishing failed", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _publishBinding = null
    }

    private fun getUser(): String {
        val shared = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!
        val owner: String = shared.getString("id", null)!!
        return owner
    }

    private fun getUserType(): String {
        val shared = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!
        val type: String = shared.getString("type", null)!!
        return type
    }

}