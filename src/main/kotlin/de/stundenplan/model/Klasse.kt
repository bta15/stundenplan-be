import kotlinx.serialization.Serializable

@Serializable
data class Klasse(
    val klassenstufe: Int,
    val bezeichnung: String,
    val unterrichtsfachList: List<Unterrichtsfach>
)