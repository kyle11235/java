#!/bin/bash

TOOLKIT_BIN=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
TOOLKIT_HOME="$(dirname "$TOOLKIT_BIN")"

echo "  --------------------------------------" 
echo " /                                      \\" 
echo "|    weblogic jms benchmarking toolkit   |"
echo " \\             kyle zhang               /"
echo "  \\             2017-04                /"
echo "   \\                                  /"
echo "    ----------------------------------"

# functions

Confirm () {
   	read -p "Continue (y/n)?" choice
	case "$choice" in 
	  y|Y ) echo "";;
	  n|N ) exit 1;;
	  * ) echo -e "\ninvalid input\n";;
	esac
}

CheckFile () {
   	if [ -e $1 ]
	then
	    echo ""
	else
	    echo -e "\nERROR:$1 is missing\n"
	    exit 1
	fi
}

ExportConfig () {
	while IFS='=' read -r key value; do
   	echo $key'='$value
	done < $1
}


# print settings
CONFIG_FILE=$TOOLKIT_HOME/config/config.properties
CheckFile $CONFIG_FILE
echo -e "\nINFO:Hi, check settings loaded from $CONFIG_FILE, if necessary update it and run this script again\n"
ExportConfig $CONFIG_FILE
# load settings
. $CONFIG_FILE


echo -e "\n"



































