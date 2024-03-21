#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/2023-COOKIEE-server
cd $REPOSITORY

APP_LOG=/home/ubuntu/2023-COOKIEE-server/application.log
ERROR_LOG=/home/ubuntu/2023-COOKIEE-server/error.log
DEPLOY_LOG=/home/ubuntu/2023-COOKIEE-server/application.log

APP_NAME=2023-COOKIEE-server

JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH "
nohup java -jar -Duser.timezone=Asia/Seoul $JAR_PATH --logging.level.org.hibernate.SQL=DEBUG > $APP_LOG 2>$ERROR_LOG &
