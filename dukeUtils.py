import hashlib

class Alg():

    def MD5(self, text):
        algMD5 = hashlib.md5()
        algMD5.update(text.encode('UTF-8'))
        return algMD5.hexdigest()

    def SHA512(self, text):
        algSHA512 = hashlib.sha512()
        algSHA512.update(text.encode('UTF-8'))
        return algSHA512.hexdigest()