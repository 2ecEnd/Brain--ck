package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class UseListElement(override var scope: ComplexBlock) : Block()
{
    var sourceRect: Rect = Rect.Zero
    var source: BlockTemplate? = null
    var index_: BlockTemplate? = null

    override var parent: BlockTemplate? = null
    override var selfRect: Rect = Rect.Zero

    fun setData(list: BlockTemplate, index: BlockTemplate)
    {
        source = list
        index_ = index
    }

    override fun execute(): Value
    {
        val list = (source ?: throw Exception(R.string.null_pointer.toString())).execute()
        val index = (index_ ?: throw Exception(R.string.null_pointer.toString())).execute()

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