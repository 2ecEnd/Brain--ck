package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

class While(
    override var scope: NewScope,
    private var varList: MutableMap<String, Value>
): NewScope()
{
    var contentRect: Rect = Rect.Zero
    var stopConditionRect: Rect = Rect.Zero
    var stopCondition: BoolExpression = BoolExpression(scope)

    override var allowedVariables = mutableSetOf<String>()
    override var blockList = SnapshotStateList<Block>()
    override var dropZones = mutableStateListOf<Rect>()
    override lateinit var spacerPair: MutableState<Pair<Int, NewScope>>

    init
    {
        stopCondition.rightValue = Constant(scope, "int", 10)
        stopCondition.operator = "<"
        stopCondition.leftValue = UseVariable(this, varList)
        (stopCondition.leftValue as UseVariable).name = "i"
    }

    override fun updateDropZones(draggingBlock: Block, pixels: Float)
    {
        dropZones.clear()
        if (blockList.isEmpty())
        {
            dropZones.add(contentRect)
        }
        else
        {
            for(i in blockList.indices)
            {
                if(blockList[i] == draggingBlock) continue
                dropZones.add(blockList[i].selfRect.copy(
                    top = blockList[i].selfRect.bottom - pixels,
                    bottom = blockList[i].selfRect.bottom + pixels)
                )
            }
        }
    }

    override fun execute()
    {
        isTroublesome = false

        while ((stopCondition.execute() as Value.BOOLEAN).value)
        {
            for (block in blockList)
                block.execute()
        }
    }
}