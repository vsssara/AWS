package jdbcsample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class JournalService_2
{
    static String tableName = "UserData";
    static String projectionExpression = "firstName,lat,lng";
    static String keyConditionExpression = "phoneno = :phoneno";

    public static List<Response> getPapers(DynamoDB dynamoDb, String phoneno)
    {
        Table table = dynamoDb.getTable(tableName);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression(keyConditionExpression)
                .withValueMap(new ValueMap().withString(":phoneno", phoneno ))
                .withProjectionExpression(projectionExpression);
        List<Response> responseList = new ArrayList<Response>();

        try
        {
            ItemCollection<QueryOutcome> items = table.getIndex("phoneno-index").query(querySpec);
            Iterator<Item> iter = items.iterator();
            while (iter.hasNext())
            {
                Item item = iter.next();

                Response res = new Response();
                res.setFirstName(item.getString("firstName"));
                res.setLat(item.getString("lat"));
                res.setLng(item.getString("lng"));

                responseList.add(res);
            }
        } catch (Exception e) {
            System.err.println("Unable to scan the table:");
            System.err.println(e.getMessage());
        }

        return responseList;
    }

}
