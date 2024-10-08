package tech.axeinstd.anilibria.enumerates.title

enum class TitleType(val type: String) {
    TV("TV"),
    ONA("ONA"),
    WEB("WEB"),
    OVA("OVA"),
    OAD("OAD"),
    MOVIE("MOVIE"),
    DORAMA("DORAMA"),
    SPECIAL("SPECIAL");

    override fun toString(): String {
        return type
    }
}