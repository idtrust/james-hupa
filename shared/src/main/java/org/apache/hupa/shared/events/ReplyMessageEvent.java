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

package org.apache.hupa.shared.events;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.Message;
import org.apache.hupa.shared.domain.MessageDetails;
import org.apache.hupa.shared.domain.User;
=======
=======
>>>>>>> first commit
import org.apache.hupa.shared.data.IMAPFolder;
import org.apache.hupa.shared.data.Message;
import org.apache.hupa.shared.data.MessageDetails;
import org.apache.hupa.shared.data.User;
<<<<<<< HEAD
>>>>>>> first commit
=======
import org.apache.hupa.shared.data.Message;
import org.apache.hupa.shared.data.MessageDetails;
import org.apache.hupa.shared.data.User;
import org.apache.hupa.shared.proxy.IMAPFolderProxy;
>>>>>>> Aim to make the front end view work after the server side's IMAPFolder services RF being working, but there are issues on RF's find* method, I think.
=======
>>>>>>> first commit

import com.google.gwt.event.shared.GwtEvent;

public class ReplyMessageEvent extends GwtEvent<ReplyMessageEventHandler>{

    public final static Type<ReplyMessageEventHandler> TYPE = new Type<ReplyMessageEventHandler>();
    private User user;
<<<<<<< HEAD
    private IMAPFolderProxy folder;
=======
    private IMAPFolder folder;
>>>>>>> first commit
    private Message message;
    private MessageDetails details;
    private boolean replyAll;

    @Override
    protected void dispatch(ReplyMessageEventHandler handler) {
        handler.onReplyMessageEvent(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ReplyMessageEventHandler> getAssociatedType() {
        return TYPE;
    }
    
<<<<<<< HEAD
    public ReplyMessageEvent(User user, IMAPFolderProxy folder, Message message, MessageDetails details, boolean replyAll) {
=======
    public ReplyMessageEvent(User user, IMAPFolder folder, Message message, MessageDetails details, boolean replyAll) {
>>>>>>> first commit
        this.user = user;
        this.folder = folder;
        this.message = message;
        this.replyAll = replyAll;
        this.details = details;
    }
    
    public User getUser() {
        return user;
    }
    
<<<<<<< HEAD
    public IMAPFolderProxy getFolder() {
=======
    public IMAPFolder getFolder() {
>>>>>>> first commit
        return folder;
    }
    
    public Message getMessage() {
        return message;
    }
    

    public MessageDetails getMessageDetails() {
        return details;
    }

    public boolean getReplyAll() {
        return replyAll;
    }

}
