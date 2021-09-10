package userRepository

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.*
import user.User


class DynamoDbUserRepository(private val dynamoClient: DynamoDbAsyncClient) : IUserRepository {

    override fun get(): List<User> {
        return mutableListOf()
    }


    override fun get(nickName: String): User? {
        val getItemRequest = GetItemRequest.builder()
            .tableName("users")
            .key(
                mapOf(nickName to AttributeValue.builder().s(nickName).build())
            ).build()

        return dynamoClient.getItem(getItemRequest)
            .handle { t, _ -> handleEmptyResponse(t) }
            .get().invoke()
    }

    private fun handleEmptyResponse(itemResponse:GetItemResponse):() -> User? {
        val itemValues = itemResponse.item()
        if ( itemValues.isNotEmpty() )
            return { User("HANDLER",
                itemValues["nickName"]
                    .toString()) }
        else
            return { null }
    }


    //---------------

    override fun add(user: User) {
        putItemInTable(dynamoClient, "users","nickName","name", user)
    }
    private fun putItemInTable(
        ddb: DynamoDbAsyncClient,
        tableName: String,
        key: String,
        name : String,
        user: User
    ) {//todo
        val itemValues = HashMap<String, AttributeValue>()
        itemValues[key] = AttributeValue.builder().s(user.userNickName).build()
        itemValues[name] = AttributeValue.builder().s(user.userName).build()
        val request = PutItemRequest.builder()
            .tableName(tableName)
            .item(itemValues)
            .build()
        ddb.putItem(request).get()
    }

    override fun getFollowers(nickname: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun getFolloweds(nickname: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun updateUsername(oldNickname: String, newNickName: String) {
        TODO("Not yet implemented")
    }

    override fun follow(follower: String, followed: String) {
        TODO("Not yet implemented")
    }

//    private fun createItemRequest(atributes: MutableMap<String, AttributeValue>): GetItemRequest {
//        val itemRequest = GetItemRequest
//            .builder()
//            .tableName("users")
//            .key(atributes)
//            .build()
//        return itemRequest
//    }

    private fun createAttributes(nickName: String): MutableMap<String, AttributeValue> {
        val atributes = mutableMapOf<String, AttributeValue>()
        atributes.put(
            "nickName",
            AttributeValue
                .builder()
                .s(nickName)
                .build()
        )
        return atributes
    }

}