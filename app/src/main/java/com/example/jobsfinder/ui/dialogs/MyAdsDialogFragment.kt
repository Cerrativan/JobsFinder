package com.example.jobsfinder.ui.dialogs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgument
import com.example.jobsfinder.R
import com.example.jobsfinder.databinding.MyAdsDialogFragmentBinding
import com.example.jobsfinder.databinding.MyAdsFragmentBinding
import com.example.jobsfinder.ui.model.Ad
import com.example.jobsfinder.ui.my_ads.MyAdsViewModel
import io.ktor.http.*

class MyAdsDialogFragment : Fragment(R.layout.my_ads_dialog_fragment) {

    companion object {
        fun newInstance() = MyAdsDialogFragment()
    }
    private var _myAdsDialogBinding: MyAdsDialogFragmentBinding? = null

    private val myAdsDialogBinding get() = _myAdsDialogBinding!!

    private lateinit var viewModel: MyAdsDialogViewModel
    private val args by navArgs<MyAdsDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myAdsDialogViewModel = ViewModelProvider(this).get(MyAdsDialogViewModel::class.java)
        viewModel = myAdsDialogViewModel
        _myAdsDialogBinding = MyAdsDialogFragmentBinding.inflate(inflater, container, false)
        val view: View = myAdsDialogBinding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myAdsDialogBinding.myAdsTitleTextView.text = args.title
        myAdsDialogBinding.myAdsCityTextView.text = args.city
        myAdsDialogBinding.myAdsSalaryTextView.text = args.salary
        myAdsDialogBinding.myAdsDescriptionTextView.text = args.description

        myAdsDialogBinding.myAdsDialogDeleteButton.setOnClickListener {
            val ad = Ad(args.id, args.title.toString(), args.city.toString(), args.description.toString(), Integer.parseInt(args.salary), args.owner.toString())
            viewModel.deleteAd(ad)
        }

        viewModel.myAdsResult.observe(viewLifecycleOwner, Observer {
            if(it.equals(HttpStatusCode.OK))
                Toast.makeText(context, "Ad Deleted", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Ad deleting failed", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _myAdsDialogBinding = null
    }

}