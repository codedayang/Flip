package cc.foxa.flip.shared.filter

import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.Tag
import org.junit.Assert
import org.junit.Test

class TagFilterTest {

    @Test
    fun check() {
        val tagFoo = Tag(name = "TagFoo")
        val tagBar = Tag(name = "TagBar")
        val tagGosh = Tag(name = "TagGosh")
        val tagFilter = TagFilter(tags = listOf(tagFoo, tagBar))

        val card = Card(tags = listOf(tagFoo))
        val card2 = Card(tags = listOf(tagFoo, tagBar))
        val card3 = Card(tags = listOf(tagGosh))
        val card4 = Card(tags = listOf(tagFoo, tagBar, tagGosh))

        Assert.assertFalse(tagFilter.check(card))
        Assert.assertTrue(tagFilter.check(card2))
        Assert.assertFalse(tagFilter.check(card3))
        Assert.assertTrue(tagFilter.check(card4))
    }

    @Test
    fun toFilterBean() {
        val tagFilter = TagFilter(tags = listOf(Tag(name = "TagFoo")))
        println(tagFilter.toFilterBean())
        Assert.assertNotNull(tagFilter.toFilterBean())
    }

    @Test
    fun testFromFilterBean() {
        val filter = TagFilter(tags = listOf(Tag(name = "TagFoo")))
        val bean = filter.toFilterBean()

        val tagFilter = TagFilter.fromFilterBean(bean)
        Assert.assertEquals(filter.id, tagFilter.id)
    }
}