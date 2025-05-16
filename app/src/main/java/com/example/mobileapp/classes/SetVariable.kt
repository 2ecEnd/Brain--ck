package com.example.mobileapp.classes

import android.util.Log
import androidx.compose.ui.graphics.Color

class SetVariable(_scope: ComplexBlock) : Block() {
    var name: String = ""
    var value: String = ""
    val scope = _scope
    override val color: Color
        get() = TODO("Not yet implemented")

    override fun execute() {
        // Нуждается в доработке
        scope.varList[name] = value
    }
}