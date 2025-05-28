package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

abstract class BlockTemplate
{
    abstract fun execute(): Any
    abstract var selfRect: Rect
    abstract var parent: BlockTemplate?
    abstract var scope: ComplexBlock
}

abstract class Block: BlockTemplate()

abstract class ComplexBlock: BlockTemplate()
{
    // Список блоков, которые будет содержать данный блок
    abstract var blockList: SnapshotStateList<BlockTemplate>
    // Список переменных, доступных в области видимости данного блока
    abstract var varList: MutableMap<String, Value>
    abstract var dropZones: SnapshotStateList<Rect>
    abstract var spacerPair: MutableState<Pair<Int, ComplexBlock>>

    fun deleteVariable(name: String)
    {
        varList.remove(name)
    }

    fun addVariable(name: String)
    {
        varList[name] = Value.INT(0)
    }

    open fun updateDropZones(draggingBlock: BlockTemplate)
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

    abstract override fun execute(): Any
}