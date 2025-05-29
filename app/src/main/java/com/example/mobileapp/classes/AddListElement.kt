package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class AddListElement(override var scope: ComplexBlock) : Block()
{
    var sourceRect: Rect = Rect.Zero
    var source: BlockTemplate? = null
    var value by mutableStateOf<BlockTemplate>(Constant(scope, "int", 0))
    var valueRect: Rect = Rect.Zero

    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    override fun execute()
    {
        val tmp = (value.execute()) as? Value
        if (tmp == null || source == null)
            throw Exception(R.string.null_pointer.toString())

        val list = (source ?: throw Exception(R.string.null_pointer.toString())).execute()
        if (list !is Value.LIST)
            throw Exception(R.string.is_not_list.toString())

        list.value.add(tmp)
    }
}