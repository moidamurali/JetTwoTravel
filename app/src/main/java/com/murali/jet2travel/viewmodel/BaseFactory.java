/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


/**
 * A creator is used to inject the project ID into the ViewModel
 */
public class BaseFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    private final Application application;
    int limit;
    int pageLoad;

    public BaseFactory(@NonNull Application application, int limit, int pageLoad) {
        this.application = application;
        this.pageLoad = pageLoad;
        this.limit = limit;
    }


    @Override
    public <T extends ViewModel > T create(Class<T> modelClass) {

            if (modelClass == ArticlesViewModel.class) {
                return (T) new ArticlesViewModel(application, limit, pageLoad);
            }
        //}
        return null;//super.create(modelClass);
    }
}