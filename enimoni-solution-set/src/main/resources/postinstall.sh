#
# This script is called after package software is installed
#

#echo  "********************postinstall****************"
#echo "arguments $*"

#echo  "***********************************************"

chmod -R 755 ${full_install_path}

ecds_install_dir=$RPM_BUILD_ROOT%{install_dir}


chown -R ${user_name}:${user_name} ${full_install_path}

# Set ownership of all the home directories
chown -R ${user_name}:${user_name} ${home_dir}

echo "Invoking solution set deployment script in ${full_install_path}"
${full_install_path}/solution-set-deployment.sh

echo "Removing unwanted eps services for " $(hostname)
${full_install_path}/unregister_services.sh
