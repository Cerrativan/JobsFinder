package com.example.jobsfinder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.FragmentHomeBinding
import com.example.jobsfinder.ui.adapters.HomeAdapter
import com.example.jobsfinder.ui.adapters.SearchAdapter
import com.example.jobsfinder.utilities.AdWithOwner

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val layoutManager: RecyclerView.LayoutManager? = null
    private val adapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view: View = binding.root

        var list = ArrayList<AdWithOwner>()

        recyclerView = view.findViewById(R.id.home_recyclerView)
        val homeAdapter: HomeAdapter = HomeAdapter(list)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = homeAdapter

        homeViewModel.searchLatest()

        homeViewModel.homeResult.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty())
                Toast.makeText(context, "Search provided no results", Toast.LENGTH_SHORT).show()
            else {
                homeAdapter.updateList(it)
            }
        })

        homeViewModel.homeFailed.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Error while searching", Toast.LENGTH_SHORT).show()
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.publishJobButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.homeToPublish_action)
        }

        binding.searchJobButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.homeToSearch_action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
