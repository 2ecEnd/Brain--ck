package com.example.mobileapp.classes

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class Context(
    private val resources: Resources,
    val console: Console
) : NewScope()
{
    override var scope: NewScope = this
    override lateinit var spacerPair: MutableState<Pair<Int, NewScope>>
    override var dropZones = mutableStateListOf<Rect>()
    override var allowedVariables = mutableSetOf<String>()
    var varList = mutableMapOf<String, Value>()
    override var blockList = mutableStateListOf<Block>(DeclareVariable(this, varList))

    override fun deleteVariable(name: String)
    {
        varList.remove(name)
        allowedVariables.remove(name)

        for (block in blockList)
        {
            if (block is NewScope)
                block.deleteVariable(name)
            else if (block is IfElse){
                block.ifBlock.deleteVariable(name)
                block.elseBlock.deleteVariable(name)
            }
        }
    }

    override fun addVariable(name: String)
    {
        varList[name] = Value.INT(0)
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

    override fun updateDropZones(draggingBlock: Block)
    {
        dropZones.clear()
        for(i in blockList.indices)
        {
            if(blockList[i] == draggingBlock) continue
            if (i != blockList.count() - 1)
            {
                dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.top +
                        ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.75.toFloat()),
                    bottom = blockList[i].selfRect.bottom + ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.25.toFloat())))
            }
            else
            {
                dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.bottom,
                    bottom = blockList[i].selfRect.bottom + (blockList[i].selfRect.bottom-blockList[i].selfRect.top)))
            }
        }
    }

    override fun execute()
    {
        try
        {
            for (i in 0..<blockList.size )
            {
                blockList[i].execute()
            }
        }
        catch (e: Exception)
        {
            if (e.message == null)
            {
                console.addString(resources.getString(R.string.unknown_error))
                println(e.message)
            }
            else
            {
                console.addString(resources.getString(e.message!!.toInt()))
                println(e.message)
            }
        }
    }
}