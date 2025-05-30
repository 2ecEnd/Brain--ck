package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class UseListElement(override var scope: NewScope) : Block()
{
    var source by mutableStateOf<Block?>(null)
    var sourceRect: Rect = Rect.Zero
    var index by mutableStateOf<Block>(Constant(scope, "int"))
    var indexRect: Rect = Rect.Zero

    fun setData(list: Block, index: Block)
    {
        source = list
        this.index = index
    }

    override fun execute(): Value
    {
        if (source == null)
        {
            isTroublesome = true
            throw Exception(R.string.null_pointer.toString())
        }

        val list = source!!.execute()
        val index = index.execute()

        if (list !is Value.LIST)
        {
            isTroublesome = true
            throw Exception(R.string.illegal_data_type.toString())
        }
        if (index !is Value.INT)
        {
            isTroublesome = true
            throw Exception(R.string.index_is_not_int.toString())
        }

        if (index.value >= list.value.size)
        {
            isTroublesome = true
            throw Exception(R.string.index_was_out_of_range.toString())
        }

        return list.value[index.value]
    }
}