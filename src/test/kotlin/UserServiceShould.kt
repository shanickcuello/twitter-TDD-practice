import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import user.User
import user.UserService
import userRepository.InMemoryUserRepository

class UserServiceShould {

    private val userRepository = InMemoryUserRepository()
    private val userService = UserService(userRepository)

    @Test
    fun registrateUserWithNameAndNickName() {
        val user = whenSpecificUserIsCreated()
        thenSpecificUserNameExpected(user)
        thenSpecificNickNameExpected(user)
    }

    @Test
    fun duplicatedNickNameExpectException() {
        val exception = assertThrows<Exception> {
            whenDuplicatedUsersAreCreated()
        }
        thenExceptionIsExpected(exception)
    }

    @Test
    fun updateUserRealName() {
        val userToUpdateName = givenSomeUser()
        whenNameIsUpdated(userToUpdateName)
        thenNameExpectToBeUpdated(userToUpdateName)
    }

    @Test
    fun returnUserByID() {
        val user = whenUserIsCreated()
        thenCanGetIt(user)
    }

    @Test
    fun returnFollowers(){
        val user = givenAUser()
        thenHasfollowers(user)
    }

    @Test
    fun followUser(){
        givenTwoUsers()
        whenOneFollowOther()
        thenIsInFollowers()
    }

    private fun givenAUser(): User {
        return userService.createNewUser("Santi", "@santi")
    }

    private fun givenTwoUsers() {
        userService.createNewUser("Leo", "@leo")
        userService.createNewUser("Alan", "@alan")
    }

    private fun givenSomeUser(): User {
        return userService.createNewUser("name to change", "indesitionUser")
    }

    private fun whenNameIsUpdated(userToUpdateName: User) {
        userService.updateUserName(userToUpdateName, "newName")
    }

    private fun whenUserIsCreated(): User {
        return userService.createNewUser("Alan", "@alan")
    }

    private fun whenSpecificUserIsCreated(): User {
        return whenUserCreated("shanick", "shan")
    }

    private fun whenDuplicatedUsersAreCreated() {
        var user = userService.createNewUser("Shanick Gauthier", "@shan")
        var user2 = userService.createNewUser("Shanick Gauthier", "@shan")
    }

    private fun whenUserCreated(name: String, nickName: String): User {
        return userService.createNewUser(name, nickName)
    }

    private fun whenOneFollowOther() {
        userService.followUser("@leo", "@alan")
    }

    private fun thenCanGetIt(alan: User) {
        Assertions.assertEquals(alan, userService.getUser("@alan"))
    }

    private fun thenNameExpectToBeUpdated(userToUpdateName: User) {
        Assertions.assertEquals("newName", userToUpdateName.userName)
    }

    private fun thenSpecificNickNameExpected(user: User) {
        Assertions.assertEquals("shan", user.userNickName)
    }

    private fun thenSpecificUserNameExpected(user: User) {
        Assertions.assertEquals("shanick", user.userName)
    }

    private fun thenExceptionIsExpected(exception: Exception) { //revisar como hacerlo sin equal
        Assertions.assertEquals(exception.message, "NickName Already used")
    }

    private fun thenIsInFollowers() {
        Assertions.assertEquals(true, isUserInFollowers())
    }

    private fun thenHasfollowers(user: User) {
        Assertions.assertNotNull(userService.getFollowers(user.userNickName))
    }

    private fun isUserInFollowers() = hasSpecificFollower("@leo", userService.getFollowers("@alan"))

    private fun hasSpecificFollower(follower: String, followers: List<String>): Boolean {
        for(item in followers){
            if(followers.contains(follower))
                return true
        }
        return false
    }
}