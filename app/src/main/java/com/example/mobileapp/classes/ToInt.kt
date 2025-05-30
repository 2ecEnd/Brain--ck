package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class ToInt(override var scope: NewScope) : Block()
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
                is Value.INT -> executedValue
                is Value.DOUBLE -> Value.INT(executedValue.value.toInt())
                is Value.STRING ->
                {
                    try
                    {
                        Value.INT(executedValue.value.toInt())
                    }
                    catch (e:Exception)
                    {
                        isTroublesome = true
                        throw Exception(R.string.word_to_number.toString())
                    }
                }
                is Value.BOOLEAN ->
                {
                    if (executedValue.value) Value.INT(1)
                    else Value.INT(0)
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