package com.simats.siddha.myapplication.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var messages = mutableStateListOf<ChatMessage>()
        private set

    var isLoading by mutableStateOf(false)
        private set

    var currentInput by mutableStateOf("")

    init {
        // Initial welcome message
        messages.add(
            ChatMessage(
                "Hello! I am your Siddha AI Assistant. How can I help you with your health today?",
                false
            )
        )
    }

    fun sendMessage() {
        val text = currentInput.trim()
        if (text.isEmpty()) return

        messages.add(ChatMessage(text, true))
        currentInput = ""
        isLoading = true

        viewModelScope.launch {
            try {
                val response = repository.chat(text)
                if (response.isSuccessful && response.body() != null) {
                    messages.add(ChatMessage(response.body()!!.response, false))
                } else {
                    messages.add(ChatMessage("Error: ${response.message()}", false))
                }
            } catch (e: Exception) {
                messages.add(ChatMessage("Connection error: ${e.message}", false))
            } finally {
                isLoading = false
            }
        }
    }
}
