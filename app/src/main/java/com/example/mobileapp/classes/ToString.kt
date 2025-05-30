package com.example.mobileapp.classes

import com.example.mobileapp.R

class ToString(override var scope: NewScope) : Block()
{
    var source: Block = Constant(scope)

    override fun execute(): Value
    {
        isTroublesome = false

        try
        {
            return when (val executedValue = (source.execute()) as? Value)
            {
                is Value.INT -> Value.STRING(executedValue.value.toString())
                is Value.DOUBLE -> Value.STRING(executedValue.value.toString())
                is Value.STRING -> executedValue
                is Value.BOOLEAN -> Value.STRING(executedValue.value.toString())
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