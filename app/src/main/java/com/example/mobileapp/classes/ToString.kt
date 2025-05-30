package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class ToString(override var scope: NewScope) : Block()
{
    var source by mutableStateOf<Block>(Constant(scope, "int"))
    var sourceRect: Rect = Rect.Zero

    override fun execute(): Value
    {
        isTroublesome = false

        try
        {
            return when (val executedValue = (source.execute()) as? Value)
            {
                is Value.INT -> Value.STRING(executedValue.value.toString())
                is Value.DOUBLE -> Value.STRING(executedValue.value.toString())
                is Value.STRING -> executedValue
                is Value.BOOLEAN -> Value.STRING(executedValue.value.toString())
                else ->
                {
                    isTroublesome = true
                    throw Exception(R.string.illegal_data_type.toString())
                }
            }
        }
        catch (e: Exception)
        {
            isTroublesome = true
            throw e
        }
    }
}