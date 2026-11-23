
package ca.gbc.comp3074.sanyoung.lab92.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.sanyoung.lab92.api.RetrofitInstance
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    onContactClick: (Int) -> Unit
) {
    var uiState by remember {
        mutableStateOf<UsersUiState>(UsersUiState.Loading)
    }

    LaunchedEffect(Unit) {
        uiState = try {
            val users = RetrofitInstance.api.getUsers()
            UsersUiState.Success(users)
        } catch (e: Exception) {
            UsersUiState.Error(
                e.message ?: "Unable to load contacts."
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") }
            )
        }
    ) { paddingValues ->

        when (val state = uiState) {
            UsersUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UsersUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${state.message}")
                }
            }

            is UsersUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        items = state.users,
                        key = { it.id }
                    ) { user ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = user.name,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            supportingContent = {
                                Text(user.email)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onContactClick(user.id)
                                }
                        )

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
