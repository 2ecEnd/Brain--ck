package com.example.mobileapp.redactorspage_components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobileapp.classes.Block
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mobileapp.DrawBlock
import com.example.mobileapp.R

@Composable
fun ScrollableTabSample(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    variablesTabList: MutableList<Block>,
    listsTabList: MutableList<Block>,
    expressionsTabList: MutableList<Block>,
    constantsTabList: MutableList<Block>,
    convertersTabList: MutableList<Block>,
    otherTabList: MutableList<Block>
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf(
        stringResource(R.string.variables),
        stringResource(R.string.lists),
        stringResource(R.string.expressions),
        stringResource(R.string.constants),
        stringResource(R.string.converters),
        stringResource(R.string.other),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScrollableTabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = {
                        Text(
                            text = title, maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }

        when (state) {
            0 -> Variables(onDragStart, onDragEnd, variablesTabList)
            1 -> Lists(onDragStart, onDragEnd, listsTabList)
            2 -> Expressions(onDragStart, onDragEnd, expressionsTabList)
            3 -> Constants(onDragStart, onDragEnd, constantsTabList)
            4 -> Converters(onDragStart, onDragEnd, convertersTabList)
            5 -> Other(onDragStart, onDragEnd, otherTabList)
        }
    }
}

@Composable
fun Variables(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    variablesTabList: MutableList<Block>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        variablesTabList.forEach { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
    }
}

@Composable
fun Lists(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    listsTabList: MutableList<Block>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        listsTabList.forEach { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
    }
}

@Composable
fun Expressions(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    expressionsTabList: MutableList<Block>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        expressionsTabList.forEach { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
    }
}

@Composable
fun Constants(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    constantsTabList: MutableList<Block>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        constantsTabList.forEach { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
    }
}

@Composable
fun Converters(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    otherTabList: MutableList<Block>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        otherTabList.forEach { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
    }
}

@Composable
fun Other(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    otherTabList: MutableList<Block>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        otherTabList.forEach { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
    }
}