package com.example.mobileapp.classes

import androidx.compose.ui.graphics.Color

class Context : ComplexBlock()
{
    override var blockList: MutableList<BlockTemplate>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var varList: MutableMap<String, Int>
        get() = TODO("Not yet implemented")
        set(value) {}

    fun deleteVariable(name: String)
    {
        varList.remove(name)
    }
    fun addVariable(name: String)
    {
        varList.put(name, 0)
    }
    override fun execute()
    {
        // Нуждается в доработке
        for (i in 0..blockList.size)
        {
            blockList[i].execute()
        }
    }
}