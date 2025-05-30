package com.example.mobileapp.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.For
import com.example.mobileapp.classes.UseVariable

@Composable
fun DrawFor(block: For, onDragStart: (Offset, Block) -> Unit, onDragEnd: (Block) -> Unit,
            isActive: Boolean){
    val contentHeight = remember { mutableStateOf(48.dp) }
    val cardWidth = remember { mutableStateOf(220.dp) }
    val density = LocalDensity.current
    Box{
        Column{
            Card(
                modifier = Modifier
                    .height(152.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    }
                    .shadow(
                        elevation = 4.dp,
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                        block.selfRect = block.selfRect.copy(bottom = block.selfRect.bottom + with(density) { (contentHeight.value + 12.dp).toPx() })
                    }
                    .onSizeChanged { size ->
                        cardWidth.value = with(density) { size.width.toDp() }
                    },
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 10.dp
                ),
                colors = CardDefaults.cardColors(containerColor = Color(255, 96, 0)),
            )
            {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                )
                {
                    Column()
                    {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        )
                        {
                            Text(
                                "for (var",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.padding(end = 8.dp)
                            )

                            var value = block.iterableVar.name
                            if (isActive) block.iterableVar.scope.addVariable(value)
                            BasicTextField(
                                modifier = Modifier
                                    .widthIn(min = 50.dp)
                                    .height(38.dp)
                                    .width((12 + value.length * 8.85).dp),
                                value = value,
                                onValueChange = { newValue ->
                                    block.iterableVar.scope.deleteVariable(value)
                                    block.iterableVar.name = newValue
                                    block.startValue.name = newValue
                                    (block.stopCondition.leftValue as UseVariable).name =
                                        newValue
                                    (block.changeIterableVar.leftValue as UseVariable).name =
                                        newValue
                                    block.iterableVar.scope.addVariable(newValue)
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
                                            .background(Color.White, RoundedCornerShape(24.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        innerTextField()
                                    }
                                }
                            )

                            Text(
                                "=",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        block.startValue.valueRect =
                                            coordinates.boundsInWindow()
                                    }
                            )
                            {
                                key(block.startValue.value) {
                                    DrawBlock(
                                        block.startValue.value,
                                        onDragStart,
                                        onDragEnd,
                                        isActive
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(start = 24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        )
                        {
                            Text(
                                block.iterableVar.name,
                                fontSize = 24.sp,
                                color = Color.White,
                            )

                            var expanded by remember { mutableStateOf(false) }
                            var selectedOperation by remember { mutableStateOf("<") }
                            Box(
                                modifier = Modifier
                                    .height(38.dp)
                                    .width(54.dp)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Button(
                                    onClick = { if (isActive) expanded = true },
                                    contentPadding = PaddingValues(0.dp)
                                )
                                {
                                    Text(
                                        selectedOperation,
                                        fontSize = 24.sp,
                                    )
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    listOf(
                                        "==",
                                        "!=",
                                        ">",
                                        "<",
                                        ">=",
                                        "<=",
                                        "&&",
                                        "||"
                                    ).forEach { operator ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    operator,
                                                    fontSize = 20.sp,
                                                    modifier = Modifier.padding(horizontal = 8.dp)
                                                )
                                            },
                                            onClick = {
                                                selectedOperation = operator
                                                block.stopCondition.operator = operator
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        block.stopCondition.rightValueRect =
                                            coordinates.boundsInWindow()
                                    }
                            ) {
                                key(block.stopCondition.rightValue) {
                                    DrawBlock(
                                        block.stopCondition.rightValue,
                                        onDragStart,
                                        onDragEnd,
                                        isActive
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 24.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        )
                        {
                            Text(
                                block.iterableVar.name,
                                fontSize = 24.sp,
                                color = Color.White,
                            )

                            var expanded by remember { mutableStateOf(false) }
                            var selectedOperation by remember { mutableStateOf("+") }
                            Box(
                                modifier = Modifier
                                    .height(38.dp)
                                    .width(54.dp)
                                    .padding(horizontal = 8.dp)
                            ) {
                                Button(
                                    onClick = { if (isActive) expanded = true },
                                    contentPadding = PaddingValues(0.dp)
                                )
                                {
                                    Text(
                                        selectedOperation,
                                        fontSize = 24.sp,
                                    )
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    listOf("+", "-", "*", "/", "%", "^").forEach { operator ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    operator,
                                                    fontSize = 20.sp,
                                                    modifier = Modifier.padding(horizontal = 8.dp)
                                                )
                                            },
                                            onClick = {
                                                selectedOperation = operator
                                                block.changeIterableVar.operator = operator
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        block.changeIterableVar.rightValueRect =
                                            coordinates.boundsInWindow()
                                    }
                            )
                            {
                                key(block.changeIterableVar.rightValue) {
                                    DrawBlock(
                                        block.changeIterableVar.rightValue,
                                        onDragStart,
                                        onDragEnd,
                                        isActive
                                    )
                                }
                            }

                            Text(
                                ")",
                                fontSize = 24.sp,
                                color = Color.White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .height(contentHeight.value)
                    .width(32.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    },
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = Color(255, 96, 0)),
            ){}

            Card(
                modifier = Modifier
                    .height(12.dp)
                    .width(cardWidth.value)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    },
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 10.dp,
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                ),
                colors = CardDefaults.cardColors(containerColor = Color(255, 96, 0)),
            ){}
        }

        Card(
            modifier = Modifier
                .padding(start = 24.dp)
                .widthIn(min = 212.dp)
                .heightIn(min = 48.dp)
                .offset(y = 152.dp)
                .onPlaced { coordinates ->
                    block.contentRect = coordinates.boundsInWindow()
                }
                .onSizeChanged { size ->
                    contentHeight.value = with(density) { size.height.toDp() }
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(250, 250, 250))
        )
        {
            Column()
            {
                for (i in block.blockList.indices) {
                    val localBlock = block.blockList[i]
                    if ((block.spacerPair.value.first == i) && block.spacerPair.value.second == block) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                    key(block.hashCode()) {
                        Box {
                            DrawBlock(localBlock, onDragStart, onDragEnd, true)
                        }
                    }
                    if (i == block.blockList.count() - 1 && block.spacerPair.value.first
                        == block.blockList.count() && block.spacerPair.value.second == block
                    ) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}

