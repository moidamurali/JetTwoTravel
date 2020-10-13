/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.viewmodel;

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application, private val id: Long) :
    AndroidViewModel(application)