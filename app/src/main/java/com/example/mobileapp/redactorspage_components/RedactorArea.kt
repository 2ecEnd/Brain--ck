package com.example.mobileapp.redactorspage_components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.NewScope
import com.example.mobileapp.ui.theme.*

@Composable
fun RedactorArea(
    blockList: SnapshotStateList<Block>,
    context: NewScope,
    currentInteractionScope: NewScope,
    draggingBlock: Block?,
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .background(NewScopeBodyColor)
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
            .padding(end = 256.dp, bottom = 512.dp)
    )
    {
        for(i in blockList.indices){
            if (currentInteractionScope.spacerPair.value.first == i &&
                currentInteractionScope.spacerPair.value.second == context)
            {
                Spacer(modifier = Modifier.height(48.dp))
            }

            val block = blockList[i]
            key(block) {
                val alpha = if (block == draggingBlock) 0.5f else 1f
                Row(
                    modifier = Modifier
                        .widthIn(min = 512.dp)
                        .background(
                            if(block.isTroublesome)
                                Red.copy(alpha = 0.8f)
                            else
                                Color.Transparent)
                )
                {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .alpha(alpha)
                    )
                    {
                        DrawBlock(block, onDragStart, onDragEnd, true)
                    }
                }
            }
        }
    }
}