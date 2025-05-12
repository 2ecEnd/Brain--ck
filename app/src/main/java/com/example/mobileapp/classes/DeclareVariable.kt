package com.example.mobileapp.classes

import androidx.compose.ui.graphics.Color

class DeclareVariable(val scope: ComplexBlock) : Block() {
    val name: String = ""
    val value: String = ""
    override val color: Color
        get() = TODO("Not yet implemented")

    override fun execute() {
        // Нуждается в доработке
        scope.varList.plus((name to value))
    }
}