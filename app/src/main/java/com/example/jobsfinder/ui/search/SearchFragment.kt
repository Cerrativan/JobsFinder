package com.example.jobsfinder.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsfinder.R
import com.example.jobsfinder.ui.adapters.SearchAdapter
import com.example.jobsfinder.databinding.FragmentSearchBinding
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.utilities.AdWithOwner

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val layoutManager: RecyclerView.LayoutManager? = null
    private val adapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>? = null
    private lateinit var recyclerView: RecyclerView
    private var viewModel: SearchViewModel = SearchViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel = searchViewModel

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view: View = binding.root
        var list = ArrayList<AdWithOwner>()

        recyclerView = view.findViewById(R.id.search_recyclerView)
        val searchAdapter: SearchAdapter = SearchAdapter(list)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = searchAdapter

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty())
                Toast.makeText(context, "Search provided no results", Toast.LENGTH_SHORT).show()
            else {
                searchAdapter.updateList(it)
            }
        })

        viewModel.searchFailed.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Error while searching", Toast.LENGTH_SHORT).show()
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterButton.setOnClickListener {
            val job = binding.enterJobEditText
            val city = binding.enterCityEditText
            viewModel.searchValidation(job, city)
        }

        viewModel.searchFailed.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Error in the search result", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}