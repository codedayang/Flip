package ink.ddddd.flip.shared.util

import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.filter.NoFilter

fun noRule() = Rule(name = "NoRule", filters = listOf(NoFilter()))