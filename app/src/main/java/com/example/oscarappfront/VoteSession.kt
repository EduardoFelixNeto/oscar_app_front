object VoteSession {
    var userId: Long? = null
    var userToken: Int? = null
    var selectedMovieId: Long? = null
    var selectedMovieName: String? = null
    var selectedDirectorId: Long? = null
    var selectedDirectorName: String? = null

    fun clearSession() {
        selectedMovieId = null
        selectedMovieName = null
        selectedDirectorId = null
        selectedDirectorName = null
    }
}
