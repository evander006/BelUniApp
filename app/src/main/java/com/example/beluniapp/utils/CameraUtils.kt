package com.example.beluniapp.utils


import android.content.Context
import android.net.Uri
import java.io.File


suspend fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return ""
    val fileName = "profile_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName) //Мы формируем уникальное имя файла и создаём File в папке внутреннего хранилища приложения
    file.outputStream().use { output -> //use закроет поток после завершения
        inputStream.copyTo(output)
    }
    return file.absolutePath // сохраним этот путь
}
fun saveImgPath(context: Context, path: String){
    val prefs=context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    prefs.edit().putString("profile_prefs",path).apply()
}
fun loadImgPath(context: Context): String{
    val prefs=context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    return prefs.getString("profile_prefs",null).toString()
}