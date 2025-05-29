package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class DeleteListElement(override var scope: ComplexBlock) : Block()
{
    var sourceRect: Rect = Rect.Zero
    var source: BlockTemplate? = null
    var index: BlockTemplate = Constant(scope, "int", 0)
    var indexRect: Rect = Rect.Zero

    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    override fun execute()
    {
        if (source == null)
            throw Exception(R.string.null_pointer.toString())

        val list = (source ?: throw Exception(R.string.null_pointer.toString())).execute()
        if (list !is Value.LIST)
            throw Exception(R.string.is_not_list.toString())

        val tmpIndex = (index.execute()) as? Value.INT ?:
        throw Exception(R.string.illegal_data_type.toString())

        list.value.removeAt(tmpIndex.value)
    }
}