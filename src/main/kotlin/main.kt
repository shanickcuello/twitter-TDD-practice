import DynamoDb.Table
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import user.UserService
import userRepository.DynamoDbUserRepository
//import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;

import java.net.URI


const val DEFAULT_DYNAMO_HOST = "http://localhost:8000"
//command to start dynamoDb: java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb

fun main() {
//    val client = DynamoDbAsyncClient.builder()
//        .region(Region.US_EAST_1)
//        .endpointOverride(URI(DEFAULT_DYNAMO_HOST))
//        .build()

    val client = DynamoDbAsyncClient.builder()
        .region(Region.US_EAST_1)
        .endpointOverride(URI(DEFAULT_DYNAMO_HOST))
        .build()

    val table = Table(client, "users").createTable()
    val dynamoUserRepository = DynamoDbUserRepository(client)
    val userService = UserService(dynamoUserRepository)
    userService.createNewUser("Leo", "@leo")
    val returnedUser = userService.getUser("@leo")

//
//    val dynamo = DynamoDbUserRepository()
//    val userService = UserService()

//    val dynamoRepository = DynamoDbUserRepository(client).add("") todo


//
//
//    val attributeDefinitions: MutableList<AttributeDefinition> = ArrayList()
//    attributeDefinitions.add(AttributeDefinition().withAttributeName("Id").withAttributeType("N"))
//
//    val keySchema: MutableList<KeySchemaElement> = ArrayList()
//    keySchema.add(KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH)) // Partition
//
//    val request: CreateTableRequest = CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
//        .withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
//            ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(6L)
//        )
//
//    val table: Table = dynamoDB.createTable(request)
//
//    System.out.println("Waiting for " + tableName.toString() + " to be created...this may take a while...")
//    table.waitForActive()


    //todo add(User())
    //todo get y comparar resultado
}