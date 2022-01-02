package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    // properties for calculator's main activity
    private var prevOp = "="
    private var operand1 = 0.0
    private var operand2 = 0.0
    private var newValue = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find views
        val digitButtonIds: IntArray =
            intArrayOf(
                R.id.btn_zero,
                R.id.btn_one,
                R.id.btn_two,
                R.id.btn_three,
                R.id.btn_four,
                R.id.btn_five,
                R.id.btn_six,
                R.id.btn_seven,
                R.id.btn_eight,
                R.id.btn_nine,
            )
        val operatorButtonIds: IntArray =
            intArrayOf(
                R.id.btn_plus,
                R.id.btn_minus,
                R.id.btn_multiply,
                R.id.btn_divide,
                R.id.btn_equal,
            )
        val screen: TextView = findViewById(R.id.screen)
        val digitButtons: List<Button> = digitButtonIds.map { id: Int -> findViewById(id) }
        val dotButton: Button = findViewById(R.id.btn_dot)
        val operatorButtons: List<Button> =
            operatorButtonIds.map { id: Int -> findViewById(id) }
        val clearButton: Button = findViewById(R.id.btn_clear)
        val negativeButton: Button = findViewById(R.id.btn_negative)

        // digit buttons' handler
        digitButtons.forEach { button: Button ->
            button.setOnClickListener {
                if (newValue) {
                    screen.text = (it as Button).text
                    newValue = false
                } else {
                    if (screen.text.endsWith('0')) {
                        val screenText = screen.text.toString()
                        screen.text = screenText.substring(0, screenText.length - 1)
                    }
                    screen.append((it as Button).text)
                }
            }
        }

        // dot button's handler
        dotButton.setOnClickListener {
            if (!screen.text.endsWith('.')) {
                if (newValue) {
                    screen.text = "0."
                    newValue = false
                } else {
                    screen.append(".")
                }
            }
        }

        // operator button's handler
        operatorButtons.forEach { button: Button ->
            button.setOnClickListener {
                if (!newValue) {
                    newValue = true
                    when (prevOp) {
                        "=" -> {
                            operand1 = screen.text.toString().toDouble()
                        }
                        "+" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 + operand2
                            screen.text = result.toString()
                            operand1 = result
                            operand2 = 0.0

                        }
                        "-" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 - operand2
                            screen.text = result.toString()
                            operand1 = result
                            operand2 = 0.0
                        }
                        "*" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 * operand2
                            screen.text = result.toString()
                            operand1 = result
                            operand2 = 0.0
                        }
                        "/" -> {
                            operand2 = screen.text.toString().toDouble()
                            val result: Double = operand1 / operand2
                            screen.text = result.toString()
                            operand1 = result
                            operand2 = 0.0
                        }
                    }
                }
                prevOp = (it as Button).text.toString()
            }
        }

        // clear button's handler
        clearButton.setOnClickListener {
            screen.text = "0"
            newValue = true
            operand1 = 0.0
            operand2 = 0.0
            prevOp = "="
        }

        // negative button's handler
        negativeButton.setOnClickListener {
            if (newValue) {
                screen.text = "-0"
                newValue = false
            } else {
                if (!screen.text.startsWith('-')) {
                    // prepend "-"
                    "-${screen.text}".also { screen.text = it }
                } else {
                    // remove "-"
                    screen.text = screen.text.toString().substring(1)
                }
            }
        }
    }

}