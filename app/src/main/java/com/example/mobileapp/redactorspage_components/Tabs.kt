package com.example.mobileapp.redactorspage_components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobileapp.classes.Block
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    otherTabList: MutableList<Block>,
) {
    var state by remember { mutableIntStateOf(0) }
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
        ScrollableTabRow(
            selectedTabIndex = state,
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.Black,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[state])
                )
            }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = {
                        Text(
                            text = title, maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                    },
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
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        items(variablesTabList) { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
        item {
            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}

@Composable
fun Lists(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    listsTabList: MutableList<Block>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        items(listsTabList) { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
        item {
            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}

@Composable
fun Expressions(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    expressionsTabList: MutableList<Block>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        items(expressionsTabList) { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
        item {
            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}

@Composable
fun Constants(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    constantsTabList: MutableList<Block>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        items(constantsTabList) { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
        item {
            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}

@Composable
fun Converters(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    otherTabList: MutableList<Block>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        items(otherTabList) { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
        item {
            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}

@Composable
fun Other(
    onDragStart: (Offset, Block) -> Unit,
    onDragEnd: (Block) -> Unit,
    otherTabList: MutableList<Block>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        items(otherTabList) { item ->
            Box(modifier = Modifier.padding(12.dp)){
                DrawBlock(
                    item, onDragStart, onDragEnd, false
                )
            }
        }
        item {
            Spacer(modifier = Modifier.padding(64.dp))
        }
    }
}