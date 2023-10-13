package org.wit.surfmate.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.surfmate.R
import org.wit.surfmate.databinding.ActivitySurfspotListBinding
import org.wit.surfmate.databinding.CardSurfspotBinding
import org.wit.surfmate.main.MainApp
import org.wit.surfmate.models.SurfspotModel

class SurfmateListActivity : AppCompatActivity() {

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
        binding.recyclerView.adapter = SurfspotAdapter(app.surfspots)
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
                notifyItemRangeChanged(0,app.surfspots.size)
            }
        }
}

class SurfspotAdapter constructor(private var surfspots: List<SurfspotModel>) :
                                RecyclerView.Adapter<SurfspotAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSurfspotBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val surfspot = surfspots[holder.adapterPosition]
        holder.bind(surfspot)
    }

    override fun getItemCount(): Int = surfspots.size

    class MainHolder(private val binding : CardSurfspotBinding) :
                            RecyclerView.ViewHolder(binding.root) {

        fun bind(surfspot: SurfspotModel) {
            binding.surfspotTitle.text = surfspot.title
            binding.description.text = surfspot.description
        }
    }
}
