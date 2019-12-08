package ca.johnmajor.aoc2019

/**
 * Returns the permutations of this list.
 *
 * This uses a non-recursive version of Heap's algorithm and does not mutate
 * the original list.
 */
fun <T> List<T>.permutations(): List<List<T>> {
    if (this.isEmpty()) {
        return emptyList()
    }

    if (this.size == 1) {
        return listOf(this)
    }

    val c = IntArray(this.size) { 0 }
    val xs = ArrayList(this)
    val result = mutableListOf(xs.toList())
    var i = 0

    while (i < size) {
        if (c[i] < i) {
            if (i % 2 == 0) {
                val tmp = xs[0]
                xs[0] = xs[i]
                xs[i] = tmp
            } else {
                val tmp = xs[i]
                xs[i] = xs[c[i]]
                xs[c[i]] = tmp
            }
            result.add(xs.toList())
            c[i]++
            i = 0
        } else {
            c[i] = 0
            i++
        }
    }
    return result
}

