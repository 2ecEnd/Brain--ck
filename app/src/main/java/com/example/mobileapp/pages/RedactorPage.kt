package com.example.mobileapp.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.DrawShadow
import com.example.mobileapp.R
import com.example.mobileapp.RedactorViewModel
import com.example.mobileapp.RedactorViewModelFactory
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.NewScope
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.redactorspage_components.RedactorArea
import com.example.mobileapp.redactorspage_components.ScrollableTabSample
import com.example.mobileapp.redactorspage_components.Toolbar
import kotlin.math.roundToInt
import com.example.mobileapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedactorPage(navController: NavController){
    val resources = LocalContext.current.resources
    val viewModel: RedactorViewModel = viewModel(
        factory = RedactorViewModelFactory(resources)
    )

    val blockList = viewModel.blockList
    val context = viewModel.context
    val console = viewModel.console

    var currentInteractionScope by viewModel.currentInteractionScope
    var draggingBlock by viewModel.draggingBlock
    var isDraggingBlockCanBeDeleted by viewModel.isDraggingBlockCanBeDeleted
    var isDragging by viewModel.isDragging
    var isDeleting by viewModel.isDeleting
    var dragOffset by viewModel.dragOffset
    var touchPoint by viewModel.touchPoint

    val scopesList = viewModel.scopesList

    val variablesTabList = viewModel.variablesTabList
    val listsTabList = viewModel.listsTabList
    val expressionsTabList = viewModel.expressionsTabList
    val constantsTabList = viewModel.constantsTabList
    val convertersTabList =  viewModel.convertersTabList
    val otherTabList =  viewModel.otherTabList

    val pagerState = rememberPagerState (pageCount = {2})
    val density = LocalDensity.current

    context.spacerPair = remember{mutableStateOf<Pair<Int, NewScope>>(-1 to context)}

    scopesList.forEach { scope ->
        scope.updateDropZones(draggingBlock,
        with(density) {(20.dp).toPx()})
    }

    val onDragStart = remember {
        { offset: Offset, chosenBlock: Block ->
            isDragging = true
            touchPoint = offset
            draggingBlock = chosenBlock
            isDraggingBlockCanBeDeleted = true
        }
    }
    val onDragEnd = remember {
        { draggedBlock: Block ->
            isDragging = false
            isDraggingBlockCanBeDeleted = false
            if (!isDeleting) viewModel.relocateBlock(draggedBlock, currentInteractionScope)
            else viewModel.deleteBlock(currentInteractionScope)
            draggingBlock = Constant(context)
        }
    }

    val tabOnDragStart = remember {
        { offset: Offset, chosenBlock: Block ->
            isDragging = true
            touchPoint = offset
            draggingBlock = chosenBlock
            scopesList.forEach { scope ->
                scope.updateDropZones(draggingBlock,
                    with(density) {(20.dp).toPx()})
            }
        }
    }
    val tabOnDragEnd = remember {
        { draggedBlock: Block ->
            isDragging = false
            viewModel.addNewBlock(draggedBlock, currentInteractionScope)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NewScopeColor)
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
                var (alpha) = remember(dragOffset) {
                    isDeleting = trashRect.contains(Offset(dragOffset.x, dragOffset.y))
                    if (isDeleting) 0.3f to true else 0f to false
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
                    colors = CardDefaults.cardColors(containerColor = Black.copy(alpha = alpha)),
                    shape = RoundedCornerShape(10.dp)
                )
                {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.trash_fill),
                            contentDescription = stringResource(R.string.trash),
                            modifier = Modifier.fillMaxSize(0.8f),
                        )
                    }
                }
            }

            currentInteractionScope = context
            scopesList.forEach { scope ->
                if (scope.selfRect.contains(Offset(dragOffset.x, dragOffset.y))) {
                    currentInteractionScope = scope
                }
            }

            var completed = true
            for(i in currentInteractionScope.dropZones.indices){
                if(currentInteractionScope.dropZones[i].contains(Offset(dragOffset.x, dragOffset.y)))
                {
                    Box(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    currentInteractionScope.dropZones[i].left.roundToInt(),
                                    if(i != currentInteractionScope.dropZones.count()-1 ||
                                        (currentInteractionScope != context &&
                                        !currentInteractionScope.blockList.isEmpty()))
                                    {
                                        (currentInteractionScope.dropZones[i].top +
                                        (currentInteractionScope.dropZones[i].bottom -
                                            currentInteractionScope.dropZones[i].top) * 0.5)
                                            .roundToInt()
                                    }
                                    else{
                                        currentInteractionScope.dropZones[i].top.roundToInt()
                                    }
                                )
                            }
                            .zIndex(0.95f)
                    )
                    {
                        DrawShadow(null)
                    }
                    if (i != currentInteractionScope.dropZones.count() - 1 ||
                        currentInteractionScope != context)
                    {
                        scopesList.forEach { scope ->
                            scope.spacerPair.value = scope.spacerPair.value.copy(
                                first = if (currentInteractionScope.blockList.contains(
                                        draggingBlock
                                    )
                                    && currentInteractionScope.blockList.indexOf(draggingBlock) <= i
                                )
                                    i + 2 else i + 1,
                                second = currentInteractionScope
                            )
                        }
                    }

                    completed = false
                    break
                }
            }
            if (completed){
                scopesList.forEach { scope ->
                    scope.spacerPair.value = scope.spacerPair.value.copy(
                        first = -1,
                        second = currentInteractionScope
                    )
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

            RedactorArea(
                blockList,
                context,
                currentInteractionScope,
                draggingBlock,
                onDragStart,
                onDragEnd
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            )
            {
                    page -> when(page) {
                0 -> {
                    Card(
                        modifier = Modifier
                            .background(TabsBackground),
                        colors = CardDefaults.cardColors(containerColor = TabsBackground),
                        shape = RoundedCornerShape(0.dp),
                    )
                    {
                        ScrollableTabSample(
                            tabOnDragStart,
                            tabOnDragEnd,
                            variablesTabList,
                            listsTabList,
                            expressionsTabList,
                            constantsTabList,
                            convertersTabList,
                            otherTabList
                        )
                    }
                }
                1 -> {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = ConsoleColor),
                        shape = RoundedCornerShape(0.dp),
                    )
                    {
                        Row {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.8f)
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start,
                                state = rememberLazyListState(),

                            )
                            {
                                items(console.text) { text ->
                                    Text(
                                        text = text,
                                        fontSize = 24.sp,
                                        color = Color.White
                                    )
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
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ExecuteButtonColor,
                                )
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.play_128px),
                                    contentDescription = stringResource(R.string.icon),
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