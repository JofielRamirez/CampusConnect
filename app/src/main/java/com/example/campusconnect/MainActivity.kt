package com.example.campusconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

data class CampusResource(
    @StringRes val nameRes: Int,
    @StringRes val categoryRes: Int,
    @StringRes val hoursRes: Int,
    val lastUpdatedDate: String
)

val sampleResources = listOf(
    CampusResource(
        nameRes = R.string.resource_intl_office_name,
        categoryRes = R.string.resource_intl_office_category,
        hoursRes = R.string.resource_intl_office_hours,
        lastUpdatedDate = "03/08/2026"
    ),
    CampusResource(
        nameRes = R.string.resource_library_name,
        categoryRes = R.string.resource_library_category,
        hoursRes = R.string.resource_library_hours,
        lastUpdatedDate = "03/08/2026"
    ),
    CampusResource(
        nameRes = R.string.resource_veterans_name,
        categoryRes = R.string.resource_veterans_category,
        hoursRes = R.string.resource_veterans_hours,
        lastUpdatedDate = "03/08/2026"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CampusConnectScreen()
            }
        }
    }
}

@Composable
fun CampusConnectScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium)

        Text(stringResource(R.string.app_subtitle), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(sampleResources) { resource ->
                ResourceCard(resource)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResourceCard(resource: CampusResource) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(stringResource(resource.nameRes), style = MaterialTheme.typography.titleMedium)
            Text(stringResource(resource.categoryRes), style = MaterialTheme.typography.bodySmall)
            Text(stringResource(R.string.hours_label, stringResource(resource.hoursRes)))
            Text(
                stringResource(R.string.last_updated_label, resource.lastUpdatedDate),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
