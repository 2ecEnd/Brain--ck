package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Print(override var scope: ComplexBlock, val console: Console): Block()
{
    var contentRect: Rect = Rect.Zero
    var content: BlockTemplate = Constant(scope)
    override var parent: BlockTemplate? = null
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        console.addString(content.execute().toString())
    }
}