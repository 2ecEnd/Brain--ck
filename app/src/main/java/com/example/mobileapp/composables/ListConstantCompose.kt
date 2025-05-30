package com.example.mobileapp.composables

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.R
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.ListConstant
import com.example.mobileapp.ui.theme.*

@Composable
fun DrawListConstant(
    block: ListConstant,
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    isActive: Boolean
) {
    block.blockList.forEach { item ->
        item.parent = block
    }
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
                shape = RoundedCornerShape(20.dp)
            )
            .onGloballyPositioned { coordinates ->
                block.selfRect = coordinates.boundsInWindow()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(min = 56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            block.blockList.forEach{ block ->
                Box(modifier = Modifier.padding(horizontal = 4.dp)){
                    key(block.hashCode()) {
                        DrawBlock(block, onDragStart, onDragEnd, isActive)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .width(56.dp)
                    .height(38.dp)
                    .onGloballyPositioned { coordinates ->
                        block.addBlockRect = coordinates.boundsInWindow()
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = BlockShadow),
            ) {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(stringResource(R.string.plus), fontSize = 24.sp)
                }
            }
        }
    }
}
