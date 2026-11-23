
package ca.gbc.comp3074.sanyoung.lab92.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.sanyoung.lab92.api.RetrofitInstance
import ca.gbc.comp3074.sanyoung.lab92.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    userId: Int?,
    onBack: () -> Unit
) {
    var user by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(userId) {
        loading = true
        errorMessage = null
        user = null

        if (userId == null) {
            errorMessage = "Invalid user ID"
            loading = false
            return@LaunchedEffect
        }

        try {
            user = RetrofitInstance.api.getUserById(userId)
        } catch (e: Exception) {
            errorMessage = "Failed to load user details"
        } finally {
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Text(
                    text = "Error: $errorMessage",
                    modifier = Modifier.padding(paddingValues)
                )
            }

            user != null -> {
                ContactDetails(
                    user = user!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ContactDetails(
    user: User,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = user.name,
            style = MaterialTheme.typography.headlineMedium
        )

        DetailRow("ID", user.id.toString())
        DetailRow("Username", user.username)
        DetailRow("Email", user.email)

        Text("Phone", fontWeight = FontWeight.Bold)

        Text(
            text = user.phone,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:${user.phone}")
                )
                context.startActivity(intent)
            }
        )

        DetailRow("Website", user.website)

        HorizontalDivider()

        Text(
            "Address",
            style = MaterialTheme.typography.titleMedium
        )

        DetailRow("Street", user.address.street)
        DetailRow("Suite", user.address.suite)
        DetailRow("City", user.address.city)
        DetailRow("Zip code", user.address.zipcode)
        DetailRow("Latitude", user.address.geo.lat)
        DetailRow("Longitude", user.address.geo.lng)

        HorizontalDivider()

        Text(
            "Company",
            style = MaterialTheme.typography.titleMedium
        )

        DetailRow("Name", user.company.name)
        DetailRow("Catch phrase", user.company.catchPhrase)
        DetailRow("Business", user.company.bs)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}
