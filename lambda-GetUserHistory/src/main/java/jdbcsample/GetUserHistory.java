package jdbcsample;

import java.util.List;
import java.util.Map;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetUserHistory implements RequestHandler<Map<String,String>, Object>
{

    DynamoDB dynamoDb;
    Regions dynamoDbRegion;


    public Object handleRequest(Map<String,String> req, Context context) {

        LambdaLogger logger = context.getLogger();


        initDynamoDbClient();

        String phoneno = req.get("phoneno");

        logger.log("phoneno :" +phoneno );

        System.out.println( "phoneno : "+ phoneno );

        List<Response> responseList = JournalService_2.getPapers(dynamoDb,phoneno);
        return responseList;


    }

    private void initDynamoDbClient() {
        this.dynamoDbRegion=Regions.US_EAST_1;

        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(dynamoDbRegion));
        dynamoDb = new DynamoDB(client);
    }
}