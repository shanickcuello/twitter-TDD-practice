import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import user.User
import user.UserService
import userRepository.InMemoryUserRepository

class RepositoryShould {

    val expectedUsersAmount = 2

    @Test
    fun returnAllUsers() {
        val (userService, allUsers) = givenUserServiceWithRepository()
        whenUsersAreCreated(userService)
        thenUsersCreatedsAreInRepository(allUsers)
    }

    private fun givenUserServiceWithRepository(): Pair<UserService, List<User>> {
        val usersRepository = InMemoryUserRepository()
        val userService = UserService(usersRepository)
        val allUsers = usersRepository.get()
        return Pair(userService, allUsers)
    }

    private fun whenUsersAreCreated(userService: UserService) {
        userService.createNewUser("Shanick", "@shan")
        userService.createNewUser("Paloma", "@riera")
    }

    private fun thenUsersCreatedsAreInRepository(allUsers: List<User>) {
        Assertions.assertEquals(allUsers.count(), expectedUsersAmount)
    }

}