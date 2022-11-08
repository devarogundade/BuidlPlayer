package ng.farma.buidlplayer.domain.models

import java.io.Serializable

data class User(
    val id: String = "",
    val name: String = "",
    val photo: String = "",
    val verified: Boolean = false
) : Serializable