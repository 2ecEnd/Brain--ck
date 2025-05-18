package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class SetVariable(var scope: ComplexBlock) : Block()
{
    var name: String = ""
    var value = Value.INT(0)
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        // Нуждается в доработке
        scope.varList[name] = value
    }
}