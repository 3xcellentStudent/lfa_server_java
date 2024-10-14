#!/usr/bin/expect -f

spawn sudo service redis-server stop
expect "password for andrii:"
send "28102000\r"
interact