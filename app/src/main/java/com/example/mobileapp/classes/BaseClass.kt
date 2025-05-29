package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

abstract class Block
{
    abstract var scope: NewScope
    abstract var parent: Block?
    abstract var selfRect: Rect

    abstract fun execute(): Any
}

abstract class BinaryExpression : Block()
{
    abstract var leftValue: Block
    abstract var leftValueRect: Rect
    abstract var rightValue: Block
    abstract var rightValueRect: Rect
    abstract var operator: String
}

abstract class NewScope : Block()
{
    abstract var blockList: SnapshotStateList<Block>
    abstract var allowedVariables: MutableSet<String>
    abstract var dropZones: SnapshotStateList<Rect>
    abstract var spacerPair: MutableState<Pair<Int, NewScope>>

    open fun deleteVariable(name: String)
    {
        allowedVariables.remove(name)

        for (block in blockList)
        {
            if (block is NewScope)
                block.deleteVariable(name)
            else if (block is IfElse)
            {
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
            if (block is NewScope)
                block.addVariable(name)
            else if (block is IfElse){
                block.if_.addVariable(name)
                block.else_.addVariable(name)
            }
        }
    }

    open fun updateDropZones(draggingBlock: Block)
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
}