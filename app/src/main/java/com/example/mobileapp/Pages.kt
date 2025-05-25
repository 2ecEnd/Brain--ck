package com.example.mobileapp

import android.R.string
import android.content.res.Resources
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.core.content.contentValuesOf
import androidx.navigation.NavController
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.BlockTemplate
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.classes.ComplexBlock
import com.example.mobileapp.classes.Console
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.Context
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.Empty
import com.example.mobileapp.classes.IfElse
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseVariable
import com.example.mobileapp.classes.Value
import kotlin.math.roundToInt

@Composable
fun HomePage(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
        )

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
        )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                )
                {
                    Button(
                        onClick = {navController.navigate("projects")},
                        modifier = Modifier
                            .width(275.dp)
                            .height(50.dp)
                    )
                    {
                        Text("Проекты", fontSize = 24.sp)
                    }

                    ExitButton()
                }
            }
        }
    }
}

@Composable
fun ProjectsPage(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
        )

        HomeButton(navController)

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
        )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                )
                {
                    Button(
                        onClick = {navController.navigate("createProject")},
                        modifier = Modifier
                            .width(275.dp)
                            .height(50.dp)
                    )
                    {
                        Text("+", fontSize = 24.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CreateProjectPage(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)){
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
        )

        HomeButton(navController)

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .width(325.dp)
                .height(200.dp),
            shape = RoundedCornerShape(15.dp)
        )
        {
            ProjectNameForm(navController)
        }
    }
}

