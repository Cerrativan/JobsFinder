package com.example.jobsfinder.ui.dialogs

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.MyAdsDialogFragmentBinding
import com.example.jobsfinder.databinding.SearchDialogFragmentBinding
import com.example.jobsfinder.ui.model.Ad

class SearchDialogFragment : Fragment(R.layout.search_dialog_fragment) {

    companion object {
        fun newInstance() = SearchDialogFragment()
    }
    private var _searchDialogBinding: SearchDialogFragmentBinding? = null

    private val searchDialogBinding get() = _searchDialogBinding!!
    private lateinit var viewModel: SearchDialogViewModel

    private val args by navArgs<SearchDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchDialogViewModel = ViewModelProvider(this).get(SearchDialogViewModel::class.java)
        viewModel = searchDialogViewModel
        _searchDialogBinding = SearchDialogFragmentBinding.inflate(inflater, container, false)
        val view: View = searchDialogBinding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchDialogBinding.searchTitleTextView.text = args.title
        searchDialogBinding.searchCityTextView.text = args.city
        searchDialogBinding.searchSalaryTextView.text = args.salary
        searchDialogBinding.searchdescriptionTextView.text = args.description
        searchDialogBinding.searchOwnerTextView.text = args.owner

        searchDialogBinding.searchDialogApplyButton.setOnClickListener {
            if(getUserType() == "user")
                Toast.makeText(context, "Applied", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Only private users can apply", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _searchDialogBinding = null
    }

    private fun getUserType(): String {
        val shared = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!
        val type: String = shared.getString("type", null)!!
        return type
    }

}