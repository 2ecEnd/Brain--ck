package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class AddListElement(override var scope: ComplexBlock) : Block()
{
    var source: String? = null
    var value: Block = Constant(scope)

    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    override fun execute()
    {
        val tmp = (value.execute()) as? Value
        if (tmp == null || source == null)
            throw Exception(R.string.null_pointer.toString())

        scope.varList[source!!] = tmp
    }
}