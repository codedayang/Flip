package ink.ddddd.flip.shared.util

import android.os.Build
import ink.ddddd.flip.shared.data.model.Card
import java.nio.file.WatchEvent
import java.util.*
import kotlin.Comparator
import kotlin.math.ln
import kotlin.math.pow
import kotlin.random.Random

object WeightedRandomSample {
    fun next(list: List<Card>): Card {
        val heap = PriorityQueue<HeapPair>()
        var Xw = 0.0
        var Tw = 0.0
        var w_acc = 0.0
        for (sample in list) {
            if (heap.size < 1) {
                val wi = sample.priority.toDouble()
                val ui = Random.nextDouble(0.0, 1.0)
                val ki = ui.pow(1.0/wi)
                heap.offer(HeapPair(ki, sample))
                continue
            }

            if (w_acc == 0.0) {
                Tw = heap.peek()!!.weight
                val r = Random.nextDouble(0.0, 1.0)
                Xw = ln(r) / ln(Tw)
            }

            val wi = sample.priority.toDouble()
            if (w_acc + wi < Xw) {
                w_acc += wi
                continue
            } else {
                w_acc = 0.0
            }

            val tw = Tw.pow(wi)
            val r2 = Random.nextDouble(tw-0.001, 1.0)
            val ki = r2.pow(1.0/wi)
            heap.poll()
            heap.offer(HeapPair(ki, sample))
        }
        return heap.poll()!!.item


    }


    private data class HeapPair(var weight: Double, var item: Card): Comparable<HeapPair> {
        override fun compareTo(other: HeapPair): Int {
            if (weight - other.weight > 0) return -1
            return 1
        }

    }

}
