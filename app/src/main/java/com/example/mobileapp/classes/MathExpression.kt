package com.example.mobileapp.classes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import kotlin.math.pow

class MathExpression: Block()
{
    var leftValue by mutableStateOf<BlockTemplate>(Constant())
    var rightValue by mutableStateOf<BlockTemplate>(Constant())
    var operation: String = "+"

    override var selfRect: Rect = Rect.Zero
    var leftValueRect: Rect = Rect.Zero
    var rightValueRect: Rect = Rect.Zero

    override fun execute(): Value
    {
        val left = (leftValue.execute()) as Value
        val right = (rightValue.execute()) as Value

        return when
        {
            (left is Value.BOOLEAN || right is Value.BOOLEAN) ->
            {
                throw IllegalArgumentException("Логический тип данных недопустим для математического выражения")
            }
            (left is Value.STRING || right is Value.STRING) ->
            {
                val l = when(left)
                {
                    is Value.STRING -> left.value
                    is Value.INT -> left.value.toString()
                    is Value.DOUBLE -> left.value.toString()
                    else -> throw IllegalArgumentException("Недопустимый тип данных для математического выражения")
                }
                val r = when(right)
                {
                    is Value.STRING -> right.value
                    is Value.INT -> right.value.toString()
                    is Value.DOUBLE -> right.value.toString()
                    else -> throw IllegalArgumentException("Недопустимый тип данных для математического выражения")
                }

                when (operation)
                {
                    "+" -> Value.STRING(l + r)
                    else -> throw IllegalArgumentException("Недопустимая операция для строк")
                }
            }
            (left is Value.INT || left is Value.DOUBLE &&
            right is Value.INT || right is Value.DOUBLE) ->
            {
                if (left is Value.INT && right is Value.INT)
                {
                    when (operation)
                    {
                        "+" -> Value.INT(left.value + right.value)
                        "-" -> Value.INT(left.value - right.value)
                        "*" -> Value.INT(left.value * right.value)
                        "/" -> Value.INT(left.value / right.value)
                        "%" -> Value.INT(left.value % right.value)
                        "^" -> Value.DOUBLE((left.value.toDouble().pow(right.value)))
                        else -> throw IllegalArgumentException("Недопустимая операция")
                    }
                }
                else
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
                    when (operation)
                    {
                        "+" -> Value.DOUBLE(l + r)
                        "-" -> Value.DOUBLE(l - r)
                        "*" -> Value.DOUBLE(l * r)
                        "/" -> Value.DOUBLE(l / r)
                        "%" -> Value.DOUBLE(l % r)
                        "^" -> Value.DOUBLE((l.pow(r)))
                        else -> throw IllegalArgumentException("Недопустимая операция")
                    }
                }
            }
            else -> throw IllegalArgumentException("Недопустимый тип данных для математического выражения")
        }
    }
}