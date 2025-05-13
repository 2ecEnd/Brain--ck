package com.example.mobileapp.classes

import androidx.compose.ui.graphics.Color

abstract class BlockTemplate {
    abstract val color: Color // Цвет блока (ui)

    abstract fun execute(): Any  // Выполняет свою функцию
}

abstract class Block() : BlockTemplate() {
    abstract override val color: Color // Цвет блока (ui)

    abstract override fun execute(): Any // Выполняет свою функцию
}

abstract class ComplexBlock() : BlockTemplate() {
    abstract override val color: Color // Цвет блока (ui)
    abstract var blockList: MutableList<BlockTemplate> // Список блоков, которые он будет содержать
    abstract var varList: MutableMap<String, Any> //Список переменных, доступных в его области видимости

    fun deleteVariable(name: String)
    {
        varList.remove(name)
    }
    fun addVariable(name: String)
    {
        varList.put(name, 0)
    }

    abstract override fun execute(): Any  // Выполняет свою функцию
}