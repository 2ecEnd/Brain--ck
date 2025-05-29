package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
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
    abstract var allowedVariables: MutableSet<String>
    abstract var dropZones: SnapshotStateList<Rect>
    abstract var spacerPair: MutableState<Pair<Int, ComplexBlock>>

    open fun deleteVariable(name: String)
    {
        allowedVariables.remove(name)

        for (block in blockList)
        {
            if (block is ComplexBlock)
                block.deleteVariable(name)
            else if (block is IfElse){
                block.if_.deleteVariable(name)
                block.else_.deleteVariable(name)
            }
        }
    }

    open fun addVariable(name: String)
    {
        allowedVariables.add(name)

        for (block in blockList)
        {
            if (block is ComplexBlock)
                block.addVariable(name)
            else if (block is IfElse){
                block.if_.addVariable(name)
                block.else_.addVariable(name)
            }
        }
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