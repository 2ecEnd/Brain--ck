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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.classes.BlockTemplate
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.For
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseVariable

@Composable
fun DrawFor(block: For, onDragStart: (Offset, BlockTemplate) -> Unit, onDragEnd: (BlockTemplate) -> Unit,
              isActive: Boolean){
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
        Column ()
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                Text(
                    "for (var",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
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
                                .background(Color.White, RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            innerTextField()
                        }
                    }
                )
            }

            Card(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .widthIn(min = 200.dp)
                    .heightIn(min = 48.dp)
                    .onPlaced { coordinates ->
                        block.contentRect = coordinates.boundsInWindow()
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
                    for(i in block.blockList.indices){
                        var localBlock = block.blockList[i]
                        if ((block.spacerPair.value.first == i) && block.spacerPair.value.second == block){
                            Spacer(modifier = Modifier.height(48.dp))
                        }
                        key(block.hashCode()) {
                            Box() {
                                DrawBlock(localBlock, onDragStart, onDragEnd, true)
                            }
                        }
                        if (i == block.blockList.count() - 1 && block.spacerPair.value.first
                            == block.blockList.count() && block.spacerPair.value.second == block){
                            Spacer(modifier = Modifier.height(48.dp))
                        }
                    }
                }
            }
        }

    }
}
