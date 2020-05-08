package ink.ddddd.flip.rulebrowse

import ink.ddddd.flip.shared.data.model.Rule

interface RuleBrowseActionHandler {
    fun openRuleEditor(rule: Rule)
}