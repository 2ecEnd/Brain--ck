package com.example.mobileapp.composables


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.IfElse

@Composable
fun DrawIfElse(block: IfElse, onDragStart: (Offset, Block) -> Unit, onDragEnd: (Block) -> Unit,
               isActive: Boolean){
    val ifHeight = remember { mutableStateOf(48.dp) }
    val elseHeight = remember { mutableStateOf(48.dp) }
    val cardWidth = remember { mutableStateOf(220.dp) }
    val cardHeight = remember { mutableStateOf(48.dp) }
    val density = LocalDensity.current
    block.condition.parent = block
    Box {
        Column {
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
                    )
                    .onGloballyPositioned { coordinates ->
                        block.selfRect = coordinates.boundsInWindow()
                        block.selfRect = block.selfRect.copy(bottom = block.selfRect.bottom +
                                with(density) {(ifHeight.value + elseHeight.value + 44.dp).toPx()})
                    }
                    .onSizeChanged { size ->
                        cardWidth.value = with(density) { size.width.toDp() }
                        cardHeight.value = with(density) { size.height.toDp() }
                    },
                shape = RoundedCornerShape(
                    topEnd = 10.dp,
                    topStart = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 10.dp
                ),
                colors = CardDefaults.cardColors(containerColor = Color(255, 96, 0)),
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
                    )
                    {
                        key(block.condition) {
                            DrawBlock(block.condition, onDragStart, onDragEnd, isActive)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .height(ifHeight.value)
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
                    .height(32.dp)
                    .width(cardWidth.value)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset -> onDragStart(offset, block) },
                            onDrag = { _, _ -> },
                            onDragEnd = { onDragEnd(block) }
                        )
                    },
                shape = RoundedCornerShape(
                    topEnd = 10.dp,
                    topStart = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 10.dp
                ),
                colors = CardDefaults.cardColors(containerColor = Color(255, 96, 0)),
            )
            {
                Text(
                    "else",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            Card(
                modifier = Modifier
                    .height(elseHeight.value)
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
                .widthIn(min = 200.dp)
                .heightIn(min = 48.dp)
                .offset(y = cardHeight.value)
                .onPlaced { coordinates ->
                    block.ifRect = coordinates.boundsInWindow()
                    block.ifBlock.selfRect = coordinates.boundsInWindow()
                }
                .onSizeChanged { size ->
                    ifHeight.value = with(density) { size.height.toDp() }
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(250, 250, 250))
        )
        {
            Column()
            {
                for (i in block.ifBlock.blockList.indices) {
                    val localBlock = block.ifBlock.blockList[i]
                    if ((block.ifBlock.spacerPair.value.first == i) && block.ifBlock.spacerPair.value.second == block.ifBlock) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                    key(block.hashCode()) {
                        Box{
                            DrawBlock(localBlock, onDragStart, onDragEnd, true)
                        }
                    }
                    if (i == block.ifBlock.blockList.count() - 1 && block.ifBlock.spacerPair.value.first
                        == block.ifBlock.blockList.count() && block.ifBlock.spacerPair.value.second == block.ifBlock
                    ) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .padding(start = 24.dp)
                .widthIn(min = 200.dp)
                .heightIn(min = 48.dp)
                .offset(y = cardHeight.value + ifHeight.value + 32.dp)
                .onPlaced { coordinates ->
                    block.elseRect = coordinates.boundsInWindow()
                    block.elseBlock.selfRect = coordinates.boundsInWindow()
                }
                .onSizeChanged { size ->
                    elseHeight.value = with(density) { size.height.toDp() }
                },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(250, 250, 250))
        )
        {
            Column()
            {
                for (i in block.elseBlock.blockList.indices) {
                    val localBlock = block.elseBlock.blockList[i]
                    if (block.elseBlock.spacerPair.value.first == i && block.elseBlock.spacerPair.value.second == block.elseBlock) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                    key(block.hashCode()) {
                        Box {
                            DrawBlock(localBlock, onDragStart, onDragEnd, true)
                        }
                    }
                    if (i == block.elseBlock.blockList.count() - 1 && block.elseBlock.spacerPair.value.first
                        == block.elseBlock.blockList.count() && block.elseBlock.spacerPair.value.second == block.elseBlock
                    ) {
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}
