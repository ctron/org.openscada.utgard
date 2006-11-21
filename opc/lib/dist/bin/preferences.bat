set JAVA=java

rem But your access data in the following variables
rem Please not that the UUID is set to the Matrikon Simulation Server

rem If the OPC server is accessable by an anonymous user
rem You don't need to set a username and password and can
rem leave the settings as they are.

rem The test samples are currently using hard coded item names from the
rem Matrikon Simulation server. So changing the UUID will break the test
rem samples for the moment.

set HOST=localhost
set DOMAIN=localhost
set USER=user
set PASSWORD=password
set VERSION=0.1.0-nightly
set UUID=F8582CF2-88FB-11D0-B850-00C0F0104305

set TEST_CLASSPATH="../lib/j-interop.jar;../lib/jarapac.jar;../lib/jcifs-1.2.9.jar;../lib/iwombat.jar;../lib/ntlm-security.jar;../lib/ncacn_np.jar;../lib/ncacn_ip_tcp.jar;../lib/cifs-ntlm-auth.jar;../lib/log4j-1.2.13.jar;../lib/openscada-opc-dcom-%VERSION%.jar;../bin/openscada-opc-lib-%VERSION%.jar;../bin/openscada-opc-lib-test-%VERSION%.jar;."