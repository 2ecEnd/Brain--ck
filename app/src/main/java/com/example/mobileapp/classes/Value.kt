package com.example.mobileapp.classes

sealed class Value
{
    data class INT(val value: Int = 0) : Value()
    {
        override fun toString() = value.toString()
    }

    data class DOUBLE(val value: Double = 0.0) : Value()
    {
        override fun toString() = value.toString()
    }

    data class BOOLEAN(val value: Boolean = true) : Value()
    {
        override fun toString() = value.toString()
    }

    data class STRING(val value: String = "str") : Value()
    {
        override fun toString() = value
    }

    data class LIST(val value: MutableList<Value> = mutableListOf()) : Value()
    {
        override fun toString() = value.toString()
    }
}