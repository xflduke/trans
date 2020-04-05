import hashlib
import datetime

class Alg():

    def MD5(self, text):
        algMD5 = hashlib.md5()
        algMD5.update(text.encode('UTF-8'))
        return algMD5.hexdigest()

    def SHA512(self, text):
        algSHA512 = hashlib.sha512()
        algSHA512.update(text.encode('UTF-8'))
        return algSHA512.hexdigest()


class utils():

    def getKinmuMonth(self, text):
        if not text :
            # days that before 10 will treated as pre month
            dt = datetime.datetime.now()
            if dt.day <= 5 :
                dt = dt - datetime.timedelta(days=11)
            return dt.strftime('%Y%m')
        else:
            return text