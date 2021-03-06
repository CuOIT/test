package effect;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;

// lop dung de chua cac du lieu cho game
// cac du lieu se duoc tai len trong file du lieu lay tu file data
public class CacheDataLoader {
    
    private static CacheDataLoader instance = null; // instance cua class CacheDataLoader
    
    // Duong dan cua cac file du lieu
    private String framefile = "/text/frame.txt";
    private String animationfile = "/text/animation.txt";
    private String physmapfile = "/map/phys_map.txt";
    private String backgroundmapfile = "/map/background_map.txt";
    private String soundfile = "/text/sounds.txt";
    
    //cac cau truc du lieu dung de luu tat ca du lieu lay tu file du lieu
    private Hashtable<String, FrameImage> frameImages; 
    private Hashtable<String, Animation> animations;
    private Hashtable<String, AudioClip> sounds;
    
    private int[][] phys_map;
    private int[][] background_map;
    
    // ham khoi tao chi dung trong class
    private CacheDataLoader() {}
    
    // phuong thuc lay ra instance cua class CacheDataLoader
    public static CacheDataLoader getInstance(){
        if(instance == null)
            instance  = new CacheDataLoader();
        return instance;
    }
    
    // cac getter dung de tuy cap cac thuoc tinh du lieu
    public AudioClip getSound(String name){
        return instance.sounds.get(name);
    }
    
    public Animation getAnimation(String name){
        
        Animation animation = new Animation(instance.animations.get(name));
        return animation;
        
    }
    
    public FrameImage getFrameImage(String name){

        FrameImage frameImage = new FrameImage(instance.frameImages.get(name));
        return frameImage;

    }
    
    public int[][] getPhysicalMap(){
        return instance.phys_map;
    }
    
    public int[][] getBackgroundMap(){
        return instance.background_map;
    }
    
    // load tat ca du lieu cho game
    public void LoadData()throws IOException{
        
        LoadFrame();
        LoadAnimation();
        LoadPhysMap();
        LoadBackgroundMap();
        LoadSounds();
        
    }
    
    // load tat ca du lieu tu file am thanh vao sounds
    public void LoadSounds() throws IOException{
        
        sounds = new Hashtable<String, AudioClip>();
        
        // cac doi tuong dung de doc file text
        InputStream fr=getClass().getResourceAsStream(soundfile);
		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//        FileReader fr = new FileReader(soundfile);
//        BufferedReader br = new BufferedReader(fr);
//        
        String line = null;
        
        if(br.readLine()== null) { // khong co du lieu nem vao ngoai le
            throw new IOException();
        }
        else {
            
            fr = getClass().getResourceAsStream(soundfile);
            // doi tuong dung doc file
            br = new BufferedReader(new InputStreamReader(fr)); // dua con tro ve dau file
            
            // bo qua dong trong
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            
            // doc tat ca du lieu tu file data cho vao sounds
            for(int i = 0;i < n; i ++){
                
                AudioClip audioClip = null;
                while((line = br.readLine()).equals(""));

                String[] str = line.split(" "); // dung de tach cac chu cach nhau boi dau " "
                String name = str[0];                
                String path = str[1];

                try {
                   audioClip =  Applet.newAudioClip(new URL("file","",str[1]));

                } catch (MalformedURLException ex) {}
                // cho du lieu da doc duoc vao sounds
                instance.sounds.put(name, audioClip);
            }
            
        }
        // dong flie
        br.close();
        
    }
    
    // load tat ca du lieu ve backgroundmap vao game 
    public void LoadBackgroundMap() throws IOException{
        
    	InputStream fr=getClass().getResourceAsStream(backgroundmapfile);
		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//        FileReader fr = new FileReader(backgroundmapfile);
//        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.background_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" |  ");
            for(int j = 0;j<numberOfColumns;j++)
                instance.background_map[i][j] = Integer.parseInt(str[j]);
        }    
        br.close();
        
    }
    // load tat ca du lieu cua map vat ly vao game
    public void LoadPhysMap() throws IOException{
    	InputStream fr=getClass().getResourceAsStream(physmapfile);
		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//        FileReader fr = new FileReader(physmapfile);
//        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.phys_map = new int[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split(" ");
            for(int j = 0;j<numberOfColumns;j++)
                instance.phys_map[i][j] = Integer.parseInt(str[j]);
        }
        
        
        br.close();
        
    }
    // load tat ca animation vao game
    public void LoadAnimation() throws IOException { 
        
        animations = new Hashtable<String, Animation>();
        
        InputStream fr=getClass().getResourceAsStream(animationfile);
		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//        FileReader fr = new FileReader(animationfile);
//        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) {
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr =getClass().getResourceAsStream(animationfile);
            br =new BufferedReader(new InputStreamReader(fr));
            
            while((line = br.readLine()).equals(""));
            int n = Integer.parseInt(line);
            
            for(int i = 0;i < n; i ++){
                
                Animation animation = new Animation();
                
                while((line = br.readLine()).equals(""));
                animation.setName(line);
                
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                for(int j = 0;j<str.length;j+=2)
                    animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
                
                instance.animations.put(animation.getName(), animation);
                
            }
            
        }
        
        br.close();
    }
    
    // load tat ca frameimage vao game
    public void LoadFrame() throws IOException{
        frameImages = new Hashtable<String, FrameImage>();
        InputStream fr=getClass().getResourceAsStream(framefile);
		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//        InputStream fr=getClass().getResourceAsStream(framefile);
//		BufferedReader br=new BufferedReader(new InputStreamReader(fr));
//        FileReader fr = new FileReader(framefile); // ?????c file
//        BufferedReader br = new BufferedReader(fr); 
        
        String line = null; // l??u t???ng d??ng trong file
        
        if(br.readLine()==null) {// file kh??ng d??? li???u
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr =getClass().getResourceAsStream(framefile);
            br =new BufferedReader(new InputStreamReader(fr));// ????a con tr??? v??? ?????u file
            
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            String path = null;
            BufferedImage imageData = null;
            int i2 = 0;
            for(int i = 0;i < n; i ++){// l???y x, y, w, h
                
                FrameImage frame = new FrameImage();
                while((line = br.readLine()).equals(""));
                frame.setName(line);
                
                while((line = br.readLine()).equals(""));
                String[] str = line.split(" ");
                
                boolean refreshImage = (path == null || !path.equals(str[1]));
                path = str[1];
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int x = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int y = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int w = Integer.parseInt(str[1]);
                
                while((line = br.readLine()).equals(""));
                str = line.split(" ");
                int h = Integer.parseInt(str[1]);
                
                if(refreshImage) {
                    refreshImage = false;
                    imageData=ImageIO.read(getClass().getResourceAsStream(path));
//                    imageData = ImageIO.read(new File(path));
                }
                if(imageData != null) {
                    BufferedImage image = imageData.getSubimage(x, y, w, h);
                    frame.setImage(image);
                }
                
                instance.frameImages.put(frame.getName(), frame);//push v??o frameImages
            }
            
        }
        
        br.close();
     
    }
    
}