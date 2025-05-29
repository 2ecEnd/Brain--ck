package com.example.mobileapp

import android.content.res.Resources
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
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
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseVariable

class RedactorViewModel(resources: Resources) : ViewModel() {
    var console = Console()
    var context = Context(resources, console = console)

    var currentInteractionScope = mutableStateOf<NewScope>(context)
    var draggingBlock = mutableStateOf<Block>(DeclareVariable(context, context.varList))
    var isDraggingBlockCanBeDeleted = mutableStateOf<Boolean>(false)
    var isDragging = mutableStateOf(false)
    var isDeleting = mutableStateOf(false)
    var dragOffset = mutableStateOf(Offset.Zero)
    var touchPoint = mutableStateOf(Offset.Zero)

    var blockList = context.blockList
    var scopesList = mutableStateListOf<NewScope>(context)
    var blockChooserList = mutableStateListOf<Block>(For(context, context.varList, "i"), IfElse(context), DeclareVariable(context, context.varList),
        UseVariable(context, context.varList), SetVariable(context, context.varList), MathExpression(context), Print(context, console),
        AddListElement(context), DeleteListElement(context), Constant(context, "int", 0), Constant(context, "string", "str"),
        Constant(context, "double", 0.0), Constant(context, "bool", true),
        ListConstant(context))

    fun createNewBlock(oldBlock: Block, scope: NewScope): Block{
        when(oldBlock){
            is DeclareVariable -> return DeclareVariable(scope, context.varList)
            is SetVariable -> return SetVariable(scope, context.varList)
            is MathExpression -> return MathExpression(scope)
            is BoolExpression -> return BoolExpression(scope)
            is Constant -> return Constant(scope, oldBlock.type, when(oldBlock.type){
                "int" -> 0
                "string" -> "str"
                "bool" -> true
                "double" -> 0.0
                else -> 0
            })
            is ListConstant -> return ListConstant(scope)
            is Print -> return Print(scope, console)
            is UseVariable -> return UseVariable(scope, context.varList)
            is IfElse -> return IfElse(scope)
            is AddListElement -> return AddListElement(scope)
            is DeleteListElement -> return DeleteListElement(scope)
            is For -> return For(scope, context.varList, "i")
        }
        return TODO("Provide the return value")
    }

