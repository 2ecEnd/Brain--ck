package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateListOf

class Console
{
    var text = mutableStateListOf <String>()
    
    fun addString(string: String) = text.add(string)
}