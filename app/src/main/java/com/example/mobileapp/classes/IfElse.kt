package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class IfElse(override var scope: NewScope): Block()
{
    var ifRect: Rect = Rect.Zero
    var elseRect: Rect = Rect.Zero
    var conditionRect: Rect = Rect.Zero
    var condition = BoolExpression(scope)
    var ifBlock = If(scope)
    var elseBlock = Else(scope)

    override fun execute()
    {
        val condRes = condition.execute()
        if (condRes is Value.BOOLEAN)
        {
            if (condRes.value)
                ifBlock.execute()
            else
                elseBlock.execute()
        }
        else
        {
            isTroublesome = true
            throw Exception(R.string.illegal_data_type.toString())
        }
    }


    class If(override var scope: NewScope): NewScope()
    {
        override var blockList = mutableStateListOf<Block>()
        override var allowedVariables = mutableSetOf<String>()
        override var dropZones = mutableStateListOf<Rect>()
        override lateinit var spacerPair: MutableState<Pair<Int, NewScope>>

        override fun execute()
        {
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }

    class Else(override var scope: NewScope): NewScope()
    {
        override var blockList = mutableStateListOf<Block>()
        override var allowedVariables = mutableSetOf<String>()
        override var dropZones = mutableStateListOf<Rect>()
        override lateinit var spacerPair: MutableState<Pair<Int, NewScope>>

        override fun execute() 
        {
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }
}