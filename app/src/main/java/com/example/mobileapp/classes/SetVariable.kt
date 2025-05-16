package com.example.mobileapp.classes

class SetVariable(var scope: ComplexBlock) : Block()
{
    var name: String = ""
    var value = 0

    override fun execute()
    {
        // Нуждается в доработке
        scope.varList[name] = value
    }
}