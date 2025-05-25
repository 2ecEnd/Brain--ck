package com.example.mobileapp.classes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Rect

abstract class BlockTemplate
{
    abstract fun execute(): Any
    abstract var selfRect: Rect
    abstract var parent: BlockTemplate?
}

abstract class Block: BlockTemplate()
{
    abstract var scope: ComplexBlock
    abstract override fun execute(): Any
}

abstract class ComplexBlock: BlockTemplate()
{
    abstract var scope: ComplexBlock
    // Список блоков, которые будет содержать данный блок
    abstract var blockList: SnapshotStateList<BlockTemplate>
    // Список переменных, доступных в области видимости данного блока
    abstract var varList: MutableMap<String, Value>
    abstract var dropZones: SnapshotStateList<Rect>
    abstract var spacerPair: MutableState<Pair<Int, ComplexBlock>>

    abstract fun deleteVariable(name: String)
    abstract fun addVariable(name: String)
    abstract fun updateDropZones(draggingBlock: BlockTemplate)
    abstract override fun execute(): Any
}