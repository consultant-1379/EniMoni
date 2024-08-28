#
# This script is called after package software is removed
#

#echo  "********************postrm*******************"
#echo "arguments $*"
#echo  "***********************************************"

# Remove the epsadmin user, group and home directory
userdel ${user_name}
rm -fr ${home_dir}