package ng.farma.buidlplayer.domain.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ng.farma.buidlplayer.domain.models.CourseSection
import ng.farma.buidlplayer.utils.Constants.COURSES_PATH
import ng.farma.buidlplayer.utils.Constants.SECTIONS_PATH
import ng.farma.buidlplayer.utils.Constants.SUBSCRIPTIONS_PATH
import ng.farma.buidlplayer.utils.Resource
import javax.inject.Inject

class FirebaseSectionDao @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getCourseSections(id: String): Resource<List<CourseSection>> {
        val response = firebaseFirestore.collection(SECTIONS_PATH)
            .whereEqualTo("courseId", id)
            .get().await()

        val sections = response.toObjects(CourseSection::class.java)
        return Resource.Success(sections)
    }

}