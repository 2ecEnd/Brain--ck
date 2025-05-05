package com.example.mobileapp.classes

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi

abstract class BlockTemplate {
    abstract val name : String;
    abstract val color : Color;

    abstract fun runCode();
    @Composable
    abstract fun drawBlock();
}