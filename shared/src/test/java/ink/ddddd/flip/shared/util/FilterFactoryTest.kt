package ink.ddddd.flip.shared.util

import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.filter.TagFilter
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class FilterFactoryTest {

    @Test
    fun get() {
        val tagFoo = Tag(name = "TagFoo")
        val tagBar = Tag(name = "TagBar")
        val tagGosh = Tag(name = "TagGosh")
        val filter = TagFilter(tags = listOf(tagFoo, tagBar))
        val bean = filter.toFilterBean()

        val tagFilter = FilterFactory.get(bean)
        Assert.assertTrue(tagFilter is TagFilter)
        Assert.assertEquals(filter.id, tagFilter.id)

        val card = Card(tags = listOf(tagFoo))
        val card2 = Card(tags = listOf(tagFoo, tagBar))
        val card3 = Card(tags = listOf(tagGosh))
        val card4 = Card(tags = listOf(tagFoo, tagBar, tagGosh))

        Assert.assertFalse(tagFilter.check(card))
        Assert.assertTrue(tagFilter.check(card2))
        Assert.assertFalse(tagFilter.check(card3))
        Assert.assertTrue(tagFilter.check(card4))
    }
}