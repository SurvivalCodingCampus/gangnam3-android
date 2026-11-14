package com.suwonsmartapp.recipeapp.data

import java.util.Locale

/** 명세 §2.2 Categories / §3.2 카테고리 칩 기준. */
enum class RecipeCategory(val label: String) {
    All("All"),
    Asian("Asian"),
    Italian("Italian"),
    Mexican("Mexican")
}

/** 명세 §3.5 검색 필터 — 조리 시간 */
enum class TimeFilter(val label: String) {
    Any("All"),
    Under15("< 15 min"),
    Under30("< 30 min");

    fun matches(minutes: Int): Boolean = when (this) {
        Any -> true
        Under15 -> minutes < 15
        Under30 -> minutes < 30
    }
}

/** 명세 §3.5 검색 필터 — 평점 조건(별점 배지, 최소 평점 기준) */
enum class RatingFilter(val label: String, val threshold: Double) {
    Any("Any", 0.0),
    Five("⭐️ 5", 5.0),
    Four("⭐️ 4", 4.0),
    Three("⭐️ 3", 3.0),
    Two("⭐️ 2", 2.0);

    fun matches(avg: Double): Boolean = avg >= threshold
}

data class Ingredient(val name: String, val amount: String)

/** 명세 §2.2 Recipes + §3.3 재료/단계. `emoji`는 이미지 없을 때의 자리표시(로더 도입 시 폴백 유지). */
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val chef: String,
    val category: RecipeCategory,
    val cookingMinutes: Int,
    val rating: Double,
    val reviews: Int,
    val emoji: String,
    val isNew: Boolean = false,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<String> = emptyList(),
    val thumbnailUrl: String? = null,
)

/** 명세 §3.5 검색/필터 조건. */
data class FilterState(
    val time: TimeFilter = TimeFilter.Any,
    val rating: RatingFilter = RatingFilter.Any,
    val categories: Set<RecipeCategory> = emptySet(),
)

/** 검색·필터 매칭 — 순수 함수(전역 AND)라 단위 테스트 가능. */
fun Recipe.matches(query: String, filter: FilterState): Boolean {
    if (query.isNotBlank() &&
        title.contains(query, ignoreCase = true).not() &&
        chef.contains(query, ignoreCase = true).not()
    ) {
        return false
    }
    return filter.time.matches(cookingMinutes) &&
        filter.rating.matches(rating) &&
        (filter.categories.isEmpty() || filter.categories.contains(category))
}

fun formatRating(rating: Double): String = "%.1f".format(Locale.US, rating)

fun formatReviews(count: Int): String =
    if (count >= 1000) "%.1fk".format(Locale.US, count / 1000.0) else count.toString()
