package ink.ddddd.flip.shared.domain.tag

import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.data.source.CardTagDao
import ink.ddddd.flip.shared.domain.UseCase
import javax.inject.Inject

class GetTags @Inject constructor(
    private val cardTagDao: CardTagDao
) : UseCase<Unit, List<Tag>>() {
    override fun execute(parameters: Unit): List<Tag> {
        return cardTagDao.getTags()
    }

}