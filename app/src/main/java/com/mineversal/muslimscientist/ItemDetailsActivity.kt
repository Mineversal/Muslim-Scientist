package com.mineversal.muslimscientist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ItemDetailsActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_NAME = "Nama Ilmuwan"
        const val EXTRA_DETAIL = "Profil Detail Ilmuwan"
        const val EXTRA_ERA = "Era Hidup Ilmuwan"
        const val EXTRA_PROFFESION = "Profesi Ilmuwan"
        const val EXTRA_INTEREST = "Bidang Minat Ilmuwan"
        const val EXTRA_WIKI = "Link Wiki"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val btnWiki: Button = findViewById(R.id.btn_wiki)
        btnWiki.setOnClickListener(this)

        val imageView: ImageView = findViewById(R.id.photo)
        val nameReceived: TextView = findViewById(R.id.name)
        val detailReceived: TextView = findViewById(R.id.detail)
        val eraReceived: TextView = findViewById(R.id.era)
        val proffesionReceived: TextView = findViewById(R.id.proffesion)
        val interestReceived: TextView = findViewById(R.id.interest)

        val bundle: Bundle? = intent.extras
        val resId: Int = bundle!!.getInt("resId")
        val named = intent.getStringExtra(EXTRA_NAME)
        val detail = intent.getStringExtra(EXTRA_DETAIL)
        val era = intent.getStringExtra(EXTRA_ERA)
        val proffesion = intent.getStringExtra(EXTRA_PROFFESION)
        val interest = intent.getStringExtra(EXTRA_INTEREST)

        val textName = "$named"
        val textDetail = "$detail"
        val textEra = "$era"
        val textProffesion = "$proffesion"
        val textInterest = "$interest"

        imageView.setImageResource(resId)
        nameReceived.text = textName
        detailReceived.text = textDetail
        eraReceived.text = textEra
        proffesionReceived.text = textProffesion
        interestReceived.text = textInterest

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Informasi Ilmuwan"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        val wiki = intent.getStringExtra(EXTRA_WIKI)

        when (v?.id) {
            R.id.btn_wiki -> {
                val website = "$wiki"
                val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                startActivity(websiteIntent)
            }
        }
    }
}

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

    private fun shareItem(name: String, detail: String, resId: Int) {
        val imageUri = Uri.parse("android.resource://${packageName}/${resId}")
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TITLE, name)
        shareIntent.putExtra(Intent.EXTRA_TEXT, detail)
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        shareIntent.type = "image/*"
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, "SEND"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                true
            }
            R.id.share -> {
                // save profile changes
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    */