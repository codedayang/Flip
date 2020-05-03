package ink.ddddd.flip.cardbrowse

import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import org.junit.Test

import org.junit.Assert.*

class CardBrowseDiffTest {

    @Test
    fun areItemsTheSame() {

    }

    @Test
    fun areContentsTheSame() {
        val tag1 = Tag(name = "Foo")
        val card1 = Card(front = "1", back = "ff", tags = listOf(tag1))
        val card2 = Card(front = "1", back = "ff", tags = listOf(tag1))
        assertTrue(CardBrowseDiff.areContentsTheSame(card1, card2))
    }
}