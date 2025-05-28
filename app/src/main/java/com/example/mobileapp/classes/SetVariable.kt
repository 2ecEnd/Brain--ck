package com.example.mobileapp.classes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class SetVariable(
    override var scope: ComplexBlock,
    val varList: MutableMap<String, Value>
) : Block()
{
    var name: String = ""
    var value by mutableStateOf<BlockTemplate>(Constant(scope, "int", 0))
    var valueRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null
    override var selfRect: Rect = Rect.Zero

    override fun execute()
    {
        val tmp = value.execute()
        varList[name] = when (tmp)
        {
            is Value -> tmp
            else -> throw Exception(R.string.illegal_data_type.toString())
        }
    }
}