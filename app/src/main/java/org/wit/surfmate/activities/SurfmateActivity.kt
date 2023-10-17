package org.wit.surfmate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.surfmate.R
import org.wit.surfmate.databinding.ActivitySurfspotBinding
import org.wit.surfmate.showImagePicker
import org.wit.surfmate.main.MainApp
import org.wit.surfmate.models.SurfspotModel
import timber.log.Timber.Forest.i


class SurfmateActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurfspotBinding
    var surfspot = SurfspotModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
//    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivitySurfspotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if (intent.hasExtra("surfspot_edit")) {
            edit = true
            surfspot = intent.extras?.getParcelable("surfspot_edit")!!
            binding.surfspotTitle.setText(surfspot.title)
            binding.description.setText(surfspot.description)
            binding.btnAdd.setText(R.string.save_surfspot)
            Picasso.get()
                .load(surfspot.image)
                .into(binding.surfspotImage)
        }

        binding.btnAdd.setOnClickListener() {
            surfspot.title = binding.surfspotTitle.text.toString()
            surfspot.description = binding.description.text.toString()
            if (surfspot.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_surfspot_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.surfspots.update(surfspot.copy())
                } else {
                    app.surfspots.create(surfspot.copy())
                }
            }
            i("add Button Pressed: $surfspot")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            surfspot.image = result.data!!.data!!
                            Picasso.get()
                                .load(surfspot.image)
                                .into(binding.surfspotImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}