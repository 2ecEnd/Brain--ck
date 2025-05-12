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
    abstract var varList: MutableList<Block> //Список переменных, доступных в его области видимости

    abstract override fun execute(): Any  // Выполняет свою функцию
}