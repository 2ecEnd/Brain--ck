package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R
import com.example.mobileapp.classes.For

class UseVariable(override var scope: ComplexBlock): Block()
{
    var name: String = ""
    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    constructor(scope: ComplexBlock, varName: String) : this(scope)
    {
        name = varName
    }

    override fun execute(): Value
    {
        val result: Value? = scope.varList[name]
        return result ?: throw Exception(R.string.null_pointer.toString())
    }
}