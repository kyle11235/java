#!/bin/bash

# common.sh
my_dir="$(dirname "$0")"
source $my_dir/common.sh

echo -e "starting producers..."


$JAVA_HOME/bin/java  -cp $TOOLKIT_HOME/lib/jms-toolkit-0.0.1-SNAPSHOT.jar:$TOOLKIT_HOME/lib/wlfullclient.jar:$TOOLKIT_HOME/lib/jms-1.1.jar  com.sample.jms.toolkit.JMSSender



