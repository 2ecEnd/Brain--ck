package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class Constant(override var scope: NewScope): Block()
{
    var value by mutableStateOf<Value>(Value.INT(0))
    lateinit var type: String

    constructor(scope: NewScope, type: String) : this(scope)
    {
        this.type = type
        setValue(type)
    }

    constructor(scope: NewScope, type: String, value: Any) : this(scope)
    {
        this.type = type
        setValue(type, value)
    }


    fun setValue(type: String)
    {
        value = when (type)
        {
            "string" -> Value.STRING("str")
            "int" -> Value.INT(0)
            "double" -> Value.DOUBLE(0.0)
            "bool" -> Value.BOOLEAN(true)
            else -> throw Exception(R.string.illegal_value.toString())
        }
    }

    fun setValue(type: String, value: Any)
    {
        when (type)
        {
            "string" ->
            {
                if (value is String)
                    this.value = Value.STRING(value)
                else
                    throw Exception(R.string.type_mismatch.toString())
            }
            "int" ->
            {
                if (value is Int)
                    this.value = Value.INT(value)
                else
                    throw Exception(R.string.type_mismatch.toString())
            }
            "double" ->
            {
                if (value is Double)
                    this.value = Value.DOUBLE(value)
                else
                    throw Exception(R.string.type_mismatch.toString())
            }
            "bool" ->
            {
                if (value is Boolean)
                    this.value = Value.BOOLEAN(value)
                else
                    throw Exception(R.string.type_mismatch.toString())
            }
            else -> throw Exception(R.string.illegal_value.toString())
        }
    }

    override fun execute(): Value = value
}