package com.example.mobileapp.classes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.mobileapp.R
import kotlin.math.pow

class MathExpression(override var scope: NewScope): BinaryExpression()
{
    override var leftValue by mutableStateOf<Block>(Constant(scope, "int"))
    override var rightValue by mutableStateOf<Block>(Constant(scope, "int"))
    override var operator = "+"

    override fun execute(): Value
    {
        val executedLeftValue = (leftValue.execute()) as? Value
        val executedRightValue = (rightValue.execute()) as? Value

        return when
        {
            (executedLeftValue is Value.BOOLEAN || executedRightValue is Value.BOOLEAN) ->
            {
                throw Exception(R.string.illegal_data_type.toString())
            }
            (executedLeftValue is Value.STRING || executedRightValue is Value.STRING) ->
            {
                val left = when(executedLeftValue)
                {
                    is Value.STRING -> executedLeftValue.value
                    is Value.INT -> executedLeftValue.value.toString()
                    is Value.DOUBLE -> executedLeftValue.value.toString()
                    else -> throw Exception(R.string.illegal_data_type.toString())
                }
                val right = when(executedRightValue)
                {
                    is Value.STRING -> executedRightValue.value
                    is Value.INT -> executedRightValue.value.toString()
                    is Value.DOUBLE -> executedRightValue.value.toString()
                    else -> throw Exception(R.string.illegal_data_type.toString())
                }

                when (operator)
                {
                    "+" -> Value.STRING(left + right)
                    else -> throw Exception(R.string.illegal_operation.toString())
                }
            }
            (executedLeftValue is Value.INT || executedLeftValue is Value.DOUBLE &&
            executedRightValue is Value.INT || executedRightValue is Value.DOUBLE) ->
            {
                if (executedLeftValue is Value.INT && executedRightValue is Value.INT)
                {
                    val left = executedLeftValue.value
                    val right = executedRightValue.value
                    when (operator)
                    {
                        "+" -> Value.INT(left + right)
                        "-" -> Value.INT(left - right)
                        "*" -> Value.INT(left * right)
                        "/" ->
                            {
                                if (right == 0)
                                    throw Exception(R.string.divide_by_zero.toString())
                                else
                                    Value.INT(left/ right)
                            }
                        "%" -> Value.INT(left % right)
                        "^" -> Value.DOUBLE((left.toDouble().pow(right)))
                        else -> throw Exception(R.string.illegal_operation.toString())
                    }
                }
                else
                {
                    val left = when (executedLeftValue)
                    {
                        is Value.INT -> executedLeftValue.value.toDouble()
                        is Value.DOUBLE -> executedLeftValue.value
                        else -> throw Exception(R.string.illegal_data_type.toString())
                    }
                    val right = when (executedRightValue)
                    {
                        is Value.INT -> executedRightValue.value.toDouble()
                        is Value.DOUBLE -> executedRightValue.value
                        else -> throw Exception(R.string.illegal_data_type.toString())
                    }

                    when (operator)
                    {
                        "+" -> Value.DOUBLE(left + right)
                        "-" -> Value.DOUBLE(left - right)
                        "*" -> Value.DOUBLE(left * right)
                        "/" ->
                        {
                            if (right == 0.0)
                                throw Exception(R.string.divide_by_zero.toString())
                            else
                                Value.DOUBLE(left / right)
                        }
                        "%" ->
                        {
                            if (right == 0.0)
                                throw Exception(R.string.divide_by_zero.toString())
                            else
                                Value.DOUBLE(left % right)
                        }
                        "^" -> Value.DOUBLE((left.pow(right)))
                        else -> throw Exception(R.string.illegal_operation.toString())
                    }
                }
            }
            else -> throw Exception(R.string.illegal_data_type.toString())
        }
    }
}