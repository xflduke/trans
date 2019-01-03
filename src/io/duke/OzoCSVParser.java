package io.duke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import io.duke.bean.OzoCSVBean;

public class OzoCSVParser implements Iterable<Map.Entry<String, OzoCSVBean>>{

    private Map<String, OzoCSVBean> dataMap = new TreeMap<String, OzoCSVBean>();

    public OzoCSVParser(String path) {

        try {
        	File fr = new File(path);
        	if (fr.exists()) {
        		try (BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(fr),"Shift-JIS"))){
        			// タイトル削除
        			String line = bfr.readLine();
        			line = bfr.readLine();
                    while (StringUtils.isNotEmpty(line)) {
                        String[] ary = line.split(",");
                        OzoCSVBean bean = new OzoCSVBean();
                        bean.setSyain_no(ary[0].replace("\"", ""));
                        bean.setNowDate(ary[2].replace("/", "").replace("\"", ""));
                        bean.setStartTime(ary[5].replace("\"", ""));
                        bean.setEndTime(ary[6].replace("\"", ""));
                        bean.setReason(ary[37].replace("\"", ""));

                        dataMap.put(bean.getNowDate(), bean);

                        line = bfr.readLine();
                    }
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Iterator<Map.Entry<String, OzoCSVBean>> iterator() {

        return dataMap.entrySet().iterator();
    }


}
