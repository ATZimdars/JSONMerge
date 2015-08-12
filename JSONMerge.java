import java.io.*;
import java.util.*;
import java.io.FileReader;
import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonMerge 
{
	public static File chosenOne;
	public static JSONObject newobject;
	@SuppressWarnings({ "unused" })
	public static void main(String[] args) throws ParseException, FileNotFoundException
	{
		JSONParser parser = new JSONParser();
		File f = new File("/Users/azimdars/documents/syria_instagram_json");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		for (int i = 9359; i <= files.size()-1;i++)
		{
				File newFile = new File("/Users/azimdars/documents/syria_instagram_json/"+i+".json");
				chosenOne = new File(""+newFile);
				System.out.println(chosenOne);
			try
			{
				Object obj = parser.parse(new FileReader((chosenOne)));
				String info = obj.toString();
				JSONObject base = new JSONObject(info);
				JSONArray imageArray = base.getJSONArray("data");
				JSONArray[] images = null;
				for (int a = 0; a < imageArray.length(); a ++)
				{
					JSONObject idsss = imageArray.getJSONObject(a);
					JSONArray ids = idsss.getJSONArray("data");
					for (int b = 0; b < ids.length(); b++)
					{
						JSONObject theID = ids.getJSONObject(b);
						String finall = theID.getString("id");
						System.out.println("Image Number: " + finall);
						JSONArray tags = theID.getJSONArray("tags");
						for (int c = 0; c < tags.length(); c++)
						{
							String tagss = tags.getString(c);
							System.out.println("Tags: " + tagss);
						}
						JSONObject user = theID.getJSONObject("user");
						System.out.println("User: " + user);
						JSONObject comments = theID.getJSONObject("comments");
						System.out.println("Comments: "+ comments);
						JSONObject location = theID.getJSONObject("location");
						System.out.println("Location: " + location);
								
					}
					
				}
					
	
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
}
