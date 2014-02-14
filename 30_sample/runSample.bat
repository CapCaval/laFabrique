@echo off

IF exist 20_prod ( laFab build Sample )

java -cp .;./02_lib/ccOutils.jar;./10_bin;./20_prod/tempSrc ccoutils.RunSample %*

