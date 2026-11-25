package com.suwonsmartapp.recipeapp.data

import java.util.Locale

/** 명세 §2.2 Categories / §3.2 카테고리 칩 기준. */
enum class RecipeCategory(val label: String) {
    // 카테고리 제한 없음을 뜻하는 칩 전용 값. 레시피에는 지정하지 않는다.
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

    /** 조리 시간이 조건에 맞는지 검사한다. 경계값(15분, 30분)은 포함하지 않는다. */
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

    /** 평균 평점이 기준값 이상인지 검사한다. */
    fun matches(avg: Double): Boolean = avg >= threshold
}

/** 재료 한 개의 이름과 계량(예: "200g"). */
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
    // 앞뒤 공백을 무시한다. 공백만 입력하면 검색어가 없는 것으로 본다.
    val keyword = query.trim()
    // 제목과 셰프 이름 모두에 검색어가 없으면 제외한다.
    if (keyword.isNotEmpty() &&
        title.contains(keyword, ignoreCase = true).not() &&
        chef.contains(keyword, ignoreCase = true).not()
    ) {
        return false
    }
    // 시간·평점·카테고리를 모두 만족해야 한다. 카테고리를 고르지 않았으면 전체 허용.
    return filter.time.matches(cookingMinutes) &&
        filter.rating.matches(rating) &&
        (filter.categories.isEmpty() || filter.categories.contains(category))
}

/** 공유용 텍스트 — 비어 있는 재료/단계 구역은 생략한다. */
fun Recipe.toShareText(): String = buildString {
    append(title).append(" - ").append(chef)
    if (description.isNotBlank()) append("\n").append(description)
    if (ingredients.isNotEmpty()) {
        append("\n\n[재료]")
        ingredients.forEach { append("\n- ").append(it.name).append(" ").append(it.amount) }
    }
    if (steps.isNotEmpty()) {
        append("\n\n[조리 단계]")
        steps.forEachIndexed { index, step -> append("\n").append(index + 1).append(". ").append(step) }
    }
}

/** 평점을 소수 첫째 자리로 표시한다. Locale.US로 고정해 "4,8"처럼 바뀌는 것을 막는다. */
fun formatRating(rating: Double): String = "%.1f".format(Locale.US, rating)

/** 리뷰 수 표시: 1000 이상은 "1.2k"처럼 줄이고, 그 미만은 그대로 보여준다. */
fun formatReviews(count: Int): String =
    if (count >= 1000) "%.1fk".format(Locale.US, count / 1000.0) else count.toString()
