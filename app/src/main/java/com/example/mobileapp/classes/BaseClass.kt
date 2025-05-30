package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp

abstract class Block
{
    abstract var scope: NewScope
    open var parent: Block? = null
    open var selfRect: Rect = Rect.Zero
    var isTroublesome by mutableStateOf(false)

    abstract fun execute(): Any
}

abstract class BinaryExpression : Block()
{
    abstract var leftValue: Block
    open var leftValueRect: Rect = Rect.Zero
    abstract var rightValue: Block
    open var rightValueRect: Rect = Rect.Zero
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
                block.ifBlock.deleteVariable(name)
                block.elseBlock.deleteVariable(name)
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
                block.ifBlock.addVariable(name)
                block.elseBlock.addVariable(name)
            }
        }
    }

    open fun updateDropZones(draggingBlock: Block, pixels: Float)
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
                dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.bottom - pixels,
                    bottom = blockList[i].selfRect.bottom + pixels))
            }
        }
    }
}