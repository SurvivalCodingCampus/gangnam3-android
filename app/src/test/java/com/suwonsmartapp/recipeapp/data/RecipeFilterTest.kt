package com.suwonsmartapp.recipeapp.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** 검색·필터·포맷 순수 로직 검증 — 에뮬레이터/로보lectric 불필요. */
class RecipeFilterTest {

    private val recipes = sampleRecipes

    @Test
    fun `empty query and filters match everything`() {
        val result = recipes.filter { it.matches("", FilterState()) }
        assertEquals(recipes.size, result.size)
    }

    @Test
    fun `query matches title or chef ignoring case`() {
        assertEquals(listOf("r5"), recipes.filter { it.matches("MISO", FilterState()) }.map { it.id })
        assertEquals(listOf("r1"), recipes.filter { it.matches("mario", FilterState()) }.map { it.id })
        assertTrue(recipes.filter { it.matches("없는재료", FilterState()) }.isEmpty())
    }

    @Test
    fun `Under15 excludes the 15 minute boundary`() {
        // r3(10)만 통과, r1(20)은 제외
        assertEquals(listOf("r3"), recipes.filter { it.matches("", FilterState(time = TimeFilter.Under15)) }.map { it.id })
    }

    @Test
    fun `Under30 excludes the 30 minute boundary`() {
        // r2(30) 경계 제외, r3(10)·r1(20)·r4(25) 통과
        assertEquals(listOf("r1", "r3", "r4"), recipes.filter { it.matches("", FilterState(time = TimeFilter.Under30)) }.map { it.id })
    }

    @Test
    fun `rating filter keeps recipes at or above the star level`() {
        assertTrue(recipes.filter { it.matches("", FilterState(rating = RatingFilter.Five)) }.isEmpty())
        assertEquals(6, recipes.filter { it.matches("", FilterState(rating = RatingFilter.Four)) }.size)
    }

    @Test
    fun `category filter is multi select and empty set means all`() {
        assertEquals(
            listOf("r2", "r5"),
            recipes.filter { it.matches("", FilterState(categories = setOf(RecipeCategory.Asian))) }.map { it.id }
        )
        assertEquals(recipes.size, recipes.filter { it.matches("", FilterState(categories = emptySet())) }.size)
    }

    @Test
    fun `criteria combine with AND`() {
        val filter = FilterState(time = TimeFilter.Under30, rating = RatingFilter.Four, categories = setOf(RecipeCategory.Italian))
        assertEquals(listOf("r1", "r4"), recipes.filter { it.matches("", filter) }.map { it.id })
    }

    @Test
    fun `rating and review formatting is locale independent`() {
        assertEquals("4.8", formatRating(4.8))
        assertEquals("4.0", formatRating(4.0))
        assertEquals("1.2k", formatReviews(1200))
        assertEquals("999", formatReviews(999))
    }

}
