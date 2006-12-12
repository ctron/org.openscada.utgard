@echo off
call preferences.bat

%JAVA% -cp %TEST_CLASSPATH% org.openscada.opc.lib.test.OPCTest5 "%HOST%" "%DOMAIN%" "%USER%" "%PASSWORD%" "%UUID%" "Random.Int1" "%UUID%" "Random.Int2"

pause