package com.example.mobileapp.classes

class IfElse: Block()
{
    class If: ComplexBlock()
    {
        override var blockList = mutableListOf<BlockTemplate>()
        override var varList = mutableMapOf<String, Int>()

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
        override var varList = mutableMapOf<String, Int>()

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
        if (condition.execute())
            if_.execute()
        else
            else_.execute()
    }
}