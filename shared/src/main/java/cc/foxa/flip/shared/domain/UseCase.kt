package cc.foxa.flip.shared.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cc.foxa.flip.shared.Result
import kotlinx.coroutines.*

/**
 * Executes business logic synchronously or asynchronously using a [Scheduler].
 */
abstract class UseCase<in P, R> {

    /** Executes the use case asynchronously and places the [Result] in a MutableLiveData
     *
     * @param parameters the input parameters to run the use case with
     * @param result the MutableLiveData where the result is posted to
     *
     */
    operator fun invoke(scope : CoroutineScope, parameters: P, result: MutableLiveData<Result<R>>) {
        scope.launch {
            val job = async {
                withContext(Dispatchers.IO) {
                    try {
                        result.postValue(Result.Success(execute(parameters)))
                    } catch (e: Exception) {
                        result.postValue(Result.Error(e))
                    }

                }
            }
            job.await()
        }
    }

    /** Executes the use case asynchronously and returns a [Result] in a new LiveData object.
     *
     * @return an observable [LiveData] with a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    open operator fun invoke(scope : CoroutineScope, parameters: P): LiveData<Result<R>> {
        val liveCallback: MutableLiveData<Result<R>> = MutableLiveData()
        this(scope, parameters, liveCallback)
        return liveCallback
    }

    /** Executes the use case synchronously  */
    fun executeNow(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}

operator fun <R> UseCase<Unit, R>.invoke(scope : CoroutineScope): LiveData<Result<R>>
        = this(scope, Unit)
operator fun <R> UseCase<Unit, R>.invoke(scope : CoroutineScope, result: MutableLiveData<Result<R>>)
        = this(scope, Unit, result)
