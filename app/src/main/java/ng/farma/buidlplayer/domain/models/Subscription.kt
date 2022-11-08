package ng.farma.buidlplayer.domain.models

data class Subscription(
    val id: String = "",
    val active: Boolean = false,
    val certificate: Boolean = false,
    val courseId: String = "",
    val viewed: List<String> = emptyList()
)
