package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect

class Context : ComplexBlock()
{
    override lateinit var spacerPair: MutableState<Pair<Int, ComplexBlock>>
    override var blockList = mutableStateListOf<BlockTemplate>(DeclareVariable(this))
    override var dropZones = mutableStateListOf<Rect>()
    override var varList = mutableMapOf<String, Value>()
    override var parent: BlockTemplate? = null

    override var selfRect: Rect = Rect.Zero

    override fun deleteVariable(name: String)
    {
        varList.remove(name)
    }
    override fun addVariable(name: String)
    {
        varList.put(name, Value.INT(0))
    }
    override fun updateDropZones(draggingBlock: BlockTemplate){
        dropZones.clear()
        for(i in blockList.indices){
            if(blockList[i] == draggingBlock) continue
            if (i != blockList.count()-1){
                dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.top +
                        ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.75.toFloat()),
                    bottom = blockList[i].selfRect.bottom + ((blockList[i].selfRect.bottom-blockList[i].selfRect.top)*0.25.toFloat())))
            }
            else{
                dropZones.add(blockList[i].selfRect.copy(top = blockList[i].selfRect.bottom,
                    bottom = blockList[i].selfRect.bottom + (blockList[i].selfRect.bottom-blockList[i].selfRect.top)))
            }
        }
    }
    override fun execute()
    {
        // Нуждается в доработке
        for (i in 0..<blockList.size )
        {
            blockList[i].execute()
        }
    }
}