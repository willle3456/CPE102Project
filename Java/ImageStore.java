import processing.core.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ImageStore 
{
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	public static final int[] DEFAULT_IMAGE_COLOR = {128,128,128,0};
	
	public PImage createDefaultImage(int tileWidth, int tileHeight, PApplet p)
	{
		
		//PImage surf = p.createImage(tileWidth, tileHeight, p.RGB);
		PImage surf = p.loadImage("../images/none.bmp");
		//p.fill(DEFAULT_IMAGE_COLOR[0], DEFAULT_IMAGE_COLOR[1], DEFAULT_IMAGE_COLOR[2], DEFAULT_IMAGE_COLOR[3]);
		return surf;
	}
	
	public HashMap<String,ArrayList<PImage>> loadImages(String fileName, int tileWidth, int tileHeight, PApplet p) throws FileNotFoundException
	{
		HashMap<String, ArrayList<PImage>> images = new HashMap<String,ArrayList<PImage>>();
		
		try(Scanner s = new Scanner(new FileReader(fileName)))
		{
			while(s.hasNextLine())
			{
				processImageLine(images, s.nextLine(),p);
			}
		}
		
		if(images.get(DEFAULT_IMAGE_NAME) == null)
		{
			PImage defaultImage = createDefaultImage(tileWidth, tileHeight, p);
			ArrayList<PImage> temp = new ArrayList<PImage>();
			temp.add(defaultImage);
			images.put(DEFAULT_IMAGE_NAME,temp);
			
		}
		
		return images;
	}
	public void processImageLine(HashMap <String,ArrayList<PImage>> images, String line, PApplet p)
	{
		String[] attrs = line.split(" ");
		if(attrs.length >= 2)
		{
			String key = attrs[0];
			PImage img = p.loadImage("../" + attrs[1]);
			
			if( img != null)
			{
				List<PImage> imgs = getImagesInternal(images,key);
				imgs.add(img);
				images.put(key,(ArrayList<PImage>) imgs);
				
				if(attrs.length == 6)
				{
					int r = Integer.parseInt(attrs[2]);
					int g = Integer.parseInt(attrs[3]);
					int b = Integer.parseInt(attrs[4]);
					int a = Integer.parseInt(attrs[5]);
					img.set(0, 0, p.color(r,g,b));// for the whole image
					
				}
			}
			
		}
		
	}
	public ArrayList<PImage> getImagesInternal(HashMap<String,ArrayList<PImage>> images, String key)
	{
		if(images.containsKey(key))
		{
			return images.get(key);
		}
		return new ArrayList<PImage>(); 
	}
	
	public static ArrayList<PImage> getImages(HashMap<String, ArrayList<PImage>> images, String key)
	{
		if(images.containsKey(key))
		{
			return images.get(key);
		}
		return images.get(DEFAULT_IMAGE_NAME); 
	}
}
