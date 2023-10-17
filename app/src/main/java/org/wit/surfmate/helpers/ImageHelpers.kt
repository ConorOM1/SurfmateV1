package org.wit.surfmate

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_surfspot_image.toString())
    intentLauncher.launch(chooseFile)
}