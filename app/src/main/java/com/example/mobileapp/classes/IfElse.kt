package com.example.mobileapp.classes

class IfElse: Block()
{
    class If: ComplexBlock()
    {
        override var blockList = mutableListOf<BlockTemplate>()
        override var varList = mutableMapOf<String, Value>()

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
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }

    class Else: ComplexBlock()
    {
        override var blockList = mutableListOf<BlockTemplate>()
        override var varList = mutableMapOf<String, Value>()

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
            for (i in 0..<blockList.size)
            {
                blockList[i].execute()
            }
        }
    }

    var condition = BoolExpression()
    var if_ = If()
    var else_ = Else()

    override fun execute()
    {
        val condRes = condition.execute()
        if (condRes is Value.BOOLEAN)
        {
            if (condRes.value)
                if_.execute()
            else
                else_.execute()
        }
        else
            throw IllegalArgumentException("Ошибка")
    }
}