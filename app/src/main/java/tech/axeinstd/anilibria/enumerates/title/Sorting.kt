package tech.axeinstd.anilibria.enumerates.title

enum class Sorting(val sortingString: String) {
    FRESH_AT_DESC("FRESH_AT_DESC"),
    FRESH_AT_ASC("FRESH_AT_ASC"),
    RATING_DESC("RATING_DESC"),
    RATING_ASC("RATING_ASC"),
    YEAR_DESC("YEAR_DESC"),
    YEAR_ASC("YEAR_ASC");

    override fun toString(): String {
        return sortingString
    }
}