/**
 * Created by Murali Mohan on 13/10/2020.
 */
package com.murali.jet2travel.viewmodel;

import Articles
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.murali.jet2travel.network.APIServiceCall
import com.murali.jet2travel.utils.Constants

class ArticlesViewModel : BaseViewModel {
    var observableProject: MutableLiveData<List<Articles>>? = null

    constructor(application: Application, limit: Int, pageLoad: Int) : super(application, 0) {
        val context = application.applicationContext
        // a different source can be passed, here i am passing
        val mAPIServiceCall = APIServiceCall.instance
        mAPIServiceCall!!.endPointURL = Constants.Companion.API_BASE_URL
        mAPIServiceCall!!.hitServiceServiceCall()
        observableProject = mAPIServiceCall.getArticlesData(limit,pageLoad) // Retro Fit Service Call
    }

}