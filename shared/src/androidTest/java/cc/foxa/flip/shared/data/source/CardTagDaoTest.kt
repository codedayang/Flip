package cc.foxa.flip.shared.data.source

import androidx.test.ext.junit.runners.AndroidJUnit4
import cc.foxa.flip.shared.di.DaggerTestComponent
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class CardTagDaoTest {
    @Inject
    lateinit var database: AppDatabase
    @Inject
    lateinit var cardTagDao: CardTagDao

    @Before
    fun prepare() {
        DaggerTestComponent.create().inject(this)
    }

    @Test
    fun testInsertCardNoTag() {
        val t = Card(front = "FooCard")
        cardTagDao.updateCard(t)

        val list = cardTagDao.getCards()
        Assert.assertEquals(1, list.size)
        Assert.assertEquals(t.id, list[0].id)
    }

    @Test
    fun testInsertMultiCard() {
        for (i in 0..20) {
            val t = Card(front = "CardNoTagFront #${i}", back = "#${i}")
            cardTagDao.updateCard(t)
        }

        val list = cardTagDao.getCards()

        Assert.assertEquals(21, list.size)

        for (i in 0..20) {
            Assert.assertEquals("CardNoTagFront #${i}", list[i].front)
            Assert.assertEquals("#${i}", list[i].back)
        }

    }

    @Test
    fun testInsertCardWithTag() {
        val a = Card(front = "CardWithTag")
        val b = Card(front = "CardNoTag")
        cardTagDao.updateCard(a)

        val tag = Tag(name = "Tag For a")
        cardTagDao.updateTag(tag)

        val join = CardTagJoin(a.id, tag.id)
        cardTagDao.updateJoin(join)

        val card1Tags = cardTagDao.getTagsByCard(a.id)

        Assert.assertEquals(1, card1Tags.size)
        Assert.assertEquals("Tag For a", card1Tags[0].name)
    }

    @Test
    fun testSearch() {
        for (i in 0..20) {
            val t = Card(front = "Card #${i}", back = "#${i}")
            cardTagDao.updateCard(t)
        }

        val card1 = Card(front = "关键词Keyword")
        cardTagDao.updateCard(card1)

        val card2 = Card(front = "词K词K词K")
        cardTagDao.updateCard(card2)

        //surround word by '%' manually
        val searchedResult = cardTagDao.searchCardContent("%词K%")

        Assert.assertEquals(2, searchedResult.size)
        Assert.assertEquals(card1.id, searchedResult[0].id)
        Assert.assertEquals(card2.id, searchedResult[1].id)
    }

    @Test
    fun testUpdateCard() {
        val origin = Card(front = "Original")
        cardTagDao.updateCard(origin)

        origin.front = "Edited"
        cardTagDao.updateCard(origin)

        val list = cardTagDao.getCards()
        Assert.assertEquals(1, list.size)
        Assert.assertEquals("Edited", list[0].front)
    }

    @Test
    fun testDeleteTag() {
        val a = Card(front = "CardWithTag")
        val b = Card(front = "CardNoTag")
        cardTagDao.updateCard(a)

        val tag = Tag(name = "Tag For a")
        cardTagDao.updateTag(tag)

        val join = CardTagJoin(a.id, tag.id)
        cardTagDao.updateJoin(join)

        val card1Tags = cardTagDao.getTagsByCard(a.id)

        Assert.assertEquals(1, card1Tags.size)
        Assert.assertEquals("Tag For a", card1Tags[0].name)

        cardTagDao.deleteTag(tag)

        val card1Tags2 = cardTagDao.getTagsByCard(a.id)

        Assert.assertEquals(0, card1Tags2.size)

    }

}