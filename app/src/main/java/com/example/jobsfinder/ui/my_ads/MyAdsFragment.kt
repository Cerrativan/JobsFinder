package com.example.jobsfinder.ui.my_ads

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobsfinder.ui.adapters.CustomAdapter
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.MyAdsFragmentBinding
import com.example.jobsfinder.ui.model.Ad

class MyAdsFragment : Fragment(R.layout.my_ads_fragment) {

    companion object {
        fun newInstance() = MyAdsFragment()
    }

    private var _myAdsBinding: MyAdsFragmentBinding? = null

    private val myAdsBinding get() = _myAdsBinding!!


    private val layoutManager: RecyclerView.LayoutManager? = null
    private val adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: MyAdsViewModel
    private val title = ArrayList<Ad>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val myAdsViewModel = ViewModelProvider(this).get(MyAdsViewModel::class.java)
        viewModel = myAdsViewModel
        _myAdsBinding = MyAdsFragmentBinding.inflate(inflater, container, false)
        val view: View = myAdsBinding.root
        recyclerView = view.findViewById(R.id.myAds_RecyclerView)
        val customAdapter: CustomAdapter = CustomAdapter(title)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = customAdapter

        var list = ArrayList<Ad>()
        viewModel.getAds(getUserId())
        viewModel.myAdsResult.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty())
                Toast.makeText(context, "Search provided no results", Toast.LENGTH_SHORT).show()
            else {
                customAdapter.updateList(it)
            }
        })
        viewModel.myAdsFailed.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Error in the search result", Toast.LENGTH_SHORT).show()
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _myAdsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun getUserId(): String {
        val shared = activity?.getSharedPreferences("userData", Context.MODE_PRIVATE)!!
        return shared.getString("id", null)!!
    }

}