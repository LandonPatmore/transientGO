from mysql.connector import MySQLConnection, Error
from python_mysql_dbconfig import read_db_config

def insert_transient(i_d, ra, dec, ut, mag, last, light):
    query = "INSERT INTO transient_information(id, ra, declination, ut_date, mag, last_time, light_curve) " \
            "VALUES(%s, %s, %s, %s, %s, %s, %s)"
    args = (i_d, ra, dec, ut, mag, last, light)

    try:
        db_config = read_db_config()
        conn = MySQLConnection(**db_config)

        cursor = conn.cursor()
        cursor.execute(query, args)

        conn.commit()
    except Error as error:
        print(error)

    finally:
        cursor.close()
        conn.close()

# def main():
#    insert_transient('TEST ID','45.666', '34.4232', '20161018', '20.42', '20161012.45', '17643')
#
# if __name__ == '__main__':
#     main()
