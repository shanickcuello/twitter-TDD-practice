import org.junit.jupiter.api.*
import user.User
import user.UserService
import userRepository.InMemoryUserRepository

class UserShould {

    var twitterUser = User("", "")
    var twitterUser2 = User("", "")
    var expectedUserName = ""
    var _userRepository = InMemoryUserRepository()
    var _userService = UserService(_userRepository)

    @BeforeEach
    fun initialize() {
        twitterUser = _userService.createNewUser("Shanick Gauthier", "@shan")
        twitterUser2 = _userService.createNewUser("Paloma", "@palo")
        expectedUserName = "Shanick Gauthier"
    }

    @Test
    fun haveName() {
        Assertions.assertEquals(expectedUserName, twitterUser.userName)
    }

    @Test
    fun haveNickName() {
        Assertions.assertEquals(expectedUserName, twitterUser.userName)
    }

}