#!/bin/bash

# common.sh
my_dir="$(dirname "$0")"
source $my_dir/common.sh

echo -e "\nthis will take half minute, please wait...\n"

cd $ORACLE_HOME/wlserver/server/lib
$JAVA_HOME/bin/java -jar wljarbuilder.jar
mv wlfullclient.jar $TOOLKIT_HOME/lib

echo -e "\nupdate wlfulljar success\n"
