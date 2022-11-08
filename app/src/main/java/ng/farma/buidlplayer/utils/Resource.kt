package ng.farma.buidlplayer.utils

sealed class Resource<T>(
    val data: T? = null,
    val error: Exception? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: Exception) : Resource<T>(error = exception)
    class Loading<T> : Resource<T>()
}