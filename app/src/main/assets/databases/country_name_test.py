import requests
import sqlite3
from cityData.isolating_cityData import isolate_city_codes
import xml.etree.ElementTree

base_url = "http://api.worldbank.org/countries/"

resp = requests.get(base_url + "in")
root = xml.etree.ElementTree.fromstring(resp.content)
country_name = root.find("{http://www.worldbank.org}country")[1].text
capital_city = root.find("{http://www.worldbank.org}country")[6].text

print(country_name)
print(capital_city)
