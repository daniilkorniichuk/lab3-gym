package com.example.lab3.ui.subscriptions

import android.app.Application
import androidx.lifecycle.*
import com.example.lab3.data.DatabaseProvider
import com.example.lab3.data.entity.*
import com.example.lab3.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SubscriptionViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DatabaseProvider.getDatabase(application)

    private val subscriptionRepository = SubscriptionRepository(db.subscriptionDao())
    private val visitorDao = db.visitorDao()
    private val gymDao = db.gymDao()

    val subscriptions: StateFlow<List<SubscriptionWithDetails>> =
        subscriptionRepository.getAllSubscriptions()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _visitors = MutableStateFlow<List<Visitor>>(emptyList())
    val visitors: StateFlow<List<Visitor>> = _visitors.asStateFlow()

    private val _gyms = MutableStateFlow<List<Gym>>(emptyList())
    val gyms: StateFlow<List<Gym>> = _gyms.asStateFlow()

    init {
        viewModelScope.launch {
            visitorDao.getAll().collectLatest {
                _visitors.value = it
            }
        }

        viewModelScope.launch {
            gymDao.getAll().collectLatest {
                _gyms.value = it
            }
        }
    }

    fun insert(subscription: Subscription) {
        viewModelScope.launch {
            subscriptionRepository.insert(subscription)
        }
    }

    fun update(subscription: Subscription) {
        viewModelScope.launch {
            subscriptionRepository.update(subscription)
        }
    }

    fun delete(subscription: Subscription) {
        viewModelScope.launch {
            subscriptionRepository.delete(subscription)
        }
    }

    fun getById(id: Int, onResult: (Subscription?) -> Unit) {
        viewModelScope.launch {
            val result = subscriptionRepository.getById(id)
            onResult(result)
        }
    }
}
