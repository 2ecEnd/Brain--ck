package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class ToDouble(override var scope: NewScope) : Block()
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
                is Value.INT -> Value.DOUBLE(executedValue.value.toDouble())
                is Value.DOUBLE -> executedValue
                is Value.STRING -> Value.DOUBLE(executedValue.value.toDouble())
                is Value.BOOLEAN ->
                {
                    if (executedValue.value) Value.DOUBLE(1.0)
                    else Value.DOUBLE(0.0)
                }
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