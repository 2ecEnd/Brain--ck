package com.example.mobileapp.classes

sealed class Value
{
    data class INT(val value: Int) : Value()
    {
        override fun toString() = value.toString()
    }

    data class DOUBLE(val value: Double) : Value()
    {
        override fun toString() = value.toString()
    }

    data class BOOLEAN(val value: Boolean) : Value()
    {
        override fun toString() = value.toString()
    }

    data class STRING(val value: String) : Value()
    {
        override fun toString() = value.toString()
    }

    data class LIST(val value: MutableList<Value>) : Value()
    {
        override fun toString() = value.toString()
    }
}