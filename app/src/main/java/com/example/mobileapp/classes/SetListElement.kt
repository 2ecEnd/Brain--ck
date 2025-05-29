package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class SetListElement(
    override var scope: NewScope,
    val varList: MutableMap<String, Value>
) : Block()
{
    var name: String = ""
    var value by mutableStateOf<Block>(Constant(scope, "int", 0))
    var index: Block = Constant(scope)
    var valueRect: Rect = Rect.Zero
    override var parent: Block? = null
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        val tmpValue = (value.execute() as? Value)
        val tmpIndex = (index.execute() as? Value.INT)
        if (tmpValue == null || tmpIndex == null)
            throw Exception(R.string.illegal_data_type.toString())


        val list = (varList[name] as? Value.LIST)
            ?: throw Exception(R.string.is_not_list.toString())

        list.value[tmpIndex.value] = tmpValue
    }
}