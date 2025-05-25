package com.example.mobileapp.classes

import android.content.res.Resources
import androidx.compose.ui.geometry.Rect
import com.example.mobileapp.R

class Context(val resources: Resources, val console: Console) : ComplexBlock()
{
    override var blockList = mutableListOf<BlockTemplate>()
    override var varList = mutableMapOf<String, Value>()
    override var parent: BlockTemplate? = null

    override var selfRect: Rect = Rect.Zero

    override fun deleteVariable(name: String)
    {
        varList.remove(name)
    }

    override fun addVariable(name: String)
    {
        varList.put(name, Value.INT(0))
    }

    override fun execute()
    {
        for (i in 0..<blockList.size )
        {
            try
            {
                blockList[i].execute()
            }
            catch (e: Exception)
            {
                if (e.message == null)
                    console.addString(resources.getString(R.string.unknown_error))
                else
                    console.addString(resources.getString(e.message!!.toInt()))
            }

        }
    }
}
