package jdbcsample;


import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetUserData implements RequestHandler <Object, Object> {


    // Refers - https://www.appsdeveloperblog.com/read-data-from-dynamodb-using-aws-lambda-function/

    DynamoDB dynamoDb;
    Regions dynamoDbRegion;



    public Object handleRequest(Object input, Context context) {
        initDynamoDbClient();

        List<Response> responseList = JournalService.getPapers(dynamoDb);
        return responseList;
    }


    private void initDynamoDbClient() {
        this.dynamoDbRegion=Regions.US_EAST_1;

        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(dynamoDbRegion));
        dynamoDb = new DynamoDB(client);
    }
}
