package com.example.comics.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.model.ResultModel
import com.example.comics.util.Result
import com.example.comics.viewmodel.ComicsViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: ComicsViewModel by inject()

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initObserver()
        refreshList()
        swipeList()
    }

    private fun initObserver(){
        viewModel.result.observe(this){
            when (it) {
                is Result.Success -> viewList(it.data.data.results)
                is Result.Failure -> showError()
            }
        }
    }

    private fun swipeList() = with(binding?.swipeRefresh) {
        this?.setOnRefreshListener {
            refreshList()
        }
    }

     private fun refreshList() {
        with(binding) {
            this?.swipeRefresh?.isRefreshing = true
            lifecycle.coroutineScope.launch {
                viewModel.getListComics()
            }
        }
    }

     private fun viewList(list: List<ResultModel>) {
        with(binding) {
            this?.errorTV?.visibility = View.GONE
            this?.listItem?.visibility = View.VISIBLE
            this?.listItem?.adapter = Adapter(list)
            this?.listItem?.layoutManager = LinearLayoutManager(this@MainActivity)
            this?.swipeRefresh?.isRefreshing = false
        }
    }

     private fun showError() {
        with(binding) {
            this?.listItem?.visibility = View.GONE
            this?.errorTV?.visibility = View.VISIBLE
            this?.swipeRefresh?.isRefreshing = false
        }
    }
}