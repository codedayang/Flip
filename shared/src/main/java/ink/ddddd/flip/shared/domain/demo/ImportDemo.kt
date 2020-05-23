package ink.ddddd.flip.shared.domain.demo

import ink.ddddd.flip.shared.data.CardRepository
import ink.ddddd.flip.shared.data.model.Card
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.data.source.CardTagDao
import ink.ddddd.flip.shared.domain.UseCase
import ink.ddddd.flip.shared.domain.card.UpdateCard
import ink.ddddd.flip.shared.domain.rule.UpdateRule
import ink.ddddd.flip.shared.domain.tag.UpdateTag
import ink.ddddd.flip.shared.filter.TagFilter
import javax.inject.Inject


class ImportDemo @Inject constructor(
    private val updateCard: UpdateCard,
    private val updateTag: UpdateTag,
    private val updateRule: UpdateRule
): UseCase<Unit, Unit>() {
    override fun execute(parameters: Unit) {
        val tag = Tag(name = "科目三灯光")
        updateTag.executeNow(tag)

        val cards = mutableListOf<Card>()
        cards.add(Card(front = "夜间急转弯", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间通过坡路", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间通过窄桥", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间通过人行横道", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间通过没有交通信号灯控制的路口", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间超车", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间同方向近距离跟车行驶", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间会车", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间直行通过路口", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间在有路灯的道路上行驶", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间在照明良好的道路上形式", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间在没有照明的道路上行驶", back = "开远光灯", tags = listOf(tag)))
        cards.add(Card(front = "夜间在照明不良的道路上行驶", back = "开远光灯", tags = listOf(tag)))
        cards.add(Card(front = "车辆发生故障", back = "示廓灯+危险报警灯", tags = listOf(tag)))
        cards.add(Card(front = "路边临时停车", back = "示廓灯+危险报警灯", tags = listOf(tag)))
        cards.add(Card(front = "发生交通事故", back = "示廓灯+危险报警灯", tags = listOf(tag)))
        cards.add(Card(front = "雾天行驶", back = "双跳灯+雾灯", tags = listOf(tag)))

        cards.forEach {
            updateCard.executeNow(it)
        }

        val rule = Rule(name = "科目三灯光", filters = listOf(TagFilter(tags = listOf(tag))))
        updateRule.executeNow(rule)
    }

}