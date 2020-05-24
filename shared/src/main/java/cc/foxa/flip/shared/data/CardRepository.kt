package cc.foxa.flip.shared.data

import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.CardTagJoin
import cc.foxa.flip.shared.data.source.CardTagDao
import javax.inject.Inject
import cc.foxa.flip.shared.data.model.RuleSet
import cc.foxa.flip.shared.domain.card.GetNextCard
import cc.foxa.flip.shared.util.WeightedRandomSample
import cc.foxa.flip.shared.util.noRuleSet
import java.util.*

/**
    Cache card list under current [RuleSet] for continuously [GetNextCard] use case
 */
class CardRepository @Inject constructor(
    private val cardTagDao: CardTagDao
) {

    private var curRuleSet: RuleSet = noRuleSet()

    private var cache = mutableMapOf<String, Card>()
    
    fun searchCard(query: String): List<Card> {
        val cards = cardTagDao.searchCardContent("%${query}%")
        cards.forEach { it.tags = cardTagDao.getTagsByCard(it.id) }
        return cards
    }

    /**
     * Get next card with Weight Random Sampling
     */
    fun getNextCard(ruleSet: RuleSet): Card {
        if (cache.isNullOrEmpty() || curRuleSet.id != ruleSet.id) {
            refreshCache(ruleSet)
        }

        return WeightedRandomSample.next(cache.values.toList())
    }

    /**
     * Get card list under [ruleSet]
     */
    fun getCards(ruleSet: RuleSet): List<Card> {
        var cards = cardTagDao.getCards()
        cards.forEach {
            it.tags = cardTagDao.getTagsByCard(it.id)
        }
        cards = cards.filter {
            ruleSet.check(it)
        }
        return cards
    }

    fun getCardById(id: String): Card {
        return cardTagDao.getCardById(id).apply {
            tags = cardTagDao.getTagsByCard(id)
        }
    }


    /**
     * Insert or update
     */
    fun updateCard(card: Card) {
        card.editTime = Date(System.currentTimeMillis())
        if (cache.containsKey(card.id)) {
            cache[card.id] = card
        }
        cardTagDao.updateCard(card)
        cardTagDao.deleteJoinByCardId(card.id)
        card.tags.forEach {
            cardTagDao.updateJoin(CardTagJoin(cardId = card.id, tagId = it.id))
        }
    }

    fun deleteCard(card: Card) {
        if (cache.containsKey(card.id)) {
            cache.remove(card.id)
        }
        cardTagDao.deleteCard(card)
    }

    fun refreshCache(ruleSet: RuleSet) {
        cache.clear()
        curRuleSet = ruleSet
        var cards = cardTagDao.getCards()
        cards.forEach {
            it.tags = cardTagDao.getTagsByCard(it.id)
        }

        cards = cards.filter {
            curRuleSet.check(it)
        }
        cards.forEach {
            cache[it.id] = it
        }
    }

}