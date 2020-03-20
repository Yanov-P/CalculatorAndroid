package com.example.calculator

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.keelar.exprk.Expressions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val buttons = ArrayList<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(editText.text.isNotEmpty()){
            val msg = editText.text.toString()
            val res = Expressions().evalToString(msg)
            textView.text = res
        }

        loopThrough(tableLayout)
        for (i in buttons){
            i.setOnClickListener(this)
        }

        button_send.setOnClickListener {
            val msg = editText.text.toString()
            val res = Expressions().evalToString(msg)
            textView.text = res
        }

        button_del.setOnClickListener {
            editText.setText("")
        }

        button_back.setOnClickListener {
            if(editText.selectionStart > 0)
                editText.setSelection(editText.selectionStart - 1)
        }

        button_forward.setOnClickListener {
            if(editText.selectionStart < editText.text.length)
                editText.setSelection(editText.selectionStart + 1)
        }

    }

    override fun onClick(v: View?) {
        var currText = editText.text.toString()
        val firstPart = currText.substring(0, editText.selectionStart)
        val secondPart = currText.substring(editText.selectionStart, currText.length)
//        currText += (v as Button).text
        val res = firstPart + (v as Button).text + secondPart
        editText.setText(res)
        editText.setSelection(firstPart.length + 1)
    }

    private fun loopThrough(parent: ViewGroup) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)

            if (child is Button) buttons.add(child)
            else if (child is ViewGroup) loopThrough(child)
        }
    }
}
