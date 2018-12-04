package com.neadev.calc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

private val STATE_OPERAND1  = "Operand1"
private val STATE_PENDING_OPERATION = "PendingOperation"
private val STATE_OPERAND1_STORE = "Operand1Stored"

class MainActivity : AppCompatActivity() {

    var operand1: Double? = null
    var operand2: Double = 0.0
    var pendingOperation = "="
    val operationText: TextView? by lazy(LazyThreadSafetyMode.NONE)  { findViewById<TextView>(R.id.operationTextView) }
    val resultEditText: EditText by lazy { findViewById<EditText>(R.id.resultEditText) }
    val newNumEditText: EditText by lazy(LazyThreadSafetyMode.NONE) { findViewById<EditText>(R.id.newNumeditText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val btn0 = findViewById<Button>(R.id.btn0)
//        val btn1 = findViewById<Button>(R.id.btn1)
//        val btn2 = findViewById<Button>(R.id.btn2)
//        val btn3 = findViewById<Button>(R.id.btn3)
//        val btn4 = findViewById<Button>(R.id.btn4)
//        val btn5 = findViewById<Button>(R.id.btn5)
//        val btn6 = findViewById<Button>(R.id.btn6)
//        val btn7 = findViewById<Button>(R.id.btn7)
//        val btn8 = findViewById<Button>(R.id.btn8)
//        val btn9 = findViewById<Button>(R.id.btn9)
//        val btnDot = findViewById<Button>(R.id.btnDot)
//
//        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
//        val btnPlus = findViewById<Button>(R.id.btnPlus)
//        val btnMinus = findViewById<Button>(R.id.btnMinus)
//        val btnEquals = findViewById<Button>(R.id.btnEquals)
//        val btnDivide = findViewById<Button>(R.id.btnDivide)

        val listener = View.OnClickListener { v ->
            val btn = v as Button
            newNumEditText.append(btn.text)
        }

        btn0.setOnClickListener(listener)
        btn1.setOnClickListener(listener)
        btn2.setOnClickListener(listener)
        btn3.setOnClickListener(listener)
        btn4.setOnClickListener(listener)
        btn5.setOnClickListener(listener)
        btn6.setOnClickListener(listener)
        btn7.setOnClickListener(listener)
        btn8.setOnClickListener(listener)
        btn9.setOnClickListener(listener)
        btnDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val operation = (v as Button).text.toString()

            try {
                val value = newNumEditText.text.toString()
                performOperation(operation, value.toDouble())
            } catch(e: Exception) {
                newNumEditText.setText("")
            }
            pendingOperation = operation
            operationText?.text = operation

        }

        btnMultiply.setOnClickListener(opListener)
        btnDivide.setOnClickListener(opListener)
        btnPlus.setOnClickListener(opListener)
        btnMinus.setOnClickListener(opListener)
        btnEquals.setOnClickListener(opListener)
    }

    fun performOperation(operation: String, value: Double) {
        if(operand1 == null) {
            operand1 = value
        } else {

            if(pendingOperation == "=") pendingOperation = operation

            when(pendingOperation) {
                "=" -> operand1 = value
                "+" -> operand1 = operand1!! + value
                "-" -> operand1 = operand1!! - value
                "*" -> operand1 = operand1!! * value
                "/" -> if(value == 0.0) operand1 = Double.NaN else operand1 = operand1!! / value
            }
        }

        resultEditText.setText(operand1.toString())
        newNumEditText.setText("")


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if(operand1 !== null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORE, true)
        }

        outState.putString(STATE_PENDING_OPERATION, pendingOperation)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(STATE_OPERAND1_STORE)) {
            savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            null
        }

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)
        this.operationText?.text = pendingOperation
    }
}
