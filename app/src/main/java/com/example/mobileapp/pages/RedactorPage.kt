package com.example.mobileapp.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.DrawShadow
import com.example.mobileapp.R
import com.example.mobileapp.classes.AddListElement
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.classes.NewScope
import com.example.mobileapp.classes.Console
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.Context
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.DeleteListElement
import com.example.mobileapp.classes.For
import com.example.mobileapp.classes.IfElse
import com.example.mobileapp.classes.ListConstant
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseVariable
import com.example.mobileapp.redactorspage_components.Toolbar
import kotlin.math.roundToInt

@Composable
fun RedactorPage(navController: NavController){
    var console = remember {Console()}
    val tmp = LocalContext.current.resources
    var context = remember {Context(tmp, console = console)}

    var currentInteractionScope by remember { mutableStateOf<NewScope>(context) }
    var draggingBlock by remember { mutableStateOf<Block>(DeclareVariable(context, context.varList))}
    var isDraggingBlockCanBeDeleted by remember { mutableStateOf<Boolean>(false) }
    var isDragging by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var touchPoint by remember { mutableStateOf(Offset.Zero) }

    var blockList = remember { context.blockList }
    var scopesList = remember { mutableStateListOf<NewScope>(context)}
    var blockChooserList = remember { mutableStateListOf<Block>(For(context, context.varList, "i"), IfElse(context), DeclareVariable(context, context.varList),
        UseVariable(context, context.varList), SetVariable(context, context.varList), MathExpression(context), Print(context, console),
        AddListElement(context), DeleteListElement(context), Constant(context, "int", 0), Constant(context, "string", "str"),
        Constant(context, "double", 0.0), Constant(context, "bool", true),
        ListConstant(context)) }

    var pagerState = rememberPagerState (pageCount = {2})

    context.spacerPair = remember{mutableStateOf<Pair<Int, NewScope>>(-1 to context)}

    scopesList.forEach { scope -> scope.updateDropZones(draggingBlock) }

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
        if(draggingBlock.parent == null) draggingBlock.scope.blockList.remove(draggingBlock)
        else{
            var parent = draggingBlock.parent
            var child = draggingBlock
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
        when(draggingBlock){
            is IfElse -> {
                scopesList.remove((draggingBlock as IfElse).ifBlock)
                scopesList.remove((draggingBlock as IfElse).elseBlock)
            }
            is For -> {
                scopesList.remove(draggingBlock)
            }
            is DeclareVariable -> {
                draggingBlock.scope.deleteVariable((draggingBlock as DeclareVariable).name)
            }
        }
    }

    fun addBlockInsideAnother(block: Block, isInsideBlock: Boolean,
                              onReplace: (newBlock: Block) -> Unit, isRelocating: Boolean,
                              relocateFunction: (Block, NewScope) -> Unit,
                              addBlockFunction: (Block, NewScope) -> Unit, localScope: NewScope){
        if (draggingBlock !is Constant && draggingBlock !is ListConstant && draggingBlock !is UseVariable && draggingBlock !is MathExpression
            && draggingBlock !is BoolExpression && block !is IfElse) return
        when(block){
            is MathExpression -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.leftValueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.leftValue, true, {newBlock -> deleteBlock(localScope)
                            block.leftValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.rightValueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.rightValue, true, {newBlock -> deleteBlock(localScope)
                            block.rightValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is SetVariable -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.valueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.value, true, {newBlock -> deleteBlock(localScope)
                            block.value = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is Constant -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y)) && isInsideBlock){
                    onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                }
            }
            is ListConstant -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y)) && isInsideBlock){
                    if (block.addBlockRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        block.blockList.add(createNewBlock(draggingBlock, localScope))
                    }
                    else{
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is Print -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.contentRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.content, true, {newBlock -> deleteBlock(localScope)
                            block.content = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is UseVariable -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y)) && isInsideBlock){
                    onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                }
            }
            is IfElse -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.conditionRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.condition, true, {newBlock -> if (newBlock is BoolExpression) block.condition = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction, localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is BoolExpression -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.leftValueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.leftValue, true, {newBlock -> deleteBlock(localScope)
                            block.leftValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.rightValueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.rightValue, true, {newBlock -> deleteBlock(localScope)
                            block.rightValue = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is AddListElement -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.valueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.value, true, {newBlock -> deleteBlock(localScope)
                            block.value = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.sourceRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        if (block.source != null) {
                            addBlockInsideAnother(block.source as Block, true, { newBlock ->
                                deleteBlock(localScope)
                                block.source = newBlock
                            }, isRelocating, relocateFunction, addBlockFunction, localScope)
                        }
                        else{
                            block.source = if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock
                        }
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
            is DeleteListElement -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.indexRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.index, true, {newBlock -> deleteBlock(localScope)
                            block.index = newBlock}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.sourceRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        if (block.source != null) {
                            addBlockInsideAnother(block.source as Block, true, { newBlock ->
                                deleteBlock(localScope)
                                block.source = newBlock
                            }, isRelocating, relocateFunction, addBlockFunction, localScope)
                        }
                        else{
                            block.source = if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock
                        }
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock, localScope) else draggingBlock)
                    }
                }
            }
        }
    }

    fun AddNewBlock(block: Block, localScope: NewScope){
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(Offset(dragOffset.x, dragOffset.y))){
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
            if (localScope.dropZones[i].contains(Offset(dragOffset.x, dragOffset.y))){
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

    @Composable
    fun RedactorArea(blockList: SnapshotStateList<Block>){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .background(Color(red = 249, green = 249, blue = 249))
        )
        {
            for(i in blockList.indices){
                if (currentInteractionScope.spacerPair.value.first == i && currentInteractionScope.spacerPair.value.second == context){
                    Spacer(modifier = Modifier.height(48.dp))
                }
                var block = blockList[i]
                key(block.hashCode()) {
                    var alpha = if (block == draggingBlock) 0.5f else 1f
                    Box(modifier = Modifier.padding(start = 12.dp).alpha(alpha)) {
                        DrawBlock(block, { offset, chosenBlock ->
                            isDragging = true
                            touchPoint = offset
                            draggingBlock = chosenBlock
                            isDraggingBlockCanBeDeleted = true
                            scopesList.forEach { scope -> scope.updateDropZones(draggingBlock) }
                        }, { draggedBlock ->
                            isDragging = false
                            isDraggingBlockCanBeDeleted = false
                            if (!isDeleting) relocateBlock(
                                draggedBlock,
                                currentInteractionScope
                            ) else deleteBlock(currentInteractionScope)
                            draggingBlock = Constant(context)
                        }, true)
                    }
                }
            }
        }
    }

    // Основной контейнер
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(red = 249, green = 249, blue = 249))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val position = event.changes.first().position
                        if (event.type == PointerEventType.Move && isDragging) {
                            dragOffset = position
                        }
                    }
                }
            }
    )
    {
        if (isDragging){
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            (dragOffset.x - touchPoint.x).roundToInt(),
                            (dragOffset.y - touchPoint.y).roundToInt()
                        )
                    }
                    .zIndex(1f)
            )
            {
                DrawBlock(draggingBlock, {_, _ ->}, {}, false)
            }

            if(isDraggingBlockCanBeDeleted){
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                var trashRect by remember { mutableStateOf(Rect.Zero) }
                var alpha: Float
                if(trashRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    alpha = 0.3f
                    isDeleting = true
                }
                else{
                    alpha = 0f
                    isDeleting = false
                }
                Card(
                    modifier = Modifier
                        .width(56.dp)
                        .height(56.dp)
                        .offset {
                            IntOffset(
                                x = ((screenWidth - 56.dp).toPx()).roundToInt(),
                                y = ((screenHeight - 56.dp).toPx() / 2.5).roundToInt()
                            )
                        }
                        .onGloballyPositioned { coordinates ->
                            trashRect = coordinates.boundsInWindow()
                        }
                        .zIndex(0.95f),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = alpha)),
                    shape = RoundedCornerShape(10.dp)
                )
                {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.trash_fill),
                            contentDescription = "trash",
                            modifier = Modifier.fillMaxSize(0.8f),
                        )
                    }
                }
            }

            var localScope: NewScope = context
            scopesList.forEach { scope ->
                if (scope.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    localScope = scope
                }
            }
            currentInteractionScope = localScope

            var completed = true
            for(i in localScope.dropZones.indices){
                if(localScope.dropZones[i].contains(Offset(dragOffset.x, dragOffset.y))) {
                    Box(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    localScope.dropZones[i].left.roundToInt(),
                                    if(i != localScope.dropZones.count()-1 || (localScope != context && !localScope.blockList.isEmpty())){
                                        (localScope.dropZones[i].top + (localScope.dropZones[i].bottom - localScope.dropZones[i].top)*0.5).roundToInt()
                                    }
                                    else{
                                        localScope.dropZones[i].top.roundToInt()
                                    }
                                )
                            }
                            .zIndex(0.95f)
                    )
                    {
                        DrawShadow(null)
                    }
                    if (i != localScope.dropZones.count()-1 || localScope != context) {
                        scopesList.forEach { scope ->
                            scope.spacerPair.value = scope.spacerPair.value.copy(
                                first = if (localScope.blockList.contains(draggingBlock)
                                    && localScope.blockList.indexOf(draggingBlock) <= i)
                                    i + 2 else i + 1,
                                second = localScope
                            )
                        }
                    }
                    completed = false
                    break
                }
            }
            if (completed){
                scopesList.forEach { scope ->
                    scope.spacerPair.value = scope.spacerPair.value.copy(first = -1, second = localScope)
                }
            }
        }
        else{
            scopesList.forEach { scope ->
                scope.spacerPair.value = scope.spacerPair.value.copy(first = -1, second = context)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Toolbar(navController)

            RedactorArea(blockList)

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            )
            {
                    page -> when(page) {
                0 -> {
                    Card(
                        modifier = Modifier
                            .background(Color(red = 230, green = 224, blue = 233)),
                        colors = CardDefaults.cardColors(containerColor = Color(230, 224, 233))
                    )
                    {
                        val scrollState = rememberScrollState()

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.Start
                        )
                        {
                            blockChooserList.forEach { item ->
                                Box(modifier = Modifier.padding(12.dp)){
                                    DrawBlock(
                                        item, {offset, chosenBlock ->
                                            isDragging = true
                                            touchPoint = offset
                                            draggingBlock = chosenBlock
                                            scopesList.forEach { scope -> scope.updateDropZones(draggingBlock) }
                                        },
                                        { draggedBlock ->
                                            isDragging = false
                                            AddNewBlock(draggedBlock, currentInteractionScope)
                                        }, false
                                    )
                                }
                            }
                        }
                    }
                }
                1 -> {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = Color(100, 100, 100)),
                        shape = RoundedCornerShape(0.dp),
                    )
                    {
                        Row {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.8f)
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            )
                            {
                                console.text.forEach { text ->
                                    Text(text, fontSize = 24.sp, color = Color.White)
                                }
                            }

                            Button(
                                onClick = {
                                    context.blockList = blockList
                                    console.text.clear()
                                    context.execute()
                                    //console.text.add("Программа завершилась штатно")
                                },
                                modifier = Modifier
                                    .width(68.dp)
                                    .height(68.dp)
                                    .padding(8.dp),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(red = 87, green = 150, blue = 92),
                                )
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.play_128px),
                                    contentDescription = "icon",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        }
                    }
                }
            }
            }

        }
    }
}