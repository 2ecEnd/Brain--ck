package com.example.mobileapp.classes

import android.graphics.Color
import androidx.compose.runtime.Composable

abstract class BlockTemplate {
    abstract val color: Color // Цвет блока (ui)

    abstract fun runCode() // Выполняет свою функцию
    @Composable
    abstract fun drawBlock() // Отрисовывает блок (выставляет нужные параметры) (ui)
}

abstract class Block() : BlockTemplate() {
    abstract override val color: Color // Цвет блока (ui)

    abstract override fun runCode() // Выполняет свою функцию
    @Composable
    abstract override fun drawBlock() // Отрисовывает блок (выставляет нужные параметры) (ui)
}

abstract class ComplexBlock() : BlockTemplate() {
    abstract override val color: Color // Цвет блока (ui)
    abstract var blockList: MutableList<BlockTemplate> // Список блоков, которые он будет содержать
    abstract var varList: MutableList<Block> //Список переменных, доступных в его области видимости

    abstract override fun runCode() // Выполняет свою функцию
    @Composable
    abstract override fun drawBlock() // Отрисовывает блок (выставляет нужные параметры) (ui)
}