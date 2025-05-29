package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect

class Print(override var scope: NewScope, private val console: Console): Block()
{
    var content by mutableStateOf<Block>(Constant(scope, "string"))
    var contentRect: Rect = Rect.Zero

    override fun execute()
    {
        console.addString(content.execute().toString())
    }
}