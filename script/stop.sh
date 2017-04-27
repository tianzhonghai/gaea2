#!/bin/bash

MAIN_CLASS="com.tim.gaea2.web.WebApplication"

ps aux | grep ${MAIN_CLASS} | grep -v grep | awk '{print $2}' | xargs kill -9 > /dev/null 2>&1
ps aux | grep ${MAIN_CLASS} | grep -v grep > /dev/null 2>&1
if [ $? -ne 0 ]; then
    exit 0
else
    exit 1
fi
