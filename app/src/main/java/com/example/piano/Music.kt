package com.example.piano

import kotlin.math.max

typealias Chord = Set<Note>

data class Note(val pitch: PitchClass, val octave: Int) : Comparable<Note> {
    override fun compareTo(other: Note): Int = Comparator<Note> { t, t2 ->
        when {
            t.pitch == t2.pitch && t.octave == t2.octave -> 0
            t.pitch > t2.pitch && t.octave >= t2.octave -> 1
            t.pitch < t2.pitch && t.octave > t2.octave -> 1
            else -> -1
        }
    }.compare(this, other)

    override fun toString(): String {
        return if (ACC_NOTES.contains(pitch)) {
            val shiftedNote = pitch.dec()
            "$shiftedNote#$octave"
        } else {
            "$pitch$octave"
        }
    }

    /**
     * Move up the major scale in intervals, for instance + 5 from C is G
     */
    operator fun plus(interval: Int): Note {
        var currentNote = this
        if (interval == 1) return this // the first is this
        var scaleIndex = 0
        var pos = 0
        while (pos < interval - 1) {
            val step = MAJOR_SCALE_INTERVALS[scaleIndex]
            repeat(step) {
                currentNote = currentNote.inc()
            }
            scaleIndex++
            if (scaleIndex == 7) {
                scaleIndex = 0
            }
            pos++
        }

        return currentNote
    }

    operator fun inc(): Note {
        var currentPitch = pitch
        return if (pitch == PitchClass.B) {
            Note(PitchClass.C, octave + 1)
        } else {
            Note(++currentPitch, octave)
        }
    }

    operator fun dec(): Note {
        var currentPitch = pitch
        return if (pitch == PitchClass.C) {
            Note(PitchClass.B, octave - 1)
        } else {
            Note(--currentPitch, octave)
        }
    }
}

enum class PitchClass(val noteName: String, val isNatural: Boolean) {
    C("C", true),
    Cs("C#", false),
    D("D", true),
    Ds("D#", false),
    E("E", true),
    F("F", true),
    Fs("F#", false),
    G("G", true),
    Gs("G#", false),
    A("A", true),
    As("A#", false),
    B("B", true);

    operator fun dec(): PitchClass {
        val index = max(values().indexOf(this) - 1, 0)
        return values()[index]
    }

    operator fun inc(): PitchClass {
        val values = values()
        val index = values.indexOf(this) + 1
        if (index >= values.size) {
            return values[0]
        }
        return values[index]
    }
}

/**
 * Turns any string sequence of notes such as "C0 D# G0" into a set of [Note]
 */
inline val String.chord: Chord
    get() = split(" ").map {
        when (it.length) {
            3 -> {
                val sharpFlat = it.substring(1, 2)
                val pitch = it.substring(0, 1)
                val octave = it.substring(2, 3).toInt()
                val pitchSum =
                    PitchClass.values()[PitchClass.valueOf(pitch).ordinal + (if (sharpFlat == "#") 1 else -1)]
                Note(pitchSum, octave)
            }

            2 -> {
                val note = it.substring(0, 1)
                val octave = it.substring(1, 2).toInt()
                Note(
                    PitchClass.valueOf(
                        note
                    ), octave
                )
            }

            else -> throw RuntimeException("Invalid format in $it")
        }
    }.toSet()


fun Set<Note>.lower(): Note = minOrNull()!!
fun Set<Note>.upper(): Note = maxOrNull()!!
fun between(from: Note, to: Note): Int =
    ((to.octave - from.octave) * 12) + PitchClass.values().indexOf(to.pitch)

private val ACC_NOTES =
    listOf(PitchClass.Cs, PitchClass.Ds, PitchClass.Fs, PitchClass.Gs, PitchClass.As)
private val MAJOR_SCALE_INTERVALS = listOf(2, 2, 1, 2, 2, 2, 1)
fun walkUpFrom(step: (Note) -> Unit) {

}