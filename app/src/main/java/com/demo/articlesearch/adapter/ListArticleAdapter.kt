package com.demo.articlesearch.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.articlesearch.BaseActivity
import com.demo.articlesearch.R
import com.demo.articlesearch.models.ListArticleObject
import kotlinx.android.synthetic.main.adapter_list_article.view.*
import java.util.*

class ListArticleAdapter(
    private var data: ArrayList<ListArticleObject>
) :
    RecyclerView.Adapter<ListArticleAdapter.ViewHolder>(), Filterable {
    private var originalData: ArrayList<ListArticleObject> = data
    private var dataFilter: Filter? = null

    fun updateModel(data: ArrayList<ListArticleObject>) {
        this.data = data
        this.originalData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_list_article, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(dataList: ListArticleObject) {

            if (dataList.title != null)
                itemView.titleLabel.text = dataList.title
            else itemView.titleLabel.text = dataList.desc1
            itemView.authorLabel.text = dataList.author
            itemView.tagLabel.text = dataList.tag
            if (dataList.date != null)
                itemView.dateLabel.text =
                    BaseActivity.changeTimeDate(dataList.date, "dd MMM yyyy")

            if (dataList.pub_date != null)
                itemView.dateLabel.text =
                    BaseActivity.changeTime(dataList.pub_date, "dd MMM yyyy")

            if (dataList.image != null && dataList.image.isNotEmpty()) Glide.with(itemView.context)
                .load(dataList.image).into(itemView.listImage)

            itemView.viewList.setOnClickListener {
                viewArticle(itemView.context, dataList)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun viewArticle(context: Context, data: ListArticleObject) {
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_view_article, null)
            val dialog = Dialog(context, R.style.DialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(dialogView)
            dialog.setCancelable(true)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.show()

            val listImage = dialogView.findViewById(R.id.listImage) as ImageView?
            val titleLabel = dialogView.findViewById(R.id.titleLabel) as TextView?
            val authorLabel = dialogView.findViewById(R.id.authorLabel) as TextView?
            val tagLabel = dialogView.findViewById(R.id.tagLabel) as TextView?
            val descLabel = dialogView.findViewById(R.id.descLabel) as TextView?
            val linkLabel = dialogView.findViewById(R.id.linkLabel) as TextView?

            if (listImage != null && data.image != null && data.image.isNotEmpty()) Glide.with(itemView.context)
                .load(data.image).into(listImage)

            if (data.title != null)
                titleLabel?.text = data.title
            else titleLabel?.text = data.desc1
            authorLabel?.text = data.author
            tagLabel?.text = data.tag
            if (data.date != null)
                authorLabel?.text = data.author + " @ " +
                        BaseActivity.changeTimeDate(data.date, "dd MMM yyyy")
            if (data.pub_date != null)
                authorLabel?.text = data.author + " @ " +
                        BaseActivity.changeTime(data.pub_date, "dd MMM yyyy")

            descLabel?.text = "${data.desc1}\n\n${data.desc2}"

            linkLabel?.text =
                Html.fromHtml("<a href=\"${data.link}\">Click here for more details</a>")
            linkLabel?.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private inner class DataFilter : Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {

        }
    }

    override fun getFilter(): Filter {
        if (dataFilter == null)
            dataFilter = DataFilter()
        return dataFilter as Filter
    }

    fun resetData() {
        data = originalData
    }
}