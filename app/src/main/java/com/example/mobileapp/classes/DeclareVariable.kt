package com.example.mobileapp.classes

class DeclareVariable(var scope: ComplexBlock) : Block()
{
    var name: String = ""
    var value: Int = 0

    override fun execute()
    {
        // Нуждается в доработке
        scope.varList.plus((name to value))
    }
}