@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.lab3.ui.subscriptions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab3.data.entity.SubscriptionWithDetails

@Composable
fun SubscriptionListScreen(
    subscriptions: List<SubscriptionWithDetails>,
    onAddClick: () -> Unit,
    onEditClick: (SubscriptionWithDetails) -> Unit,
    onDeleteClick: (SubscriptionWithDetails) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Список абонементів") }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Button(onClick = onAddClick) {
                    Text("Додати абонемент")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(subscriptions) { sub ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Відвідувач: ${sub.visitor?.firstName ?: "?"} ${sub.visitor?.lastName ?: "?"}")
                        Text("Зал: ${sub.gym?.name ?: "?"}")
                        Text("Період: ${sub.subscription.startDate} — ${sub.subscription.endDate}")

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Button(
                                onClick = { onEditClick(sub) },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Редагувати")
                            }
                            Button(
                                onClick = { onDeleteClick(sub) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Видалити")
                            }
                        }
                    }
                }
            }
        }
    }
}
