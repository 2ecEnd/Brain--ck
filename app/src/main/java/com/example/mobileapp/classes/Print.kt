package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Print(val console: Console, var msg: String): Block()
{
    var content: BlockTemplate = Constant()
    override var selfRect: Rect = Rect.Zero
    override fun execute()
    {
        console.addString(msg)
    }
}