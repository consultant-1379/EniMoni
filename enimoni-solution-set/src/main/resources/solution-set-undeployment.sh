#
# This script is called after package software has been removed and should perform solution-set undeployment actions
#

source ${full_install_path}/shared_solutionset_data.sh

for srv in ${eps_services[@]:0}
do
	echo "Deregistering service $srv"
	rm -f /etc/init.d/$srv
	chkconfig --del $srv
	echo Successfully deregistered service $srv""
done

# remove all flow descriptor files

for (( i=1; i<${#eps_services[@]}; i++ ))
do
	target_folder=${ROOT_EPS_DEPLOY_FOLDER}/${eps_services[i]}
	echo "Removing folder $target_folder/"
	# remove all xml deployments folders
	rm -rf $target_folder
done

# remove all other resources

eps_lib_dir=$EPS_HOME/ext-lib/

echo "removing jar files from $eps_lib_dir"
for jar in $jar_file_list
do
	echo "removing $jar from $eps_lib_dir"
	rm -rf $eps_lib_dir/$jar
done
