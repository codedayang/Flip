package ink.ddddd.flip.shared.domain.tag

import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.data.source.CardTagDao
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class UpdateTag @Inject constructor(
    private val cardTagDao: CardTagDao
) : UseCase<Tag, Unit>() {
    override fun execute(parameters: Tag) {
        cardTagDao.updateTag(parameters)
    }
}