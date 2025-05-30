package com.example.mobileapp.classes

import com.example.mobileapp.R

class ToInt(override var scope: NewScope) : Block()
{
    var source: Block = Constant(scope)

    override fun execute(): Value
    {
        isTroublesome = false

        try
        {
            return when (val executedValue = (source.execute()) as? Value)
            {
                is Value.INT -> executedValue
                is Value.DOUBLE -> Value.INT(executedValue.value.toInt())
                is Value.STRING -> Value.INT(Integer.parseInt(executedValue.value))
                is Value.BOOLEAN ->
                {
                    if (executedValue.value) Value.INT(1)
                    else Value.INT(0)
                }
                else ->
                {
                    isTroublesome = true
                    throw Exception(R.string.illegal_data_type.toString())
                }
            }
        }
        catch (e: Exception)
        {
            isTroublesome = true
            throw e
        }
    }
}