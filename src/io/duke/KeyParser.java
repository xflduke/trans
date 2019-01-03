package io.duke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.duke.bean.KinmuCSVBean;

import java.util.Properties;

public class KeyParser {

	private Map<String, String> dataMap = new HashMap<String, String>();

	private static volatile int tskIdx = 133;

	public KeyParser(String path) {

		try (FileReader fr = new FileReader(new File(path))) {

			BufferedReader bfr = new BufferedReader(fr);
			String line = bfr.readLine();
			while (line != null) {

				dataMap.put(line, null);
				line = bfr.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parserRecord(KinmuCSVBean bean) throws ParseException {

		synchronized (this) {
			dataMap.put("taskid", new Integer(tskIdx++).toString());
		}
		dataMap.put("db_EDIT_GROUP", TransProperties.getInstance().getProperty("db_EDIT_GROUP"));
//		dataMap.put("db_EDIT_UID", TransProperties.getInstance().getProperty("db_EDIT_UID"));
		dataMap.put("challenge", TransProperties.getInstance().getProperty("challenge"));
		dataMap.put("response", TransProperties.getInstance().getProperty("response"));

		dataMap.put(new String("対象日付"), formatDate(bean.getNowDate()));
		dataMap.put("日付", formatDate(bean.getNowDate()));
		dataMap.put("日付-1", formatDate(bean.getNowDate()));
		dataMap.put("BOSS名前", TransProperties.getInstance().getProperty("bossName"));
		dataMap.put("名前", TransProperties.getInstance().getProperty("name"));
		dataMap.put("社員番号", TransProperties.getInstance().getProperty("ozoUser"));
		dataMap.put("出勤時刻", bean.getStartTime());
		dataMap.put("退勤時刻", bean.getEndTime());
		dataMap.put("休憩1_開始", "12:00"); // TODO
		dataMap.put("休憩1_終了", "13:00"); // TODO
		dataMap.put("休憩2_開始", ""); // TODO
		dataMap.put("休憩2_終了", ""); // TODO
		dataMap.put("休憩3_開始", ""); // TODO
		dataMap.put("休憩3_終了", ""); // TODO
		dataMap.put("総残業", calcZanGyo(bean.getStartTime(), bean.getEndTime()));
		dataMap.put("総労務", calcDiffJikan(bean.getStartTime(), bean.getEndTime(), 1000 * 60 * 60));
		dataMap.put("出勤時間", calcSyukinjikan(bean.getStartTime(), bean.getEndTime()));
		dataMap.put("休憩総時間", "01:00");
		dataMap.put("残業時間", calcZanGyo(bean.getStartTime(), bean.getEndTime()));
		dataMap.put("深夜残業時間", calcShinyajikan(bean.getStartTime(), bean.getEndTime()));
		dataMap.put("合計労務時間", calcDiffJikan(bean.getStartTime(), bean.getEndTime(), 1000 * 60 * 60));
		dataMap.put("プロジェクト情報１", calcPJInfo1(bean.getStartTime(), bean.getEndTime()));
		dataMap.put("プロジェクト情報２", calcPJInfo2(bean.getStartTime(), bean.getEndTime()));
		dataMap.put("ワークタイム", calcDiffJikan(bean.getStartTime(), bean.getEndTime(), 1000 * 60 * 60));
		dataMap.put("総備考", ""); // TODO

		System.out.println("......................START[" + bean.getNowDate() + "].........................");
		for (Entry<String, String> entry : dataMap.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		return;
	}

	// YYYYMMDD→YYYY/MM/DD
	private String formatDate(String date) {

		return date.substring(0, 4) + "/" + date.substring(4, 6) + "/" + date.substring(6, 8);
	}

	private String calcDiffJikan(String start, String end, long yasumi) throws ParseException {

		Date startDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2018/11/01 " + start);
		Date endDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse("2018/11/01 " + end);
		// Date startDate = DateFormat.getDateInstance().parse("2018/11/01 " + start);
		// Date endDate = DateFormat.getDateInstance().parse("2018/11/01 " + end);

		long diff = endDate.getTime() - startDate.getTime();

		// TODO 休憩時間除く
		diff = diff - yasumi;

		// 一日以内の差の前提
		if (diff > 0) {
			String tmp = paddingZero(Math.abs(diff / (1000 * 60 * 60))) + ":"
					+ paddingZero(Math.abs(diff / (1000 * 60) % 60)).toString();

			if (tmp.length() < 5) {
				tmp = "0" + tmp;
			}

			return tmp;

		} else {
			return "";
		}

	}

	// TODO 休憩時間より変わるすべき、残業時間 : 7:30超過時間
	private String calcZanGyo(String start, String end) throws ParseException {

		// return calcDiffJikan("18:00", end, 0);
		String real = calcDiffJikan(start, end, 1000 * 60 * 60);

		if (real.length() == 0) {
			return "";
		}

		String base = "07:30";
		return calcDiffJikan(base, real, 0);
	}

	// TODO 残業より変わるすべき：出勤時間 > 7:30 ? 7:30 : real
	private String calcSyukinjikan(String start, String end) throws ParseException {

		String syukin = calcDiffJikan(start, end, 1000 * 60 * 60);

		if (syukin.compareTo("07:30") > 0) {
			return "07:30";
		} else {
			return syukin;
		}

	}

	// TODO 深夜残業
	private String calcShinyajikan(String start, String end) throws ParseException {

		return calcDiffJikan("22:00", end, 0);
	}

	// TODO
	private String calcPJInfo1(String start, String end) throws ParseException {

		Properties props = TransProperties.getInstance();
		return props.getProperty("PJ_ID")
				+ "§"
				+ props.getProperty("PJ_NAME")
				+ "§"
				+ props.getProperty("PJ_START")
				+ "§"
				+ props.getProperty("PJ_END")
				+ "§0§&nbsp;§&nbsp;§00:00§00:00§"
				+ calcDiffJikan(start, end, 1000 * 60 * 60) 
				+ "§&nbsp;§"
				+ props.getProperty("PJ_ID") 
				+ "§&nbsp;§¤";
	}

	// TODO
	private String calcPJInfo2(String start, String end) throws ParseException {

		Properties props = TransProperties.getInstance();
		return props.getProperty("PJ_ID")
				+ "§"
				+ props.getProperty("PJ_NAME")
				+ "§"
				+ props.getProperty("PJ_START")
				+ "§"
				+ props.getProperty("PJ_END")
				+ "§0§§§00:00§00:00§"
				+ calcDiffJikan(start, end, 1000 * 60 * 60)
				+ "§§"
				+ props.getProperty("PJ_ID")
				+ "§§1§¤§§§§§§§§§§§§§0§¤§§§§§§§§§§§§§0§¤§§§§§§§§§§§§§0§¤§§§§§§§§§§§§§0§¤";
	}

	public String replaceKey(String data) throws UnsupportedEncodingException {

		String tmp = data;

		for (Entry<String, String> entry : dataMap.entrySet()) {

			if (!"名前".equals(entry.getKey())) {
				tmp = tmp.replace(decodeKey(entry.getKey()), URLEncoder.encode(entry.getValue(), "utf-8"));
			} else {
				tmp = tmp.replace(decodeKey(entry.getKey()), entry.getValue());
			}
		}

		return tmp;
	}

	private String decodeKey(String key) {

		return "${" + key + "}";
	}

	private String paddingZero(long l) {
		String tmp = "00" + new Long(l).toString();
		return tmp.substring(tmp.length() - 2);
	}

}
