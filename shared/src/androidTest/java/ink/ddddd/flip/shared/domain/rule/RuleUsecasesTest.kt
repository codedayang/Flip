package ink.ddddd.flip.shared.domain.rule

import androidx.test.ext.junit.runners.AndroidJUnit4
import ink.ddddd.flip.shared.Result
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.data.source.CardTagDao
import ink.ddddd.flip.shared.data.source.RuleFilterDao
import ink.ddddd.flip.shared.di.DaggerTestComponent
import ink.ddddd.flip.shared.filter.TagFilter
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class RuleUsecasesTest {
    @Inject lateinit var ruleFilterDao: RuleFilterDao
    @Inject lateinit var cardTagDao: CardTagDao

    @Before
    fun prepare() {
        DaggerTestComponent.create().inject(this)
    }
    @Test
    fun testUpdateRule() {
        val updateRule = UpdateRule(ruleFilterDao)
        val tag0 = Tag(name = "TagFoo")
        cardTagDao.updateTag(tag0)
        val tagFilter0 = TagFilter(tags = listOf(tag0))
        val rule0 = Rule(name = "RuleFoo")
        tagFilter0.ruleId = rule0.id
        rule0.filters = listOf(tagFilter0)

        updateRule.executeNow(rule0)

        val getRules = GetRules(ruleFilterDao)
        val rule = (getRules.executeNow(Unit) as Result.Success).data[0]

        assertEquals(rule0.id, rule.id)
    }
}