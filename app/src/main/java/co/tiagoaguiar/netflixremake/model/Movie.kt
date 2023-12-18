package co.tiagoaguiar.netflixremake.model

data class Movie(
    val id: Int,
    val desc: String? = null,
    val cast: String? = null,
    val title: String? = null,
    val coverUrl: String,
)
