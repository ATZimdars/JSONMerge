import java.io.*;
import java.util.*;
import java.io.FileReader;
import java.net.URL;

import org.json.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JsonMerge 
{
	public static File chosenOne;
	public static JSONObject newobject;
	public static String lines;
	public static ArrayList<String> lined = new ArrayList();
	@SuppressWarnings({ "unused" })
	public static void main(String[] args) throws ParseException, FileNotFoundException
	{
		JSONParser parser = new JSONParser();
		File f = new File("/Users/azimdars/documents/syria_instagram_json/instagram_json/");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		for (int i = 0 ; i < files.size();i++)
		{
				File newFile = new File("/Users/azimdars/Documents/syria_instagram_json/instagram_json/"+i+".json");
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
						URL download = new URL("http://imagecat.dyndns.org:8081/solr/imagecatdev/select?q=id%3A*"+finall+".jpg*&wt=json&indent=true");
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(download.openStream(), "UTF-8"))) 
						{
						    for (String line; (line = reader.readLine()) != null;) 
						    {
						    	lines = line;
						    	lined.add(lines);
						    	System.out.println(lines);
						    }
						}
						File indexJSONFile2 = new File("/Users/azimdars/Documents/syria_instagram_json/imagecatdev_solr_json/"+ finall+".img");
						System.out.println(indexJSONFile2);
						if (indexJSONFile2.exists())
						{
							indexJSONFile2.delete();
						}
						if(indexJSONFile2.createNewFile() == true)
						{
							System.out.println("File Has Been Downloaded!");
							FileWriter inputter = new FileWriter(indexJSONFile2);
							try 
							{
					            inputter.write(lined.toString());
					            System.out.println("Successfully Copied JSON Object to File...");
					        } 
							catch (IOException e) 
							{
					            e.printStackTrace();
					 
					        } 
							finally 
							{
					            inputter.flush();
					            inputter.close();
					 
					        }
						}
						else
						{
							System.out.println("Error in File Download for: "+download);
						}
						File indexFile = new File("/Users/azimdars/Documents/syria_instagram_json/imagecatdev_solr_json/"+ finall+".img");
						if (indexFile.isFile())
						{
							File newJSONFile = new File("/Users/azimdars/Documents/syria_instagram_json/finished_json/"+finall+".json");
							if (newJSONFile.exists())
							{
								newJSONFile.delete();
							}
							if(newJSONFile.createNewFile() == true)
							{
								System.out.println("JSON Base Created!");
							}
							else
							{
								System.out.println("ERROR");
							}
							Object newObj = parser.parse(new FileReader(indexFile));
							String newInfo = newObj.toString();
							JSONArray FinalJSON = new JSONArray();
							FinalJSON.put(newInfo);
							FinalJSON.put(info);
							FileWriter finalFile = new FileWriter(newJSONFile);
							try 
							{
					            finalFile.write(FinalJSON.toString());
					            System.out.println("Successfully Copied JSON Object to File...");
					        } 
							catch (IOException e) 
							{
					            e.printStackTrace();
					 
					        } 
							finally 
							{
					            finalFile.flush();
					            finalFile.close();
					 
					        }
							
						}
						break;
					}	
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
		System.out.println("DONE");
	}
}
