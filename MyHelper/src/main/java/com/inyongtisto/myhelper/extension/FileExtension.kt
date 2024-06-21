package com.inyongtisto.myhelper.extension

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun File?.toMultipartBody(name: String = "image"): MultipartBody.Part? {
    if (this == null) return null
    val reqFile: RequestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, this.name, reqFile)
}

fun File?.toAudioMultipartBody(name: String = "file"): MultipartBody.Part? {
    if (this == null) return null
    val reqFile: RequestBody = this.asRequestBody("audio/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, this.name, reqFile)
}

fun File?.toPdfMultipartBody(name: String = "file"): MultipartBody.Part? {
    if (this == null) return null
    val reqFile: RequestBody = this.asRequestBody("application/pdf".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, this.name, reqFile)
}

fun saveViewToFileImage(view: View, file: File): File {
    // Measure the view at its exact dimensions
    val width = view.width
    val height = view.height

    // Create a bitmap backed by an array of pixels
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    // Associate a canvas with the bitmap
    val canvas = Canvas(bitmap)

    // Draw the view on the canvas
    view.draw(canvas)

    // Now we can save the bitmap to a file
    var outputStream: FileOutputStream? = null
    try {
        outputStream = FileOutputStream(file)
        // Compress the bitmap, write it to the output stream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            outputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Return the file
    return file
}

fun saveViewAsBitmap(view: View): Bitmap {
    // Measure the view at its exact dimensions
    val width = view.width
    val height = view.height

    // Create a bitmap backed by an array of pixels
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    // Associate a canvas with the bitmap
    val canvas = Canvas(bitmap)

    // Draw the view on the canvas
    view.draw(canvas)

    // Return the bitmap
    return bitmap
}

fun saveBitmapToFileExplorer(imageToSave: Bitmap, fileName: String, pathLocation: String? = null) {
    var savePath = getDownloadDirectory() + "/"
    if (pathLocation != null) {
        savePath = pathLocation
    }

    val direct = File(savePath)
    if (!direct.exists()) {
        val directory = File(savePath)
        directory.mkdirs()
    }
    val file = File(savePath, fileName)
    if (file.exists()) {
        file.delete()
    }
    try {
        val out = FileOutputStream(file)
        imageToSave.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
        out.close()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun getExternalStoragePublicDirectory(): String {
    val downloadDirectory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    val folder = File(downloadDirectory)
    if (!folder.mkdirs()) {
        folder.mkdirs()
    }
    return folder.absolutePath
}

fun getDownloadDirectory(): String {
    return getExternalStoragePublicDirectory()
}

private fun Activity.saveFile(imageToSave: Bitmap, pathLocation: String, fileName: String) {
    saveBitmapToFileExplorer(imageToSave, pathLocation, fileName)
}
