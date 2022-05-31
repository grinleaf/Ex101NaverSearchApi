package com.grinleaf.ex101naversearchapi.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grinleaf.ex101naversearchapi.R
import com.grinleaf.ex101naversearchapi.databinding.RecyclerItemBinding
import com.grinleaf.ex101naversearchapi.model.NaverSearchApiResponse
import com.grinleaf.ex101naversearchapi.model.SearchItem

class MyAdapter(val context: Context, val items:MutableList<SearchItem>):RecyclerView.Adapter<MyAdapter.VH>() {
    inner class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding:RecyclerItemBinding= RecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        //웹기반 JSON 크롤링할 때, <>태그문이 포함되어 있는 경우 그대로 출력될 수 있음! <>태그문을 없애보자 : HtmlCompat.FROM_HTML_MODE_COMPACT
        var title= HtmlCompat.fromHtml(items[position].title,HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.binding.tvTitle.text= title
        holder.binding.tvLowPrice.text= items[position].lprice
        holder.binding.tvBrand.text= items[position].brand
        Glide.with(context).load(items[position].image).into(holder.binding.iv)

        holder.binding.root.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse(items[position].link))
            //웹브라우저를 띄우는 Intent : ACTION_VIEW, Uri.parse(URL)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}