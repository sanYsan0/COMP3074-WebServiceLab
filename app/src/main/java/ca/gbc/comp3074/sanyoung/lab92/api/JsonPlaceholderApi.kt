
package ca.gbc.comp3074.sanyoung.lab92.api

import ca.gbc.comp3074.sanyoung.lab92.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): User
}
