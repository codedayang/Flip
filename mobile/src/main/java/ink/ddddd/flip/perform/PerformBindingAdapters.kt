package ink.ddddd.flip.perform

import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textview.MaterialTextView
import ink.ddddd.flip.R
import ink.ddddd.flip.shared.data.model.Rule
import ink.ddddd.flip.shared.data.model.RuleSet
import ink.ddddd.flip.shared.data.model.Tag
import ink.ddddd.flip.shared.filter.NoFilter
import ink.ddddd.flip.shared.util.noRuleSet
import kotlinx.android.synthetic.main.fragment_perform.view.*

@BindingAdapter("app:cardTags")
fun cardTags(textView: MaterialTextView, tags: List<Tag>?) {
    val sb = StringBuilder()
    tags?.forEach {
        sb.append(" " + it.name + " |")
    }
    textView.text = sb.toString()

}

@BindingAdapter("app:ruleSet")
fun ruleSet(toolbar: MaterialToolbar, ruleSet: RuleSet) {
    val split = if (ruleSet.isUnion) "∪" else "∩"
    val title = StringBuilder()
    title.append("当前:")
    if (ruleSet.rules.isEmpty() || ruleSet.rules[0].filters.isEmpty()) {
        title.append("全部")
    } else if (ruleSet.rules.size == 1 && ruleSet.rules[0].filters[0]::class == NoFilter::class) {
        title.append("全部")
    } else {
        ruleSet.rules.forEach {
            title.append(it.name)
            title.append(split)
        }
        title.deleteCharAt(title.lastIndex)
    }
    toolbar.title = title
}

@BindingAdapter("app:rules")
fun rules(chipGroup: ChipGroup, rules: List<Rule>?) {
    val context = chipGroup.context
    rules?.forEach {
        val chip = LayoutInflater.from(context).inflate(R.layout.widget_rule_chip, null, false) as Chip
        chip.apply {
            text = it.name
            tag = it
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    chipGroup.findViewById<Chip>(R.id.clear_rule).isChecked = false
                }
            }
        }
        chipGroup.addView(chip)
    }
}