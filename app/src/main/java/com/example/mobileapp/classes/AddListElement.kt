package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class AddListElement(override var scope: NewScope) : Block()
{
    var source: Block? = null
    var sourceRect: Rect = Rect.Zero
    var value by mutableStateOf<Block>(Constant(scope, "int"))
    var valueRect: Rect = Rect.Zero

    override fun execute()
    {
        val executedValue = (value.execute()) as? Value
            ?: throw Exception(R.string.null_pointer.toString())
        val list = (source
            ?: throw Exception(R.string.null_pointer.toString())).execute()

        if (list !is Value.LIST)
            throw Exception(R.string.is_not_list.toString())

        list.value.add(executedValue)
    }
}