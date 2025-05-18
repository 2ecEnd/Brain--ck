package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class UseVariable(var scope: ComplexBlock): Block()
{
    var name: String = ""
    override var selfRect: Rect = Rect.Zero

    override fun execute(): Value
    {
        var result: Value? = scope.varList[name]
        return if (result == null)
            throw IllegalArgumentException("Ошибка")
        else
            result
    }
}