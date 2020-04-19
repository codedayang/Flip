package ink.ddddd.flip.shared.data

import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.CardTagJoin
import ink.ddddd.flip.shared.data.source.CardTagDao
import javax.inject.Inject
import javax.inject.Singleton
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.domain.card.GetNextCard
import ink.ddddd.flip.shared.util.WeightedRandomSample
import ink.ddddd.flip.shared.util.noRuleSet

/**
    Cache card list under current [RuleSet] for continuously [GetNextCard] use case
 */
@Singleton
class CardRepository @Inject constructor(
    private val cardTagDao: CardTagDao
) {

    private var curRuleSet: RuleSet = noRuleSet()

    private var cache = mutableMapOf<String, Card>()

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


    /**
     * Insert or update
     */
    fun updateCard(card: Card) {
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