package io.duke;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import io.duke.bean.KinmuCSVBean;

public class KinmuCSVParser implements Iterable<Map.Entry<String, KinmuCSVBean>>{

    private Map<String, KinmuCSVBean> dataMap = new TreeMap<String, KinmuCSVBean>();

    public KinmuCSVParser(String path) {

        try (FileReader fr = new FileReader(new File(path))) {

            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                String[] ary = line.split(",");

                KinmuCSVBean bean = new KinmuCSVBean();
                bean.setSyain_no(ary[0]);
                bean.setNowDate(ary[1]);
                bean.setStartTime(ary[2]);
                bean.setEndTime(ary[3]);

                dataMap.put(bean.getNowDate(), bean);

                line = bfr.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Iterator<Map.Entry<String, KinmuCSVBean>> iterator() {

        return dataMap.entrySet().iterator();
    }


}
