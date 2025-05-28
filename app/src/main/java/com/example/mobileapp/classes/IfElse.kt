package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class IfElse(override var scope: ComplexBlock): Block()
{
    var ifRect: Rect = Rect.Zero
    var elseRect: Rect = Rect.Zero
    var conditionRect: Rect = Rect.Zero
    var condition = BoolExpression(scope)
    var if_ = If(scope)
    var else_ = Else(scope)

    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    override fun execute()
    {
        val condRes = condition.execute()
        if (condRes is Value.BOOLEAN)
        {
            if (condRes.value)
                if_.execute()
            else
                else_.execute()
        }
        else
            throw Exception(R.string.illegal_data_type.toString())
    }

    class If(override var scope: ComplexBlock): ComplexBlock()
    {
        override lateinit var spacerPair: MutableState<Pair<Int, ComplexBlock>>
        override var blockList = mutableStateListOf<BlockTemplate>()
        override var dropZones = mutableStateListOf<Rect>()
        override var varList = mutableMapOf<String, Value>()
        override var selfRect: Rect = Rect.Zero
        override var parent: BlockTemplate? = null

        override fun execute()
        {
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }

    class Else(override var scope: ComplexBlock): ComplexBlock()
    {
        override lateinit var spacerPair: MutableState<Pair<Int, ComplexBlock>>
        override var blockList = mutableStateListOf<BlockTemplate>()
        override var dropZones = mutableStateListOf<Rect>()
        override var varList = mutableMapOf<String, Value>()
        override var selfRect: Rect = Rect.Zero
        override var parent: BlockTemplate? = null

        override fun execute() 
        {
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }
}