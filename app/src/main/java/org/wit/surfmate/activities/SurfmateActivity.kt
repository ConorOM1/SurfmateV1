package org.wit.surfmate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.surfmate.R
import org.wit.surfmate.databinding.ActivitySurfspotBinding
import org.wit.surfmate.main.MainApp
import org.wit.surfmate.models.SurfspotModel
import timber.log.Timber.Forest.i

class SurfmateActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurfspotBinding
    var surfspot = SurfspotModel()
//    val surfspots = ArrayList<SurfspotModel>()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurfspotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Surfmate Activity started...")

        binding.btnAdd.setOnClickListener() {
            surfspot.title = binding.surfspotTitle.text.toString()
            surfspot.description = binding.description.text.toString()
            if (surfspot.title.isNotEmpty()) {

                app.surfspots.add(surfspot.copy())
                i("add Button Pressed: ${surfspot}")
                for (i in app.surfspots.indices) {
                    i("Surfspot[$i]:${this.app.surfspots[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_surfspot, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}