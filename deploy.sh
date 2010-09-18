sudo rm -rf /var/lib/tomcat6/webapps/ROOT/*
unzip webapp/target/webapp-0.1.0-SNAPSHOT.war -d /var/lib/tomcat6/webapps/ROOT
sudo chown -hR galak:tomcat6 /var/lib/tomcat6/webapps/ROOT
sudo chmod -R g+w /var/lib/tomcat6/webapps/ROOT/*

