package com.example.mobileapp.classes

class Context : ComplexBlock()
{
    override var blockList = mutableListOf<BlockTemplate>()
    override var varList = mutableMapOf<String, Value>()

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