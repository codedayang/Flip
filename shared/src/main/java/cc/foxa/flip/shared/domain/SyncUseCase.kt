package cc.foxa.flip.shared.domain

import cc.foxa.flip.shared.Result

abstract class SyncUseCase<in P, R> : UseCase<P, R>() {
    operator fun invoke(parameters: P): R {
        return (executeNow(parameters) as Result.Success).data
    }
}