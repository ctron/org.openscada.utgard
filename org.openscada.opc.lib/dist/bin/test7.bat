@echo off
call preferences.bat

%JAVA% -cp %TEST_CLASSPATH% org.openscada.opc.lib.test.OPCTest7 "%HOST%" "%DOMAIN%" "%USER%" "%PASSWORD%" "%UUID%" %1

pause