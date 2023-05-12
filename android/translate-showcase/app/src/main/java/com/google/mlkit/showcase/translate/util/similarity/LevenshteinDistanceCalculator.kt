package com.google.mlkit.showcase.translate.util.similarity

import kotlin.math.min

class LevenshteinDistanceCalculator(val thres: Double): SimilarityCalculator {
    companion object {
        private fun calculate(a: String, b: String, pA: Int, pB: Int, arr: Array<Array<Int?>>): Int {
            if (arr[pA][pB] != null) return arr[pA][pB]!!

            var result: Int
            if (pA == a.length) result = b.length - pB
            else if (pB == b.length) result = a.length - pA
            else if (a[pA] == b[pB]) result = calculate(a, b, pA + 1, pB + 1, arr)
            else {
                result = calculate(a, b, pA, pB + 1, arr)
                result = min(result, calculate(a, b, pA + 1, pB, arr))
                result = min(result, calculate(a, b, pA + 1, pB + 1, arr))
            }
            arr[pA][pB] = result
            return result
        }
    }

    override fun isVeryDifferent(a: String, b: String): Boolean {
        return calculate(a, b) >= min(a.length, b.length) * thres
    }

    override fun calculate(a: String, b: String): Double {
        val arr: Array<Array<Int?>> = Array(a.length) { Array(b.length) {null} }
        return calculate(a, b, 0, 0, arr).toDouble()
    }
}