package com.example.mobileapp.classes

class Console
{
    var text = mutableListOf<String>()
    
    fun addString(string: String) = text.add(string)
}