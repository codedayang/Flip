package ink.ddddd.flip.shared.util

import ink.ddddd.flip.shared.data.model.Card
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class WeightedRandomSampleTest {

    @Test
    fun next() {
        val list = listOf(
            Card(front = "1", priority = 1),
            Card(front = "2", priority = 2),
            Card(front = "3", priority = 7)
        )
        val count = intArrayOf(0,0,0,0,0,0,0,0,0,0)
        repeat(10000) {
            val priority = WeightedRandomSample.next(list).priority
            count[priority] = count[priority] + 1
        }
        Assert.assertTrue((count[1]/10000.0)-0.1 < 0.01)
        Assert.assertTrue((count[2]/10000.0)-0.2 < 0.01)
        Assert.assertTrue((count[7]/10000.0)-0.7 < 0.01)

    }
}