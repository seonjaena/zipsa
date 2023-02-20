#!/bin/sh

function check_proc() {
    PS_STAT=`ps -o user,group,pid,stat,comm,args | grep -m1 'java.*-jar.*zipsa.jar'`
    if [[ -z "$PS_STAT" ]]; then
        echo "Process Not Running"
        exit 1
    fi

    USERNAME=`echo $PS_STAT | cut -d' ' -f1` && PS_STAT=`echo $PS_STAT | sed s/"$USERNAME"//`
    GROUPNAME=`echo $PS_STAT | cut -d' ' -f1` && PS_STAT=`echo $PS_STAT | sed s/"$GROUPNAME"//`
    PID=`echo $PS_STAT | cut -d' ' -f1` && PS_STAT=`echo $PS_STAT | sed s/"$PID"//`
    STATUS=`echo $PS_STAT | cut -d' ' -f1` && PS_STAT=`echo $PS_STAT | sed s/"$STATUS"//`
    COMMAND=`echo $PS_STAT | cut -d' ' -f1` && PS_STAT=`echo $PS_STAT | sed s/"$COMMAND"//`
    ARGS=$PS_STAT
}

function check_prop() {
    if [[ -z "$DB_HOST" ]]; then
        DB_HOST=mariadb-ver10-01.c3edzk8fothj.ap-northeast-2.rds.amazonaws.com
    fi
    if [[ -z "$MQ_HOST" ]]; then
        MQ_HOST=144.24.83.179
    fi
    if [[ -z "$REDIS_HOST" ]]; then
        REDIS_HOST=144.24.83.179
    fi
    if [[ -z "$DB_PORT" ]]; then
        DB_PORT=1433
    fi
    if [[ -z "$MQ_PORT" ]]; then
        MQ_PORT=5672
    fi
    if [[ -z "$REDIS_PORT" ]]; then
        REDIS_PORT=6379
    fi
}

function thrid_party_conn_test() {
    nc -vz $DB_HOST $DB_PORT && \
    nc -vz $MQ_HOST $MQ_PORT && \
    nc -vz $REDIS_HOST $REDIS_PORT
}

check_proc
check_prop
thrid_party_conn_test