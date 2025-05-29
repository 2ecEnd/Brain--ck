package com.example.mobileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapp.classes.AddListElement
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.Empty
import com.example.mobileapp.classes.IfElse
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.classes.DeleteListElement
import com.example.mobileapp.classes.For
import com.example.mobileapp.classes.ListConstant
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseVariable
import com.example.mobileapp.composables.DrawAddListElement
import com.example.mobileapp.composables.DrawBoolExpression
import com.example.mobileapp.composables.DrawConstant
import com.example.mobileapp.composables.DrawDeclareVariable
import com.example.mobileapp.composables.DrawDeleteListElement
import com.example.mobileapp.composables.DrawFor
import com.example.mobileapp.composables.DrawIfElse
import com.example.mobileapp.composables.DrawListConstant
import com.example.mobileapp.composables.DrawMathExpression
import com.example.mobileapp.composables.DrawPrint
import com.example.mobileapp.composables.DrawSetVariable
import com.example.mobileapp.composables.DrawUseVariable

@Composable
fun DrawBlock(block: Block, onDragStart: (Offset, Block) -> Unit, onDragEnd: (Block) -> Unit,
              isActive: Boolean){

    when(block) {
        is DeclareVariable -> DrawDeclareVariable(block, onDragStart, onDragEnd, isActive)
        is SetVariable -> DrawSetVariable(block, onDragStart, onDragEnd, isActive)
        is MathExpression -> DrawMathExpression(block, onDragStart, onDragEnd, isActive)
        is Constant -> DrawConstant(block, onDragStart, onDragEnd, isActive)
        is ListConstant -> DrawListConstant(block, onDragStart, onDragEnd, isActive)
        is Print -> DrawPrint(block, onDragStart, onDragEnd, isActive)
        is UseVariable -> DrawUseVariable(block, onDragStart, onDragEnd, isActive)
        is BoolExpression -> DrawBoolExpression(block, onDragStart, onDragEnd, isActive)
        is IfElse -> DrawIfElse(block, onDragStart, onDragEnd, isActive)
        is AddListElement -> DrawAddListElement(block, onDragStart, onDragEnd, isActive)
        is DeleteListElement -> DrawDeleteListElement(block, onDragStart, onDragEnd, isActive)
        is For -> DrawFor(block, onDragStart, onDragEnd, isActive)
    }
}

@Composable
fun DrawShadow(block: Block?){
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(48.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(25, 25, 25).copy(alpha = 0.25f)),
    )
    {}
}