package cc.foxa.flip.shared.di

import dagger.Component
import cc.foxa.flip.shared.data.CardRepositoryTest
import cc.foxa.flip.shared.data.source.CardTagDaoTest
import cc.foxa.flip.shared.data.source.RuleFilterDaoTest
import cc.foxa.flip.shared.domain.rule.RuleUsecasesTest
import javax.inject.Singleton

@Singleton
@Component(modules = [SharedModule::class, TestModule::class])
interface TestComponent {
    fun inject(cardTagDaoTest: CardTagDaoTest)
    fun inject(ruleFilterDaoTest: RuleFilterDaoTest)
    fun inject(cardRepositoryTest: CardRepositoryTest)
    fun inject(ruleUsecasesTest: RuleUsecasesTest)
}