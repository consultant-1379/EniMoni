#
# This script is called after package software is removed
#

#echo  "********************postrm*******************"
#echo "arguments $*"
#echo  "***********************************************"

echo "Invoking solution set undeployment script in ${full_install_path}"
${full_install_path}/solution-set-undeployment.sh

# Delete/Move enimoni files...
if [ -e "${full_install_path}" ]; then        
    rm -fr ${full_install_path}_purged
    mv -f ${full_install_path} ${full_install_path}_purged
    echo installation directory "${full_install_path}" removed
fi