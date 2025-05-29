package com.example.mobileapp.composables
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.DeclareVariable

@Composable
fun DrawDeclareVariable(block: DeclareVariable, onDragStart: (Offset, Block) -> Unit, onDragEnd: (Block) -> Unit,
              isActive: Boolean){
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
            var value = block.name
            if (isActive) block.scope.addVariable(value)
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
