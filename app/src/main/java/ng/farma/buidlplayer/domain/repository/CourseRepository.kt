package ng.farma.buidlplayer.domain.repository

import ng.farma.buidlplayer.domain.dao.FirebaseCourseDao
import ng.farma.buidlplayer.utils.SafeCall.parallelExecute
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val dao: FirebaseCourseDao
) {
    suspend fun getCourses(address: String) = parallelExecute {
        dao.getSubscribedCourses(address)
    }
}