from mysql.connector import MySQLConnection, Error
from python_mysql_dbconfig import read_db_config

def insert_transient(i_d, author, ra, dec, time_discovered):
    query = "INSERT INTO transient_table(id, author, ra, declination, time_discovered) " \
            "VALUES(%s, %s, %s, %s, %s)"
    args = (i_d, author, ra, dec, time_discovered)

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
