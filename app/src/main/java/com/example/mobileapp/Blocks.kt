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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.classes.BlockTemplate
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.SetVariable
import kotlin.math.roundToInt

@Composable
fun DrawBlock(block: BlockTemplate, onDragStart: (Offset, BlockTemplate) -> Unit, onDragEnd: (BlockTemplate) -> Unit,
              isNeedToUpdateDropZone: Boolean, dropZoneUpdated: (Rect) -> Unit, isActive: Boolean){

    when(block){
        is DeclareVariable -> {var density = LocalDensity.current
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
                    .padding(start = 16.dp)
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
                        if(isNeedToUpdateDropZone) {
                            with(density) {
                                var position = coordinates.positionInWindow()
                                var newZone = Rect(
                                    left = position.x,
                                    top = position.y + 48.dp.toPx(),
                                    right = position.x + 200.dp.toPx(),
                                    bottom = position.y + 48.dp.toPx() + 48.dp.toPx()
                                )
                                dropZoneUpdated(newZone)
                            }
                        }
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
                    var value by remember{ mutableStateOf("my variable") }
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxHeight(0.7f),
                        value = value,
                        onValueChange = {
                                newValue ->
                            block.scope.deleteVariable(value)
                            block.name = newValue
                            block.scope.addVariable(newValue)
                            value = newValue
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp, textAlign = TextAlign.Center),
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
            }}
        is SetVariable -> {var density = LocalDensity.current
            var expanded by remember { mutableStateOf(false) }
            var items = remember { block.scope.varList.keys.toMutableList()  }
            var selectedItem by remember { mutableStateOf("my variable") }

            Card(
                modifier = Modifier
                    .width(310.dp)
                    .height(48.dp)
                    .padding(start = 16.dp)
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
                        if(isNeedToUpdateDropZone) {
                            with(density) {
                                var position = coordinates.positionInWindow()
                                var newZone = Rect(
                                    left = position.x,
                                    top = position.y + 48.dp.toPx(),
                                    right = position.x + 200.dp.toPx(),
                                    bottom = position.y + 48.dp.toPx() + 48.dp.toPx()
                                )
                                dropZoneUpdated(newZone)
                            }
                        }
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
                    Text("set", fontSize = 16.sp, color = Color.White)

                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.7f),
                    )
                    {
                        Button(
                            onClick = { if (isActive) expanded = true },
                        )
                        { Text(text = selectedItem) }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ){
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

                    Text("to", fontSize = 16.sp, color = Color.White)

                    var value by remember{ mutableStateOf("0") }
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxHeight(0.7f),
                        value = value,
                        onValueChange = {
                            newValue -> value = newValue
                            block.value = newValue
                        },
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp, textAlign = TextAlign.Center),
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
            }}
    }

}

@Composable
fun DrawShadow(block: BlockTemplate?){
    Card(
        modifier = Modifier
            .width(184.dp)
            .height(48.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(25, 25, 25).copy(alpha = 0.25f)),
    )
    {

    }
}