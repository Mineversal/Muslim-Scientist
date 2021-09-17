package com.mineversal.muslimscientist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mineversal.muslimscientist.db.ScientistDao
import com.mineversal.muslimscientist.db.ScientistRoomDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var rvScientist: RecyclerView
    private var list: ArrayList<Scientist> = arrayListOf()
    private var title: String = "Mode List"
    private lateinit var database: ScientistRoomDatabase
    private lateinit var dao: ScientistDao

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)

        rvScientist = findViewById(R.id.rv_scientist)
        rvScientist.setHasFixedSize(true)

        list.addAll(ScientistsData.listData)
        showRecyclerList()

        database = ScientistRoomDatabase.getDatabase(applicationContext)
        dao = database.getScientistDao()
    }

    private fun showDevProfile() {
        val moveIntent = Intent(this@MainActivity, DevProfileActivity::class.java)
        startActivity(moveIntent)
    }

    private fun showFavorite() {
        val moveIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
        startActivity(moveIntent)
    }

    private fun showItemDetails(scientist: Scientist) {
        val itemDetailsIntent = Intent(this@MainActivity, ItemDetailsActivity::class.java)
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

    private fun favoriteSelected(scientist: Scientist){
        if (dao.getById(scientist.id).isEmpty()){
            dao.insert(scientist)
        }
        else{
            dao.update(scientist)
        }
        Toast.makeText(applicationContext, "Added to Favorite", Toast.LENGTH_SHORT).show()

    }

    private fun showRecyclerList() {
        rvScientist.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListScientistAdapter(list)
        rvScientist.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object : ListScientistAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Scientist) {
                showSelectedScientist(data)
            }
        })
    }

    private fun showRecyclerGrid() {
        rvScientist.layoutManager = GridLayoutManager(this, 2)
        val gridScientistAdapter = GridScientistAdapter(list)
        rvScientist.adapter = gridScientistAdapter

        gridScientistAdapter.setOnItemClickCallback(object : GridScientistAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Scientist) {
                showSelectedScientist(data)
            }
        })
    }

    private fun showRecyclerCardView() {
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
                favoriteSelected(data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.profile -> {
                showDevProfile()
            }
            R.id.favorite -> {
                showFavorite()
            }
            R.id.action_list -> {
                title = "Mode List"
                showRecyclerList()
            }
            R.id.action_grid -> {
                title = "Mode Grid"
                showRecyclerGrid()
            }
            R.id.action_cardview -> {
                title = "Mode Card View"
                showRecyclerCardView()
            }
        }
        setActionBarTitle(title)
    }

    fun showSelectedScientist(scientist: Scientist) {
        showItemDetails(scientist)
    }
}