import requests
import htmlParser
import dukeUtils
import http
http.client.HTTPConnection.debuglevel = 1
import loadKinmuData
import submitDayDetails
import properties as pp

ua = 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36'
headers = {'User-Agent': ua,
# 'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
# 'Accept-Encoding': 'gzip, deflate, br',
# 'Accept-Language': 'zh-CN,zh;q=0.9,ja;q=0.8,en-US;q=0.7,en;q=0.6',
# 'Cache-Control: max-age=0
# 'Connection: keep-alive
# 'Content-Length: 2979
# 'Content-Type': 'application/x-www-form-urlencoded',
# 'Cookie': 'CFID=280181; CFTOKEN=6840a7681522c9fd-9657DB02-D3A5-1371-48EE426368639C0C',
# 'DNT': '1',
# 'Host': 'xx',
# 'Origin': 'https://xx',
# 'Referer': 'https://xx/xx/xx?xx&xx&xx',
# 'Sec-Fetch-Mode': 'navigate',
# 'Sec-Fetch-Site': 'same-origin',
# 'Sec-Fetch-User': '?1',
# 'Upgrade-Insecure-Requests': '1'
}

s = requests.Session()

# get challenge and cookies
response = s.get(pp.PP['url_init'])
print(response.status_code)    # HTTPのステータスコード取得

parser = htmlParser.Parser();
parser.feed(response.text);

parser.close()

for i in parser.data:
    challenge = i["challenge"]

# challenge = '5d49eab363208af9f0785e4870ed2035'

# calc sha512 and md5
alg = dukeUtils.Alg()
responseT = alg.MD5(pp.PP['id'] + ':' + alg.MD5(pp.PP['password']) + ':' + challenge)
response512 = alg.SHA512(pp.PP['id'] + ':' + alg.SHA512(pp.PP['password']) + ':' + challenge)

print("challenge: " + challenge)
print("response: " + responseT)
print("response512: " + response512)

# login
responseLogin = s.post(pp.PP['url_login'], 
data = {
  'response512': response512, 
  'challenge': challenge,
  'response': responseT,
  'EncriptResponse': '', 
  'LoginUserID': pp.PP['id'],
  'LoginPassword': ''
},
headers = headers)

# print(responseLogin.text)
print(responseLogin.status_code)

# kinmu page 
responseKinmu = s.get(pp.PP['url_kinmu'],
# cookies = responseLogin.cookies,
headers = headers
)
# print(responseKinmu.text)
print(responseKinmu.status_code)

# get daily info 
utils = dukeUtils.utils()
daysData = loadKinmuData.loadCsvFromUrl(pp.PP['id'], utils.getKinmuMonth(pp.PP['target_date']))
for index, target_list in daysData.iterrows():
  id = str(target_list['id']).zfill(4) # id's length is 4, rpadding 0
  date = target_list['date']
  startTime = target_list['startTime']
  endTime = target_list['endTime']
  submitDayDetails.submitDayInfo(s, headers, pp.PP['url_kinmu_regist'],
                   date, pp.PP['gName'], pp.PP['uName'], id, startTime, endTime)
 
  

