package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class UseVariable(var scope: ComplexBlock): Block()
{
    var name: String = ""
    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    override fun execute(): Value
    {
        val result: Value? = scope.varList[name]
        return result ?: throw Exception(R.string.null_pointer.toString())
    }
}