package com.example.mobileapp.composables


import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.R
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.ui.theme.*

@Composable
fun DrawBoolExpression(
    block: BoolExpression,
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    isActive: Boolean
)
{
    val operators = listOf(
        stringResource(R.string.equals),
        stringResource(R.string.not_equals),
        stringResource(R.string.great),
        stringResource(R.string.less),
        stringResource(R.string.great_or_equals),
        stringResource(R.string.less_or_equals),
        stringResource(R.string.and),
        stringResource(R.string.or)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOperation by remember { mutableStateOf(operators[0]) }
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
        colors = CardDefaults.cardColors(containerColor = BlockColor),
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
            ) {
                key(block.leftValue) {
                    DrawBlock(block.leftValue, onDragStart, onDragEnd, isActive)
                }
            }

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
                    operators.forEach { operator ->
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
                                block.operator = operator
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
            ) {
                key(block.rightValue) {
                    DrawBlock(block.rightValue, onDragStart, onDragEnd, isActive)
                }
            }
        }
    }
}
