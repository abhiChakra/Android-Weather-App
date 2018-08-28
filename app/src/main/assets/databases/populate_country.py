import sqlite3

import string


from cityData.isolating_cityData import isolate_city_codes

listed_countries = []


connection = sqlite3.connect('city_list_final3.db')
c = connection.cursor()


def data_entry():
    city_codes = isolate_city_codes()
    for code in city_codes:
        if "'" in code:
            continue

        try:
            last_letters = code[-2:]
        except IndexError:
            continue

        try:
            c.execute('CREATE TABLE IF NOT EXISTS ' + 'countries' + '(' + 'countries' + ' TEXT)')
        except sqlite3.OperationalError:
            continue

        #try:


        if (last_letters not in listed_countries):
            c.execute("INSERT INTO " + "countries" + "(" + "countries" + ") VALUES('{}')".format(str(last_letters)))

            listed_countries.append(last_letters)

        else:
            continue

        # except sqlite3.OperationalError:
        #     c.execute("alter table " + first_letters + " add column '%s' 'TEXT'" % firstLetter)
        #     c.execute("INSERT INTO " + first_letters + "(" + firstLetter + ") VALUES('{}')".format(str(code)))

    connection.commit()
    c.close()
    connection.close()


if __name__ == '__main__':
    #create_table()
    data_entry()
