package com.example.mobileapp.classes

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class Context(val resources: Resources, val console: Console) : ComplexBlock()
{
    override var scope: ComplexBlock = this
    override lateinit var spacerPair: MutableState<Pair<Int, ComplexBlock>>
    override var dropZones = mutableStateListOf<Rect>()
    override var allowedVariables = mutableListOf<String>()
    var varList = mutableMapOf<String, Value>()
    override var blockList = mutableStateListOf<BlockTemplate>(DeclareVariable(this, varList))
    override var parent: BlockTemplate? = null

    override var selfRect: Rect = Rect.Zero

    override fun deleteVariable(name: String)
    {
        varList.remove(name)
        super.deleteVariable(name)
    }

    override fun addVariable(name: String)
    {
        varList[name] = Value.INT(0)
        super.addVariable(name)
    }

    override fun updateDropZones(draggingBlock: BlockTemplate)
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
        for (i in 0..<blockList.size )
        {
            try
            {
                blockList[i].execute()
            }
            catch (e: Exception)
            {
                if (e.message == null)
                {
                    try
                    {
                        console.addString(resources.getString(R.string.unknown_error))
                    }
                    catch (e: Exception)
                    {
                        println(e.message)
                    }
                }
                else
                {
                    try
                    {
                        console.addString(resources.getString(e.message!!.toInt()))
                    }
                    catch (e: Exception)
                    {
                        println(e.message)
                    }
                }
            }

        }
    }
}