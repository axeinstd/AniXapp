package tech.axeinstd.anilibria.enumerates.title

enum class PublishStatuse(val publishStatus: String) {
    IS_ONGOING("IS_ONGOING"),
    IS_NOT_ONGOING("IS_NOT_ONGOING");

    override fun toString(): String {
        return publishStatus
    }
}