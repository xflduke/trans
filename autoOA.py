import requests
import http
http.client.HTTPConnection.debuglevel = 1
import re
import os
import time
import datetime
import random
import string
import traceback
import sys

import tkinter
import tkinter.messagebox

import properties as pp

salt = ''.join(random.sample(string.ascii_letters + string.digits, 8))
test = ''

try:

    log = open("C:\\autoOA\\autoOARun.log", mode='a+')
    log.write("%s %s バッチ開始\n" %(datetime.datetime.now(), salt))

    with open("C:\\autoOA\\autoOA.lock", mode='w+') as f:
        f.write(salt)
        f.flush
        f.close
    time.sleep(3)
    with open("C:\\autoOA\\autoOA.lock", mode='r') as f:
        test = f.read()
        f.close

    if salt == test:
        log.write("%s %s 処理開始\n" %(datetime.datetime.now(), salt))

        os.environ["http_proxy"] = pp.PP["http_proxy"]
        os.environ["https_proxy"] = pp.PP["https_proxy"]
        ua = pp.PP['useragent']
        headers = {'user-agent': ua,}

        def getPageToken(data) :
            match = re.findall("name=\"_token\" value=\"([0-9a-zA-Z]*)\"", data)
            print(match)
            return match[0]

        def isInEnabled(data) :
            match = re.findall( "type=\"submit\" value=\"in\"[^>]*disabled", data)
            print(match)
            if len(match) > 0 :
                return False
            else :
                return True

        def getCheckInButtonName(data) :
            match = re.findall("value=\"in\" name=\"([0-9a-zA-Z]*)\"", data)
            print(match)
            return match[0]
      
        def isClockInOutSuceessed(data) :
            match = re.findall("(未入力)", data)
            if len(match) > 0 :
                return False
            else :
                return True

        s = requests.Session()
        s.headers = headers

        # get challenge and cookies
        response = s.get(pp.PP["clockinFullUrl"])
        print()
        print('----------------------------------------------------------------------------------------------------------')
        print()
        print(response.status_code)    # HTTPのステータスコード取得
        print(response.text)
        pagetoken = getPageToken(response.text)
        buttonName = getCheckInButtonName(response.text)

        log.write("%s %s 初期画面\n" %(datetime.datetime.now(), salt))
        log.write("%s %s ページトーケン：%s\n" %(datetime.datetime.now(), salt, pagetoken))
        log.write("%s %s ボタンのネーム：%s\n" %(datetime.datetime.now(), salt, pagetoken))
        log.write("%s %s 初期画面レスポンスコンテキスト：%s\n" %(datetime.datetime.now(), salt, response.text))

        time.sleep(1)

        # first we will check if it can clockin
        if isInEnabled(response.text) :
            response3 = s.post(pp.PP['clockinUrl'],
                                data = {
                                    '_token': pagetoken, 
                                    buttonName: "in",
                                    'token': pp.PP['clockinToken'],
                                        })
            print(response3.status_code)    # HTTPのステータスコード取得
            print(response3.text)
            
            log.write("%s %s チェックイン\n" %(datetime.datetime.now(), salt))
            log.write("%s %s チェックインレスポンスコンテキスト：%s\n" %(datetime.datetime.now(), salt, response3.text))

        time.sleep(2)

        # after clockin 2 secs we will clock out
        response2 = s.post(pp.PP['clockinUrl'],
                            data = {
                                '_token': pagetoken, 
                                buttonName: "out",
                                    'token': pp.PP['clockinToken'],
                                    })
        print(response2.status_code)    # HTTPのステータスコード取得
        print(response2.text)
        log.write("%s %s チェックアウト\n" %(datetime.datetime.now(), salt))
        log.write("%s %s チェックアウトレスポンスコンテキスト：%s\n" %(datetime.datetime.now(), salt, response2.text))

        with open("C:\\autoOA\\autoOA.log", mode='a') as file:
            file.write("%s\n" %datetime.datetime.now())

        if not isClockInOutSuceessed(response2.text):
            tkinter.messagebox.showerror("結果確認でエラー", "勤務時間を確認してください")
        
    else:
        log.write("%s %s 処理開始\n" %(datetime.datetime.now(), salt))
except:
    log.write("%s %s 処理完了（例外あり）\n" %(datetime.datetime.now(), salt))
    traceback.print_exception(*sys.exc_info(), 9999, log)
    traceback.print_exc()
    tkinter.messagebox.showerror("エラー", "勤務時間を確認してください")
else:
    log.write("%s %s 処理完了（例外なし）\n" %(datetime.datetime.now(), salt))
finally:
    log.close

