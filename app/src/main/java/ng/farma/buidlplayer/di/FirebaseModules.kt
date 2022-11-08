package ng.farma.buidlplayer.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModules {
    @Provides
    @Singleton
    fun firebaseFireStore() = FirebaseFirestore.getInstance()
}