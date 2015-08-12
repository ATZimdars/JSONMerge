import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class NameChange extends JsonMerge
{
	public static void main(String[] args)
	{
		File d = new File("/Users/azimdars/documents/syria_instagram_json");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(d.listFiles()));
		for (int i = 0; i <= files.size()-1;i++)
		{
			File newFile = new File("/Users/azimdars/documents/syria_instagram_json/"+i+".json");
			files.get(i).renameTo(newFile);
			String path = newFile.getAbsolutePath();
			chosenOne = new File(path);
			System.out.println(path);
		}
	}
}
