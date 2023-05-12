package com.google.mlkit.showcase.translate.util.similarity

interface SimilarityCalculator {
    fun calculate(a: String, b: String): Double
    fun isVeryDifferent(a: String, b: String): Boolean
}