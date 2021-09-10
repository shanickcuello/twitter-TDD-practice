package userRepository

import user.User

class InMemoryUserRepository : IUserRepository {

    private val usersInDB = mutableListOf<User>()
    private var usersFollowers = mutableMapOf<String, MutableList<String>>()
    private var usersFollows = mutableMapOf<String, MutableList<String>>()

    override fun follow(follower: String, followed: String) {
        addFollower(followed, follower)
        addFollowed(follower, followed)
    }

    override fun updateUsername(oldNickname: String, newNickName: String) { //todo tiene que haber otro metodo para reemplazar la key
        updateFollowers(oldNickname, newNickName)
        updateFollows(oldNickname, newNickName)
    }

    override fun get(): List<User> {
        return usersInDB
    }

    override fun add(user: User) {
        usersInDB.add(user)
        val emptyFollows = mutableListOf<String>()
        initializeFollows(user, emptyFollows)

    }

    override fun getFollowers(nickname: String): List<String> {
        return usersFollowers.getValue(nickname)
    }

    override fun getFolloweds(nickname: String): List<String> {
        return usersFollows.getValue(nickname)
    }

    override fun get(nickName: String): User {
        for (item in usersInDB) {
            if (item.userNickName == nickName)
                return item
        }
        return throw Exception("User doesn't exist")
    }

    private fun initializeFollows(user: User, emptyFollows: MutableList<String>) {
        usersFollowers.put(user.userNickName, emptyFollows)
        usersFollows.put(user.userNickName, emptyFollows)
    }

    private fun addFollowed(follower: String, followed: String) {
        val followedsOfUser = usersFollows.getValue(follower)
        if (isNotAlreadyFollowed(followedsOfUser, followed))
            followedsOfUser.add(followed)
    }

    private fun addFollower(followed: String, follower: String) {
        val followersOfUser = usersFollowers.getValue(followed)
        if (isNotAlreadyFollower(followersOfUser, follower))
            followersOfUser.add(follower)
    }

    private fun isNotAlreadyFollowed(
        followsOfUser: MutableList<String>,
        followed: String
    ) = !followsOfUser.contains(followed)

    private fun isNotAlreadyFollower(
        followersOfUser: MutableList<String>,
        follower: String
    ) = !followersOfUser.contains(follower)

    private fun updateFollows(oldNickname: String, newNickName: String) {
        val followeds = usersFollows[oldNickname]
        usersFollows.remove(oldNickname)
        if (followeds != null) {
            usersFollows.put(newNickName, followeds)
        }
    }

    private fun updateFollowers(oldNickname: String, newNickName: String) {
        val followers = usersFollowers[oldNickname]
        usersFollowers.remove(oldNickname)
        if (followers != null) {
            usersFollowers.put(newNickName, followers)
        }
    }
}