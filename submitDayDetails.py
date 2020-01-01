import requests
import datetime
import properties as pp
formatDate = lambda data: data[0:4] + '/' + data[4:6] + '/' + data[6:]

# s = session
# url = 'url'
# date = 201912
# gName = （无名氏・无名氏）勤怠確認Ｇ
# uName = [0000]无名氏
# id = 0000
# startTime = 9:00
# endTime = 18:00
# breakTime1Start = 12:00
# breakTime1End = 13:00

def submitDayInfo(s, headers, url, date, gName, uName, id, startTime, endTime):

  date = formatDate(str(date))

  breakTime1Start = '12:00'
  breakTime1End = '13:00'
  breakTime1Dur = '1:00'

  ary1 = startTime.split(':')
  ary2 = endTime.split(':')

  min = int(ary2[1]) + 60 - int(ary1[1])
  hour = int(ary2[0]) - int(ary1[0]) + int((min - min % 60) / 60 - 1) - 1
  min = min % 60
  souRoudo = str(hour).zfill(2) + ':' + str(min).zfill(2)

  min = min + 60 - 30
  hour = hour - 7 + int((min - min % 60) / 60 - 1)
  min = min % 60
  if (hour >= 0 and min >=0):
    zanGyou = str(hour).zfill(2) + ':' + str(min).zfill(2)
  else:
    zanGyou = '00:00'

  if (souRoudo > '07:30'):
    syuKkinJikan = '07:30'
  else:
    syuKkinJikan = souRoudo

  if (int(ary2[0]) >= 22):
    zanGyo2 = str(int(ary2[0]) - 22).zfill(2) + ':' + ary2[1].zfill(2)
  else:
    zanGyo2 = ''

  # commit day
  responseDay = s.post(url , 
  data = {
  'FSB_CTRL_SESSION': 'True',
  'app_cd': '229',
  'RegistFLG': '1',
  'DeleteFLG': '0',
  'NippouEditFLG': '',
  'ROW_ACT': '',
  'ROW_ACT_NO': '1',
  'SinseiMeisaiCount': '0',
  'ReturnFuseAction': 'kinmu',
  'db_KINMU_TAIKEI_NAME': '裁量労働制',
  'old_KINMU_TAIKEI': '1',
  'db_EDIT_DATE': date,
  'db_EDIT_GROUP': '95aa8fd38a487cde60ea18984bb9758e',
  'db_EDIT_GROUP_NAME': gName,
  'db_EDIT_UID': '8566b56c361c1e23fb5aa28320337f5a',
  'db_EDIT_USER': id,
  'db_EDIT_USER_NAME': uName,
  'db_MoveFLG': '0',
  'db_RETURN_DATE': date,
  'db_YOTEI_SYUKKIN_JIKOKU': '-',
  'db_YOTEI_TAISYUTU_JIKOKU': '-',
  'db_YOTEI_KINMU_KUBUN': '0',
  'db_CALC_KUBUN': '1',
  'sTaisyutuJikoku': '',
  'iSyukkinKubun': '0',
  'iCalcKubun': '1',
  'db_YUKYU_ZAN_NISSU': '', # TODO 27　残る年休、しばらく処理しない
  'db_YUKYU_ZAN_JIKAN': '0',
  'db_YUKYU_ONE_DAY_TIMES': '0',
  'db_DAIKYU_ZAN_JIKAN': '0',
  'db_DAITAI_ZAN_JIKAN': '0',
  'db_TOKKYU_ZAN_NISSU1': '0',
  'db_TOKKYU_ZAN_JIKAN1': '0',
  'db_TOKKYU_ZAN_NISSU2': '0',
  'db_TOKKYU_ZAN_JIKAN2': '0',
  'db_JIYUU_KBN1': '',
  'db_JIYUU_NAME1': '-',
  'db_savJIYUU_CD1': '',
  'db_savJIYUU_KBN1': '',
  'db_savJIYUU_SYUTOKU_TANI1': '',
  'db_savTOKKYU_SUM_KBN1': '',
  'db_JIYUU_KBN2': '',
  'db_JIYUU_NAME2': '',
  'db_savJIYUU_CD2': '',
  'db_savJIYUU_KBN2': '',
  'db_savJIYUU_SYUTOKU_TANI2': '',
  'db_savTOKKYU_SUM_KBN2': '',
  'db_JIYUU_KBN3': '',
  'db_JIYUU_NAME3': '',
  'db_savJIYUU_CD3': '',
  'db_savJIYUU_KBN3': '',
  'db_savJIYUU_SYUTOKU_TANI3': '',
  'db_savTOKKYU_SUM_KBN3': '',
  'db_RESTSTR_JIKOKU4': '',
  'db_RESTEND_JIKOKU4': '',
  'db_RESTSTR_JIKOKU5': '',
  'db_RESTEND_JIKOKU5': '',
  'db_RESTSTR_JIKOKU6': '',
  'db_RESTEND_JIKOKU6': '',
  'db_RESTSTR_JIKOKU7': '',
  'db_RESTEND_JIKOKU7': '',
  'db_RESTSTR_JIKOKU8': '',
  'db_RESTEND_JIKOKU8': '',
  'db_RESTSTR_JIKOKU9': '',
  'db_RESTEND_JIKOKU9': '',
  'db_RESTSTR_JIKOKU10': '',
  'db_RESTEND_JIKOKU10': '',
  'db_RESTSTR_JIKOKU11': '',
  'db_RESTEND_JIKOKU11': '',
  'db_RESTSTR_JIKOKU12': '',
  'db_RESTEND_JIKOKU12': '',
  'db_KINMU_TAIKEI': '1',
  'db_SYUKKIN_JIKOKU1': startTime,
  'db_TAISYUTU_JIKOKU1': endTime,
  'db_RESTSTR_JIKOKU1': breakTime1Start,
  'db_RESTEND_JIKOKU1': breakTime1End,
  'db_RESTSTR_JIKOKU2': '',
  'db_RESTEND_JIKOKU2': '',
  'db_RESTSTR_JIKOKU3': '',
  'db_RESTEND_JIKOKU3': '',
  'db_JIYUU1': '0',
  'db_JIYUU_SYUTOKU_TANI1': '0',
  'db_TOKKYU_SUM_KBN1': ' ',
  'db_JIYUU2': '',
  'db_JIYUU_SYUTOKU_TANI2': '',
  'db_TOKKYU_SUM_KBN2': '',
  'db_SYUKKIN_JIKOKU2': '',
  'db_TAISYUTU_JIKOKU2': '',
  'db_JIYUU3': '',
  'db_JIYUU_SYUTOKU_TANI3': '',
  'db_TOKKYU_SUM_KBN3': '',
  'db_SYUKKIN_JIKOKU3': '',
  'db_TAISYUTU_JIKOKU3': '',
  'db_JIYUU_KBN_FLAGS': '',
  'db_TIME_EDIT_FLAG': '',
  'db_SOU_ZANGYOU': zanGyou,
  'db_SOU_ROUDOU': souRoudo,
  'db_REST_TIME_AUTOCALC': 'NO',
  'db_JIYUU_JIKAN1': '',
  'db_GAISYUTU_JIKOKU1': '',
  'db_SAINYU_JIKOKU1': '',
  'db_GAISYUTU_JIKOKU2': '',
  'db_SAINYU_JIKOKU2': '',
  'db_GAISYUTU_JIKOKU3': '',
  'db_SAINYU_JIKOKU3': '',
  'db_GAISYUTU_JIKOKU4': '',
  'db_SAINYU_JIKOKU4': '',
  'tmp_JIYUU_SYUJITU': '0',
  'db_SYUKKIN_JIKAN': syuKkinJikan,
  'db_TIKOKU_JIKAN': '',
  'db_SOUTAI_JIKAN': '',
  'db_REST_JIKAN': breakTime1Dur,
  'db_GAISYUTU_JIKAN': '',
  'db_YUKYU_JIKAN': '',
  'db_DAIKYU_JIKAN': '',
  'db_DAITAI_JIKAN': '',
  'db_JIYUU_JIKAN2': '',
  'db_JIYUU_JIKAN3': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN1': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN2': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN3': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN4': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN5': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN6': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN7': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN8': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN9': '',
  'db_SINSEI_SYONIN_ZANGYOU_JIKAN10': '',
  'db_ZANGYOU_JIKAN1': zanGyou,
  'db_ZANGYOU_JIKAN2': zanGyo2,
  'db_ZANGYOU_JIKAN3': '',
  'db_ZANGYOU_JIKAN4': '',
  'db_ZANGYOU_JIKAN5': '',
  'db_ZANGYOU_JIKAN6': '',
  'db_ZANGYOU_JIKAN7': '',
  'db_ZANGYOU_JIKAN8': '',
  'db_ZANGYOU_JIKAN9': '',
  'db_ZANGYOU_JIKAN10': '',
  'db_WORKING_JIKAN_INPUT': '1',
  'db_REST_TIME_RIYOU_FLAG': '',
  'db_NIPPOU_WORKING_IS_BIGGER': '0',
  'cf_SUBMIT_DATA_HEADER': souRoudo,
  'cf_SUBMIT_DATA_MEISAI': pp.PP['cf_SUBMIT_DATA_MEISAI_prefix'] + souRoudo + pp.PP['cf_SUBMIT_DATA_MEISAI_suffix'],
  'cf_SAVE_DATA_MEISAI': pp.PP['cf_SAVE_DATA_MEISAI_prefix'] + souRoudo + pp.PP['cf_SAVE_DATA_MEISAI_suffix'],
  'hid_RESTCALCKBN': '',
  'hid_RESTSTR_JIKOKU_1': '',
  'hid_RESTEND_JIKOKU_1': '',
  'hid_RESTSTR_JIKOKU_2': '',
  'hid_RESTEND_JIKOKU_2': '',
  'hid_RESTSTR_JIKOKU_3': '',
  'hid_RESTEND_JIKOKU_3': '',
  'hid_RESTSTR_JIKOKU_4': '',
  'hid_RESTEND_JIKOKU_4': '',
  'hid_RESTSTR_JIKOKU_5': '',
  'hid_RESTEND_JIKOKU_5': '',
  'hid_RESTSTR_JIKOKU_6': '',
  'hid_RESTEND_JIKOKU_6': '',
  'hid_RESTSTR_JIKOKU_7': '',
  'hid_RESTEND_JIKOKU_7': '',
  'hid_RESTSTR_JIKOKU_8': '',
  'hid_RESTEND_JIKOKU_8': '',
  'hid_RESTSTR_JIKOKU_9': '',
  'hid_RESTEND_JIKOKU_9': '',
  'hid_RESTSTR_JIKOKU_10': '',
  'hid_RESTEND_JIKOKU_10': '',
  'hid_RESTSTR_JIKOKU_11': '',
  'hid_RESTEND_JIKOKU_11': '',
  'hid_RESTSTR_JIKOKU_12': '',
  'hid_RESTEND_JIKOKU_12': '',
  'cf_nippo_date': date,
  'db_WORK_TIME_FROM': '',
  'db_WORK_TIME_TO': '',
  'db_WORK_TIME': '',
  'radio_button': 'on',
  'db_WORK_TIME_FROM': '00:00',
  'db_WORK_TIME_TO': '00:00',
  'db_WORK_TIME': souRoudo,
  'db_WORK_TIME_FROM': '',
  'db_WORK_TIME_TO': '',
  'db_WORK_TIME': '',
  'db_WORK_TIME_FROM': '',
  'db_WORK_TIME_TO': '',
  'db_WORK_TIME': '',
  'db_WORK_TIME_FROM': '',
  'db_WORK_TIME_TO': '',
  'db_WORK_TIME': '',
  'db_WORK_TIME_FROM': '',
  'db_WORK_TIME_TO': '',
  'db_WORK_TIME': '',
  'db_COMMENT': '' # TODO 備考欄
  },
  # cookies = responseKinmu.cookies,
  headers = headers)


  print(responseDay.text)
  print(responseDay.status_code)