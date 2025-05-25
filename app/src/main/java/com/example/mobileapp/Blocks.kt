package com.example.mobileapp

import android.R
import android.R.bool
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.BlockTemplate
import com.example.mobileapp.classes.ComplexBlock
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.Context
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.Empty
import com.example.mobileapp.classes.IfElse
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseVariable
import com.example.mobileapp.classes.Value
import kotlin.math.roundToInt

@Composable
fun DrawBlock(block: BlockTemplate, onDragStart: (Offset, BlockTemplate) -> Unit, onDragEnd: (BlockTemplate) -> Unit,
              isActive: Boolean, draggingBlock: MutableState<BlockTemplate>){

    when(block) {
        is DeclareVariable -> {
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Text("declare", fontSize = 16.sp, color = Color.White)
                    var value by remember { mutableStateOf(block.name) }
                    block.scope.addVariable(value)
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxHeight(0.8f),
                        value = value,
                        onValueChange = { newValue ->
                            block.scope.deleteVariable(value)
                            block.name = newValue
                            block.scope.addVariable(newValue)
                            value = newValue
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        ),
                        singleLine = true,
                        enabled = isActive,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(20.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }
        is SetVariable -> {
            var expanded by remember { mutableStateOf(false) }
            var selectedItem by remember { mutableStateOf("my variable") }
            block.value.parent = block
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            )
            {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Text("set", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))

                    Box(
                        modifier = Modifier
                            .height(38.dp),
                    )
                    {
                        Button(
                            onClick = { if (isActive) expanded = true },
                        )
                        { Text(text = selectedItem) }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            block.scope.varList.keys.toMutableList().forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        selectedItem = item
                                        block.name = item
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Text("to", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))

                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                block.valueRect = coordinates.boundsInWindow()
                            }
                    )
                    {
                        DrawBlock(block.value, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                    }
                }
            }
        }
        is MathExpression -> {
            var expanded by remember { mutableStateOf(false) }
            var selectedOperation by remember { mutableStateOf("+") }
            block.leftValue.parent = block
            block.rightValue.parent = block
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            )
            {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                block.leftValueRect = coordinates.boundsInWindow()
                            }
                            .alpha(if (block.leftValue == draggingBlock) 0.5f else 1f)
                    )
                    {
                        DrawBlock(block.leftValue, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                    }

                    Box(
                        modifier = Modifier.height(38.dp)
                    ) {
                        Button(
                            onClick = { if (isActive) expanded = true },
                        )
                        {
                            Text(
                                selectedOperation,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("+", "-", "*", "/", "%", "^").forEach { operation ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            operation,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    },
                                    onClick = {
                                        selectedOperation = operation
                                        block.operation = operation
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                block.rightValueRect = coordinates.boundsInWindow()
                            }
                            .alpha(if (block.rightValue == draggingBlock) 0.5f else 1f)
                    )
                    {
                        DrawBlock(block.rightValue, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                    }
                }
            }
        }
        is Constant -> {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(38.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            )
            {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    var focusManager = LocalFocusManager.current
                    var value by remember { mutableStateOf(block.value.toString()) }
                    var isFocused by remember { mutableStateOf(false) }
                    BasicTextField(
                        modifier = Modifier
                            .widthIn(min = 50.dp)
                            .fillMaxHeight()
                            .width((12 + value.length * 8.85).dp)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                                if (!isFocused) {
                                    if (value.isNotEmpty()) {
                                        block.value = Value.INT(value.toIntOrNull() ?: 0)
                                    } else {
                                        block.value = Value.INT(0)
                                    }
                                    value = block.value.toString()
                                }
                            },
                        value = value,
                        onValueChange = { newValue ->
                            value = newValue
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center
                        ),
                        singleLine = true,
                        enabled = isActive,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (value.isNotEmpty()) {
                                    block.value = Value.INT(value.toIntOrNull() ?: 0)
                                } else {
                                    block.value = Value.INT(0)
                                }
                                value = block.value.toString()
                                focusManager.clearFocus()
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(Color.White, RoundedCornerShape(20.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }
        is Print -> {
            block.content.parent = block
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            )
            {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Text("print", fontSize = 16.sp, color = Color.White)

                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                block.contentRect = coordinates.boundsInWindow()
                            }
                            .padding(horizontal = 8.dp)
                    )
                    {
                        DrawBlock(block.content, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                    }
                }
            }
        }
        is UseVariable -> {
            var expanded by remember { mutableStateOf(false) }
            var selectedItem by remember { mutableStateOf("my variable") }

            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            )
            {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    Box(
                        modifier = Modifier
                            .height(38.dp),
                    )
                    {
                        Button(
                            onClick = { if (isActive) expanded = true },
                        )
                        { Text(text = selectedItem) }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            block.scope.varList.keys.toMutableList().forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item) },
                                    onClick = {
                                        selectedItem = item
                                        block.name = item
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        is Empty -> {
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ){}
        }
        is BoolExpression -> {
            var expanded by remember { mutableStateOf(false) }
            var selectedOperation by remember { mutableStateOf("==") }
            block.leftValue.parent = block
            block.rightValue.parent = block
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                block.leftValueRect = coordinates.boundsInWindow()
                            }
                            .alpha(if (block.leftValueRect == draggingBlock) 0.5f else 1f)
                    ) {
                        DrawBlock(block.leftValue, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                    }

                    Box(
                        modifier = Modifier.height(38.dp)
                    ) {
                        Button(
                            onClick = { if (isActive) expanded = true },
                        )
                        {
                            Text(
                                selectedOperation,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("==", "!=", ">", "<", ">=", "<=", "&&", "||").forEach { operation ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            operation,
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    },
                                    onClick = {
                                        selectedOperation = operation
                                        block.operation = operation
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                block.rightValueRect = coordinates.boundsInWindow()
                            }
                            .alpha(if (block.rightValueRect == draggingBlock) 0.5f else 1f)
                    ) {
                        DrawBlock(block.rightValue, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                    }
                }
            }
        }
        is IfElse -> {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 96, 0)),
            )
            {
                Column (

                )
                {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {
                        Text(
                            "if",
                            fontSize = 24.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    block.conditionRect = coordinates.boundsInWindow()
                                }
                                .alpha(if (block.conditionRect == draggingBlock) 0.5f else 1f)
                        )
                        {
                            DrawBlock(block.condition, onDragStart, onDragEnd, isActive, remember{draggingBlock})
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .widthIn(min = 200.dp)
                            .heightIn(min = 48.dp)
                            .onGloballyPositioned { coordinates ->
                                block.ifRect = coordinates.boundsInWindow()
                                block.if_.selfRect = coordinates.boundsInWindow()
                                if(block.if_.blockList.isEmpty()) block.if_.dropZones.add(coordinates.boundsInWindow())
                            },
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            bottomStart = 10.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        colors = CardDefaults.cardColors(containerColor = Color(250, 250, 250))
                    )
                    {
                        Column ()
                        {
                            for(i in block.if_.blockList.indices){
                                var localBlock = block.if_.blockList[i]
                                if ((block.if_.spacerPair.value.first == i) && block.if_.spacerPair.value.second == block.if_){
                                    Spacer(modifier = Modifier.height(48.dp))
                                }
                                key(block.hashCode()) {
                                    var alpha = if (localBlock == draggingBlock) 0.5f else 1f
                                    Box(modifier = Modifier.alpha(alpha)) {
                                        DrawBlock(localBlock, onDragStart, onDragEnd, true, remember{draggingBlock})
                                    }
                                }
                                if (i == block.if_.blockList.count() - 1 && block.if_.spacerPair.value.first
                                    == block.if_.blockList.count() && block.if_.spacerPair.value.second == block.if_){
                                    Spacer(modifier = Modifier.height(48.dp))
                                }
                            }
                        }
                    }

                    Text(
                        "else",
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Card(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .widthIn(min = 200.dp)
                            .heightIn(min = 48.dp)
                            .onGloballyPositioned { coordinates ->
                                block.elseRect = coordinates.boundsInWindow()
                                block.else_.selfRect = coordinates.boundsInWindow()
                                if(block.else_.blockList.isEmpty()) block.else_.dropZones.add(coordinates.boundsInWindow())
                            },
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            bottomStart = 10.dp,
                            topEnd = 0.dp,
                            bottomEnd = 0.dp
                        ),
                        colors = CardDefaults.cardColors(containerColor = Color(250, 250, 250))
                    )
                    {
                        Column ()
                        {
                            for(i in block.else_.blockList.indices){
                                var localBlock = block.else_.blockList[i]
                                if (block.else_.spacerPair.value.first == i && block.else_.spacerPair.value.second == block.else_){
                                    Spacer(modifier = Modifier.height(48.dp))
                                }
                                key(block.hashCode()) {
                                    var alpha = if (localBlock == draggingBlock) 0.5f else 1f
                                    Box(modifier = Modifier.alpha(alpha)) {
                                        DrawBlock(localBlock, onDragStart, onDragEnd, true, remember{draggingBlock})
                                    }
                                }
                                if (i == block.else_.blockList.count() - 1 && block.else_.spacerPair.value.first
                                    == block.else_.blockList.count() && block.else_.spacerPair.value.second == block.else_){
                                    Spacer(modifier = Modifier.height(48.dp))
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun DrawShadow(block: BlockTemplate?){
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(48.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(25, 25, 25).copy(alpha = 0.25f)),
    )
    {

    }
}