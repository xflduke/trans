package io.duke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ContentManager {
    
    private StringBuilder contentSB = new StringBuilder();
    
    public ContentManager(String path) {
        
        try (FileReader fr = new FileReader(new File(path))) {
            
            BufferedReader bfr = new BufferedReader(fr);

            String line = bfr.readLine();
            while (line != null) {
                contentSB.append(line);
                contentSB.append("\n");
                line = bfr.readLine();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public String makeContentSB(String content) {
        return contentSB.toString().replace("${任務}", content);
    }
    
}
