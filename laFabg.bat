@echo off



java -version
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=y -cp .;./10_bin;./02_lib/ccOutils.jar org.capcaval.lafabrique.lafab.LaFab %*

