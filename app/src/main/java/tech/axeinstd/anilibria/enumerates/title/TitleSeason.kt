package tech.axeinstd.anilibria.enumerates.title

enum class TitleSeason(val season: String) {
    WINTER("winter"),
    SPRING("spring"),
    SUMMER("summer"),
    AUTUMN("autumn");

    override fun toString(): String {
        return season
    }
}