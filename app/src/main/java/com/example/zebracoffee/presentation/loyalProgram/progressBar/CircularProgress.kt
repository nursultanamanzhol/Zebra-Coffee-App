package com.example.zebracoffee.presentation.loyalProgram.progressBar


import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.zebracoffee.R
import com.example.zebracoffee.ui.theme.BackLoyaltyCard

@Composable
fun CircularProgress(
    progressPercentage: Int,
    modifier: Modifier = Modifier,
    primaryProgressColor: Color = colorResource(id = R.color.fill_colors_buttons),
    backgroundProgressColor: Color = BackLoyaltyCard,
    values: () -> ProgressValues,
    maxValue: Float,
    strokeWidth: Float,
    animationSpec: AnimationSpec<Float> = tween(500),
    centerContent: (@Composable () -> Unit)? = null,
) {
    val colorCircleNotProgress: Color = if (progressPercentage == 100) {
        Color.Transparent
    } else {
        backgroundProgressColor.copy(alpha = 0.5f)
    }

    val colorCircleProgress: Color = if (progressPercentage == 100) {
        Color.Transparent
    } else {
        primaryProgressColor
    }
    val primaryValue = values().primary
    val primaryWrappedValue =
        if (primaryValue > maxValue) maxValue
        else {
            if (primaryValue < 0) 0f else primaryValue
        }
    val animatedPrimaryValue = animateFloatAsState(
        targetValue = primaryWrappedValue,
        animationSpec = animationSpec
    )
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            drawProgress(
                color = colorCircleNotProgress,
                strokeWidth = strokeWidth
            )

            drawProgress(
                color = colorCircleProgress,
                value = calculateCurrentValue(animatedPrimaryValue.value, maxValue),
                strokeWidth = strokeWidth
            )
        }

        centerContent?.let {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(strokeWidth.dp),
                contentAlignment = Alignment.Center
            ) {
                it()
            }
        }
    }
}

private fun DrawScope.drawProgress(
    value: Float = 360f,
    color: Color,
    strokeWidth: Float
) {
    drawArc(
        color = color,
        startAngle = 270f,
        sweepAngle = value,
        useCenter = false,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}