    fun deleteBlock(localScope: NewScope){
        if(draggingBlock.value.parent == null) draggingBlock.value.scope.blockList.remove(draggingBlock.value)
        else{
            var parent = draggingBlock.value.parent
            var child = draggingBlock.value
            when(parent) {
                is MathExpression -> {
                    if (parent.leftValue == child) parent.leftValue = Constant(localScope, "int", 0)
                    else if (parent.rightValue == child) parent.rightValue = Constant(localScope, "int", 0)
                }
                is BoolExpression -> {
                    if (parent.leftValue == child) parent.leftValue = Constant(localScope, "bool", true)
                    else if (parent.rightValue == child) parent.rightValue = Constant(localScope, "bool", true)
                }
                is ListConstant -> {
                    parent.blockList.remove(child)
                }
                is AddListElement -> {
                    if (parent.source == child) parent.source = null
                }
                is DeleteListElement -> {
                    if (parent.source == child) parent.source = null
                }
                is Print -> {
                    parent.content = Constant(localScope, "string", "str")
                }
                is SetVariable -> {
                    parent.value = Constant(localScope, "int", 0)
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
                draggingBlock.value.scope.deleteVariable((draggingBlock.value as DeclareVariable).name)
            }
        }
    }

    fun addBlockInsideAnother(block: Block, isInsideBlock: Boolean,
                              onReplace: (newBlock: Block) -> Unit, isRelocating: Boolean,
                              relocateFunction: (Block, NewScope) -> Unit,
                              addBlockFunction: (Block, NewScope) -> Unit, localScope: NewScope){
        if (draggingBlock.value !is Constant && draggingBlock.value !is ListConstant && draggingBlock.value !is UseVariable && draggingBlock.value !is MathExpression
            && draggingBlock.value !is BoolExpression && block !is IfElse) return
        when(block){
            is MathExpression -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.leftValueRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.leftValue, true, {newBlock -> deleteBlock(localScope)
                            block.leftValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.rightValueRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.rightValue, true, {newBlock -> deleteBlock(localScope)
                            block.rightValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is SetVariable -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.valueRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.value, true, {newBlock -> deleteBlock(localScope)
                            block.value = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is Constant -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y)) && isInsideBlock){
                    onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                }
            }
            is ListConstant -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y)) && isInsideBlock){
                    if (block.addBlockRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        block.blockList.add(createNewBlock(draggingBlock.value, localScope))
                    }
                    else{
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is Print -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.contentRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.content, true, {newBlock -> deleteBlock(localScope)
                            block.content = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is UseVariable -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y)) && isInsideBlock){
                    onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                }
            }
            is IfElse -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.conditionRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.condition, true, {newBlock -> if (newBlock is BoolExpression) block.condition = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction, localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is BoolExpression -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.leftValueRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.leftValue, true, {newBlock -> deleteBlock(localScope)
                            block.leftValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.rightValueRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.rightValue, true, {newBlock -> deleteBlock(localScope)
                            block.rightValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is AddListElement -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.valueRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.value, true, {newBlock -> deleteBlock(localScope)
                            block.value = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.sourceRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        if (block.source != null) {
                            addBlockInsideAnother(block.source as Block, true, { newBlock ->
                                deleteBlock(localScope)
                                block.source = newBlock
                            }, isRelocating, relocateFunction, addBlockFunction, localScope)
                        }
                        else{
                            block.source = if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value
                        }
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
            is DeleteListElement -> {
                if (block.selfRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                    if (block.indexRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        addBlockInsideAnother(block.index, true, {newBlock -> deleteBlock(localScope)
                            block.index = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.sourceRect.contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                        if (block.source != null) {
                            addBlockInsideAnother(block.source as Block, true, { newBlock ->
                                deleteBlock(localScope)
                                block.source = newBlock
                            }, isRelocating, relocateFunction, addBlockFunction, localScope)
                        }
                        else{
                            block.source = if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value
                        }
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock.value, localScope) else draggingBlock.value)
                    }
                }
            }
        }
    }

    fun AddNewBlock(block: Block, localScope: NewScope){
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                if (block !is Constant && block !is ListConstant && block !is UseVariable && block !is MathExpression){
                    val insertIndex = if (localScope.blockList.isEmpty()) 0 else i+1
                    var newBlock = createNewBlock(block, localScope)
                    localScope.blockList.add(insertIndex, newBlock)
                    newBlock.scope = localScope
                    when(newBlock){
                        is IfElse -> {
                            newBlock.ifBlock.spacerPair = mutableStateOf<Pair<Int, NewScope>>(context.spacerPair.value)
                            newBlock.elseBlock.spacerPair = mutableStateOf<Pair<Int, NewScope>>(context.spacerPair.value)
                            scopesList.add(newBlock.ifBlock)
                            scopesList.add(newBlock.elseBlock)
                        }
                        is For -> {
                            newBlock.spacerPair = mutableStateOf<Pair<Int, NewScope>>(context.spacerPair.value)
                            scopesList.add(newBlock)
                        }
                    }
                    return
                }
            }
        }
        localScope.blockList.forEach { item ->
            addBlockInsideAnother(item, false, {}, false, {_, _ ->}, {block, scope -> AddNewBlock(block, scope)}, localScope)
        }
    }

    fun relocateBlock(block: Block, localScope: NewScope){
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(Offset(dragOffset.value.x, dragOffset.value.y))){
                if (!(block is Constant) && !(block is UseVariable) && !(block is MathExpression)){
                    deleteBlock(block.scope)
                    localScope.blockList.add(if (localScope.blockList.isEmpty()) i else i+1, block)
                    block.scope = localScope
                    return
                }
            }
        }
        localScope.blockList.forEach { item ->
            addBlockInsideAnother(item, false, {}, true, {block, scope -> relocateBlock(block, scope)}, {_, _ ->}, localScope)
        }
    }
}