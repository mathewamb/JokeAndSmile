package com.example.jokeandsmile.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeandsmile.service.JokeApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CoroutineDemoViewModel : ViewModel() {
    private var demoJob: Job? = null

    private val _uiGenericState = MutableStateFlow("")
    val uiGenericState: StateFlow<String> = _uiGenericState

    private val _uiFirstQuestionState = MutableStateFlow("")
    val uiFirstQuestionState: StateFlow<String> = _uiFirstQuestionState

    private val _uiFirstAnswerState = MutableStateFlow("")
    val uiFirstAnswerState: StateFlow<String> = _uiFirstAnswerState

    private val _uiSecondQuestionState = MutableStateFlow("")
    val uiSecondQuestionState: StateFlow<String> = _uiSecondQuestionState

    private val _uiSecondAnswerState = MutableStateFlow("")
    val uiSecondAnswerState: StateFlow<String> = _uiSecondAnswerState

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val jokeApi = JokeApi.create()

    fun startCoroutineDemo() {
        demoJob?.cancel()
        demoJob = viewModelScope.launch {
            try {
                _uiFirstQuestionState.value = ""
                _uiFirstAnswerState.value = ""
                _uiSecondQuestionState.value = ""
                _uiSecondAnswerState.value = ""

                _uiGenericState.value = "Loading...We are working so hard to make it fast :)"

                showResultDialog()

                val ioResult = withContext(Dispatchers.IO) {
                    delay(1000)
                    "\nThank you for your patience"
                }

                _uiGenericState.value = ""
                _uiGenericState.value += "$ioResult\n"

                val joke1 = async { jokeApi.getRandomJoke() }
                val joke2 = async { jokeApi.getRandomJoke() }

                val jokeres1 = joke1.await()
                val jokeres2 = joke2.await()

                _uiFirstQuestionState.value += "Q. ${jokeres1.setup}\n"
                _uiSecondQuestionState.value += "Q. ${jokeres2.setup}\n"

                withContext(Dispatchers.IO) {
                    delay(1000)
                }


                _uiFirstAnswerState.value += "A. ${jokeres1.punchline}\n"
                _uiSecondAnswerState.value += "A. ${jokeres2.punchline}\n"
            } catch (e: CancellationException) {
                _uiGenericState.value += "\nDemo cancelled!"
            } catch (e: Exception) {
                _uiGenericState.value += "\nError: ${e.localizedMessage}"
            }
        }
    }

   

    private fun showResultDialog() {
        _showDialog.value = true
        // Automatically close after 2 seconds
        viewModelScope.launch {
            delay(2000)
            _showDialog.value = false
        }
    }
}
