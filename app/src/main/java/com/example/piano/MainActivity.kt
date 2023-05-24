package com.example.piano

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.piano.ui.theme.PianoTheme

/**
 * Resources:
 * https://www.reddit.com/r/piano/comments/3u6ke7/heres_some_midi_and_mp3_files_for_individual/
 * https://github.com/fluxtah/pianoroll
 */

class MainActivity : ComponentActivity() {

    private val viewModel = PianoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val highlightedNote by viewModel.highlightedNoteFlow.collectAsState()
            var showNoteNames by remember { mutableStateOf(true) }
            PianoTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF878A8C))
                ) {
                    PianoRoll(
                        startNote = Note(PitchClass.C, 3),
                        endNote = Note(PitchClass.C, 6),
                        options = PianoRollOptions(
                            highlightedNotes = highlightedNote?.chord ?: setOf(),
                            highlightKeyColor = Color.Green,
                            borderColor = Color.Transparent,
                            topBorderSize = 0f,
                            showNoteNames = showNoteNames
                        ),
                        onKeyPressed = { viewModel.onKeyPressed(it, context) }
                    )
                    Button(onClick = { showNoteNames = !showNoteNames }) {
                        Text(text = "Show names")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PianoTheme {
        Greeting("Android")
    }
}