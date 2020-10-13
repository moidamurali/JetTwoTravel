/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.graphics.drawable.ScaleDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class Constants{
    companion object {

        val API_BASE_URL = "https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/"


        fun imageLoadProgress(context: Context): Drawable? {
            val attrs = intArrayOf(R.attr.indeterminateDrawable)
            val attrs_indeterminateDrawable_index = 0
            val a = context.obtainStyledAttributes(R.style.Widget_ProgressBar, attrs)
            return try {
                val scaled = a.getDrawable(attrs_indeterminateDrawable_index)
                scaled!!.setBounds(0,0,
                    (scaled.intrinsicWidth * 0.5).toInt(),
                    (scaled.intrinsicHeight * 0.5).toInt())

                ///////////////
                ScaleDrawable(scaled, 0, 60f, 60f)
            } finally {
                a.recycle()
            }
        }

        fun loadImageFromGlide(context: Context, imageURL: String, imageView: ImageView) {

            Glide.with(context).load(imageURL)
                .thumbnail(0.5f)
                .placeholder(imageLoadProgress(context))
                .into(imageView)
        }



        fun daysBetween(dateStart: String,dateStop: String, pattern: String): String{
            var currentDate: Date? = null
            var pastDate: Date? = null
            val format = SimpleDateFormat(pattern, Locale.ENGLISH)

                currentDate = format.parse(dateStart);
                pastDate = format.parse(dateStop);

                var diff = pastDate.getTime() - currentDate.getTime();

                if(currentDate.compareTo(pastDate) == 0){

                    var inutesDiff = diff / (60 * 1000) % 60
                    var hoursDiff = diff / (60 * 60 * 1000) % 24

                    if(hoursDiff!=null){
                        return hoursDiff.toString().plus( " Hr").plus(inutesDiff).plus(" Min")

                    }else{
                        return (inutesDiff.toString() + " Min")
                    }

                }else{
                    return (diff / (24 * 60 * 60 * 1000)).toString().plus( " Days")
                }

            }

        fun noConnectionDialog( activity: Activity){
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
            builder.setMessage("No Internet connection. Do you want to close application")
            builder.setCancelable(true)

            builder.setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
                activity.finish()
            })

            builder.setNegativeButton( "No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            val alert: android.app.AlertDialog? = builder.create()
            alert!!.show()
        }

        }

    }
