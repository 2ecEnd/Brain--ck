package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class UseListElement(override var scope: NewScope) : Block()
{
    var source: Block? = null
    var sourceRect: Rect = Rect.Zero
    var index: Block? = null
    var valueRect: Rect = Rect.Zero

    fun setData(list: Block, index: Block)
    {
        source = list
        this.index = index
    }

    override fun execute(): Value
    {
        val list = (source
            ?: throw Exception(R.string.null_pointer.toString())).execute()
        val index = (index
            ?: throw Exception(R.string.null_pointer.toString())).execute()

        if (list !is Value.LIST) throw Exception(R.string.illegal_data_type.toString())
        if (index !is Value.INT) throw Exception(R.string.index_is_not_int.toString())

        return try
        {
            list.value[index.value]
        }
        catch (e : Exception)
        {
            throw Exception(e.message)
        }
    }
}