package com.google.mlkit.showcase.translate.util.similarity

import android.util.Log
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
                result++
            }
            arr[pA][pB] = result
            return result
        }
    }

    override fun isVeryDifferent(a: String, b: String): Boolean {
        val v = calculate(a, b)
        val l = min(a.length, b.length)
        Log.w("Calculator", "---------------------------------------------------------------------\n" +
                "a:\n$a\n b:\n$b\n diff:$v; min length: $l; threshold: ${l * thres}\n" +
                "---------------------------------------------------------------------\n")
        return v >= l * thres
    }

    override fun calculate(a: String, b: String): Double {
        if (a.isEmpty()) return b.length.toDouble()
        if (b.isEmpty()) return a.length.toDouble()
        val arr: Array<Array<Int?>> = Array(a.length + 1) { Array(b.length + 1) {null} }
        return calculate(a, b, 0, 0, arr).toDouble()
    }
}