@echo off
set path=D:\prg\java\jdk1.7.0_11\bin

java -cp ..\..\..\11_bin;..\..\..\03\lib\ccOutils.jar org.capcaval.cctools.commandline.test.c3.C3 %*
pause