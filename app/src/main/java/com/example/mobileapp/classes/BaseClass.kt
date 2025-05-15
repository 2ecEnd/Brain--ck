package com.example.mobileapp.classes

import androidx.compose.ui.graphics.Color

abstract class BlockTemplate
{
    abstract fun execute(): Any  // Выполняет свою функцию
}

abstract class Block: BlockTemplate()
{
    abstract override fun execute(): Any // Выполняет свою функцию
}

abstract class ComplexBlock: BlockTemplate()
{
    abstract var blockList: MutableList<BlockTemplate> // Список блоков, которые он будет содержать
    abstract var varList: MutableMap<String, Int> //Список переменных, доступных в его области видимости

    abstract override fun execute(): Any  // Выполняет свою функцию
}