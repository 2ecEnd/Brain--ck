package com.example.mobileapp.classes

import androidx.compose.ui.graphics.Color

class Constant : Block() {
    var value: String = ""
    override val color: Color = Color(255, 255, 255)

    // Нуждается в доработке
    override fun execute() = value
}