package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class SetVariable(
    override var scope: NewScope,
    val varList: MutableMap<String, Value>
) : Block()
{
    var name: String = ""
    var value by mutableStateOf<Block>(Constant(scope, "int"))
    var valueRect: Rect = Rect.Zero

    override fun execute()
    {
        val tmp = value.execute()
        varList[name] = when (tmp)

        {
            is Value -> tmp
            else ->
            {
                isTroublesome = true
                throw Exception(R.string.illegal_data_type.toString())
            }
        }
    }
}