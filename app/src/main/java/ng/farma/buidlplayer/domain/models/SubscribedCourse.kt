package ng.farma.buidlplayer.domain.models

data class SubscribedCourse(
    val courseId: String,
    val course: Course,
    val subscription: Subscription
)