package com.example.jokeandsmile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jokeandsmile.viewmodel.CoroutineDemoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    CoroutineDemoScreen()
                }
            }
        }
    }
}

@Composable
fun CoroutineDemoScreen(viewModel: CoroutineDemoViewModel = viewModel()) {

    val genericState by viewModel.uiGenericState.collectAsState()
    val firstQuestionState by viewModel.uiFirstQuestionState.collectAsState()
    val firstAnswerState by viewModel.uiFirstAnswerState.collectAsState()
    val secondQuestionState by viewModel.uiSecondQuestionState.collectAsState()
    val secondAnswerState by viewModel.uiSecondAnswerState.collectAsState()

    val showDialog by viewModel.showDialog.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.smile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

       Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

        ) {


            Text(
                "Joke and Smile",

                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )


            Spacer(Modifier.height(50.dp))



            Button(
                onClick = { viewModel.startCoroutineDemo() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Make Me Smile")
            }
            Spacer(Modifier.height(25.dp))

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { /* Ignore or add viewModel._showDialog.value = false if you want manual close */ },
                    text = { Text(genericState) },
                    confirmButton = {}
                )
            }


            Spacer(Modifier.height(24.dp))


            Text(
                firstQuestionState,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(firstAnswerState)

            if (!showDialog && genericState.isNotEmpty()) {

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 2.dp,             // Line thickness
                    color = Color.Black           // Line color
                )
            }

            Text(
                secondQuestionState,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(secondAnswerState)


        }
    }
}
