package com.corevo.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.corevo.main.data.model.ChatMessage
import com.corevo.main.data.model.Conversation
import com.corevo.main.viewmodel.ChatViewModel

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val conversations by viewModel.conversations.collectAsState()
    val messages by viewModel.activeMessages.collectAsState()
    val selectedId by viewModel.currentConversationId.collectAsState()
    val input by viewModel.inputMessage.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (selectedId == null) {
            Text("Coach & Friend Messages", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(conversations) { conv ->
                    ConversationItem(conv = conv, onClick = { viewModel.selectConversation(conv.id) })
                }
            }
        } else {
            Button(onClick = { viewModel.currentConversationId.value = null }) {
                Text("← Back to Chats")
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(messages) { msg ->
                    MessageBubble(msg)
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { viewModel.inputMessage.value = it },
                    label = { Text("Type a message...") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { viewModel.sendMessage() }) {
                    Text("Send")
                }
            }
        }
    }
}

@Composable
fun ConversationItem(conv: Conversation, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onClick() }, elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(conv.name, style = MaterialTheme.typography.titleMedium)
            conv.lastMessage?.let {
                Text(it.text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun MessageBubble(msg: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (msg.isMine) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (msg.isMine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (!msg.isMine) {
                    Text("@${msg.senderUsername}", style = MaterialTheme.typography.labelSmall)
                }
                Text(
                    msg.text,
                    color = if (msg.isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
