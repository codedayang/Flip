package cc.foxa.flip.shared.data

import cc.foxa.flip.shared.data.source.CardTagDao
import cc.foxa.flip.shared.di.DaggerTestComponent
import cc.foxa.flip.shared.filter.TagFilter
import cc.foxa.flip.shared.util.noRuleSet
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import javax.inject.Inject

class CardRepositoryTest {
    @Inject
    lateinit var cardTagDao: CardTagDao
    lateinit var cardRepository: CardRepository

    @Before
    fun prepare() {
        DaggerTestComponent.create().inject(this)
        cardRepository = CardRepository(cardTagDao)
    }

    @Test
    fun getNextCard() {
        val tag = Tag(name = "Tag")

        val card1 = Card(front = "Card1", priority = 5)
        val card2 = Card(front = "Card2", priority = 5)
        val card3 = Card(front = "Card3", priority = 5)

        val noRuleSet = noRuleSet()

        cardTagDao.updateTag(tag)
        cardTagDao.updateCard(card1)
        cardTagDao.updateCard(card2)
        cardTagDao.updateCard(card3)
        cardTagDao.updateJoin(CardTagJoin(card1.id, tag.id))

        val count = intArrayOf(0, 0, 0, 0)
        repeat(2000) {
            val card = cardRepository.getNextCard(noRuleSet)
            when (card.front) {
                "Card1" -> count[1] = count[1] + 1
                "Card2" -> count[2] = count[2] + 1
                "Card3" -> count[3] = count[3] + 1
            }
        }

        Assert.assertTrue((count[1]/2000.0 - 5.0/15.0) < 0.1)
        Assert.assertTrue((count[2]/2000.0 - 5.0/15.0) < 0.1)
        Assert.assertTrue((count[3]/2000.0 - 5.0/15.0) < 0.1)

        val tagRuleSet = RuleSet(
            rules = listOf(Rule(filters = listOf(TagFilter(tags = listOf(tag))))))

        var card: Card? = null
        repeat(100) {
            card = cardRepository.getNextCard(tagRuleSet)
        }

        Assert.assertEquals(card1.id, card?.id)
    }

    @Test
    fun updateCard() {
        val tag1 = Tag(name = "Tag1")
        val tag2 = Tag(name = "Tag2")
        cardTagDao.updateTag(tag1)
        cardTagDao.updateTag(tag2)

        val card1 = Card(front = "Card1", priority = 5, tags = listOf(tag1))
        cardTagDao.updateCard(card1)
        val noRuleSet = noRuleSet()
        cardRepository.getNextCard(noRuleSet)

        card1.front = "Edited"
        card1.tags = listOf(tag2)
        cardRepository.updateCard(card1)

        val card = cardRepository.getNextCard(noRuleSet)

        assertEquals("Edited", card.front)
        assertEquals("Edited", cardTagDao.getCards()[0].front)
        assertEquals(tag2, card.tags[0])
    }

    @Test
    fun deleteCard() {
        val card1 = Card(front = "Card1")
        cardTagDao.updateCard(card1)
        val noRuleSet = noRuleSet()
        val card = cardRepository.getNextCard(noRuleSet)

        val card2 = Card(front = "Card2")
        cardRepository.updateCard(card2)
        cardRepository.deleteCard(card1)

        val cardb = cardRepository.getNextCard(noRuleSet)

        assertEquals(card2.id, cardb.id)

    }

}