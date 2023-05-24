package com.example.piano

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.piano.ui.theme.PianoTheme

@Composable
fun BlackKey(
    modifier: Modifier = Modifier,
    note: Note,
    highlighted: Boolean = false,
    onClick: (Note) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val borderColor = Color(if (isPressed) 0xFF313436 else 0xFF3A3D40)

    val backgroundGradient = when {
        isPressed -> listOf(
            Color(0xFF747577),
            Color(0xFF313436),
        )

        highlighted -> listOf(Color.White, Color.Green)

        else -> listOf(
            Color(0xFF878A8C),
            Color(0xFF3A3D40),
        )
    }

    val embossGradient = if (isPressed) listOf(
        Color(0xFF0C0D0E),
        Color(0xFF2C2E31)
    ) else listOf(
        Color(0xFF0D0F10),
        Color(0xFF3A3D40)
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .offset(y = (-2).dp)
            .border(1.dp, borderColor, RoundedCornerShape(bottomStart = 3.dp, bottomEnd = 3.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick(note) }
            .width(46.dp)
    )
    {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(22.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 3.dp, bottomStart = 3.dp))
                .background(Brush.verticalGradient(embossGradient))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = if (isPressed) (-7).dp else (-10).dp)
                .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                .background(Brush.verticalGradient(backgroundGradient))
        )
    }
}

@Preview
@Composable
private fun BlackKeyPreview() {
    PianoTheme {
        BlackKey(note = Note(PitchClass.C, 3))
    }
}