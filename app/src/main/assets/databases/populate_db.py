import sqlite3

import string


from cityData.isolating_cityData import isolate_city_codes



connection = sqlite3.connect('city_list_final3.db')
c = connection.cursor()
letters_list = []
#def create_table():

    #alphabet = list(string.ascii_lowercase)

    #for char in alphabet:


def data_entry():

    city_codes = isolate_city_codes()
    for code in city_codes:
        #try:
        first_letters = code[-2:]
        if first_letters != "IN":
            continue
        else:
            print(code)
            firstletter = code[0].upper()

            c.execute('CREATE TABLE IF NOT EXISTS ' + 'INDIA' + '(' + "hello" + ' TEXT)')
            #letters_list.append(firstletter)

            if firstletter not in letters_list:
                print('if')
                print(letters_list)
                letters_list.append(firstletter)
                c.execute("alter table " + 'INDIA' + " add column '%s' 'TEXT'" % firstletter)
                c.execute("INSERT INTO INDIA" + "(" + firstletter + ") VALUES('{}')".format(str(code)))
                print(letters_list)
                continue

            else:
                print('else')
                print(letters_list)
                c.execute("INSERT INTO INDIA" + "(" + firstletter + ") VALUES('{}')".format(str(code)))
                continue


        #except IndexError:
        #continue

        #try:
        # c.execute('CREATE TABLE IF NOT EXISTS ' + first_letters + '(' + firstletter + ' TEXT)')
        #except sqlite3.OperationalError:
         #   continue

        # try:
        #
        #     c.execute("INSERT INTO " + first_letters + "(" + firstletter + ") VALUES('{}')".format(str(code)))
        #
        # except sqlite3.OperationalError:
        #     c.execute("alter table " + first_letters + " add column '%s' 'TEXT'" % firstletter)
        #     c.execute("INSERT INTO " + first_letters + "(" + firstletter + ") VALUES('{}')".format(str(code)))

    connection.commit()
    c.close()
    connection.close()


if __name__ == '__main__':
    #create_table()
    data_entry()
