package jdbcsample;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DynamoDBsample  implements RequestHandler<PersonRequest, PersonResponse> {


    // Refers : https://www.baeldung.com/aws-lambda-dynamodb-java

    //another reference : https://www.javacodegeeks.com/2020/03/aws-lambda-to-save-data-in-dynamodb.html

    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "UserDetails";
    private Regions REGION = Regions.US_EAST_1;

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("HHmmss");


    public PersonResponse handleRequest(PersonRequest personRequest, Context context)
    {

        this.initDynamoDbClient();

        persistData(personRequest);

        PersonResponse personResponse = new PersonResponse();
        personResponse.setMessage("Saved Successfully!!!");
        return personResponse;
    }

    private PutItemOutcome persistData(PersonRequest personRequest)
            throws ConditionalCheckFailedException
    {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(sdf1.format(timestamp));

        this.dynamoDb.getTable("UserData")
                .putItem(new PutItemSpec().withItem(new Item()
                        .withInt("id", Integer.parseInt(sdf1.format(timestamp) ))
                        .withString("phoneno", personRequest.getPhoneno())
                        .withString("firstName", personRequest.getFirstName())
                        .withString("lat", personRequest.getLat())
                        .withString("lng", personRequest.getLng())) );

        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                .putItem(new PutItemSpec().withItem(new Item()
                        .withString("phoneno", personRequest.getPhoneno())
                                .withString("firstName", personRequest.getFirstName())
                                .withString("lat", personRequest.getLat())
                        .withString("lng", personRequest.getLng())) );


    }

    private void initDynamoDbClient()
    {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }

}
