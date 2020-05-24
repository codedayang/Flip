package cc.foxa.flip.shared.domain.tag

import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.data.source.CardTagDao
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class GetTags @Inject constructor(
    private val cardTagDao: CardTagDao
) : UseCase<Unit, List<Tag>>() {
    override fun execute(parameters: Unit): List<Tag> {
        return cardTagDao.getTags()
    }

}