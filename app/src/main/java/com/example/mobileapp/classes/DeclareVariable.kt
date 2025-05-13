package com.example.mobileapp.classes

import android.util.Log
import androidx.compose.ui.graphics.Color

class DeclareVariable(_scope: ComplexBlock) : Block() {
    val name: String = ""
    val value: String = ""
    val scope = _scope
    override val color: Color
        get() = TODO("Not yet implemented")

    init {
        var hashCode = this.hashCode()
        Log.i("tag", "$hashCode")
    }

    override fun execute() {
        // Нуждается в доработке
        scope.varList.plus((name to value))
    }
}