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
package org.apache.hupa.server.utils;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> re-add server unit tests
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;

import org.apache.hupa.server.HupaGuiceTestCase;
import org.junit.Test;

public class MessageUtilsTest extends HupaGuiceTestCase {
    
    @Test public void extractMessageAttachments() throws Exception {
<<<<<<< HEAD
=======
=======
>>>>>>> first commit
import org.apache.hupa.server.HupaGuiceTestCase;

import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.mail.Address;
=======
>>>>>>> first commit
=======
import javax.mail.Address;
>>>>>>> constantly changed by manolo
import javax.mail.BodyPart;
import javax.mail.Message;

public class MessageUtilsTest extends HupaGuiceTestCase {
    
    public void testExtractMessageAttachments() throws Exception {
<<<<<<< HEAD
>>>>>>> first commit
=======
>>>>>>> first commit
=======
>>>>>>> re-add server unit tests
        Message message = TestUtils.createMockMimeMessage(session, 2);
        List<BodyPart> parts = MessageUtils.extractMessageAttachments(logger, message.getContent());
        assertEquals(2, parts.size());
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    @Test public void extractInlineAttachments() throws Exception {
=======
    public void testExtractInlineAttachments() throws Exception {
>>>>>>> first commit
=======
    public void testExtractInlineAttachments() throws Exception {
>>>>>>> first commit
=======
    @Test public void extractInlineAttachments() throws Exception {
>>>>>>> re-add server unit tests
        Message message = TestUtils.createMockMimeMessage(session, 1);
        
        List<BodyPart> attachments = MessageUtils.extractMessageAttachments(logger, message.getContent());
        List<BodyPart> inlineImgs = MessageUtils.extractInlineImages(logger, message.getContent());
        assertEquals(1, attachments.size());
        assertEquals(0, inlineImgs.size());
        
        TestUtils.addMockAttachment(message, "mfile.bin", false);
        
        attachments = MessageUtils.extractMessageAttachments(logger, message.getContent());
        inlineImgs = MessageUtils.extractInlineImages(logger, message.getContent());
        assertEquals(2, attachments.size());
        assertEquals(0, inlineImgs.size());

        TestUtils.addMockAttachment(message, "mfile.jpg", true);
        
        attachments = MessageUtils.extractMessageAttachments(logger, message.getContent());
        inlineImgs = MessageUtils.extractInlineImages(logger, message.getContent());
        assertEquals(3, attachments.size());
        assertEquals(1, inlineImgs.size());
    }
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    
    @Test public void getRecipients () throws Exception  {
=======
    
    public void testGetRecipients () throws Exception  {
>>>>>>> constantly changed by manolo
=======
    
    public void testGetRecipients () throws Exception  {
>>>>>>> constantly changed by manolo
=======
    
    @Test public void getRecipients () throws Exception  {
>>>>>>> re-add server unit tests
        String encodedEmail = "=?ISO-8859-1?Q?Manolo_Pe=F1a?= <hello@hupa.org>";
        String decodedEmail = MessageUtils.decodeText(encodedEmail);
        assertFalse(encodedEmail.equals(decodedEmail));
        
        Address[] addr = MessageUtils.getRecipients(encodedEmail, decodedEmail);
        assertEquals(addr[0].toString(), addr[1].toString());
    }
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> first commit
=======
>>>>>>> constantly changed by manolo
=======
>>>>>>> first commit
=======
>>>>>>> constantly changed by manolo
=======
>>>>>>> re-add server unit tests
}