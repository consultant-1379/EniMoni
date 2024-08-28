#
# This script is called before package software is installed
#

#echo  "********************preinstall*****************"
#echo "arguments $*"

#echo  "***********************************************"

# Add the home directory, user and group
if [ ! -d ${home_dir} ]; then
	mkdir -p ${home_dir}
	groupadd ${user_name}
	useradd -g ${user_name} -d ${home_dir} ${user_name}
	chown -R ${user_name}:${user_name} ${home_dir}
	echo "created user ${user_name}"
fi
