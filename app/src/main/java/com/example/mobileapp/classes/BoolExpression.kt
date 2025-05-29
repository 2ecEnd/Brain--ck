package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mobileapp.R

class BoolExpression(override var scope: NewScope): BinaryExpression()
{
    override var leftValue by mutableStateOf<Block>(Constant(scope, "bool", true))
    override var rightValue by mutableStateOf<Block>(Constant(scope, "bool", true))
    override var operator: String = "=="
    override var parent: Block? = null

    override var selfRect: Rect = Rect.Zero
    override var leftValueRect: Rect = Rect.Zero
    override var rightValueRect: Rect = Rect.Zero

    override fun execute(): Value
    {
        val left = (leftValue.execute()) as Value
        val right = (rightValue.execute()) as Value

        return when
        {
            (left is Value.BOOLEAN && right is Value.BOOLEAN) ->
            {
                val result = when (operator)
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
                    else -> throw Exception(R.string.illegal_operation.toString())
                }
                Value.BOOLEAN(result)
            }
            (left is Value.STRING && right is Value.STRING) ->
            {
                val result = when (operator)
                {
                    "==" -> left.value == right.value
                    "!=" -> left.value != right.value
                    ">" -> left.value > right.value
                    ">=" -> left.value >= right.value
                    "<" -> left.value < right.value
                    "<=" -> left.value <= right.value
                    else -> throw Exception(R.string.illegal_operation.toString())
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
                    else -> throw Exception(R.string.illegal_data_type.toString())
                }
                val r = when (right)
                {
                    is Value.INT -> right.value.toDouble()
                    is Value.DOUBLE -> right.value
                    else -> throw Exception(R.string.illegal_data_type.toString())
                }

                val result = when (operator)
                {
                    "==" -> l == r
                    "!=" -> l != r
                    ">" -> l > r
                    ">=" -> l >= r
                    "<" -> l < r
                    "<=" -> l <= r
                    else -> throw Exception(R.string.illegal_operation.toString())
                }
                Value.BOOLEAN(result)
            }
            else -> throw Exception(R.string.illegal_data_type.toString())
        }
    }
}