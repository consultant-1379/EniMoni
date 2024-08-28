#!/bin/bash

#stops all eps services registered on current service

for service in /etc/init.d/enimoni_eps*
do
	$service stop
done
