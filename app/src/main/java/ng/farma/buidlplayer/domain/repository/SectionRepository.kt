package ng.farma.buidlplayer.domain.repository

import ng.farma.buidlplayer.domain.dao.FirebaseSectionDao
import ng.farma.buidlplayer.utils.SafeCall.parallelExecute
import javax.inject.Inject

class SectionRepository @Inject constructor(
    private val dao: FirebaseSectionDao
) {
    suspend fun getSections(id: String) = parallelExecute {
        dao.getCourseSections(id)
    }
}