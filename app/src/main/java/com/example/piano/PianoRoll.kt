package com.example.piano

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@Composable
@Preview
fun PianoRollPreview() {
    val startNote = Note(PitchClass.C, 0)
    val endNote = Note(PitchClass.E, 1)
    PianoRoll(startNote, endNote)
}

@Composable
fun PianoChord(
    chord: Set<Note>,
    options: PianoRollOptions = PianoRollOptions(),
    onKeyPressed: (Note) -> Unit = {}
) {
    val lower = chord.lower()
    val upper = chord.upper()
    val optionsExtended = options.copy(highlightedNotes = chord)
    PianoRoll(lower, upper, optionsExtended, onKeyPressed)
}

@Composable
fun PianoRoll(
    startNote: Note,
    endNote: Note,
    options: PianoRollOptions = PianoRollOptions(),
    onKeyPressed: (Note) -> Unit = {}
) {
    val notesStartIndex =
        PIANO_ROLL_NOTES.indexOfFirst { it.pitch == startNote.pitch && it.octave == startNote.octave }
    val notesEndIndex =
        PIANO_ROLL_NOTES.indexOfFirst { it.pitch == endNote.pitch && it.octave == endNote.octave } + 1
    val notes = PIANO_ROLL_NOTES.subList(notesStartIndex, notesEndIndex)
    val naturalNoteCount = notes.filter { it.pitch.isNatural }.size

    Box(
        Modifier
            .width((naturalNoteCount * (options.keyWidthScaled + options.keyMarginScaled)).dp)
            .height((options.keyHeightScaled + options.topBorderSizeScaled + options.bottomBorderSizeScaled).dp)
            .background(options.borderColor)
    ) {
        var xPos = options.keyMarginScaled / 2
        notes.forEachIndexed { index, note ->
            val isBlackNote = !note.pitch.isNatural
            val highlighted =
                options.highlightedNotes.any { it.pitch == note.pitch && it.octave == note.octave }
            if (!isBlackNote && index > 0) xPos += (options.keyWidthScaled + options.keyMarginScaled).roundToInt()
            var bias = 0f;
            if (note.pitch == PitchClass.Cs || note.pitch == PitchClass.Fs) bias = -(options.blackKeyWidthScaled / 4)
            if (note.pitch == PitchClass.Ds || note.pitch == PitchClass.As) bias = (options.blackKeyWidthScaled / 4)
            if (isBlackNote) {
                BlackKey(
                    modifier = Modifier
                        .width(options.blackKeyWidthScaled.dp)
                        .height(options.blackKeyHeightScaled.dp)
                        .zIndex(2f)
                        .offset(
                            x = (bias + xPos + (options.blackKeyWidthScaled).roundToInt()).dp,
                            y = options.topBorderSizeScaled.dp
                        ),
                    note = note,
                    highlighted = highlighted,
                    onClick = onKeyPressed
                )
            } else {
                WhiteKey(
                    modifier = Modifier
                        .width(options.keyWidthScaled.dp)
                        .height(options.keyHeightScaled.dp)
                        .zIndex(1f)
                        .offset(
                            x = (bias + xPos).dp,
                            y = options.topBorderSizeScaled.dp
                        ),
                    note = note,
                    highlighted = highlighted,
                    onClick = onKeyPressed,
                    showNoteName = options.showNoteNames,
                    textColor = options.whiteKeyTextColor
                )
            }
        }
    }
}

@Composable
private fun computeKeyColor(
    options: PianoRollOptions,
    isBlackKey: Boolean,
    isHighlightedKey: Boolean
): Color = when {
    isHighlightedKey -> options.highlightKeyColor
    isBlackKey -> options.blackKeyColor
    else -> options.whiteKeyColor
}

private const val BASE_KEY_WIDTH = 64f
private const val BASE_KEY_MARGIN = 4f
private const val BASE_KEY_HEIGHT = 256f
private const val BASE_ACC_KEY_WIDTH = 44f
private const val BASE_ACC_KEY_HEIGHT = 172f
private const val BASE_FONT_SIZE = 12f
private const val BASE_TOP_BORDER = 4f
private const val BASE_BOTTOM_BORDER = 4f

private val PIANO_ROLL_NOTES = generatePianoRollData()

private fun generatePianoRollData(): List<Note> {
    val list = mutableListOf<Note>()
    for (octave in -8..8) {
        list.add(Note(PitchClass.C, octave))
        list.add(Note(PitchClass.Cs, octave))
        list.add(Note(PitchClass.D, octave))
        list.add(Note(PitchClass.Ds, octave))
        list.add(Note(PitchClass.E, octave))
        list.add(Note(PitchClass.F, octave))
        list.add(Note(PitchClass.Fs, octave))
        list.add(Note(PitchClass.G, octave))
        list.add(Note(PitchClass.Gs, octave))
        list.add(Note(PitchClass.A, octave))
        list.add(Note(PitchClass.As, octave))
        list.add(Note(PitchClass.B, octave))
    }

    return list
}

data class PianoRollOptions(
    val sizeScale: Float = 1.0f,
    val keyWidth: Float = BASE_KEY_WIDTH,
    val blackKeyWidth: Float = BASE_ACC_KEY_WIDTH,
    val keyMargin: Float = BASE_KEY_MARGIN,
    val keyHeight: Float = BASE_KEY_HEIGHT,
    val blackKeyHeight: Float = BASE_ACC_KEY_HEIGHT,
    val topBorderSize: Float = BASE_TOP_BORDER,
    val bottomBorderSize: Float = BASE_BOTTOM_BORDER,
    val fontSize: Float = BASE_FONT_SIZE,
    val showNoteNames: Boolean = true,
    val highlightedNotes: Set<Note> = setOf(),
    val blackKeyColor: Color = Color.Black,
    val whiteKeyColor: Color = Color.White,
    val blackKeyTextColor: Color = Color.White,
    val whiteKeyTextColor: Color = Color.Black,
    val highlightKeyColor: Color = Color.Yellow,
    val borderColor: Color = Color.Black
) {
    val keyWidthScaled = keyWidth * sizeScale
    val blackKeyWidthScaled = blackKeyWidth * sizeScale
    val keyMarginScaled = keyMargin * sizeScale
    val keyHeightScaled = keyHeight * sizeScale
    val blackKeyHeightScaled = blackKeyHeight * sizeScale
    val topBorderSizeScaled = topBorderSize * sizeScale
    val bottomBorderSizeScaled = bottomBorderSize * sizeScale
    val fontSizeScaled = fontSize * sizeScale
}
