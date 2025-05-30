package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class AddListElement(override var scope: NewScope) : Block()
{
    var source by mutableStateOf<Block?>(null)
    var sourceRect: Rect = Rect.Zero
    var value by mutableStateOf<Block>(Constant(scope, "int"))
    var valueRect: Rect = Rect.Zero

    override fun execute()
    {
        isTroublesome = false

        try
        {
            val executedValue = (value.execute()) as? Value
            if (executedValue == null)
            {
                isTroublesome = true
                throw Exception(R.string.null_pointer.toString())
            }

            if (source == null)
            {
                isTroublesome = true
                throw Exception(R.string.null_pointer.toString())
            }
            val list = source!!.execute()

            if (list !is Value.LIST)
            {
                isTroublesome = true
                throw Exception(R.string.is_not_list.toString())
            }

            list.value.add(executedValue)
        }
        catch (e: Exception)
        {
            isTroublesome = true
            throw e
        }
    }
}