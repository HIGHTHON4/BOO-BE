package com.example.domain.report.enum

enum class FearLevel {
    F, E, D, C, B, A, S;

    fun getFearLevel(ordinal: Int): FearLevel {
        return entries.getOrNull(ordinal)!!
    }
}