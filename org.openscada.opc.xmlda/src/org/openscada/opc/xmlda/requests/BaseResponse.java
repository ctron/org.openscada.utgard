/*******************************************************************************
 * Copyright (c) 2015 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.openscada.opc.xmlda.requests;

import java.util.Calendar;

import org.opcfoundation.webservices.xmlda._1.ReplyBase;
import org.openscada.opc.xmlda.internal.Helper;

public class BaseResponse
{
    protected final static String NL = System.lineSeparator ();

    protected final static String LINE = "---------------";

    private String clientRequestHandle;

    private String revisedLocaleId;

    private ServerState serverState;

    private Calendar requestReceived;

    private Calendar requestReplied;

    protected BaseResponse ()
    {
    }

    public String getClientRequestHandle ()
    {
        return this.clientRequestHandle;
    }

    public String getRevisedLocaleId ()
    {
        return this.revisedLocaleId;
    }

    public ServerState getServerState ()
    {
        return this.serverState;
    }

    public Calendar getRequestReceived ()
    {
        return this.requestReceived;
    }

    public Calendar getRequestReplied ()
    {
        return this.requestReplied;
    }

    @Override
    public String toString ()
    {
        final StringBuilder sb = new StringBuilder ();

        sb.append ( "BaseResponse" + NL );
        sb.append ( LINE + NL );

        sb.append ( " RequestRecevied: " + Helper.toStringLocal ( this.requestReceived ) + NL );
        sb.append ( " RequestReplied:  " + Helper.toStringLocal ( this.requestReplied ) + NL );
        sb.append ( " ServerState: " + this.serverState + NL );
        sb.append ( " ClientRequestHandle: " + this.clientRequestHandle + NL );
        sb.append ( " RevisedLocaleId: " + this.revisedLocaleId + NL );

        return sb.toString ();
    }

    static class Builder
    {
        private ReplyBase value;

        protected void apply ( final BaseResponse result )
        {
            result.clientRequestHandle = this.value.getClientRequestHandle ();
            result.revisedLocaleId = this.value.getRevisedLocaleID ();
            result.serverState = Helper.convert ( this.value.getServerState () );
            result.requestReceived = Helper.convert ( this.value.getRcvTime () );
            result.requestReplied = Helper.convert ( this.value.getReplyTime () );
        }

        public void setBase ( final ReplyBase value )
        {
            this.value = value;
        }
    }
}
