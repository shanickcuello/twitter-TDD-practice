package DynamoDb

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.concurrent.CompletableFuture


class Table(private val dynamoDbAsyncClient: DynamoDbAsyncClient, private val tableName: String) {

    fun createTableRequest(): CreateTableRequest =
        CreateTableRequest.builder()
            .tableName(tableName)
            .attributeDefinitions(
                AttributeDefinition.builder()
                    .attributeName("nickName")
                    .attributeType(ScalarAttributeType.S).build()
            )
            .keySchema(
                KeySchemaElement.builder()
                    .attributeName("nickName")
                    .keyType(KeyType.HASH).build()
            )
            .provisionedThroughput(
                ProvisionedThroughput.builder()
                    .readCapacityUnits(5)
                    .writeCapacityUnits(5).build()
            )
            .build()

    fun createTable(): CompletableFuture<CreateTableResponse>? {
        return dynamoDbAsyncClient.createTable(
            createTableRequest()
        )
    }
}

