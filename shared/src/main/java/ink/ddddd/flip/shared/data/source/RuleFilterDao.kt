package ink.ddddd.flip.shared.data.source

import androidx.room.*
import ink.ddddd.flip.shared.data.model.FilterBean
import ink.ddddd.flip.shared.data.model.Rule

@Dao
interface RuleFilterDao {
    @Query("SELECT * FROM filter_bean ORDER BY id")
    fun getFilterBeans(): List<FilterBean>

    @Query("SELECT * FROM filter_bean WHERE rule_id = :ruleId ORDER BY id")
    fun getFilterBeans(ruleId: String): List<FilterBean>

    @Query("SELECT * FROM rule ORDER BY id")
    fun getRules(): List<Rule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateFilterBean(filterBean: FilterBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateRule(rule: Rule)

    @Delete
    fun deleteRule(rule: Rule)

    @Delete
    fun deleteFilterBean(filterBean: FilterBean)

    @Query("DELETE FROM filter_bean WHERE rule_id = :ruleId")
    fun deleteFilterBeanByRule(ruleId: String)
}