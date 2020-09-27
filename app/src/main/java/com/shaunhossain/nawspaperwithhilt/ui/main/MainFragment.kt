package com.shaunhossain.nawspaperwithhilt.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.shaunhossain.nawspaperwithhilt.R
import com.shaunhossain.nawspaperwithhilt.model.Resource
import com.shaunhossain.nawspaperwithhilt.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private  val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allNewsList = viewModel.breakingNews

        allNewsList.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                        //Toast.makeText(activity, "article: ${newsResponse.articles}", Toast.LENGTH_LONG).show()
                        Log.d("main","${newsResponse.articles}")
                    }
                }
                is Resource.Error -> {

                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {

                }
            }

        })
    }

}