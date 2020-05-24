package cc.foxa.flip.rulebrowse

import cc.foxa.flip.shared.data.model.Rule

interface RuleBrowseActionHandler {
    fun openRuleEditor(rule: Rule)
}