package com.mineversal.muslimscientist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListScientistAdapter(val listScientist: ArrayList<Scientist>) : RecyclerView.Adapter<ListScientistAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_detail)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_scientist, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val scientist = listScientist[position]
        Glide.with(holder.itemView.context)
            .load(scientist.photo)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPhoto)
        holder.tvName.text = scientist.name
        holder.tvDetail.text = scientist.detail
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listScientist[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listScientist.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Scientist)
    }
}