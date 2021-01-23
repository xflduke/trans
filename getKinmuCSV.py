import loadKinmuData
import dukeUtils
import properties as pp

# get daily info
utils = dukeUtils.utils() 
daysData = loadKinmuData.saveCsvFromUrl(pp.PP['id'], utils.getKinmuMonth(pp.PP['target_date']))
 
  

