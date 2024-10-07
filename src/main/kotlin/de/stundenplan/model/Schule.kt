import kotlinx.serialization.Serializable

@Serializable
data class Schule(
    val name: String,
    val schulId: String, //TODO muss unique sein (in DB)
    val schulform: Schulform,
    val klassen: List<Klasse>
)