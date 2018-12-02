package io.duke;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class TransToOzo {
    
    public static void main(String[] args) {
        
        if (args == null || args.length < 1) {
            System.out.println("パラメータ不正");
        }
        
        try {
        	
        	Properties props = TransProperties.getInstance(args[0]);
        
            // フルJMeterファイル
            // 日付ごとのJMeter部分
            // 勤務システムからのCSVデータ（備考追加可能が、暫く対応しないで）
            // キーワード一覧
            
            // CSVパサー
            ContentManager contentManager = new ContentManager(props.getProperty("fullJmeter"));
            TempleteEngine engine = new TempleteEngine(props.getProperty("templet"));
            KinmuCSVParser csvParser = new KinmuCSVParser(props.getProperty("appspotFile"));
            
            KeyParser keyParser = null;
            for (Entry<String, KinmuCSVBean> entry : csvParser) {
                
                keyParser = new KeyParser(props.getProperty("keyword"));
                keyParser.parserRecord(entry.getValue());
                engine.addToContent(keyParser.replaceKey(engine.getTemplet()));
            }
            
            String rlt = contentManager.makeContentSB(engine.getResult());
            
            // TODO
            if (keyParser != null) {
            	rlt = keyParser.replaceKey(rlt);
            }
            
            // TODO 出力
            System.out.println(rlt);

            File file = new File (props.getProperty("targetXML"));
            if (file.exists()) {
               file.delete();
            }
            file.createNewFile();
            
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
            bfw.write(rlt);
            bfw.flush();
            bfw.close();
            
            runJmeter(file.getAbsolutePath());
            
            
        } catch (ParseException | IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        
    }
    
    public static void runJmeter(String path) throws IOException {
    	// JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties("bin/jmeter.properties");
        JMeterUtils.setJMeterHome(TransProperties.getInstance().getProperty("jmeterHome"));
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        File file = new File(path);
        HashTree testPlanTree = SaveService.loadTree(file);
        // file.close();
        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }

}
