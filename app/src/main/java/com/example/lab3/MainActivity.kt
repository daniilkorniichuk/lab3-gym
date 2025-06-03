package com.example.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab3.data.entity.Subscription
import com.example.lab3.ui.subscriptions.SubscriptionDetailScreen
import com.example.lab3.ui.subscriptions.SubscriptionListScreen
import com.example.lab3.ui.subscriptions.SubscriptionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SubscriptionViewModel = viewModel()

            val subscriptions by viewModel.subscriptions.collectAsState()
            val visitors by viewModel.visitors.collectAsState()
            val gyms by viewModel.gyms.collectAsState()


            var editing by remember { mutableStateOf(false) }
            var editingItem by remember { mutableStateOf<Subscription?>(null) }

            if (editing) {
                SubscriptionDetailScreen(
                    initial = editingItem,
                    visitors = visitors,
                    gyms = gyms,
                    onSave = { subscription ->
                        if (subscription.id == 0) {
                            viewModel.insert(subscription)
                        } else {
                            viewModel.update(subscription)
                        }
                        editing = false
                    },
                    onCancel = {
                        editing = false
                    }
                )
            } else {
                SubscriptionListScreen(
                    subscriptions = subscriptions,
                    onAddClick = {
                        editingItem = null
                        editing = true
                    },
                    onEditClick = {
                        editingItem = it.subscription
                        editing = true
                    },
                    onDeleteClick = {
                        viewModel.delete(it.subscription)
                    }
                )
            }
        }
    }
}