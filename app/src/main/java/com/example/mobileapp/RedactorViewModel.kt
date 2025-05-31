package com.example.mobileapp

import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapp.classes.AddListElement
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.classes.Console
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.Context
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.DeleteListElement
import com.example.mobileapp.classes.For
import com.example.mobileapp.classes.IfElse
import com.example.mobileapp.classes.ListConstant
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.NewScope
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetListElement
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.ToBoolean
import com.example.mobileapp.classes.ToDouble
import com.example.mobileapp.classes.ToInt
import com.example.mobileapp.classes.ToString
import com.example.mobileapp.classes.UseListElement
import com.example.mobileapp.classes.UseVariable


val dataTypes: MutableList<String> = mutableStateListOf()
var defaultStringValue = String()
var iterable = String()

class RedactorViewModel(resources: Resources) : ViewModel() {
    var console = Console()
    var context = Context(resources, console = console)

    var currentInteractionScope = mutableStateOf<NewScope>(context)
    var draggingBlock = mutableStateOf<Block>(DeclareVariable(context, context.varList))
    var isDraggingBlockCanBeDeleted = mutableStateOf(false)
    var isDragging = mutableStateOf(false)
    var isDeleting = mutableStateOf(false)
    var dragOffset = mutableStateOf(Offset.Zero)
    var touchPoint = mutableStateOf(Offset.Zero)

    var blockList = context.blockList
    var scopesList = mutableStateListOf<NewScope>(context)

    var variablesTabList = mutableStateListOf(
        DeclareVariable(context, context.varList),
        UseVariable(context, context.varList),
        SetVariable(context, context.varList)
    )

    var listsTabList = mutableStateListOf(
        ListConstant(context),
        AddListElement(context),
        DeleteListElement(context),
        SetListElement(context),
        UseListElement(context)
    )

    var expressionsTabList = mutableStateListOf<Block>(
        MathExpression(context),
        BoolExpression(context)
    )

    var constantsTabList = mutableStateListOf<Block>(
        Constant(context, resources.getString(R.string.integer)),
        Constant(context, resources.getString(R.string.string)),
        Constant(context, resources.getString(R.string.fractional)),
        Constant(context, resources.getString(R.string.bool))
    )

    var convertersTabList = mutableStateListOf(
        ToBoolean(context),
        ToDouble(context),
        ToInt(context),
        ToString(context)
    )

    var otherTabList = mutableStateListOf(
        For(context, context.varList, resources.getString(R.string.i)),
        IfElse(context),
        Print(context, console)
    )

    init {
        dataTypes.add(resources.getString(R.string.integer))
        dataTypes.add(resources.getString(R.string.fractional))
        dataTypes.add(resources.getString(R.string.string))
        dataTypes.add(resources.getString(R.string.bool))

        defaultStringValue = resources.getString(R.string.default_string_value)

        iterable = resources.getString(R.string.i)
    }

    private fun createNewBlock(
        oldBlock: Block,
        scope: NewScope
    ): Block
    {
        return when(oldBlock){
            is DeclareVariable -> DeclareVariable(scope, context.varList)
            is SetVariable -> SetVariable(scope, context.varList)
            is MathExpression -> MathExpression(scope)
            is BoolExpression -> BoolExpression(scope)
            is Constant -> Constant(scope, oldBlock.type, when(oldBlock.type){
                dataTypes[0] -> 0
                dataTypes[1] -> 0.0
                dataTypes[2] -> defaultStringValue
                dataTypes[3] -> true
                else -> 0
            })
            is ListConstant -> ListConstant(scope)
            is Print -> Print(scope, console)
            is UseVariable -> UseVariable(scope, context.varList)
            is IfElse -> IfElse(scope)
            is AddListElement -> AddListElement(scope)
            is DeleteListElement -> DeleteListElement(scope)
            is SetListElement -> SetListElement(scope)
            is UseListElement -> UseListElement(scope)
            is For -> For(scope, context.varList, iterable)
            is ToBoolean -> ToBoolean(scope)
            is ToDouble -> ToDouble(scope)
            is ToInt -> ToInt(scope)
            is ToString -> ToString(scope)
            else -> Constant(scope)
        }
    }

    private fun isNotSpecialBlock(block: Block): Boolean =
        block !is Constant &&
        block !is ListConstant &&
        block !is UseVariable &&
        block !is MathExpression &&
        block !is BoolExpression &&
        block !is UseListElement &&
        block !is ToBoolean &&
        block !is ToDouble &&
        block !is ToInt &&
        block !is ToString

