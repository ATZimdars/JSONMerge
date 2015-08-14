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
	public static ArrayList<String> indexObjects = new ArrayList<String>();
	public static ArrayList<String> lined = new ArrayList();
	public static ArrayList<String> important = new ArrayList();
	public static JSONObject jsonFromWeb;
	
	@SuppressWarnings({ "unused" })
	public static void main(String[] args) throws ParseException, FileNotFoundException
	{
		JSONParser parser = new JSONParser();
		File f = new File("/Users/azimdars/documents/syria_instagram_json/instagram_json/");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		for (int i = 9358 ; i < files.size();i++)
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
						JSONArray tags = theID.getJSONArray("tags");
						String tagsF = ("tags" + tags);
						JSONObject user = theID.getJSONObject("user");
						String userF = ("user" + user);
						JSONObject comments = theID.getJSONObject("comments");
						String commentsF = ("comments" + comments);
						String link = theID.getString("link");
						String linkF = ("link" + link);
						JSONObject location = theID.getJSONObject("location");
						String locationF = ("location" + location);
						JSONObject likes = theID.getJSONObject("likes");
						String likesF = ("likes" + likes);
						JSONObject oneMore = null;
						URL download = new URL("http://imagecat.dyndns.org:8081/solr/imagecatdev/select?q=id%3A*"+finall+".jpg*&wt=json&indent=true");
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(download.openStream(), "UTF-8"))) 
						{
						    for (String line; (line = reader.readLine()) != null;) 
						    {
						    	lines = line;
						    	lined.add(lines);
						    }
						}
						File indexJSONFile2 = new File("/Users/azimdars/Documents/syria_instagram_json/imagecatdev_solr_text/"+ finall+".txt");
						FileWriter hereWEgo = new FileWriter(indexJSONFile2);
						try 
						{
							hereWEgo.write(lined.toString());
				            lined.clear();
				            System.out.println("Successfully Downloaded JSON Object");
				        } 
						catch (IOException e) 
						{
				            e.printStackTrace();
				 
				        } 
						finally 
						{
							hereWEgo.flush();
							hereWEgo.close();
				 
				        }
						File indexJSONFile3 = new File("/Users/azimdars/Documents/syria_instagram_json/imagecatdev_solr_text/"+ finall+".json");
						System.out.println(indexJSONFile2);
						try
						{
							Object obj2 = parser.parse(new FileReader((indexJSONFile2)));
							String info3 = obj2.toString();
							JSONArray yep = new JSONArray(info3);
							for (int q = 0; q < yep.length(); q++)
							{
								JSONObject yeah = yep.getJSONObject(q);
								JSONObject yessir = yeah.getJSONObject("response");
								JSONArray almost = yessir.getJSONArray("docs");
								for (int m = 0; m < almost.length(); m ++)
								{
									oneMore = almost.getJSONObject(m);
								}
							}
						}
						catch(IOException e)
						{
							System.out.println(e);
						}
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
					            lined.clear();
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
						File indexJSONFile4 = new File("/Users/azimdars/Documents/syria_instagram_json/imagecatdev_solr_text/"+ finall+".txt");
						if (indexJSONFile4.isFile())
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
							JSONObject inputDataStuff = new JSONObject();
							oneMore.put("tags" , tags);
							oneMore.put("user" , user);
							oneMore.put("comments" , comments);
							oneMore.put("link" , link);
							oneMore.put("likes" , likes);
							oneMore.put("location" , location);
							FileWriter finalFile = new FileWriter(newJSONFile);
							System.out.println(oneMore);
							try 
							{
					            finalFile.write(oneMore.toString());
					            System.out.println("Item Finished");
					            info = "";
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
							File flat = new File("/Users/azimdars/Documents/syria_instagram_json/flattened/"+finall+".flat");
							FileWriter flattener = new FileWriter(flat);
							JSONObject nuller = null;
							if (flat.exists())
							{
								flat.delete();
							}
							if (flat.createNewFile() == true)
							{
								try 
								{
									System.out.println("test");
						            flattener.write(flatten(oneMore, nuller).toString());
						            System.out.println("test");
						        } 
								catch (IOException e) 
								{
						            e.printStackTrace();
						 
						        } 
								finally 
								{
						            flattener.flush();
						            flattener.close();
						 
						        } 
							}
							else
							{
								System.out.println("error");
							}
						}
						else
						{
							System.out.println("Error");
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
	private static JSONObject flatten(JSONObject object, JSONObject flattened)
	{
		System.out.println("help");
			if (flattened == null)
			{
				flattened = new JSONObject();
			}
		Iterator<?> keyss= object.keys();
		while(keyss.hasNext())
		{
			String key = (String) keyss.next();
			try
			{
				if(object.get(key) instanceof JSONObject)
				{
					flatten(object.getJSONObject(key), flattened);;
				}
				else
				{
					flattened.put(key, object.get(key));
				}
				System.out.println(flattened);
				System.out.println("whut");
			}
			catch(JSONException e)
			{
				System.out.println(e);
			}
		}
		return flattened;
	}
}
