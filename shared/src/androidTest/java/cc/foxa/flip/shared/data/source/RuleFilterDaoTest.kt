package cc.foxa.flip.shared.data.source

import androidx.test.ext.junit.runners.AndroidJUnit4
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.di.DaggerTestComponent
import cc.foxa.flip.shared.filter.TagFilter
import cc.foxa.flip.shared.util.FilterFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class RuleFilterDaoTest {
    @Inject lateinit var ruleFilterDao: RuleFilterDao

    @Before
    fun prepare() {
        DaggerTestComponent.create().inject(this)
    }

    @Test
    fun testGetFilterBeans() {
        val rule = Rule(name = "Rule")
        ruleFilterDao.updateRule(rule)

        val bean = TagFilter(tags = listOf(Tag(name = "FOO")), ruleId = rule.id).toFilterBean()
        ruleFilterDao.updateFilterBean(bean)

        Assert.assertEquals(bean, ruleFilterDao.getFilterBeans()[0])
    }

    @Test
    fun testDeleteRule() {
        val rule = Rule(name = "Rule")
        ruleFilterDao.updateRule(rule)

        val bean = TagFilter(tags = listOf(Tag(name = "FOO")), ruleId = rule.id).toFilterBean()
        ruleFilterDao.updateFilterBean(bean)

        ruleFilterDao.deleteRule(rule)

        Assert.assertTrue(ruleFilterDao.getFilterBeans().isEmpty())
        Assert.assertTrue(ruleFilterDao.getRules().isEmpty())
    }

    @Test
    fun testDeleteFilter() {
        val rule = Rule(name = "Rule")
        ruleFilterDao.updateRule(rule)

        val bean = TagFilter(tags = listOf(Tag(name = "FOO")), ruleId = rule.id).toFilterBean()
        ruleFilterDao.updateFilterBean(bean)

        ruleFilterDao.deleteFilterBean(bean)

        Assert.assertTrue(ruleFilterDao.getFilterBeans().isEmpty())

        val rule1 = ruleFilterDao.getRules()[0].apply {
            filters = ruleFilterDao.getFilterBeans(id).map {
                FilterFactory.get(it)
            }
        }
        Assert.assertTrue(rule1.filters.isEmpty())
    }

    @Test
    fun testRuleCheck() {
        val rule = Rule(name = "Rule")
        val tag = Tag(name = "FOO")
        ruleFilterDao.updateRule(rule)

        val bean = TagFilter(tags = listOf(tag), ruleId = rule.id).toFilterBean()
        ruleFilterDao.updateFilterBean(bean)

        val card1 = Card(tags = listOf(tag))
        val card2 = Card(tags = listOf(Tag(name = "BAR")))

        val rule1 = ruleFilterDao.getRules()[0].apply {
            filters = ruleFilterDao.getFilterBeans(id).map {
                FilterFactory.get(it)
            }
        }
        Assert.assertTrue(rule1.check(card1))
        Assert.assertFalse(rule1.check(card2))
    }

}