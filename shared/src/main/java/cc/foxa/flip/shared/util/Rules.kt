package cc.foxa.flip.shared.util

import cc.foxa.flip.shared.data.model.Rule
import cc.foxa.flip.shared.filter.NoFilter

fun noRule() = Rule(name = "NoRule", filters = listOf(NoFilter()))