package com.example.mobileapp.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.R
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.ui.theme.*

@Composable
fun DrawConstant(
    block: Constant,
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    isActive: Boolean
) {
    val dataColors = listOf(
        IntConstantColor,
        DoubleConstantColor,
        StringConstantColor,
        BlockColor
    )
    val dataTypes = listOf(
        stringResource(R.string.integer),
        stringResource(R.string.fractional),
        stringResource(R.string.string),
        stringResource(R.string.bool),
    )
    val defaultStringValue = stringResource(R.string.default_string_value)

    val selectedType = block.type
    val selectedColor = dataColors[dataTypes.indexOf(selectedType)]
    println(dataTypes.indexOf(selectedType))

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
        colors = CardDefaults.cardColors(containerColor = selectedColor),
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val focusManager = LocalFocusManager.current
            var value by remember(block.value) { mutableStateOf(block.value.toString()) }
            var booleanValue by remember { mutableStateOf(true) }
            var isFocused by remember { mutableStateOf(false) }

            fun setValue(){
                when (selectedType){
                    dataTypes[0] -> {
                        if (value.isNotEmpty()) {
                            val tmp = value.toIntOrNull() ?: 0
                            block.setValue(dataTypes[0], tmp)
                        } else {
                            block.setValue(dataTypes[0], 0)
                        }
                    }
                    dataTypes[1] -> {
                        if (value.isNotEmpty()) {
                            val tmp = value.toDoubleOrNull() ?: 0.0
                            block.setValue(dataTypes[1], tmp)
                        } else {
                            block.setValue(dataTypes[1], 0.0)
                        }
                    }
                    dataTypes[2] -> {
                        if (value.isNotEmpty()) {
                            block.setValue(dataTypes[2], value)
                        } else {
                            block.setValue(dataTypes[2], defaultStringValue)
                        }
                    }
                    dataTypes[3] -> {
                        block.setValue(dataTypes[3], booleanValue)
                    }
                }
                value = block.value.toString()
            }

            if(selectedType != dataTypes[3]){
                BasicTextField(
                    modifier = Modifier
                        .widthIn(min = 50.dp)
                        .fillMaxHeight()
                        .width((12 + value.length * 8.85).dp)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                            if (!isFocused) {
                                setValue()
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
                            setValue()
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType =
                            if (selectedType == dataTypes[0] ||
                                selectedType == dataTypes[1])
                                KeyboardType.Number
                            else
                                KeyboardType.Text
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .background(selectedColor, RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            innerTextField()
                        }
                    }
                )
            }
            else{
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .height(38.dp)
                        .width(48.dp)
                ) {
                    Button(
                        onClick = { if (isActive) expanded = true },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            booleanValue.toString(),
                            fontSize = 16.sp,
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf(true, false).forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        item.toString(),
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                },
                                onClick = {
                                    booleanValue = item
                                    setValue()
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
