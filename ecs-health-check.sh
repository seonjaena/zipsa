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

    echo "USERNAME = $USERNAME"
    echo "GROUPNAME = $GROUPNAME"
    echo "PID = $PID"
    echo "STATUS = $STATUS"
    echo "COMMAND = $COMMAND"
    echo "ARGS = $ARGS"

    if [ -z "$USERNAME" ] || [ -z "$GROUPNAME" ] || [ -z "$PID" ] || [ -z "$STATUS" ] || [ -z "$COMMAND" ] || [ -z "$ARGS" ]; then
      echo "Process is Abnormal"
      exit 1
    fi

    if [ "$STATUS" != "D" ] && [ "$STATUS" = "S" ] && [ "$STATUS" = "R" ]; then
      echo "Process Status is Abnormal"
      echo "PROCESS STATUS = $STATUS"
      exit 1
    fi
}

function check_api() {
  curl -H 'Authorization: Bearer' http://localhost:8080/api/healthcheck/task
}

check_proc
check_api