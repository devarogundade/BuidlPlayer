package ng.farma.buidlplayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ng.farma.buidlplayer.domain.models.CourseSection
import ng.farma.buidlplayer.domain.models.SubscribedCourse
import ng.farma.buidlplayer.domain.repository.CourseRepository
import ng.farma.buidlplayer.domain.repository.SectionRepository
import ng.farma.buidlplayer.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SectionListViewModel @Inject constructor(
    private val sectionRepository: SectionRepository
) : ViewModel() {

    private val _sections = MutableLiveData<Resource<List<CourseSection>>>()
    val sections: LiveData<Resource<List<CourseSection>>> = _sections

    fun getSections(id: String) {
        viewModelScope.launch {
            _sections.postValue(Resource.Loading())
            val result = sectionRepository.getSections(id)
            _sections.postValue(result)
        }
    }

}