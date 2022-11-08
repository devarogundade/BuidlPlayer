package ng.farma.buidlplayer.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SafeCall {
    suspend fun <T> execute(func: suspend () -> T): Resource<T> {
        /* make sure func is execute on IO threads */
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(func())
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }
    }

    suspend fun <T> parallelExecute(func: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            func()
        }
    }
}