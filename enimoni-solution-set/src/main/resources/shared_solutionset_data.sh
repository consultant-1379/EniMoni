#
# This script is shared between ss deploy and undeploy scripts
#
EPS_HOME=/opt/ericsson/eps/eps

SERVICE_NUMBER

eps_user_name=epsadmin

eps_services=""

for i in {1..9}
do
   eps_services[i]="enimoni_eps0$i"
done


eps_services[10]="enimoni_eps10"

echo "eps services are ${eps_services[@]:0} "

flow_files[1]="enimoni_call_drop.xml enimoni_enrichment.xml enimoni_cache_population.xml" 
flow_files[2]="enimoni_call_drop.xml enimoni_enrichment.xml enimoni_cache_population.xml" 
flow_files[3]="enimoni_call_drop.xml enimoni_enrichment.xml enimoni_cache_population.xml" 
flow_files[4]="enimoni_call_drop.xml enimoni_enrichment.xml enimoni_cache_population.xml" 

flow_files[5]="enimoni_cell_log.xml"
flow_files[6]="enimoni_cell_log.xml"
flow_files[7]="enimoni_cell_log.xml"

flow_files[8]="enimoni_radio_router_call_drop.xml"
flow_files[9]="enimoni_radio_router_ctum.xml"
flow_files[10]="enimoni_radio_router_cell_log.xml"


#echo "there are ${#flow_files[@]} xml flows definitions"

ROOT_EPS_DEPLOY_FOLDER=/var/ericsson/eps/flows

jar_file_list="guava-${version.guava}.jar joda-time-${version.joda.time}.jar enimoni-components-${project.version}.jar enimoni-core-${project.version}.jar enimoni-metadata-${project.version}.jar enimoni-correlation-${project.version}.jar xstream-base-${version.xstream}.jar xstream-correlation-${version.xstream}.jar xstream-metadata-${version.xstream}.jar wwecds-imsi-enricher-${version.ww}.jar wwecds-core-api-${version.ww}.jar  wwecds-radio-router-${version.ww}.jar"
