package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

abstract class BlockTemplate
{
    abstract fun execute(): Any
    abstract var selfRect: Rect
    abstract var parent: BlockTemplate?
}

abstract class Block: BlockTemplate()
{
    abstract override fun execute(): Any
}

abstract class ComplexBlock: BlockTemplate()
{
    // Список блоков, которые будет содержать данный блок
    abstract var blockList: MutableList<BlockTemplate>
    // Список переменных, доступных в области видимости данного блока
    abstract var varList: MutableMap<String, Value>

    abstract fun deleteVariable(name: String)
    abstract fun addVariable(name: String)
    abstract override fun execute(): Any
}

sealed class Value
{
    data class INT(val value: Int) : Value(){
        override fun toString() = value.toString()
    }
    data class DOUBLE(val value: Double) : Value() {
        override fun toString() = value.toString()
    }
    data class BOOLEAN(val value: Boolean) : Value() {
        override fun toString() = value.toString()
    }
    data class STRING(val value: String) : Value() {
        override fun toString() = value.toString()
    }
}