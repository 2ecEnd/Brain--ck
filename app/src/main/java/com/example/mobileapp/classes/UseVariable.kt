package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class UseVariable(
    override var scope: NewScope,
    val varList: MutableMap<String, Value>
) : Block()
{
    var name: String = ""
    override var selfRect: Rect = Rect.Zero
    override var parent: Block? = null

    constructor(scope: NewScope, varList: MutableMap<String, Value>, varName: String) : this(scope, varList)
    {
        name = varName
    }

    override fun execute(): Value
    {
        val result: Value? = varList[name]
        return result ?: throw Exception(R.string.null_pointer.toString())
    }
}