package de.gello.domain.model

data class Entry(
    val id: Int,
    val name: String,
    val owner: String,
    val createdAt: String,
    val updatedAt: String? = ""
) {
    companion object {
        val mocks = listOf(
            Entry(1, "Entry 1", "Tester", "23/23/34"),
            Entry(2, "Entry 2", "Admin", "23/23/34"),
            Entry(3, "Entry 3", "Max", "23/23/34")
        )
    }
}
