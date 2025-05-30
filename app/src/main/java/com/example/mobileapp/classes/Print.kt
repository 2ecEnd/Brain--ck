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
        val result: String

        val executedContent = content.execute()
        if (executedContent is Value.LIST)
        {
            val pattern = "(SnapshotStateList\\(value=)|(\\)@\\d*)".toRegex()
            result = pattern.replace(executedContent.toString(), "")
        }
        else
            result = executedContent.toString()

        console.addString(result)
    }
}