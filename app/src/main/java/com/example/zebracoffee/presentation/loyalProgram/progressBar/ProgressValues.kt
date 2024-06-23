package com.example.zebracoffee.presentation.loyalProgram.progressBar


data class ProgressValues(
    val primary: Float,
)
fun calculateCurrentValue(value: Float, max: Float): Float{
    return value / max * 360f
}