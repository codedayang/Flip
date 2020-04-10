package ink.ddddd.flip.shared.di

import dagger.Component
import ink.ddddd.flip.shared.data.CardRepositoryTest
import ink.ddddd.flip.shared.data.source.CardTagDaoTest
import ink.ddddd.flip.shared.data.source.RuleFilterDaoTest
import javax.inject.Singleton

@Singleton
@Component(modules = [SharedModule::class, TestModule::class])
interface TestComponent {
    fun inject(cardTagDaoTest: CardTagDaoTest)
    fun inject(ruleFilterDaoTest: RuleFilterDaoTest)
    fun inject(cardRepositoryTest: CardRepositoryTest)
}