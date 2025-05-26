package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class DeleteListElement(override var scope: ComplexBlock) : Block()
{
    var source: String? = null
    var index: Int = 0

    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    override fun execute()
    {
        if (source == null)
            throw Exception(R.string.null_pointer.toString())

        val list = scope.varList[source!!]
        if (list !is Value.LIST)
            throw Exception(R.string.is_not_list.toString())

        list.value.removeAt(index)
    }
}