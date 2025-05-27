package com.example.mobileapp.classes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Rect

class ListConstant(
    override var scope: ComplexBlock,
    var value: Value.LIST = Value.LIST(mutableStateListOf())
) : Block()
{
    override var parent: BlockTemplate? = null
    override var selfRect: Rect = Rect.Zero

    override fun execute() = value
}