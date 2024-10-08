package tech.axeinstd.anilibria.enumerates.title

enum class ScheduleType(val scheduleType: String) {
    NOW("now"),
    WEEK("week");

    override fun toString(): String {
        return scheduleType
    }
}