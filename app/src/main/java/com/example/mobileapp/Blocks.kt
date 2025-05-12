package com.example.mobileapp

import android.R
import android.R.bool
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
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.mobileapp.classes.BlockTemplate
import kotlin.math.roundToInt

@Composable
fun DrawBlock(block: BlockTemplate?, onDragStart: (Offset) -> Unit, onDragEnd: (BlockTemplate?) -> Unit, isNeedToUpdateDropZone: Boolean, dropZoneUpdated: (Rect) -> Unit){
    var density = LocalDensity.current

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(48.dp)
            .padding(start = 16.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset -> onDragStart(offset) },
                    onDrag = { _, _ -> },
                    onDragEnd = { onDragEnd(block) }
                )
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(15.dp)
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
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(255, 128, 0)),
    )
    {

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

