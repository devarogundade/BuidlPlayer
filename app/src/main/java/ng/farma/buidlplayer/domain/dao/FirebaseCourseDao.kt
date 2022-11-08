package ng.farma.buidlplayer.domain.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import ng.farma.buidlplayer.domain.models.*
import ng.farma.buidlplayer.utils.Constants.COURSES_PATH
import ng.farma.buidlplayer.utils.Constants.SUBSCRIPTIONS_PATH
import ng.farma.buidlplayer.utils.Constants.USERS_PATH
import ng.farma.buidlplayer.utils.Resource
import javax.inject.Inject

class FirebaseCourseDao @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getSubscribedCourses(address: String): Resource<List<SubscribedCourse>> {
        val result = ArrayList<SubscribedCourse>()
        val response = firebaseFirestore.collection(SUBSCRIPTIONS_PATH)
            .whereEqualTo("address", address.uppercase())
            .get().await()

        val subscriptions = response.toObjects(Subscription::class.java)
        subscriptions.forEach { subscription ->
            val course = firebaseFirestore.collection(COURSES_PATH)
                .whereEqualTo("id", subscription.courseId)
                .get().await().first().toObject<Course>()

            val user = firebaseFirestore.collection(USERS_PATH)
                .whereEqualTo("id", course.address.uppercase())
                .get().await().first().toObject<User>()

            result.add(
                SubscribedCourse(
                    course.id,
                    course,
                    subscription,
                    user
                )
            )
        }
        return Resource.Success(result)
    }

}