package com.example.mobileapp.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DeclareVariable(
    override var scope: NewScope,
    private val varList: MutableMap<String, Value>
) : Block()
{
    var name by mutableStateOf("my variable")
    var value = Value.INT()

    override fun execute()
    {
        isTroublesome = false

        varList[name] = value
    }
}