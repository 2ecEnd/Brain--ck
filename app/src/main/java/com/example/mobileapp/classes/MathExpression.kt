package com.example.mobileapp.classes

class MathExpression: Block()
{
    var leftValue: BlockTemplate = Constant()
    var rightValue: BlockTemplate = Constant()
    var operartion: String = "+"

    override fun execute(): Int
    {
        var left = (leftValue.execute()) as Int
        var right = (rightValue.execute()) as Int

        return when(operartion)
        {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> left / right
            "%" -> left % right
            else -> throw IllegalArgumentException("Ошибка")
        }
    }
}