/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.view

import Articles
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.murali.jet2travel.R
import com.murali.jet2travel.viewmodel.ArticlesViewModel
import com.murali.jet2travel.viewmodel.BaseFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this,BaseFactory(getApplication(),1,10)).get(ArticlesViewModel::class.java)

    }

    private fun setupUI(articles: ArrayList<Articles>) {
        rv_articles.layoutManager = LinearLayoutManager(this)
        rv_articles.addItemDecoration(DividerItemDecoration(rv_articles.context, (rv_articles.layoutManager as LinearLayoutManager).orientation))
        adapter = ArticlesAdapter(this, articles/*arrayListOf()*/)
        rv_articles.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.observableProject!!.observe(this, Observer {
            it?.let { resource ->
                progressBar.visibility = View.GONE
                //retrieveList(resource)
                setupUI(resource as ArrayList<Articles>)
            }
        })
    }

    private fun retrieveList(articles: List<Articles>) {
        adapter.apply {
            addArticles(articles)
            notifyDataSetChanged()
        }
    }
}