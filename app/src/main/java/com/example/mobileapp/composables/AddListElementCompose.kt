package com.example.mobileapp.composables


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.classes.AddListElement
import com.example.mobileapp.classes.Block

@Composable
fun DrawAddListElement(block: AddListElement, onDragStart: (Offset, Block) -> Unit, onDragEnd: (Block) -> Unit,
              isActive: Boolean){
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
            Text("add", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))

            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        block.valueRect = coordinates.boundsInWindow()
                    }
            )
            {
                DrawBlock(block.value, onDragStart, onDragEnd, isActive)
            }

            Text("to list", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))

            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        block.sourceRect = coordinates.boundsInWindow()
                    }
            )
            {
                if (block.source != null) {
                    key(block.source) {
                        DrawBlock(block.source as Block, onDragStart, onDragEnd, isActive)
                    }
                }
                else{
                    Card(
                        modifier = Modifier
                            .height(38.dp)
                            .width(56.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(150, 150, 150))
                    )
                    {

                    }
                }
            }
        }
    }
}
