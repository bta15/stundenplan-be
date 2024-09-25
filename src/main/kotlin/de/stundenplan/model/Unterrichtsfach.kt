import kotlinx.serialization.Serializable

@Serializable
data class Unterrichtsfach(
    val name: String,
    val anzahlProWoche: Int
)