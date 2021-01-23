import requests
import matplotlib.pyplot as plt
import pandas
import time
current_milli_time = lambda: int(round(time.time() * 1000))
import properties as pp
from pathlib import Path

def loadCsvFromUrl(id, date):
  url = pp.PP['url_appspot_prefix'] +  id +  pp.PP['url_appspot_suffix'] + date + "/" + id + "?" + str(current_milli_time())
  names = ['id', 'date', 'startTime', 'endTime', 'userAgent', 'ipAddress', 'ua', 'ip']
  data = pandas.read_csv(url, names=names)
  return data

def saveCsvFromUrl(id, date):
  url = pp.PP['url_appspot_prefix'] +  id +  pp.PP['url_appspot_suffix'] + date + "/" + id + "?" + str(current_milli_time())
  names = ['id', 'date', 'startTime', 'endTime', 'userAgent', 'ipAddress', 'ua', 'ip']
  data = pandas.read_csv(url, names=names)
  data.to_csv(Path(__file__).parent.absolute().as_posix() + "/kinmu.csv")
  print(Path(__file__).parent.absolute().as_posix() + "/kinmu.csv")
  




