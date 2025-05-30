package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class SetListElement(
    override var scope: NewScope,
) : Block()
{
    var source: Block = ListConstant(scope)
    var index: Block = Constant(scope)
    var indexRect: Rect = Rect.Zero
    var value by mutableStateOf<Block>(Constant(scope, "int"))
    var valueRect: Rect = Rect.Zero

    override fun execute()
    {
        val indexExecuted = (index.execute() as? Value.INT)
        val valueExecuted = (value.execute() as? Value)
        if (valueExecuted == null || indexExecuted == null)
            throw Exception(R.string.illegal_data_type.toString())

        val sourceExecuted = (source.execute() as? Value.LIST)
            ?: throw Exception(R.string.is_not_list.toString())

        sourceExecuted.value[indexExecuted.value] = valueExecuted
    }
}