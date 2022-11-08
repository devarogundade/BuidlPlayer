package ng.farma.buidlplayer.domain.models

data class Course(
    val id: String = "",
    val address: String = "",
    val category: Int = -1,
    val certificate: Boolean = false,
    val description: String = "",
    val name: String = "",
    val preview: String = "",
    val price: String = "",
    val publish: Boolean = false,
    val updatedAt: String = "",
    val photo: String = ""
)