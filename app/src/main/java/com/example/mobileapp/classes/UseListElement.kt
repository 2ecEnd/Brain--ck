package com.example.mobileapp.classes

import androidx.compose.ui.geometry.Rect

class UseListElement(var scope: ComplexBlock) : Block()
{
    var list_: BlockTemplate? = null
    var index_: BlockTemplate? = null

    override var parent: BlockTemplate? = null
    override var selfRect: Rect = Rect.Zero

    fun setData(list: BlockTemplate, index: BlockTemplate)
    {
        list_ = list
        index_ = index
    }

    override fun execute(): Value
    {
        val list = (list_ ?: throw IllegalArgumentException("Null")).execute()
        val index = (index_ ?: throw IllegalArgumentException("Null")).execute()

        if (list !is Value.LIST) throw IllegalArgumentException("Variable is not list")
        if (index !is Value.INT) throw IllegalArgumentException("Index is ont int")

        return try
        {
            list.value[index.value];
        }
        catch (e : Exception)
        {
            throw IllegalArgumentException(e.message);
        }
    }
}