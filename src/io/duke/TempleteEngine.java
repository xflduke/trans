package io.duke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TempleteEngine {
    
    private StringBuilder fullContent = new StringBuilder();
    private StringBuilder templet = new StringBuilder();
    
    public TempleteEngine(String path) {
        
        
        try (FileReader fr = new FileReader(new File(path))) {
            
            BufferedReader bfr = new BufferedReader(fr);

            String line = bfr.readLine();
            while (line != null) {
                templet.append(line);
                templet.append("\n");
                line = bfr.readLine();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public String getTemplet() {
        return templet.toString();
    }
    
    public void addToContent(String content) {
        fullContent.append(content);
    }
    
    public String getResult() {
        return fullContent.toString();
    }
}
