package jdbcsample;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JournalService
{
    static String tableName = "UserDetails";
    static String projectionExpression = "firstName,lat,lng";

    public static List<Response> getPapers(DynamoDB dynamoDb) {
        Table table = dynamoDb.getTable(tableName);
        ScanSpec scanSpec = new ScanSpec().withProjectionExpression(projectionExpression);
        List<Response> responseList = new ArrayList<Response>();
        try {
            ItemCollection<ScanOutcome> items = table.scan(scanSpec);
            Iterator<Item> iter = items.iterator();
            while (iter.hasNext()) {
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
