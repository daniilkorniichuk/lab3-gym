package com.example.lab3.ui.subscriptions

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lab3.data.entity.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SubscriptionDetailScreen(
    initial: Subscription?,
    visitors: List<Visitor>,
    gyms: List<Gym>,
    onSave: (Subscription) -> Unit,
    onCancel: () -> Unit
) {
    var visitorId by remember { mutableStateOf(initial?.visitorId ?: visitors.firstOrNull()?.id ?: 0) }
    var gymId by remember { mutableStateOf(initial?.gymId ?: gyms.firstOrNull()?.id ?: 0) }

    val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val context = LocalContext.current

    var startDate by remember { mutableStateOf(initial?.startDate ?: dateFormat.format(Date())) }
    var endDate by remember { mutableStateOf(initial?.endDate ?: dateFormat.format(Date())) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val pickDate = { onPicked: (String) -> Unit ->
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _: DatePicker, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onPicked(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Оберіть відвідувача:")
        DropdownMenuBox(
            items = visitors,
            selectedId = visitorId,
            onSelectedChange = { visitorId = it },
            labelExtractor = { "${it.firstName} ${it.lastName}" },
            idExtractor = { it.id }
        )

        Text("Оберіть зал:")
        DropdownMenuBox(
            items = gyms,
            selectedId = gymId,
            onSelectedChange = { gymId = it },
            labelExtractor = { it.name },
            idExtractor = { it.id }
        )

        Text(
            "Дата початку: $startDate",
            modifier = Modifier.clickable { pickDate { startDate = it } })
        Text(
            "Дата завершення: $endDate",
            modifier = Modifier.clickable { pickDate { endDate = it } })

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onCancel) {
                Text("Скасувати")
            }
            Button(onClick = {
                try {
                    val start = dateFormat.parse(startDate)
                    val end = dateFormat.parse(endDate)

                    val calendarNow = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val today = calendarNow.time

                    if (start == null || end == null) {
                        errorMessage = "Невірний формат дати"
                        return@Button
                    }
                    if (start.before(today)) {
                        errorMessage = "Дата початку не може бути в минулому"
                        return@Button
                    }
                    if (end.before(start)) {
                        errorMessage = "Дата завершення не може бути раніше за дату початку"
                        return@Button
                    }
                    if (start == end) {
                        errorMessage = "Дата початку і завершення не можуть бути однаковими"
                        return@Button
                    }
                    if (visitorId == 0 || gymId == 0) {
                        errorMessage = "Будь ласка, оберіть відвідувача і зал"
                        return@Button
                    }

                    errorMessage = null
                    onSave(
                        Subscription(
                            id = initial?.id ?: 0,
                            visitorId = visitorId,
                            gymId = gymId,
                            startDate = startDate,
                            endDate = endDate
                        )
                    )
                } catch (e: Exception) {
                    errorMessage = "Помилка: ${e.localizedMessage}"
                }
            }) {
                Text("Зберегти")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenuBox(
    items: List<T>,
    selectedId: Int,
    onSelectedChange: (Int) -> Unit,
    labelExtractor: (T) -> String,
    idExtractor: (T) -> Int
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedItem = items.find { idExtractor(it) == selectedId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedItem?.let { labelExtractor(it) } ?: "Оберіть...",
            onValueChange = {},
            readOnly = true,
            label = { Text("Вибір") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(labelExtractor(item)) },
                    onClick = {
                        onSelectedChange(idExtractor(item))
                        expanded = false
                    }
                )
            }
        }
    }
}
