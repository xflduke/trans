package io.duke;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;

import io.duke.bean.KinmuCSVBean;
import io.duke.bean.OzoCSVBean;

public class CheckKinmu {
	SimpleDateFormat sdf =new SimpleDateFormat("HH:mm");
	private List<Map.Entry<String, KinmuCSVBean>> kinmuList;
	private List<Map.Entry<String, OzoCSVBean>> ozoList;
	public CheckKinmu(List<Map.Entry<String, KinmuCSVBean>> kinmuList, List<Map.Entry<String, OzoCSVBean>> ozoList) {
		super();
		this.kinmuList = kinmuList;
		this.ozoList = ozoList;
	}

	public boolean check() throws Exception{

		// 片方または両方がない時に、直ちに却下
		if (kinmuList == null || kinmuList.size() == 0 || ozoList == null || ozoList.size() == 0) {
			return false;
		}

		// 前提条件：ozoシステムの制約により、勤務を提出する前、合理性チェックあり。
		// ・出勤したが勤務記入「当日なし、出勤時間、退勤時間」なし、だめ。
		// ・ozoの出力結果には休日あり、比較対象外

		// kinmu index i
		int i = 0;
		int kinmuLength = kinmuList.size();

		// ozo index j
		int j = 0;
		int ozoLength = ozoList.size();

		while(i < kinmuList.size() && j < ozoList.size()) {
			Map.Entry<String, KinmuCSVBean> kinmuMap = kinmuList.get(i);
			Map.Entry<String, OzoCSVBean> ozoMap = ozoList.get(j);
			String ozoNowDate = ozoMap.getKey();
			String kinmuNowDate = kinmuMap.getKey();
			OzoCSVBean ozoCsv = ozoMap.getValue();
			KinmuCSVBean kinmuCsv = kinmuMap.getValue();
			// 日付が一致の場合、比較
			if ((ozoNowDate.compareTo(kinmuNowDate) == 0)) {

				// 15分以上乖離、かつ、備考なしの場合、却下
				if (!checkTime(kinmuCsv, ozoCsv)) {
					return false;
				}
				i++;
				j++;
			// ozo休日の場合、パス。
			} else if (ozoNowDate.compareTo(kinmuNowDate) < 0 &&
				StringUtils.isEmpty(ozoCsv.getStartTime()) &&
				StringUtils.isEmpty(ozoCsv.getEndTime())) {
				j++;

			// 逆に、ozo記録あり、kinmuなしの場合、日単位打刻忘れ、却下
			} else {
				return false;
			}
		}

		// ozo完了、kinmu未完了の場合、誤打刻、かつ、ozo未記入の可能性あり、却下
		if (i != kinmuLength) {
			return false;
		}

		// kinmu完了、ozo未完了の場合、残りの分、全部休日であることを確認
		if (j != ozoLength) {
			for(int index = j;index<ozoLength;index++) {
				System.out.println(ozoList.get(index).getValue());
				if (!(StringUtils.isEmpty(ozoList.get(index).getValue().getStartTime()) &&
					StringUtils.isEmpty(ozoList.get(index).getValue().getEndTime()))) {
					return false;
				}
			}
		}


		return true;
	}

	private boolean checkTime(KinmuCSVBean kinmu,  OzoCSVBean ozo) throws Exception{

		// 打刻忘れの場合、却下
		if (StringUtils.isNotEmpty(ozo.getReason()) && (StringUtils.isEmpty(kinmu.getStartTime()) || StringUtils.isEmpty(kinmu.getEndTime()))) {
			return true;
		}
		if (StringUtils.isEmpty(ozo.getReason()) && (StringUtils.isEmpty(kinmu.getStartTime()) || StringUtils.isEmpty(kinmu.getEndTime()))) {
			return false;
		}
		Date kinmuSDate = sdf.parse(kinmu.getStartTime());
		Date ozoSDate = sdf.parse(ozo.getStartTime());
		Date kinmuEDate = sdf.parse(kinmu.getEndTime());
		Date ozoEDate = sdf.parse(ozo.getEndTime());
		int min15 = 1000 * 60 * 15;

		// 15分以上乖離、却下
		if (StringUtils.isEmpty(ozo.getReason()) && ((Math.abs(kinmuSDate.getTime() - ozoSDate.getTime()) >= min15) ||
			(Math.abs(kinmuEDate.getTime() - ozoEDate.getTime()) >= min15))) {
			return false;
		}
		return true;
	}

	public static void main(String[]args) throws Exception{
		String ozoFilePath = "D:\\git\\trans\\src\\io\\duke\\bean\\ozo.csv";
		String kinmuFilePath = "D:\\git\\trans\\src\\io\\duke\\bean\\kinmu.csv";
		KinmuCSVParser kinmuCSVParser = new KinmuCSVParser(kinmuFilePath);
		OzoCSVParser ozoCSVParser = new OzoCSVParser(ozoFilePath);
		List<Map.Entry<String, OzoCSVBean>> ozoList = IteratorUtils.toList(ozoCSVParser.iterator());
		List<Map.Entry<String, KinmuCSVBean>> kinmuList = IteratorUtils.toList(kinmuCSVParser.iterator());
		CheckKinmu checkKinmu = new CheckKinmu(kinmuList, ozoList);
		System.out.println(checkKinmu.check());
	}
}
