def isolate_city_codes():
   filename = 'cityList_final.txt'
   with open(filename, 'r') as f:
       contents = f.read()
   list_of_cities = []

   cities = contents.split('\n');

   for list in cities:
    list_of_cities.append(list);


   return list_of_cities


if __name__ == '__main__':
    print(isolate_city_codes());
