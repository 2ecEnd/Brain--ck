package com.example.mobileapp.classes

import com.example.mobileapp.R

class ToBoolean(override var scope: NewScope) : Block()
{
    var source: Block = Constant(scope)

    override fun execute(): Value
    {
        isTroublesome = false

        try
        {
            return when (val executedValue = (source.execute()) as? Value)
            {
                is Value.INT ->
                {
                    if (executedValue.value != 0)
                        Value.BOOLEAN(true)
                    else
                        Value.BOOLEAN(false)
                }
                is Value.DOUBLE ->
                {
                    if (executedValue.value != 0.0)
                        Value.BOOLEAN(true)
                    else
                        Value.BOOLEAN(false)
                }
                is Value.STRING ->
                {
                    if (executedValue.value.length != 0)
                        Value.BOOLEAN(true)
                    else
                        Value.BOOLEAN(false)
                }
                is Value.BOOLEAN -> executedValue
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