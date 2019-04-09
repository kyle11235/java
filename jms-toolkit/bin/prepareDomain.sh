#!/bin/bash

# common.sh
my_dir="$(dirname "$0")"
source $my_dir/common.sh


# functions
CreateDomain () {
	export WLST_PROPERTIES="-Dweblogic.security.SSL.ignoreHostnameVerification=true,-Dweblogic.security.TrustKeyStore=DemoTrust"
	CheckFile $ORACLE_HOME/oracle_common/common/bin/wlst.sh
	CheckFile $TOOLKIT_HOME/lib/prepareDomain.py
	$ORACLE_HOME/oracle_common/common/bin/wlst.sh $TOOLKIT_HOME/lib/prepareDomain.py -loadProperties $CONFIG_FILE
}

# check domain exists or not
TEST_DOMAIN=$ORACLE_HOME/user_projects/domains/$TEST_DOMAIN_NAME

if [ -e $DOMAIN ]
then

    echo -e "\nWARN:domain aleady exists as $TEST_DOMAIN\n"
    read -p "overwrite it (y/n)?" choice
	case "$choice" in 
	  y|Y ) CreateDomain;;
	  n|N ) echo "";;
	  * ) echo -e "\ninvalid input\n";;
	esac
else
    echo -e "\nINFO:We will create domain $TEST_DOMAIN\n"
    Confirm
    CreateDomain
fi



















