    fun deleteBlock(
        localScope: NewScope
    )
    {
        if(draggingBlock.value.parent == null)
            draggingBlock.value.scope.blockList.remove(draggingBlock.value)
        else{
            val parent = draggingBlock.value.parent
            val child = draggingBlock.value
            when(parent) {
                is MathExpression -> {
                    if (parent.leftValue == child)
                        parent.leftValue = Constant(localScope, dataTypes[0])
                    else if (parent.rightValue == child)
                        parent.rightValue = Constant(localScope, dataTypes[0])
                }
                is BoolExpression -> {
                    if (parent.leftValue == child)
                        parent.leftValue = Constant(localScope, dataTypes[0])
                    else if (parent.rightValue == child)
                        parent.rightValue = Constant(localScope, dataTypes[3])
                }
                is ListConstant -> {
                    parent.blockList.remove(child)
                }
                is AddListElement -> {
                    when(child){
                        parent.source -> parent.source = null
                        parent.value -> Constant(localScope, dataTypes[0])
                    }

                }
                is DeleteListElement -> {
                    when(child){
                        parent.source -> parent.source = null
                        parent.index -> Constant(localScope, dataTypes[0])
                    }
                }
                is SetListElement -> {
                    when(child){
                        parent.source -> parent.source = null
                        parent.value -> Constant(localScope, dataTypes[0])
                        parent.index -> Constant(localScope, dataTypes[0])
                    }
                }
                is UseListElement -> {
                    when(child){
                        parent.source -> parent.source = null
                        parent.index -> Constant(localScope, dataTypes[0])
                    }
                }
                is Print -> {
                    parent.content = Constant(localScope, dataTypes[2])
                }
                is SetVariable -> {
                    parent.value = Constant(localScope, dataTypes[0])
                }
                is ToBoolean -> {
                    parent.source = Constant(localScope, dataTypes[0])
                }
                is ToDouble -> {
                    parent.source = Constant(localScope, dataTypes[0])
                }
                is ToInt -> {
                    parent.source = Constant(localScope, dataTypes[0])
                }
                is ToString -> {
                    parent.source = Constant(localScope, dataTypes[0])
                }
            }
        }
        when(draggingBlock.value){
            is IfElse -> {
                scopesList.remove((draggingBlock.value as IfElse).ifBlock)
                scopesList.remove((draggingBlock.value as IfElse).elseBlock)
            }
            is For -> {
                scopesList.remove(draggingBlock.value)
            }
            is DeclareVariable -> {
                draggingBlock.value.scope.deleteVariable(
                    (draggingBlock.value as DeclareVariable).name
                )
            }
        }
    }

    private fun addBlockInsideAnother(
        block: Block,
        isInsideBlock: Boolean,
        onReplace: (newBlock: Block) -> Unit,
        isRelocating: Boolean,
        relocateFunction: (Block, NewScope) -> Unit,
        addBlockFunction: (Block, NewScope) -> Unit,
        localScope: NewScope
    ) {
        if ((isNotSpecialBlock(draggingBlock.value) && block !is IfElse)
            || draggingBlock.value == block)
            return
        when(block){
            is MathExpression -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.leftValueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.leftValue,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.leftValue = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (block.rightValueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.rightValue,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.rightValue = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is SetVariable -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.valueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.value,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.value = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is Constant -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ) && isInsideBlock)
                {
                    onReplace(
                        if(!isRelocating)
                            createNewBlock(draggingBlock.value, localScope)
                        else
                            draggingBlock.value)
                }
            }

