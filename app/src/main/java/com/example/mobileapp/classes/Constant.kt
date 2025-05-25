package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class Constant(var value: Value = Value.INT(0)): Block()
{
    override var selfRect: Rect = Rect.Zero
    override var parent: BlockTemplate? = null

    constructor(type: String, value_: Any) : this()
    {
        setValue(type, value_)
    }

    fun setValue(type: String, value_: Any)
    {
        when (type)
        {
            "string" ->
            {
                if (value_ is String)
                    value = Value.STRING(value_)
                else
                    throw IllegalArgumentException("Ошибка")
            }
            "int" ->
            {
                if (value_ is Int)
                    value = Value.INT(value_)
                else
                    throw IllegalArgumentException("Ошибка")
            }
            "double" ->
            {
                if (value_ is Double)
                    value = Value.DOUBLE(value_)
                else
                    throw IllegalArgumentException("Ошибка")
            }
            "bool" ->
            {
                if (value_ is Boolean)
                    value = Value.BOOLEAN(value_)
                else
                    throw IllegalArgumentException("Ошибка")
            }
            else -> throw  IllegalArgumentException("Ошибка")
        }
    }

    override fun execute(): Value = value
}