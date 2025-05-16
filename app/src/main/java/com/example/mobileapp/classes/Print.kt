package com.example.mobileapp.classes

class Print(val console: Console, var msg: String): Block()
{
    override fun execute()
    {
        console.addString(msg)
    }
}