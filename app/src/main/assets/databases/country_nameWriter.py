import requests
import sqlite3
from cityData.isolating_cityData import isolate_city_codes
import xml.etree.ElementTree


listed_countries = []

connection = sqlite3.connect('city_list_final3.db')
c = connection.cursor()

#base_url = "http://api.worldbank.org/countries/"

def data_entry():
    city_codes = isolate_city_codes()



    # c.execute('CREATE TABLE IF NOT EXISTS ' + 'countries_fullform' + '(' + 'countries' + ' TEXT)')

    # c.execute('CREATE TABLE IF NOT EXISTS ' + 'countries_fullFinalnames' + '(' + 'countries' + ' TEXT)')

    # c.execute("alter table " + "countries_fullnames" + " add column '%s' 'TEXT'" % "abbreviation")
    #
    # c.execute("alter table " + "countries_fullnames" + " add column '%s' 'TEXT'" % "first city")

    for code in city_codes:



        c.execute('CREATE TABLE IF NOT EXISTS ' + 'countries_fullform' + '(' + 'countries' + ' TEXT)')
        # if "'" in code:
        #     continue

        # try:
        #
        #     city_name = (code.split(','))[0].strip()
        #     last_letters = (code.split(','))[1].strip()
        #
        # #last_letters = country_name[-2:].lower()
        # except IndexError:
        #     continue



        #try:

        # if last_letters not in listed_countries:
        #
        #     resp = requests.get(base_url + last_letters)
        #
        #     try:
        #         root = xml.etree.ElementTree.fromstring(resp.content)
        #     except xml.etree.ElementTree.ParseError:
        #         continue
        #
        #     try:
        #         country_name = root.find("{http://www.worldbank.org}country")[1].text
        #         capital_city = root.find("{http://www.worldbank.org}country")[6].text
        #     except TypeError:
        #         continue
        #
        #     try:
        #         c.execute("INSERT INTO " + "countries_fullFinalnames" + "(" + "countries" + ") VALUES('{}')".format(country_name + ', ' + last_letters + ', ' + capital_city))
        #
        #         # c.execute("INSERT INTO " + "countries_fullnames" + "(" + "abbreviation" + ") VALUES('{}')".format(last_letters))
        #         #
        #         # c.execute("INSERT INTO " + "countries_fullnames" + "(" + "first city" + ") VALUES('{}')".format(city_name))
        #
        #     except sqlite3.OperationalError:
        #         continue
        #     except TypeError:
        #         continue
        #
        #     listed_countries.append(last_letters)
        #
        # else:
        #     continue

    connection.commit()
    c.close()
    connection.close()

if __name__ == '__main__':
        # create_table()
        data_entry()

# def data_entry():
#     city_codes = isolate_city_codes()
#
#     file = open('country_names.txt', 'w')
#
#     for code in city_codes:
#         if "'" in code:
#             continue
#
#         try:
#             last_letters = code[-2:]
#         except IndexError:
#             continue

