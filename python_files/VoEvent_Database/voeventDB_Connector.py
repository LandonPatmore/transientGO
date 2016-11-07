from __future__ import print_function
import voeventdb.remote as vr
import voeventdb.remote.apiv1 as apiv1
from voeventdb.remote.apiv1 import FilterKeys, OrderValues
from voeventdb.remote.helpers import Synopsis
from datetime import datetime
import pytz
from mysql_connector import insert_transient

"""
@author: Landon Patmore
"""
def send_data():
    print("Sending Data...")
    my_filters = {
        FilterKeys.role:'observation',
        FilterKeys.stream:'voevent.4pisky.org/GAIA',
        FilterKeys.authored_since:datetime(2016,10,31,tzinfo=pytz.UTC)
    }

    swift_bat_grb_list = apiv1.list_ivorn(
        filters=my_filters,
        order=OrderValues.author_datetime_desc,
        n_max=1
    )

    swift_bat_grb_ivorn = swift_bat_grb_list[0]
    swift_bat_grb_ivorn

    # Retrieve a 'synopsis' (nested dictionary) for the VOEvent packet,
    grb_nested_dict = apiv1.packet_synopsis(swift_bat_grb_ivorn)
    # And convert it to a user-friendly class-object:
    grb_synopsis = Synopsis(grb_nested_dict)

    sky_event=grb_synopsis.sky_events[0]

    insert_transient(grb_synopsis.ivorn, grb_synopsis.author_ivorn, sky_event.position.ra.deg, sky_event.position.dec.deg, grb_synopsis.author_datetime, grb_synopsis.received)
    print("Done sending data.")

send_data()
