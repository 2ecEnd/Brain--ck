package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class DeleteListElement(override var scope: NewScope) : Block()
{
    var source by mutableStateOf<Block?>(null)
    var sourceRect: Rect = Rect.Zero
    var index by mutableStateOf<Block>(Constant(scope, "int"))
    var indexRect: Rect = Rect.Zero

    override fun execute()
    {
        if (source == null)
            throw Exception(R.string.null_pointer.toString())

        val list = (source ?: throw Exception(R.string.null_pointer.toString())).execute()
        if (list !is Value.LIST)
            throw Exception(R.string.is_not_list.toString())

        val executedIndex = (index.execute()) as? Value.INT
            ?: throw Exception(R.string.illegal_data_type.toString())

        list.value.removeAt(executedIndex.value)
    }
}