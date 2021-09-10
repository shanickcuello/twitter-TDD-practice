package userRepository
import user.User
import java.util.concurrent.CompletableFuture

interface IUserRepository {
    fun get() : List<User>
    fun get(nickName: String): User?
    fun add(user : User)
    fun getFollowers(nickname : String) : List<String>
    fun getFolloweds(nickname: String): List<String>
    fun updateUsername(oldNickname: String, newNickName: String)
    fun follow(follower: String, followed: String)
}