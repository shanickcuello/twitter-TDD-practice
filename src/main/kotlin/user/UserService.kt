package user

import userRepository.DynamoDbUserRepository
import userRepository.IUserRepository
import java.lang.Exception

class UserService(userRepository: IUserRepository) {

    private var dynamoUserRepository = userRepository

    init {
        this.dynamoUserRepository = userRepository as DynamoDbUserRepository
    }

    fun followUser(follower: String, followed: String) {
        dynamoUserRepository.follow(follower, followed)
    }

    fun getUser(userNickname: String): User {
        return dynamoUserRepository.get(userNickname)!!
    }

    fun createNewUser(userName: String, userNickname: String): User {
        canCreateUser(userNickname)
        println("create new user done")
        return createUser(userName, userNickname)

    }

    fun updateUserName(oldNickName: User, newNickName: String) {
        for (item in dynamoUserRepository.get()) {
            if (item == oldNickName)
                item.setName(newNickName)
        }
        dynamoUserRepository.updateUsername(oldNickName.userNickName, newNickName)
    }

    fun getFollowers(nickname: String): List<String> {
        return dynamoUserRepository.getFollowers(nickname)
    }

    fun getFolloweds(nickname: String): List<String> {
        return dynamoUserRepository.getFolloweds(nickname)
    }

    private fun canCreateUser(userNickname: String) {
        if (!isNicknameAvaible(userNickname))
            return throw Exception("NickName Already used")
    }

    private fun createUser(userName: String, userNickname: String): User {
        val user = User(userName, userNickname)
        println("add user to dynamodbRepository called from Interface")
        dynamoUserRepository.add(user)
        return user
    }

    private fun isNicknameAvaible(nickName: String): Boolean {
        if (isNickNameUsed(nickName)) return false
        return true
    }

    private fun isNickNameUsed(nickName: String): Boolean {
        for (item in dynamoUserRepository.get()) {
            if (item.userNickName == nickName)
                return true
        }
        return false
    }
}