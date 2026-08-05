package com.corevo.main.realtime

import com.corevo.main.system.SessionManager
import com.corevo.main.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.*

enum class RealtimeConnectionState { CONNECTED, DISCONNECTED, CONNECTING }

class RealtimeService(
    private val sessionManager: SessionManager
) {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    private val _connectionState = MutableStateFlow(RealtimeConnectionState.DISCONNECTED)
    val connectionState: StateFlow<RealtimeConnectionState> = _connectionState

    private val _newMessagesEvent = MutableStateFlow<String?>(null)
    val newMessagesEvent: StateFlow<String?> = _newMessagesEvent

    fun connect() {
        if (_connectionState.value == RealtimeConnectionState.CONNECTED) return
        _connectionState.value = RealtimeConnectionState.CONNECTING

        CoroutineScope(Dispatchers.IO).launch {
            val token = sessionManager.getToken() ?: return@launch
            val request = Request.Builder()
                .url("${Constants.WS_URL_LOCAL}?token=$token")
                .build()

            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    _connectionState.value = RealtimeConnectionState.CONNECTED
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    _newMessagesEvent.value = text
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    _connectionState.value = RealtimeConnectionState.DISCONNECTED
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    _connectionState.value = RealtimeConnectionState.DISCONNECTED
                }
            })
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
        _connectionState.value = RealtimeConnectionState.DISCONNECTED
    }
}
