package cc.foxa.flip.shared.domain.tag

import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.data.source.CardTagDao
import cc.foxa.flip.shared.domain.UseCase
import javax.inject.Inject

class DeleteTag @Inject constructor(
    private val cardTagDao: CardTagDao
) : UseCase<Tag, Unit>() {
    override fun execute(parameters: Tag) {
        cardTagDao.deleteTag(parameters)
    }

}