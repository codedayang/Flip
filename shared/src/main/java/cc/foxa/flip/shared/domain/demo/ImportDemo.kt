package cc.foxa.flip.shared.domain.demo

import cc.foxa.flip.shared.data.CardRepository
import cc.foxa.flip.shared.data.model.Card
import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.data.model.Tag
import cc.foxa.flip.shared.data.source.CardTagDao
import cc.foxa.flip.shared.domain.UseCase
import cc.foxa.flip.shared.domain.card.UpdateCard
import cc.foxa.flip.shared.domain.rule.UpdateRule
import cc.foxa.flip.shared.domain.tag.UpdateTag
import cc.foxa.flip.shared.filter.TagFilter
import javax.inject.Inject


class ImportDemo @Inject constructor(
    private val updateCard: UpdateCard,
    private val updateTag: UpdateTag,
    private val updateRule: UpdateRule
): UseCase<Unit, Unit>() {
    override fun execute(parameters: Unit) {
        val tag = Tag(id = "095b4567-08b7-4f1b-a101-44c924863c69", name = "科目三灯光")
        updateTag.executeNow(tag)

        val cards = mutableListOf<Card>()
        cards.add(Card(id = "7ac0bce9-b50c-4377-bdcb-dd5c14690dd2", front = "夜间急转弯", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(id = "b5d4d4a9-1de8-4e25-99d5-9c481aca300b \n", front = "夜间通过坡路", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(id = "06441594-ed24-4db8-aebd-226ba9dc8252", front = "夜间通过窄桥", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(id = "dc09aadc-3afc-4f07-ab3a-003419ee62ac", front = "夜间通过人行横道", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(id = "b3972fe2-4d37-4999-9136-9349559929ca \n", front = "夜间通过没有交通信号灯控制的路口", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(id = "da4eadf0-2249-4d4f-998c-4a3fb2535c49 \n", front = "夜间超车", back = "交替使用远近光灯", tags = listOf(tag)))
        cards.add(Card(id = "4a2caafb-e7d4-49b3-9439-029d94f365bb \n", front = "夜间同方向近距离跟车行驶", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(id = "3d344c37-3440-4275-beb2-ebeff0f86dee", front = "夜间会车", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(id = "e84574ca-3ca0-424e-bea0-b0dce3ee88ab", front = "夜间直行通过路口", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(id = "7c592164-c5f8-4845-a84c-9f36e4d887a8", front = "夜间在有路灯的道路上行驶", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(id = "ef706bc0-6c9d-4d03-8c33-2eeb12b159d3 \n", front = "夜间在照明良好的道路上形式", back = "开近光灯", tags = listOf(tag)))
        cards.add(Card(id = "39b4fe52-5484-402e-a164-951597fc63b5", front = "夜间在没有照明的道路上行驶", back = "开远光灯", tags = listOf(tag)))
        cards.add(Card(id = "577aee32-cb3a-4c0c-9b93-695b70fa7aa6", front = "夜间在照明不良的道路上行驶", back = "开远光灯", tags = listOf(tag)))
        cards.add(Card(id = "6581d138-913b-41f2-b08f-19cd31c0d5bf", front = "车辆发生故障", back = "示廓灯+危险报警灯", tags = listOf(tag)))
        cards.add(Card(id = "d99db351-be49-4e87-a7a8-d5e515cb11b0", front = "路边临时停车", back = "示廓灯+危险报警灯", tags = listOf(tag)))
        cards.add(Card(id = "45f44478-7a05-455b-8bf5-8e846c262dc4", front = "发生交通事故", back = "示廓灯+危险报警灯", tags = listOf(tag)))
        cards.add(Card(id = "bcc510db-1fc5-4dbd-b066-658f1e18041c", front = "雾天行驶", back = "双跳灯+雾灯", tags = listOf(tag)))

        cards.forEach {
            updateCard.executeNow(it)
        }

        val rule = Rule(id = "5b94a46f-0df2-411a-ae0e-790d8755cc82", name = "科目三灯光", filters =
            listOf(TagFilter(id = "c7826155-f7ee-4ec3-a06a-aba0676d08c9", tags = listOf(tag))))
        updateRule.executeNow(rule)
    }

}