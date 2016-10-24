import requests
import json
from bs4 import BeautifulSoup
from mysql_connector import insert_transient

transient_array = []

#transient class to create transient objects to be added to an array
class transient:

    def __init__(self, i_d, ra, dec, ut, mag, last, light):
        self.i_d = i_d
        self.ra = ra
        self.dec = dec
        self.ut = ut
        self.mag = mag
        self.last = last
        self.light = light

    def get_id(self):
        return self.i_d

    def get_ra(self):
        return self.ra

    def get_dec(self):
        return self.dec

    def get_ut(self):
        return self.ut

    def get_mag(self):
        return self.mag

    def get_last(self):
        return self.last

    def get_light(self):
        return self.light


# crawls the website specified and finds the table on the website and returns
# the rows
def LSST_crawler():
    url = "http://nesssi.cacr.caltech.edu/MLS/CRTSII_Allns.html"
    source_code = requests.get(url)
    plain_text = source_code.text
    soup = BeautifulSoup(plain_text, "html.parser")

    table = soup.findChildren("table")
    my_table = table[0]

    rows = my_table.findChildren(["th", "tr"])
    return rows

# takes in the rows from the LSST_crawler() function and goes through the rows
# and gets the data in the cells and strips the whitespace from them and then
# creates objects inside of the dictionary which then is created as a file
# specified where the user wants
def set_data(rows):
    for row in rows:
        cells = row.findChildren("td")
        for cell in cells[:8]:
            crts_id = cells[0].string.lstrip().rstrip()
            ra = cells[1].string.lstrip().rstrip()
            dec = cells[2].string.lstrip().rstrip()
            ut_date = cells[3].string.lstrip().rstrip()
            mag = cells[4].string.lstrip().rstrip()
            last_time = cells[6].string.lstrip().rstrip()
            light_curve = cells[7].string.lstrip().rstrip()
            trans = transient(crts_id, ra, dec, ut_date, mag, last_time, light_curve)
            transient_array.append(trans)
            break

def send_data():
    print("Sending Data...")
    for t in transient_array:
        insert_transient(t.get_id(), t.get_ra(), t.get_dec(), t.get_ut(), t.get_mag(), t.get_last(), t.get_light())
    print("Done sending data.")

set_data(LSST_crawler())
send_data()
