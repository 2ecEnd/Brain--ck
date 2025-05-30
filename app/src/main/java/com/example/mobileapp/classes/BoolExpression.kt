package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.mobileapp.R

class BoolExpression(override var scope: NewScope): BinaryExpression()
{
    override var leftValue by mutableStateOf<Block>(Constant(scope, "bool", true))
    override var rightValue by mutableStateOf<Block>(Constant(scope, "bool", true))
    override var operator: String = "=="

    override fun execute(): Value
    {
        isTroublesome = false

        val executedLeftValue = (leftValue.execute()) as? Value
        val executedRightValue = (rightValue.execute()) as? Value

        return when
        {
            (executedLeftValue is Value.BOOLEAN &&
            executedRightValue is Value.BOOLEAN) ->
            {
                val left = executedLeftValue.value
                val right = executedRightValue.value

                val result = when (operator)
                {
                    "&&", "and" -> left && right
                    "||", "or" -> left || right
                    "->" -> !left || right
                    "==" -> left == right
                    "!=" -> left != right
                    ">" -> left > right
                    ">=" -> left >= right
                    "<" -> left < right
                    "<=" -> left <= right
                    else ->
                    {
                        isTroublesome = true
                        throw Exception(R.string.illegal_operation.toString())
                    }
                }
                Value.BOOLEAN(result)
            }

            (executedLeftValue is Value.STRING &&
            executedRightValue is Value.STRING) ->
            {
                val left = executedLeftValue.value
                val right = executedRightValue.value

                val result = when (operator)
                {
                    "==" -> left == right
                    "!=" -> left != right
                    ">" -> left > right
                    ">=" -> left >= right
                    "<" -> left < right
                    "<=" -> left <= right
                    else ->
                    {
                        isTroublesome = true
                        throw Exception(R.string.illegal_operation.toString())
                    }
                }
                Value.BOOLEAN(result)
            }

            (executedLeftValue is Value.INT || executedLeftValue is Value.DOUBLE &&
            executedRightValue is Value.INT || executedRightValue is Value.DOUBLE) ->
            {
                val left = when (executedLeftValue)
                {
                    is Value.INT -> executedLeftValue.value.toDouble()
                    is Value.DOUBLE -> executedLeftValue.value
                    else ->
                    {
                        isTroublesome = true
                        throw Exception(R.string.illegal_data_type.toString())
                    }
                }
                val right = when (executedRightValue)
                {
                    is Value.INT -> executedRightValue.value.toDouble()
                    is Value.DOUBLE -> executedRightValue.value
                    else ->
                    {
                        isTroublesome = true
                        throw Exception(R.string.illegal_data_type.toString())
                    }
                }

                val result = when (operator)
                {
                    "==" -> left == right
                    "!=" -> left != right
                    ">" -> left > right
                    ">=" -> left >= right
                    "<" -> left < right
                    "<=" -> left <= right
                    else ->
                    {
                        isTroublesome = true
                        throw Exception(R.string.illegal_operation.toString())
                    }
                }
                Value.BOOLEAN(result)
            }

            else ->
            {
                isTroublesome = true
                throw Exception(R.string.illegal_data_type.toString())
            }
        }
    }
}