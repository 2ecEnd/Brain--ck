package com.example.mobileapp.classes

import androidx.compose.ui.graphics.Color

class Context : ComplexBlock() {
    override val color: Color
        get() = TODO("Not yet implemented")
    override var blockList: MutableList<BlockTemplate>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var varList: MutableMap<String, Any>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun execute() {
        // Нуждается в доработке
        for (i in 0..blockList.size){
            blockList[i].execute()
        }
    }
}