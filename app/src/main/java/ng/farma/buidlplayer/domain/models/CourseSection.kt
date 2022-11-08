package ng.farma.buidlplayer.domain.models

import java.io.Serializable

class CourseSection(
    val id: String = "",
    val courseId: String = "",
    val title: String = "",
    val duration: String = "",
    val sectionId: String = "",
    val content: String = "",
    val src: String = ""
) : Serializable