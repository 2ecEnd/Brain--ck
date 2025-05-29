package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

class For(
    override var scope: ComplexBlock,
    var varList: MutableMap<String, Value>
): ComplexBlock()
{
    var contentRect: Rect = Rect.Zero

    private lateinit var iterableVar: DeclareVariable
    private lateinit var startValue: SetVariable
    private lateinit var stopCondition: BoolExpression
    private lateinit var changeIterableVar: MathExpression

    override var allowedVariables = mutableSetOf<String>()
    override var blockList = SnapshotStateList<BlockTemplate>()

    override var parent: BlockTemplate? = null
    override var selfRect = Rect.Zero
    override var dropZones = mutableStateListOf<Rect>()
    override lateinit var spacerPair: MutableState<Pair<Int, ComplexBlock>>

    constructor(
        scope: ComplexBlock,
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
        stopCondition.operation = "<"
        stopCondition.leftValue = UseVariable(this, varList)
        (stopCondition.leftValue as UseVariable).name = "i"

        changeIterableVar = MathExpression(scope)
        changeIterableVar.rightValue = Constant(scope, "int", 1)
        changeIterableVar.leftValue = UseVariable(this, varList)
        (changeIterableVar.leftValue as UseVariable).name = "i"
    }


    //-=-=-=-=-=-Setters-=-=-=-=-=-
    fun setIterableVar(newIterableVar: DeclareVariable)
    {
        iterableVar = newIterableVar
    }
    fun setStartValue(newStartValue: SetVariable)
    {
        startValue = newStartValue
    }
    fun setStopCondition(newStopCondition: BoolExpression)
    {
        stopCondition = newStopCondition
    }
    fun setChangeIterableVar(newChangeIterableVar: MathExpression)
    {
        changeIterableVar = newChangeIterableVar
    }
    //-=-=-=-=-=-Getters-=-=-=-=-=-
    fun getIterableVar() = iterableVar
    fun getStartValue() = startValue
    fun getStopCondition() = stopCondition
    fun getChangeIterableVar() = changeIterableVar


    override fun updateDropZones(draggingBlock: BlockTemplate)
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