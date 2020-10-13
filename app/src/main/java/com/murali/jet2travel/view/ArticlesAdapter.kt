/**
 * Created by Murali Mohan on 13/10/2020.
 */

package com.murali.jet2travel.view

import Articles
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murali.jet2travel.R
import com.murali.jet2travel.utils.Constants
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ArticlesAdapter(
    private val context: Context,
    val articlesList: ArrayList<Articles>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext: Context
    private val mKeyNames: List<Articles>

    init {
        this.mContext = context
        this.mKeyNames = articlesList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val inflater = LayoutInflater.from(parent.context)

        val viewHolder: RecyclerView.ViewHolder = when (viewType) {

            CellType.HEADER.ordinal -> HeaderViewHolder(inflater.inflate(R.layout.layout_header, parent, false))

            CellType.CONTENT.ordinal ->  return  InfoViewHolder(inflater.inflate(R.layout.layout_articles_adapter, parent, false))

            CellType.FOOTER.ordinal -> return FooterViewHolder(inflater.inflate(R.layout.layout_footer, parent, false))

            else -> return  InfoViewHolder(inflater.inflate(R.layout.layout_articles_adapter, parent, false))

        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return articlesList.size + 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //To change body of created functions use File | Settings | File Templates.

        when (getItemViewType(position)) {
            CellType.HEADER.ordinal -> {
                val headerViewHolder = holder as HeaderViewHolder
            }
            CellType.CONTENT.ordinal -> {
                val headerViewHolder = holder as InfoViewHolder
                headerViewHolder.bind(position,articlesList.get(position-1), mContext)
            }
            CellType.FOOTER.ordinal -> {
                val footerViewHolder = holder as FooterViewHolder
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        return when (position) {
            0 -> CellType.HEADER.ordinal
            articlesList.size + 1 -> CellType.FOOTER.ordinal
            else -> CellType.CONTENT.ordinal
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    //Do Nothing
    }

    class InfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nameTV: TextView
        var designationTV: TextView
        var dascriptionTextView: TextView
        var timeTextView: TextView
        var likesTextView: TextView
        var commentsTextView: TextView
        var userAvatarImageView: ImageView
        var userImageView: ImageView
        var cContext: Context

        init {
            nameTV = itemView.findViewById<View>(R.id.tv_user_name) as TextView
            designationTV = itemView.findViewById<View>(R.id.tv_designation) as TextView
            dascriptionTextView = itemView.findViewById<View>(R.id.tv_description) as TextView
            timeTextView = itemView.findViewById<View>(R.id.tv_time) as TextView
            likesTextView = itemView.findViewById<View>(R.id.tv_likes) as TextView
            commentsTextView = itemView.findViewById<View>(R.id.tv_comments) as TextView
            userAvatarImageView = itemView.findViewById<View>(R.id.user_avatar) as ImageView
            userImageView = itemView.findViewById<View>(R.id.user_image) as ImageView
            cContext = view.context
        }

        internal fun bind(position: Int, articles: Articles, context: Context) {
            // This method will be called anytime a list item is created or update its data
            //Do your stuff here
            if(articles.user!=null && !articles.user.isEmpty()){
                for(i in 0 ..articles.user.size) {
                    nameTV.text = articles.user[0].name
                    designationTV.text = articles.user[0].designation
                    dascriptionTextView.text = articles.user[0].about


                    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US)
                    val date = inputFormat.parse(articles.user[0].createdAt)

                    val fmtOut = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a" , Locale.US)
                    val resultDate: String = fmtOut.format(date)

                    val calendarDate = Calendar.getInstance().time
                    val todayDate = fmtOut.format(calendarDate)

                    timeTextView.text = Constants.daysBetween(resultDate, todayDate, fmtOut.toPattern());
                    Constants.loadImageFromGlide(context,articles.user[0].avatar,userAvatarImageView)
                }
            }

            if(articles.likes!=null && articles.likes!=0) {
                likesTextView.text = articles.likes!!.toString() + " Likes"
            }else
                likesTextView.text = "0" + " Likes"


            if(articles.comments!=null && articles.comments!=0) {
                commentsTextView.text = articles.comments!!.toString()
            }else
                commentsTextView.text = " No Comments"

            if(articles.media!=null && !articles.media.isEmpty()) {
                for (i in 0..articles.media.size) {
                    //timeTextView.text = articles.user[0].createdAt
                    Constants.loadImageFromGlide(context,articles.media[0].image,userImageView)
                }
            }
        }

    }



    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var loadMoreButton: Button
        var mContext: Context
        var count = 1
        init {
            loadMoreButton = itemView.findViewById<View>(R.id.btn_load_more) as Button
            loadMoreButton.setOnClickListener(this)
            mContext = view.context
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.btn_load_more -> {
                    (mContext as MainActivity).getViewModelStore().clear();
                    count++
                    (mContext as MainActivity).setupViewModel(count)
                    (mContext as MainActivity).setupObservers()
                }
            }
        }

    }

    /***
     * Enum class for recyclerview Cell type
     */
    enum class CellType(viewType: Int) {
        HEADER(0),
        FOOTER(1),
        CONTENT(2)
    }



}