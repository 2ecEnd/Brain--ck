package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Print(val console: Console): Block()
{
    var contentRect: Rect = Rect.Zero
    var content: BlockTemplate = Constant()
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        console.addString(content.execute().toString())
    }
}