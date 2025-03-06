package com.fdlr.omieteste.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveImageToInternalStorage(context: Context, imageUri: Uri): String? {
    val bitmap: Bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
    
    val directory = context.filesDir
    val file = File(directory, "catalog_image_${System.currentTimeMillis()}.jpg")
    
    try {
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    
    return file.absolutePath
}
