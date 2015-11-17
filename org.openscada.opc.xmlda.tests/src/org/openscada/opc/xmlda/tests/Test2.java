package org.openscada.opc.xmlda.tests;

import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.eclipse.scada.utils.concurrent.FutureListener;
import org.eclipse.scada.utils.concurrent.NotifyFuture;
import org.junit.Test;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.requests.GetStatusRequest;
import org.openscada.opc.xmlda.requests.GetStatusResponse;
import org.openscada.opc.xmlda.requests.ReadRequest;
import org.openscada.opc.xmlda.requests.ReadResponse;

public class Test2
{
    @Test
    public void test1 () throws Exception
    {
        try ( Connection c = new Connection ( "http://advosol.com/XMLDADemo/ts_sim/OpcDaGateway.asmx", "OpcXmlDaSrv" ) )
        {
            final Semaphore s = new Semaphore ( 1 );
            s.acquire ();

            c.scheduleTask ( new GetStatusRequest () ).addListener ( new FutureListener<GetStatusResponse> () {

                @Override
                public void complete ( final Future<GetStatusResponse> future )
                {
                    try
                    {
                        System.out.println ( future.get () );
                        System.out.println ();
                    }
                    catch ( final Exception e )
                    {
                        e.printStackTrace ();
                    }
                }
            } );

            final NotifyFuture<ReadResponse> f = c.scheduleTask ( new ReadRequest.Builder ( "Dynamic.Analog Types.Double", "Dynamic.Analog Types.Double1" ).build () );
            f.addListener ( new FutureListener<ReadResponse> () {

                @Override
                public void complete ( final Future<ReadResponse> future )
                {
                    try
                    {
                        System.out.println ( future.get () );
                        System.out.println ();
                    }
                    catch ( final Exception e )
                    {
                        e.printStackTrace ();
                    }
                    finally
                    {
                        s.release ();
                    }
                }
            } );

            c.scheduleTask ( new ReadRequest.Builder ( "Dynamic.Analog Types.Double" ).build () ).cancel ( false );

            s.acquire ();
        }
    }
}
