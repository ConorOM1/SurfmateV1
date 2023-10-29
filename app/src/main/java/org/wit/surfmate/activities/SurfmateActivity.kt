package org.wit.surfmate.activities

import android.content.Intent
import android.net.Uri
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
import org.wit.surfmate.models.Location
import org.wit.surfmate.models.SurfspotModel
import timber.log.Timber.Forest.i
import android.widget.RatingBar
import android.widget.Toast


class SurfmateActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurfspotBinding
    var surfspot = SurfspotModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        edit = false
        binding = ActivitySurfspotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)


        app = application as MainApp

        if (intent.hasExtra("surfspot_edit")) {
            edit = true
            surfspot = intent.extras?.getParcelable("surfspot_edit")!!
            binding.surfspotTitle.setText(surfspot.name)
            binding.description.setText(surfspot.observations)
            ratingBar.rating = surfspot.rating  // Set the rating value for Edit mode
            binding.btnAdd.setText(R.string.save_surfspot)
            Picasso.get()
                .load(surfspot.image)
                .into(binding.surfspotImage)
            if (surfspot.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_surfspot_image)
            }
        }

        ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                Toast.makeText(this, "You rated the surfspot: $rating stars", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAdd.setOnClickListener() {
            surfspot.name = binding.surfspotTitle.text.toString()
            surfspot.observations = binding.description.text.toString()
            surfspot.rating = ratingBar.rating  // Save the rating value
            if (surfspot.name.isEmpty()) {
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
            showImagePicker(imageIntentLauncher,this)
        }

        binding.surfspotLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (surfspot.zoom != 0f) {
                location.lat =  surfspot.lat
                location.lng = surfspot.lng
                location.zoom = surfspot.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_surfspot, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                setResult(99)
                app.surfspots.delete(surfspot)
                finish()
            }        R.id.item_cancel -> { finish() }
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

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            surfspot.image = image

                            Picasso.get()
                                .load(surfspot.image)
                                .into(binding.surfspotImage)
                            binding.chooseImage.setText(R.string.change_surfspot_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            surfspot.lat = location.lat
                            surfspot.lng = location.lng
                            surfspot.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}