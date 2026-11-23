package ca.gbc.comp3074.sanyoung.lab92.model

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val geo: Geo,
    val zipcode: String
)