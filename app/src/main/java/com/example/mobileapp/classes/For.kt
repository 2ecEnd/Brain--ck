package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

class For(
    override var scope: NewScope,
    var varList: MutableMap<String, Value>
): NewScope()
{
    var contentRect: Rect = Rect.Zero
    lateinit var iterableVar: DeclareVariable
    lateinit var startValue: SetVariable
    lateinit var stopCondition: BoolExpression
    lateinit var changeIterableVar: MathExpression

    override var allowedVariables = mutableSetOf<String>()
    override var blockList = SnapshotStateList<Block>()
    override var dropZones = mutableStateListOf<Rect>()
    override lateinit var spacerPair: MutableState<Pair<Int, NewScope>>

    constructor(
        scope: NewScope,
        varList: MutableMap<String, Value>,
        iterableVarName: String
    ) : this(scope, varList)
    {
        iterableVar = DeclareVariable(this, varList)
        iterableVar.name = iterableVarName

        startValue = SetVariable(this, varList)
        startValue.name = iterableVarName

        stopCondition = BoolExpression(scope)
        stopCondition.rightValue = Constant(scope, "int", 10)
        stopCondition.operator = "<"
        stopCondition.leftValue = UseVariable(this, varList)
        (stopCondition.leftValue as UseVariable).name = "i"

        changeIterableVar = MathExpression(scope)
        changeIterableVar.rightValue = Constant(scope, "int", 1)
        changeIterableVar.leftValue = UseVariable(this, varList)
        (changeIterableVar.leftValue as UseVariable).name = "i"
    }

    override fun updateDropZones(draggingBlock: Block)
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
                dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.top +
                        ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.75.toFloat()),
                    bottom = blockList[i].selfRect.bottom + ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.25.toFloat())))
            }
        }
    }

    override fun execute()
    {
        isTroublesome = false

        iterableVar.execute()
        startValue.execute()

        while ((stopCondition.execute() as Value.BOOLEAN).value)
        {
            for (block in blockList)
                block.execute()

            println(iterableVar.name + ": " + varList[iterableVar.name])
            varList[iterableVar.name] = changeIterableVar.execute()
        }
    }
}