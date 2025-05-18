package com.example.mobileapp.classes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect

class SetVariable(var scope: ComplexBlock) : Block()
{
    var name: String = ""
    var value by mutableStateOf<BlockTemplate>(Constant())
    var valueRect: Rect = Rect.Zero
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        // Нуждается в доработке
        val tmp = value.execute()
        scope.varList[name] = when (tmp)
        {
            is Value -> tmp
            else -> throw IllegalArgumentException("Ошибка")
        }
    }
}