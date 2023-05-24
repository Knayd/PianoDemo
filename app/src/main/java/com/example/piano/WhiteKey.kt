package com.example.piano

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.piano.ui.theme.PianoTheme

@Composable
fun WhiteKey(
    modifier: Modifier = Modifier,
    note: Note,
    highlighted: Boolean = false,
    showNoteName: Boolean = true,
    textColor: Color = Color.Black,
    onClick: (Note) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val borderGradient = if (isPressed) listOf(
        Color(0xFFAFAFAF),
        Color(0xFFD5D5D5)
    ) else listOf(
        Color(0xFFCDCDCD),
        Color(0xFFFAFAFA)
    )

    val backgroundGradient = when {
        isPressed -> listOf(
            Color(0xFFC2C2C2),
            Color(0xFFD9D9D9),
        )

        highlighted -> listOf(Color.White, Color.Green)

        else -> listOf(
            Color(0xFFE4E4E4),
            Color(0xFFFFFFFF),
        )
    }

    val embossGradient = if (isPressed) listOf(
        Color(0xFFB9B9B9),
        Color(0xFFD6D6D6)
    ) else listOf(
        Color(0xFFD9D9D9),
        Color(0xFFFFFFFF)
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(interactionSource = interactionSource, indication = null) { onClick(note) }
            .width(48.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .zIndex(1f)
                .clip(RoundedCornerShape(3.dp))
                .background(Brush.verticalGradient(backgroundGradient))
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(borderGradient),
                    shape = RoundedCornerShape(3.dp)
                )
        ) {
            if (showNoteName) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    text = "${note.pitch.noteName}${note.octave}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = textColor
                )
            }
        }
        Box(
            modifier = Modifier
                .height(if (isPressed) 12.dp else 17.dp)
                .fillMaxWidth()
                .offset(y = (-3).dp)
                .clip(RoundedCornerShape(bottomEnd = 3.dp, bottomStart = 3.dp))
                .background(Brush.verticalGradient(embossGradient))
        )
    }
}

@Preview
@Composable
private fun WhiteKeyPreview() {
    PianoTheme {
        WhiteKey(note = Note(PitchClass.C, 3))
    }
}