package com.example.mobileapp.classes

class Empty(override var scope: NewScope): Block()
{
    override fun execute()
    {
        println("goyda")
    }
}