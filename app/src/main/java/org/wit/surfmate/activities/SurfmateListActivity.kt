package org.wit.surfmate.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.surfmate.R
import org.wit.surfmate.adapters.SurfmateAdapter
import org.wit.surfmate.adapters.SurfmateListener
import org.wit.surfmate.databinding.ActivitySurfspotListBinding
import org.wit.surfmate.main.MainApp
import org.wit.surfmate.models.SurfspotModel

class SurfmateListActivity : AppCompatActivity(), SurfmateListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySurfspotListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurfspotListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = SurfmateAdapter(app.surfspots.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SurfmateActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.surfspots.findAll().size)
            }
        }

    override fun onSurfspotClick(surfspot: SurfspotModel) {
        val launcherIntent = Intent(this, SurfmateActivity::class.java)
        launcherIntent.putExtra("surfspot_edit", surfspot)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.surfspots.findAll().size)
            }
        }
}
