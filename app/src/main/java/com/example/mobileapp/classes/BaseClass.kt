package com.example.mobileapp.classes

abstract class BlockTemplate
{
    abstract fun execute(): Any
}

abstract class Block: BlockTemplate()
{
    abstract override fun execute(): Any
}

abstract class ComplexBlock: BlockTemplate()
{
    // Список блоков, которые будет содержать данный блок
    abstract var blockList: MutableList<BlockTemplate>
    //Список переменных, доступных в области видимости данного блока
    abstract var varList: MutableMap<String, Int>

    abstract override fun execute(): Any
}