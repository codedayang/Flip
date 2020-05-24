package cc.foxa.flip.shared.filter

val FILTER_NAME = mapOf(
    TagFilter::class to "包含TAG",
    KeyWordFilter::class to "包含关键字",
    PriorityFilter::class to "权值范围",
    WithinDaysFilter::class to "X天内添加的卡片"
)