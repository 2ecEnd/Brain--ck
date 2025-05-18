package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Empty: Block()
{
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        println("goyda")
    }
}