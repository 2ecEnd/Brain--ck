package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Empty(override var scope: NewScope): Block()
{
    override var selfRect: Rect = Rect.Zero
    override var parent: Block? = null

    override fun execute()
    {
        println("goyda")
    }
}