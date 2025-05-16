package com.example.mobileapp.classes

class Constant : Block()
{
    var value = Value.INT(0)

    // Нуждается в доработке
    override fun execute(): Value = value
}