package com.example.mobileapp.classes

import com.example.mobileapp.R

class UseVariable(
    override var scope: NewScope,
    private val varList: MutableMap<String, Value>
) : Block()
{
    var name: String = ""

    constructor(
        scope: NewScope,
        varList: MutableMap<String, Value>,
        varName: String
    ) : this(scope, varList)
    {
        name = varName
    }

    override fun execute(): Value
    {
        isTroublesome = false

        try
        {
            val result: Value? = varList[name]

            if (result == null)
            {
                isTroublesome = true
                throw Exception(R.string.null_pointer.toString())
            }

            return result
        }
        catch (e: Exception)
        {
            isTroublesome = true
            throw e
        }
    }
}