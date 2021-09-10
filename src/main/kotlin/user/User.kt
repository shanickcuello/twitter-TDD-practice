package user

class User(var userName: String, val userNickName: String) {

    fun getName(): String {
        return userName
    }

    fun getNickName(): String {
        return userNickName
    }

    fun setName(newName: String) {
        userName = newName
    }
}