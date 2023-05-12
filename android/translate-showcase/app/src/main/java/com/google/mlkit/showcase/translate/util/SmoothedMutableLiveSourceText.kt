package com.google.mlkit.showcase.translate.util

import com.google.mlkit.showcase.translate.util.similarity.SimilarityCalculator

class SmoothedMutableLiveSourceText(duration: Long, private val calc: SimilarityCalculator): SmoothedMutableLiveData<String>(duration) {
    override fun setValue(value: String) {
        if (super.pendingValue == null) super.setValue(value)
        else if (calc.isVeryDifferent(super.pendingValue!!, value)) super.setValue(value)
    }
}