import kotlinx.serialization.Serializable

@Serializable
data class Schule(
    val name: String,
    val schulform: Schulform,
    val klassen: List<Klasse>
)