package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Empty(override var scope: NewScope): Block()
{
    override fun execute()
    {
        println("goyda")
    }
}