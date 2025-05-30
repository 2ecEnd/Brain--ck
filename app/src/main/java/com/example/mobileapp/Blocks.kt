package com.example.mobileapp


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileapp.classes.AddListElement
import com.example.mobileapp.classes.Block
import com.example.mobileapp.classes.Constant
import com.example.mobileapp.classes.DeclareVariable
import com.example.mobileapp.classes.IfElse
import com.example.mobileapp.classes.MathExpression
import com.example.mobileapp.classes.BoolExpression
import com.example.mobileapp.classes.DeleteListElement
import com.example.mobileapp.classes.For
import com.example.mobileapp.classes.ListConstant
import com.example.mobileapp.classes.Print
import com.example.mobileapp.classes.SetListElement
import com.example.mobileapp.classes.SetVariable
import com.example.mobileapp.classes.UseListElement
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
import com.example.mobileapp.composables.DrawSetListElement
import com.example.mobileapp.composables.DrawSetVariable
import com.example.mobileapp.composables.DrawUseListElement
import com.example.mobileapp.composables.DrawUseVariable

@Composable
fun DrawBlock(block: Block,
              onDragStart: (Offset, Block) -> Unit,
              onDragEnd: (Block) -> Unit,
              isActive: Boolean
)
{
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
        is SetListElement -> DrawSetListElement(block, onDragStart, onDragEnd, isActive)
        is UseListElement -> DrawUseListElement(block, onDragStart, onDragEnd, isActive)
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