package ng.farma.buidlplayer.domain.models

import java.io.Serializable

data class SubscribedCourse(
    val courseId: String,
    val course: Course,
    val subscription: Subscription,
    val user: User
) : Serializable