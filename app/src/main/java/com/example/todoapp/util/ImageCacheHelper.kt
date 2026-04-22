package com.example.todoapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

fun getCachedBitmap(context: Context, fileName: String): Bitmap? {
    val file = File(context.cacheDir, fileName)
    return if(file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
}

fun saveBitmapToCache(context: Context, fileName: String, byteArray: ByteArray) {
    val file = File(context.cacheDir, fileName)
    file.writeBytes(byteArray)
}