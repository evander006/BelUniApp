package com.example.beluniapp.utils

import android.content.Context


suspend fun saveName(context: Context, name: String){
    val prefs=context.getSharedPreferences("name_pref", Context.MODE_PRIVATE)
    prefs.edit().putString("name_pref",name).apply()
}
suspend fun loadName(context: Context): String{
    val prefs=context.getSharedPreferences("name_pref", Context.MODE_PRIVATE)
    return prefs.getString("name_pref","").toString()
}
suspend fun saveLastname(context: Context, lastname: String){
    val prefs=context.getSharedPreferences("lastname_pref", Context.MODE_PRIVATE)
    prefs.edit().putString("lastname_pref",lastname).apply()
}
suspend fun loadLastname(context: Context): String{
    val prefs=context.getSharedPreferences("lastname_pref", Context.MODE_PRIVATE)
    return prefs.getString("lastname_pref","").toString()
}
suspend fun saveEmail(context: Context, email: String){
    val prefs=context.getSharedPreferences("email_pref", Context.MODE_PRIVATE)
    prefs.edit().putString("email_pref",email).apply()
}
suspend fun loadEmail(context: Context): String{
    val prefs=context.getSharedPreferences("email_pref", Context.MODE_PRIVATE)
    return prefs.getString("email_pref","").toString()
}
suspend fun savePhone(context: Context, phone: String){
    val prefs=context.getSharedPreferences("phone_pref", Context.MODE_PRIVATE)
    prefs.edit().putString("phone_pref",phone).apply()
}
suspend fun loadPhone(context: Context): String{
    val prefs=context.getSharedPreferences("phone_pref", Context.MODE_PRIVATE)
    return prefs.getString("phone_pref","").toString()
}
suspend fun saveGender(context: Context, gender: String){
    val prefs=context.getSharedPreferences("gender_pref", Context.MODE_PRIVATE)
    prefs.edit().putString("gender_pref",gender).apply()
}
suspend fun loadGender(context: Context): String{
    val prefs=context.getSharedPreferences("gender_pref", Context.MODE_PRIVATE)
    return prefs.getString("gender_pref","").toString()
}
fun saveBirthdate(context: Context, birthdateMillis: Long?) {
    val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    prefs.edit().putLong("birthdate_pref", birthdateMillis ?: 0L).apply()
}

fun loadBirthdate(context: Context): Long {
    val prefs = context.getSharedPreferences("profile_prefs", Context.MODE_PRIVATE)
    return prefs.getLong("birthdate_pref", 0L)
}
