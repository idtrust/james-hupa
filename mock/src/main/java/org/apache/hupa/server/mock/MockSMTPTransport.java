/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.apache.hupa.server.mock;

import java.io.IOException;
import java.io.OutputStream;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;

public class MockSMTPTransport extends Transport {

    public static final String MOCK_HOST = "mock-host";
    public static final Integer MOCK_PORT = 143;
    
    static final URLName mockUrl = new URLName(null, MOCK_HOST, MOCK_PORT, null, null, null);

    public MockSMTPTransport(Session session) {
        super(session, mockUrl);
    }

    @Override
    public void sendMessage(Message msg, Address[] addresses) throws MessagingException {
        try {
            msg.writeTo(new OutputStream() {
                public void write(int b) throws IOException {}
            });
        } catch (IOException e) {
            // Do nothing
        }
    }

    @Override
    public void connect(String host, int port, String user, String password) throws MessagingException {
    }

}
