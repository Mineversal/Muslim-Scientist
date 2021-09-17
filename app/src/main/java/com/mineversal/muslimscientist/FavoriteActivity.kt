package com.mineversal.muslimscientist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mineversal.muslimscientist.db.ScientistDao
import com.mineversal.muslimscientist.db.ScientistRoomDatabase

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rvScientist: RecyclerView
    private var list: ArrayList<Scientist> = arrayListOf()
    private lateinit var database: ScientistRoomDatabase
    private lateinit var dao: ScientistDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvScientist = findViewById(R.id.rv_scientist)
        rvScientist.setHasFixedSize(true)

        list.addAll(ScientistsData.listData)
        getScientistData()

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Favorite Bookmark"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    private fun getScientistData(){
        database = ScientistRoomDatabase.getDatabase(applicationContext)
        dao = database.getScientistDao()
        val listItems = arrayListOf<Scientist>()
        listItems.addAll(dao.getAll())
        showRecyclerCardView(listItems)
        //if (listItems.isNotEmpty()){
        //    text_view_note_empty.visibility = View.GONE
        //}
        //else{
        //    text_view_note_empty.visibility = View.VISIBLE
        //}
    }

    override fun onResume() {
        super.onResume()
        getScientistData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showDevProfile() {
        val moveIntent = Intent(this@FavoriteActivity, DevProfileActivity::class.java)
        startActivity(moveIntent)
    }

    private fun showItemDetails(scientist: Scientist) {
        val itemDetailsIntent = Intent(this@FavoriteActivity, ItemDetailsActivity::class.java)
        itemDetailsIntent.putExtra(ItemDetailsActivity.EXTRA_NAME, scientist.name)
        itemDetailsIntent.putExtra(ItemDetailsActivity.EXTRA_DETAIL, scientist.detail)
        itemDetailsIntent.putExtra(ItemDetailsActivity.EXTRA_ERA, scientist.era)
        itemDetailsIntent.putExtra(ItemDetailsActivity.EXTRA_PROFFESION, scientist.proffesion)
        itemDetailsIntent.putExtra(ItemDetailsActivity.EXTRA_INTEREST, scientist.interest)
        itemDetailsIntent.putExtra(ItemDetailsActivity.EXTRA_WIKI, scientist.wiki)
        itemDetailsIntent.putExtra("resId", scientist.photo)
        startActivity(itemDetailsIntent)
    }

    private fun shareItem(scientist: Scientist) {
        val imageUri = Uri.parse("android.resource://${packageName}/${scientist.photo}")
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TITLE, scientist.name)
        shareIntent.putExtra(Intent.EXTRA_TEXT, scientist.detail)
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        shareIntent.type = "image/*"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, "SEND"))
    }

    private fun deleteFavorite(scientist: Scientist){
        dao.delete(scientist)
        Toast.makeText(applicationContext, "Remove from Favorite", Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerCardView(list: ArrayList<Scientist>) {
        rvScientist.layoutManager = LinearLayoutManager(this)
        val cardViewScientistAdapter = CardViewScientistAdapter(list)
        rvScientist.adapter = cardViewScientistAdapter

        cardViewScientistAdapter.setOnItemClickCallback(object : CardViewScientistAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Scientist) {
                showSelectedScientist(data)
            }
        })

        cardViewScientistAdapter.setOnItemShareClickCallback(object : CardViewScientistAdapter.OnItemShareClickCallback {
            override fun onShareClicked(data: Scientist) {
                shareItem(data)
            }
        })

        cardViewScientistAdapter.setOnItemFavoriteClickCallback(object : CardViewScientistAdapter.OnItemFavoriteClickCallback {
            override fun onFavoriteClicked(data: Scientist) {
                deleteFavorite(data)
                finish()
            }
        })
    }

    fun showSelectedScientist(scientist: Scientist) {
        showItemDetails(scientist)
    }
}