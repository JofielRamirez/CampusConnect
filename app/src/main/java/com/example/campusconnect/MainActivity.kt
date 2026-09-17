package com.example.campusconnect

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics

data class CampusResource(
    @param:StringRes val nameRes: Int,
    @param:StringRes val categoryRes: Int,
    @param:StringRes val hoursRes: Int,
    val lastUpdatedDate: LocalDate,
    val reviewCount: Int,
    val capacity: Int,
    val fee: Double
)

val sampleResources = listOf(
    CampusResource(
        nameRes = R.string.resource_intl_office_name,
        categoryRes = R.string.resource_intl_office_category,
        hoursRes = R.string.resource_intl_office_hours,
        lastUpdatedDate = LocalDate.of(2026, 8, 3),
        reviewCount = 1,
        capacity = 15,
        fee = 0.0
    ),
    CampusResource(
        nameRes = R.string.resource_library_name,
        categoryRes = R.string.resource_library_category,
        hoursRes = R.string.resource_library_hours,
        lastUpdatedDate = LocalDate.of(2026, 8, 10),
        reviewCount = 24,
        capacity = 1200,
        fee = 0.10
    ),
    CampusResource(
        nameRes = R.string.resource_veterans_name,
        categoryRes = R.string.resource_veterans_category,
        hoursRes = R.string.resource_veterans_hours,
        lastUpdatedDate = LocalDate.of(2026, 8, 15),
        reviewCount = 0,
        capacity = 40,
        fee = 0.0
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
    Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_screen))) {
        Text(
            stringResource(R.string.app_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            stringResource(R.string.app_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_section)))

        Text(
            stringResource(R.string.resources_section_header),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.semantics { heading() }
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_list_header)))

        LazyColumn {
            items(sampleResources) { resource ->
                ResourceCard(resource)
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_card_gap)))
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@Composable
fun ResourceCard(resource: CampusResource) {
    val context = LocalContext.current
    val locale = LocalLocale.current.platformLocale

    val dateFullFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.FULL)
        .withLocale(locale)
    val dateShortFormatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT)
        .withLocale(locale)
    val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
    val integerFormatter = NumberFormat.getIntegerInstance(locale)

    val name = stringResource(resource.nameRes)
    val category = stringResource(resource.categoryRes)
    val hours = stringResource(
        R.string.hours_label,
        stringResource(resource.hoursRes)
    )

    val reviewText = context.resources.getQuantityString(
        R.plurals.review_count,
        resource.reviewCount,
        resource.reviewCount
    )

    val capacityFormatted = integerFormatter.format(resource.capacity)
    val contentDesc = stringResource(
        R.string.content_desc_resource_card,
        name,
        category,
        hours
    )

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_card_inner))
                .padding(start = dimensionResource(R.dimen.padding_card_start))
        ) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text(
                stringResource(R.string.category_label, category),
                style = MaterialTheme.typography.bodySmall
            )
            Text(hours)
            Text(stringResource(R.string.capacity_label, capacityFormatted))
            Text(reviewText)

            if (resource.fee > 0.0) {
                Text(stringResource(
                    R.string.fee_label,
                    currencyFormatter.format(resource.fee))
                )
            }
            Text(
                stringResource(
                    R.string.updated_short_label,
                    resource.lastUpdatedDate.format(dateShortFormatter)
                ),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                stringResource(
                    R.string.last_updated_label,
                    resource.lastUpdatedDate.format(dateFullFormatter)
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}