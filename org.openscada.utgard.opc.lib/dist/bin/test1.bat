@echo off
call preferences.bat

%JAVA% -cp %TEST_CLASSPATH% org.openscada.opc.lib.test.OPCTest1 "%HOST%" "%DOMAIN%" "%USER%" "%PASSWORD%" "%UUID%"

pause