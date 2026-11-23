
package ca.gbc.comp3074.sanyoung.lab92.components

import ca.gbc.comp3074.sanyoung.lab92.model.User

sealed interface UsersUiState {
    data object Loading : UsersUiState

    data class Success(
        val users: List<User>
    ) : UsersUiState

    data class Error(
        val message: String
    ) : UsersUiState
}
