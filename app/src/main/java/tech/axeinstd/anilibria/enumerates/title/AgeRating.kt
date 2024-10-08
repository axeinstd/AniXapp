package tech.axeinstd.anilibria.enumerates.title

enum class AgeRating(val ageRating: String) {
    R0_PLUS("R0_PLUS"),
    R6_PLUS("R6_PLUS"),
    R12_PLUS("R12_PLUS"),
    R16_PLUS("R16_PLUS"),
    R18_PLUS("R18_PLUS");

    override fun toString(): String {
        return ageRating
    }
}