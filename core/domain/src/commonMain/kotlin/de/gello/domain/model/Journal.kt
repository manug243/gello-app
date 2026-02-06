package de.gello.domain.model

data class Journal(
    val id: Int,
    val title: String,
    val color: String,
    val owner: String,
    val updatedAt: String,
    val entries: List<Entry>? = emptyList()
) {
    companion object {
        val mocks = listOf(
            Journal(1, "Journal Alpha", "#FF5252", "Max Muster", "12/12/23", Entry.mocks),
            Journal(
                2,
                "Journal Beta",
                "#448AFF",
                "Lisa Example",
                "15/01/24",
                listOf(Entry("1", "1", "1"))
            ),
            Journal(
                3,
                "Journal Gamma",
                "#69F0AE",
                "Fabian Brüns",
                "27/01/26",
                listOf(
                    Entry("1", "1", "1"),
                    Entry("1", "1", "1"),
                    Entry("1", "1", "1"),
                    Entry("1", "1", "1")
                )
            ),
            Journal(
                4, "Journal Delta", "#FFD740", "Anna Schmidt", "01/02/26", listOf(
                    Entry("1", "1", "1"),
                    Entry("1", "1", "1")
                )
            ),
            Journal(5, "Journal Epsilon", "#B388FF", "John Doe", "05/02/26"),
            Journal(6, "Daily Notes", "#FF8A65", "Clara Weber", "07/02/26"),
            Journal(7, "Project Phoenix", "#4DB6AC", "Tom Fischer", "08/02/26"),
            Journal(8, "Ideas & Brainstorm", "#90CAF9", "Sarah Klein", "09/02/26"),
            Journal(9, "Meeting Logs", "#A1887F", "Daniel Roth", "10/02/26"),
            Journal(10, "Research Journal", "#81C784", "Julia Hoffmann", "11/02/26"),
            Journal(11, "Private Thoughts", "#F48FB1", "Nina Braun", "12/02/26"),
            Journal(12, "Work Notes", "#CE93D8", "Paul Schneider", "13/02/26"),
            Journal(13, "Travel Diary", "#80DEEA", "Laura König", "14/02/26"),
            Journal(14, "Fitness Log", "#FFAB91", "Markus Vogel", "15/02/26"),
            Journal(15, "Learning Kotlin", "#A5D6A7", "Felix Neumann", "16/02/26"),
            Journal(16, "UX Feedback", "#FFF59D", "Miriam Beck", "17/02/26"),
            Journal(17, "Bug Reports", "#EF9A9A", "Jan Peters", "18/02/26")
        )
    }
}
