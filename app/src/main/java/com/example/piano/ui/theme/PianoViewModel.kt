package com.example.piano.ui.theme

import android.content.Context
import android.media.MediaPlayer
import com.citizenwarwick.music.Note
import com.citizenwarwick.music.PitchClass
import com.example.piano.R
import kotlinx.coroutines.flow.MutableStateFlow


class PianoViewModel {

    private var notesPlayer: MediaPlayer? = null

    private val notesToHighlight = listOf("C3", "E3", "F#3", "B3")
    private var highlightedNoteIndex = 0
    val highlightedNoteFlow = MutableStateFlow<String?>(notesToHighlight[highlightedNoteIndex])

    fun onKeyPressed(note: Note, context: Context) {
        getNoteResource(note)?.let {
            notesPlayer = MediaPlayer.create(context, it).apply { start() }
        }

        if (note.toString() == highlightedNoteFlow.value) {
            highlightedNoteIndex += 1
            highlightedNoteFlow.value = notesToHighlight.getOrNull(highlightedNoteIndex)
        }
    }

    private fun getNoteResource(key: Note): Int? {
        val pitch = key.pitch
        val octave = key.octave
        return when (pitch) {
            PitchClass.C -> when (octave) {
                3 -> R.raw.c_3
                4 -> R.raw.c_4
                5 -> R.raw.c_5
                6 -> R.raw.c_6
                else -> null
            }

            PitchClass.Cs -> when (octave) {
                3 -> R.raw.c_sharp_3
                4 -> R.raw.c_sharp_4
                5 -> R.raw.c_sharp_5
                else -> null
            }

            PitchClass.D -> when (octave) {
                3 -> R.raw.d_3
                4 -> R.raw.d_4
                5 -> R.raw.d_5
                else -> null
            }

            PitchClass.Ds -> when (octave) {
                3 -> R.raw.d_sharp_3
                4 -> R.raw.d_sharp_4
                5 -> R.raw.d_sharp_5
                else -> null
            }

            PitchClass.E -> when (octave) {
                3 -> R.raw.e_3
                4 -> R.raw.e_4
                5 -> R.raw.e_5
                else -> null
            }

            PitchClass.F -> when (octave) {
                3 -> R.raw.f_3
                4 -> R.raw.f_4
                5 -> R.raw.f_5
                else -> null
            }

            PitchClass.Fs -> when (octave) {
                3 -> R.raw.f_sharp_3
                4 -> R.raw.f_sharp_4
                5 -> R.raw.f_sharp_5
                else -> null
            }

            PitchClass.G -> when (octave) {
                3 -> R.raw.g_3
                4 -> R.raw.g_4
                5 -> R.raw.g_5
                else -> null
            }

            PitchClass.Gs -> when (octave) {
                3 -> R.raw.g_sharp_3
                4 -> R.raw.g_sharp_4
                5 -> R.raw.g_sharp_5
                else -> null
            }

            PitchClass.A -> when (octave) {
                3 -> R.raw.a_3
                4 -> R.raw.a_4
                5 -> R.raw.a_5
                else -> null
            }

            PitchClass.As -> when (octave) {
                3 -> R.raw.a_sharp_3
                4 -> R.raw.a_sharp_4
                5 -> R.raw.a_sharp_5
                else -> null
            }

            PitchClass.B -> when (octave) {
                3 -> R.raw.b_3
                4 -> R.raw.b_4
                5 -> R.raw.b_5
                else -> null
            }

            else -> null
        }
    }
}