package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect

class Print(override var scope: ComplexBlock, val console: Console): Block()
{
    var contentRect: Rect = Rect.Zero
    var content by mutableStateOf<BlockTemplate>(Constant(scope, "string", "str"))
    override var parent: BlockTemplate? = null
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        console.addString(content.execute().toString())
    }
}