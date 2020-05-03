package ink.ddddd.flip.shared.domain

import androidx.lifecycle.LiveData
import ink.ddddd.flip.shared.Result
import kotlinx.coroutines.CoroutineScope

abstract class SyncUseCase<in P, R> : UseCase<P, R>() {
    operator fun invoke(parameters: P): R {
        return (executeNow(parameters) as Result.Success).data
    }
}