            is ListConstant -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ) && isInsideBlock)
                {
                    if (block.addBlockRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        block.blockList.add(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                    else
                    {
                        var flag = true
                        for(i in block.blockList.indices)
                        {
                            if (block.blockList[i].selfRect.contains(
                                    Offset(dragOffset.value.x, dragOffset.value.y)
                            ))
                            {
                                addBlockInsideAnother(
                                    block.blockList[i],
                                    true,
                                    {newBlock -> deleteBlock(localScope)
                                        block.blockList[i] = newBlock
                                    },
                                    isRelocating,
                                    relocateFunction,
                                    addBlockFunction,
                                    localScope
                                )
                                flag = false
                            }
                        }
                        if (flag)
                            onReplace(
                                if(!isRelocating)
                                    createNewBlock(
                                        draggingBlock.value,
                                        localScope
                                    )
                                else
                                    draggingBlock.value)
                    }
                }
            }

            is Print -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.contentRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.content,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.content = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is UseVariable -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ) && isInsideBlock)
                {
                    onReplace(
                        if(!isRelocating)
                            createNewBlock(
                                draggingBlock.value,
                                localScope
                            )
                        else
                            draggingBlock.value)
                }
            }

            is IfElse -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.conditionRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.condition,
                            true,
                            {newBlock ->
                                if (newBlock is BoolExpression)
                                    block.condition = newBlock
                                deleteBlock(localScope)
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is BoolExpression -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.leftValueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.leftValue,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.leftValue = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (block.rightValueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.rightValue,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.rightValue = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is AddListElement -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.valueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.value,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.value = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        if (block.source != null)
                        {
                            addBlockInsideAnother(
                                block.source as Block,
                                true,
                                { newBlock ->
                                    deleteBlock(localScope)
                                    block.source = newBlock
                                },
                                isRelocating,
                                relocateFunction,
                                addBlockFunction,
                                localScope
                            )
                        }
                        else
                        {
                            block.source =
                                if(!isRelocating)
                                    createNewBlock(draggingBlock.value, localScope)
                                else
                                    draggingBlock.value
                        }
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is DeleteListElement -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.indexRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.index,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.index = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        if (block.source != null)
                        {
                            addBlockInsideAnother(
                                block.source as Block,
                                true,
                                {newBlock ->
                                    deleteBlock(localScope)
                                    block.source = newBlock
                                },
                                isRelocating,
                                relocateFunction,
                                addBlockFunction,
                                localScope
                            )
                        }
                        else
                        {
                            block.source =
                                if(!isRelocating)
                                    createNewBlock(
                                        draggingBlock.value,
                                        localScope)
                                else
                                    draggingBlock.value
                        }
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is SetListElement -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.indexRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.index,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.index = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        if (block.source != null)
                        {
                            addBlockInsideAnother(
                                block.source as Block,
                                true,
                                { newBlock ->
                                    deleteBlock(localScope)
                                    block.source = newBlock
                                },
                                isRelocating,
                                relocateFunction,
                                addBlockFunction,
                                localScope
                            )
                        }
                        else
                        {
                            block.source =
                                if(!isRelocating)
                                    createNewBlock(
                                        draggingBlock.value,
                                        localScope
                                    )
                                else
                                    draggingBlock.value
                        }
                    }
                    else if (block.valueRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.value,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.value = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is UseListElement -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.indexRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.index,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.index = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        if (block.source != null)
                        {
                            addBlockInsideAnother(
                                block.source as Block,
                                true,
                                { newBlock -> deleteBlock(localScope)
                                    block.source = newBlock
                                },
                                isRelocating,
                                relocateFunction,
                                addBlockFunction,
                                localScope
                            )
                        }
                        else
                        {
                            block.source =
                                if(!isRelocating)
                                    createNewBlock(
                                        draggingBlock.value,
                                        localScope
                                    )
                                else
                                    draggingBlock.value
                        }
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is ToBoolean -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.source,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.source = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is ToDouble -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.source,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.source = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is ToInt -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.source,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.source = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }

            is ToString -> {
                if (block.selfRect.contains(
                        Offset(dragOffset.value.x, dragOffset.value.y)
                ))
                {
                    if (block.sourceRect.contains(
                            Offset(dragOffset.value.x, dragOffset.value.y)
                    ))
                    {
                        addBlockInsideAnother(
                            block.source,
                            true,
                            {newBlock -> deleteBlock(localScope)
                                block.source = newBlock
                            },
                            isRelocating,
                            relocateFunction,
                            addBlockFunction,
                            localScope
                        )
                    }
                    else if (isInsideBlock)
                    {
                        onReplace(
                            if(!isRelocating)
                                createNewBlock(
                                    draggingBlock.value,
                                    localScope
                                )
                            else
                                draggingBlock.value)
                    }
                }
            }
        }
    }

    fun addNewBlock(
        block: Block,
        localScope: NewScope
    )
    {
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(
                    Offset(dragOffset.value.x, dragOffset.value.y)
            ))
            {
                if (isNotSpecialBlock(block))
                {
                    val insertIndex = if (localScope.blockList.isEmpty()) 0 else i+1
                    val newBlock = createNewBlock(block, localScope)
                    localScope.blockList.add(insertIndex, newBlock)
                    newBlock.scope = localScope
                    when(newBlock){
                        is IfElse -> {
                            newBlock.ifBlock.spacerPair =
                                mutableStateOf(context.spacerPair.value)
                            newBlock.elseBlock.spacerPair =
                                mutableStateOf(context.spacerPair.value)
                            scopesList.add(newBlock.ifBlock)
                            scopesList.add(newBlock.elseBlock)
                        }
                        is For -> {
                            newBlock.spacerPair =
                                mutableStateOf(context.spacerPair.value)
                            scopesList.add(newBlock)
                        }
                    }
                    return
                }
            }
        }
        localScope.blockList.forEach { item -> addBlockInsideAnother(
            item,
            false,
            {},
            false,
            {_, _ ->},
            {block, scope -> addNewBlock(block, scope)},
            localScope
        )}
    }

    fun relocateBlock(
        block: Block,
        localScope: NewScope
    )
    {
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(
                    Offset(dragOffset.value.x, dragOffset.value.y)
            ))
            {
                if (isNotSpecialBlock(block))
                {
                    deleteBlock(block.scope)
                    localScope.blockList.add(
                        if (localScope.blockList.isEmpty())
                            i
                        else
                            i + 1,
                        block
                    )
                    block.scope = localScope
                    return
                }
            }
        }
        localScope.blockList.forEach { item -> addBlockInsideAnother(
            item,
            false,
            {},
            true,
            {block, scope -> relocateBlock(block, scope)},
            {_, _ ->},
            localScope
        )}
    }
}


class RedactorViewModelFactory(private var resources: Resources) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RedactorViewModel(resources) as T
    }
}