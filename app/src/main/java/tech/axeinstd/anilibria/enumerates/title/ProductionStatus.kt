package tech.axeinstd.anilibria.enumerates.title

enum class ProductionStatus(val productionStatus: String) {
    IS_IN_PRODUCTION("IS_IN_PRODUCTION"),
    IS_NOT_IN_PRODUCTION("IS_NOT_IN_PRODUCTION");

    override fun toString(): String {
        return productionStatus
    }
}