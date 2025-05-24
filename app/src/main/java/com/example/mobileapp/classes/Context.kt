package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Context : ComplexBlock()
{
    override var blockList = mutableListOf<BlockTemplate>()
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
    override fun execute()
    {
        // Нуждается в доработке
        for (i in 0..<blockList.size )
        {
            try
            {
                blockList[i].execute()
            }
            catch (e: Exception)
            {
                println(e.message)
            }

        }
    }
}