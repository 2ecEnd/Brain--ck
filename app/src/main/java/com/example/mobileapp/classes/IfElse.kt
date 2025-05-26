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

        override fun deleteVariable(name: String)
        {
            varList.remove(name)
        }
        override fun addVariable(name: String)
        {
            varList[name] = Value.INT(0)
        }
        override fun updateDropZones(draggingBlock: BlockTemplate)
        {
            dropZones.clear()
            if (blockList.isEmpty())
            {
                dropZones.add(selfRect)
            }
            else
            {
                for(i in blockList.indices)
                {
                    if(blockList[i] == draggingBlock) continue
                    dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.top +
                            ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.75.toFloat()),
                        bottom = blockList[i].selfRect.bottom + ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.25.toFloat())))
                }
            }
        }
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

        override fun deleteVariable(name: String)
        {
            varList.remove(name)
        }
        override fun addVariable(name: String)
        {
            varList.put(name, Value.INT(0))
        }
        override fun updateDropZones(draggingBlock: BlockTemplate)
        {
            dropZones.clear()
            if (blockList.isEmpty())
            {
                dropZones.add(selfRect)
            }
            else
            {
                for(i in blockList.indices)
                {
                    if(blockList[i] == draggingBlock) continue
                    dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.top +
                            ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.75.toFloat()),
                        bottom = blockList[i].selfRect.bottom + ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.25.toFloat())))
                }
            }
        }
        override fun execute() 
        {
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }
}