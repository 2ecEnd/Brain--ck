package com.example.mobileapp.classes

class BoolExpression: Block()
{
    var leftValue: BlockTemplate = Constant()
    var rightValue: BlockTemplate = Constant()
    var operartion: String = "=="

    override fun execute(): Boolean
    {
        val left = (leftValue.execute()) as Int
        val right = (rightValue.execute()) as Int
        return when(operartion)
        {
            // Для bool (не доработано)
            //"&&", "and" -> left + right
            //"||", "or" -> left - right
            "->" -> left <= right
            "==" -> left == right
            ">" -> left > right
            ">=" -> left >= right
            "<" -> left < right
            "<=" -> left <= right
            else -> throw IllegalArgumentException("Некорректная операция")
        }
    }
}