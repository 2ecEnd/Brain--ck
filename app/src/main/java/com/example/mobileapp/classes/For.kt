package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.classes.Constant

class For(override var scope: ComplexBlock): ComplexBlock()
{
    var contentRect: Rect = Rect.Zero

    lateinit var iterableVar: DeclareVariable
    lateinit var startValue: SetVariable
    lateinit var stopCondition: BoolExpression
    lateinit var changeIterableVar: MathExpression

    override var varList = mutableMapOf<String, Value>()
    override var blockList = SnapshotStateList<BlockTemplate>()

    override var parent: BlockTemplate? = null
    override var selfRect = Rect.Zero
    override var dropZones = mutableStateListOf<Rect>()
    override lateinit var spacerPair: MutableState<Pair<Int, ComplexBlock>>

    constructor(scope: ComplexBlock, iterableVarName: String) : this(scope)
    {
        iterableVar = DeclareVariable(this)
        iterableVar.name = iterableVarName

        startValue = SetVariable(this)
        startValue.name = iterableVarName

        stopCondition = BoolExpression(scope)
        stopCondition.rightValue = Constant(scope, "int", 10)
        stopCondition.operation = "<"
        stopCondition.leftValue = UseVariable(this, iterableVarName)

        changeIterableVar = MathExpression(scope)
        changeIterableVar.rightValue = Constant(scope, "int", 1)
        changeIterableVar.leftValue = UseVariable(this, iterableVarName)
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
        try
        {
            startValue.execute()

            while ((stopCondition.execute() as Value.BOOLEAN).value)
            {
                for (block in blockList)
                    block.execute()

                varList[iterableVar.name] = changeIterableVar.execute()
            }
        }
        catch (e: Exception)
        {
            throw e
        }
    }
}