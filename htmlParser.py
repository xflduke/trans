import requests
from html.parser import HTMLParser

class Parser(HTMLParser):
    def __init__(self):
        HTMLParser.__init__(self)
        self.data = []

    def handle_starttag(self, tag, attrs):
        attrs = dict(attrs)
        if tag == "input" and "name" in attrs and attrs['name'] == "challenge":
            self.data.append({'challenge' : attrs['value']})

