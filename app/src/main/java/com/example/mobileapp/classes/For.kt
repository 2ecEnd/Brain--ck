package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

class For(override var scope: ComplexBlock): ComplexBlock()
{
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
        try
        {
            startValue.execute()

            while (!(stopCondition.execute() as Value.BOOLEAN).value)
            {
                for (block in blockList)
                    block.execute()

                changeIterableVar.execute()
            }
        }
        catch (e: Exception)
        {
            throw e
        }
    }
}