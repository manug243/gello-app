package de.gello.domain.model

data class Entry(
    val type: String, //needs to change to EntryType later on (type name, type icon)
    val owner: String,
    val createdAt: String
) {
    companion object {
        val mocks = listOf(
            Entry("1", "Tester", "23/23/34"),
            Entry("3", "Tester", "23/23/34"),
            Entry("2", "Tester", "23/23/34")
        )
    }
}
