package com.mineversal.muslimscientist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CardViewScientistAdapter(private val listScientist: ArrayList<Scientist>) : RecyclerView.Adapter<CardViewScientistAdapter.CardViewViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onItemShareClickCallback: OnItemShareClickCallback
    private lateinit var onItemFavoriteClickCallback: OnItemFavoriteClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnItemShareClickCallback(onItemShareClickCallback: OnItemShareClickCallback) {
        this.onItemShareClickCallback = onItemShareClickCallback
    }

    fun setOnItemFavoriteClickCallback(onItemFavoriteClickCallback: OnItemFavoriteClickCallback) {
        this.onItemFavoriteClickCallback = onItemFavoriteClickCallback
    }

    class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_detail)
        var btnFavorite: Button = itemView.findViewById(R.id.btn_set_favorite)
        var btnShare: Button = itemView.findViewById(R.id.btn_set_share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_scientist, parent, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        val scientist = listScientist[position]
        Glide.with(holder.itemView.context)
            .load(scientist.photo)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)
        holder.tvName.text = scientist.name
        holder.tvDetail.text = scientist.detail
        holder.btnFavorite.setOnClickListener { Toast.makeText(holder.itemView.context, "Favorite " + listScientist[holder.adapterPosition].name, Toast.LENGTH_SHORT).show() }
        holder.btnFavorite.setOnClickListener { onItemFavoriteClickCallback.onFavoriteClicked(listScientist[holder.adapterPosition]) }
        holder.btnShare.setOnClickListener { Toast.makeText(holder.itemView.context, "Share " + listScientist[holder.adapterPosition].name, Toast.LENGTH_SHORT).show() }
        holder.btnShare.setOnClickListener { onItemShareClickCallback.onShareClicked(listScientist[holder.adapterPosition]) }
        holder.itemView.setOnClickListener { Toast.makeText(holder.itemView.context, "Kamu memilih " + listScientist[holder.adapterPosition].name, Toast.LENGTH_SHORT).show() }
        holder.itemView.setOnClickListener { Toast.makeText(holder.itemView.context, "Kamu memilih " + listScientist[position].name, Toast.LENGTH_SHORT).show() }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listScientist[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return listScientist.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Scientist)
    }

    interface OnItemShareClickCallback {
        fun onShareClicked(data: Scientist)
    }

    interface OnItemFavoriteClickCallback {
        fun onFavoriteClicked(data: Scientist)
    }
}