@Composable
fun ProjectNameForm(navController: NavController){
    var text by remember{ mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        )
        {
            TextField(
                modifier = Modifier
                    .width(275.dp)
                    .height(80.dp),
                value = text,
                onValueChange = { newText -> text = newText },
                textStyle = LocalTextStyle.current.copy(fontSize = 24.sp)
            )

            Button(
                onClick = {navController.navigate("redactor")},
                modifier = Modifier
                    .width(275.dp)
                    .height(50.dp)
            )
            {
                Text("Создать", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun RedactorPage(navController: NavController){
    var console = remember {Console()}
    var context = remember {Context(Resources.getSystem(), console = console)}

    var currentInteractionScope by remember { mutableStateOf<ComplexBlock>(context) }
    var draggingBlock by remember { mutableStateOf<BlockTemplate>(DeclareVariable(context))}
    var emptyBlock by remember {mutableStateOf<BlockTemplate>(Empty())}
    var isDraggingBlockCanBeDeleted by remember { mutableStateOf<Boolean>(false) }
    var isDragging by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var touchPoint by remember { mutableStateOf(Offset.Zero) }

    var blockList = remember { context.blockList }
    var scopesList = remember { mutableStateListOf<ComplexBlock>(context)}
    var blockChooserList = remember { mutableStateListOf<BlockTemplate>(IfElse(), DeclareVariable(context),
        UseVariable(context), SetVariable(context), MathExpression(), Constant(), Print(console)) }

    var pagerState = rememberPagerState (pageCount = {2})

    context.spacerPair = remember{mutableStateOf<Pair<Int, ComplexBlock>>(-1 to context)}

    scopesList.forEach { scope -> scope.updateDropZones(draggingBlock) }

    fun createNewBlock(oldBlock: BlockTemplate): BlockTemplate{
        when(oldBlock){
            is DeclareVariable -> return DeclareVariable(context)
            is SetVariable -> return SetVariable(context)
            is MathExpression -> return MathExpression()
            is Constant -> return Constant()
            is Print -> return Print(console)
            is UseVariable -> return UseVariable(context)
            is IfElse -> return IfElse()
        }
        return TODO("Provide the return value")
    }

    fun deleteBlock(localScope: ComplexBlock){
        if(draggingBlock.parent == null) localScope.blockList.remove(draggingBlock)
        else{
            var parent = draggingBlock.parent
            var child = draggingBlock
            when(parent) {
                is MathExpression -> {
                    if (parent.leftValue == child) parent.leftValue = Constant()
                    else if (parent.rightValue == child) parent.rightValue = Constant()
                }
                is Print -> {
                    parent.content = Constant()
                }
                is SetVariable -> {
                    parent.value = Constant()
                }
            }
        }
    }

    fun addBlockInsideAnother(block: BlockTemplate, isInsideBlock: Boolean,
                              onReplace: (newBlock: BlockTemplate) -> Unit, isRelocating: Boolean,
                              relocateFunction: (BlockTemplate, ComplexBlock) -> Unit,
                              addBlockFunction: (BlockTemplate, ComplexBlock) -> Unit, localScope: ComplexBlock){
        when(block){
            is MathExpression -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.leftValueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.leftValue, true, {newBlock -> block.leftValue = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (block.rightValueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.rightValue, true, {newBlock -> block.rightValue = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction,localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock) else draggingBlock)
                    }
                }
            }
            is SetVariable -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.valueRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.value, true, {newBlock -> block.value = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction, localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock) else draggingBlock)
                    }
                }
            }
            is Constant -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y)) && isInsideBlock){
                    onReplace(if(!isRelocating) createNewBlock(draggingBlock) else draggingBlock)
                }
            }
            is Print -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.contentRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.content, true, {newBlock -> block.content = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction, localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock) else draggingBlock)
                    }
                }
            }
            is UseVariable -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y)) && isInsideBlock){
                    onReplace(if(!isRelocating) createNewBlock(draggingBlock) else draggingBlock)
                }
            }
            is IfElse -> {
                if (block.selfRect.contains(Offset(dragOffset.x, dragOffset.y))){
                    if (block.conditionRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        addBlockInsideAnother(block.condition, true, {newBlock -> if (newBlock is BoolExpression) block.condition = newBlock
                            deleteBlock(localScope)}, isRelocating, relocateFunction, addBlockFunction, localScope)
                    }
                    else if (block.ifRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        if (isRelocating) relocateFunction(draggingBlock, localScope)
                        else addBlockFunction(draggingBlock, localScope)
                    }
                    else if (block.elseRect.contains(Offset(dragOffset.x, dragOffset.y))){
                        if (isRelocating) relocateFunction(draggingBlock, localScope)
                        else addBlockFunction(draggingBlock, localScope)
                    }
                    else if (isInsideBlock){
                        onReplace(if(!isRelocating) createNewBlock(draggingBlock) else draggingBlock)
                    }
                }
            }
        }
        //updateDropZones()
    }

    fun AddNewBlock(block: BlockTemplate, localScope: ComplexBlock){
        localScope.blockList.remove(emptyBlock)
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(Offset(dragOffset.x, dragOffset.y))){
                if (!(block is Constant) && !(block is UseVariable) && !(block is MathExpression)){
                    val insertIndex = if (localScope.blockList.isEmpty()) 0 else i+1
                    var newBlock = createNewBlock(block)
                    localScope.blockList.add(insertIndex, newBlock)
                    when(newBlock){
                        is IfElse -> {
                            newBlock.if_.spacerPair = mutableStateOf<Pair<Int, ComplexBlock>>(context.spacerPair.value)
                            newBlock.else_.spacerPair = mutableStateOf<Pair<Int, ComplexBlock>>(context.spacerPair.value)
                            scopesList.add(newBlock.if_)
                            scopesList.add(newBlock.else_)
                        }
                    }
                    //updateDropZones()
                    return
                }
            }
        }
        //if (!(block is Constant) && !(block is UseVariable) && !(block is MathExpression)) return
        localScope.blockList.forEach { item ->
            addBlockInsideAnother(item, false, {}, false, {_, _ ->}, {block, scope -> AddNewBlock(block, scope)}, localScope)
        }
    }

    fun relocateBlock(block: BlockTemplate, localScope: ComplexBlock){
        localScope.blockList.remove(emptyBlock)
        for(i in localScope.dropZones.indices){
            if (localScope.dropZones[i].contains(Offset(dragOffset.x, dragOffset.y))){
                if (!(block is Constant) && !(block is UseVariable) && !(block is MathExpression)){
                    deleteBlock(localScope)
                    localScope.blockList.add(i+1, block)
                    //updateDropZones()
                    return
                }
            }
        }
        if (!(block is Constant) && !(block is UseVariable) && !(block is MathExpression)) return
        localScope.blockList.forEach { item ->
            addBlockInsideAnother(item, false, {}, true, {block, scope -> relocateBlock(block, scope)}, {_, _ ->}, localScope)
        }
    }

    @Composable
    fun RedactorArea(blockList: SnapshotStateList<BlockTemplate>){
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
                            draggingBlock = Constant()
                        }, true, remember { mutableStateOf(draggingBlock) })
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
                DrawBlock(draggingBlock, {_, _ ->}, {}, false, remember{mutableStateOf(draggingBlock)})
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

            var localScope: ComplexBlock = context
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
//                    if (!isEmptyBlockAdded && i != localScope.dropZones.count()-1){
//                        if (localScope.blockList.contains(draggingBlock) && localScope.blockList.indexOf(draggingBlock) <= i) localScope.blockList.add(i+2, emptyBlock)
//                        else localScope.blockList.add(i+1, emptyBlock)
//                        scopesList.forEach { scope -> scope.updateDropZones(draggingBlock) }
//                        isEmptyBlockAdded = true
//                    }
//                    completed = false
//                    break
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .background(Color(red = 230, green = 224, blue = 233))
            )
            {
                HomeButton(navController)
            }

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
                            .background(Color(red = 230, green = 224, blue = 233))
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
                                        }, false, remember{mutableStateOf(draggingBlock)}
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
                                },
                                modifier = Modifier
                                    .width(68.dp)
                                    .height(68.dp)
                                    .padding(8.dp),
                                shape = RoundedCornerShape(12.dp),
                            ){}
                        }
                    }
                }
            }
            }

        }
    }
}