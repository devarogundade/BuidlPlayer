package ng.farma.buidlplayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ng.farma.buidlplayer.domain.models.SubscribedCourse
import ng.farma.buidlplayer.domain.repository.CourseRepository
import ng.farma.buidlplayer.utils.Resource
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _courses = MutableLiveData<Resource<List<SubscribedCourse>>>()
    val courses: LiveData<Resource<List<SubscribedCourse>>> = _courses

    fun getCourses(address: String) {
        viewModelScope.launch {
            _courses.postValue(Resource.Loading())
            val result = courseRepository.getCourses(address)
            _courses.postValue(result)
        }
    }

}