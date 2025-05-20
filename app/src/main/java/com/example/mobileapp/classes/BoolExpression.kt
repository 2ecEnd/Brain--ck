package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class BoolExpression(): Block()
{
    var leftValue: BlockTemplate = Constant()
    var rightValue: BlockTemplate = Constant()
    var operation: String = "=="
    override var parent: BlockTemplate? = null

    override var selfRect: Rect = Rect.Zero
    var leftValueRect: Rect = Rect.Zero
    var rightValueRect: Rect = Rect.Zero

    override fun execute(): Value
    {
        val left = (leftValue.execute()) as Value
        val right = (rightValue.execute()) as Value

        return when
        {
            (left is Value.BOOLEAN && right is Value.BOOLEAN) ->
            {
                val result = when (operation)
                {
                    "&&", "and" -> left.value && right.value
                    "||", "or" -> left.value || right.value
                    "->" -> !left.value || right.value
                    "==" -> left.value == right.value
                    "!=" -> left.value != right.value
                    ">" -> left.value > right.value
                    ">=" -> left.value >= right.value
                    "<" -> left.value < right.value
                    "<=" -> left.value <= right.value
                    else -> throw IllegalArgumentException("Некорректная операция")
                }
                Value.BOOLEAN(result)
            }
            (left is Value.STRING && right is Value.STRING) ->
            {
                val result = when (operation)
                {
                    "==" -> left.value == right.value
                    "!=" -> left.value != right.value
                    ">" -> left.value > right.value
                    ">=" -> left.value >= right.value
                    "<" -> left.value < right.value
                    "<=" -> left.value <= right.value
                    else -> throw IllegalArgumentException("Некорректная операция")
                }
                Value.BOOLEAN(result)
            }
            (left is Value.INT || left is Value.DOUBLE &&
            right is Value.INT || right is Value.DOUBLE) ->
            {
                val l = when (left)
                {
                    is Value.INT -> left.value.toDouble()
                    is Value.DOUBLE -> left.value
                    else -> throw IllegalArgumentException("Недопустимый тип данных для математического выражения")
                }
                val r = when (right)
                {
                    is Value.INT -> right.value.toDouble()
                    is Value.DOUBLE -> right.value
                    else -> throw IllegalArgumentException("Недопустимый тип данных для математического выражения")
                }

                val result = when (operation)
                {
                    "==" -> l == r
                    "!=" -> l != r
                    ">" -> l > r
                    ">=" -> l >= r
                    "<" -> l < r
                    "<=" -> l <= r
                    else -> throw IllegalArgumentException("Некорректная операция")
                }
                Value.BOOLEAN(result)
            }
            else -> throw IllegalArgumentException("Ошибка")
        }
    }
}