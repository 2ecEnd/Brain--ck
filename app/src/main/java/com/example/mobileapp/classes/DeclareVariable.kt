package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect

class DeclareVariable(
    override var scope: NewScope,
    val varList: MutableMap<String, Value>
) : Block()
{
    var name by mutableStateOf<String>("my variable")
    var value = Value.INT(0)
    override var selfRect: Rect = Rect.Zero
    override var parent: Block? = null

    override fun execute()
    {
        // Нуждается в доработке
        varList[name] = value
    }
}