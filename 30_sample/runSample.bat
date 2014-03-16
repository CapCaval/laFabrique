@echo off

IF NOT exist 20_prod ( call laFab build Sample )

java -cp .;./02_lib/ccOutils.jar;./10_bin;./20_prod/tempSrc ccoutils.RunSample %*

