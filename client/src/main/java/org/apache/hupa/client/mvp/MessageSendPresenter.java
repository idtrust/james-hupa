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

package org.apache.hupa.client.mvp;

import java.util.ArrayList;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import org.apache.hupa.client.SessionAsyncCallback;
import org.apache.hupa.client.validation.EmailListValidator;
import org.apache.hupa.client.validation.NotEmptyValidator;
import org.apache.hupa.shared.Util;
import org.apache.hupa.shared.data.IMAPFolder;
import org.apache.hupa.shared.data.Message;
import org.apache.hupa.shared.data.MessageAttachment;
import org.apache.hupa.shared.data.MessageDetails;
import org.apache.hupa.shared.data.SMTPMessage;
import org.apache.hupa.shared.data.User;
import org.apache.hupa.shared.events.BackEvent;
import org.apache.hupa.shared.events.FolderSelectionEvent;
import org.apache.hupa.shared.events.FolderSelectionEventHandler;
import org.apache.hupa.shared.events.LoadMessagesEvent;
import org.apache.hupa.shared.events.LoadMessagesEventHandler;
import org.apache.hupa.shared.events.SentMessageEvent;
import org.apache.hupa.shared.rpc.EmptyResult;
import org.apache.hupa.shared.rpc.ForwardMessage;
import org.apache.hupa.shared.rpc.ReplyMessage;
import org.apache.hupa.shared.rpc.SendMessage;
import org.apache.hupa.widgets.ui.HasEnable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;

import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.FocusAction;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import gwtupload.client.IUploader;
import gwtupload.client.Uploader;

public class MessageSendPresenter extends WidgetPresenter<MessageSendPresenter.Display>{

	private User user;
	private DispatchAsync dispatcher;
	public static final Place PLACE = new Place("MessageSend");
	private ArrayList<MessageAttachment> aList = new ArrayList<MessageAttachment>();
	private EndValueChangeHandler handler = new EndValueChangeHandler();
	private StartValueChangeHandler startHandler = new StartValueChangeHandler();
	private Type type = Type.NEW;
	private IMAPFolder folder;
	private Message oldmessage;
	private ValidationMessages vMessages = new ValidationMessages();
	private ValidationProcessor validator = new DefaultValidationProcessor(
			vMessages);
	private MessageDetails oldDetails;
	
	@Inject
	public MessageSendPresenter(Display display, EventBus eventBus, DispatchAsync dispatcher) {
		super(display, eventBus);
		this.dispatcher = dispatcher;		
		
		FocusAction fAction = new FocusAction();
		validator.addValidators("cc", new EmailListValidator(display.getCcText())
				.addActionForFailure(
						new StyleAction("hupa-validationErrorBorder"))
				.addActionForFailure(fAction));
		validator.addValidators("bcc", new EmailListValidator(display.getBccText())
				.addActionForFailure(
						new StyleAction("hupa-validationErrorBorder"))
				.addActionForFailure(fAction));
		validator.addValidators("to", new EmailListValidator(display.getToText())
				.addActionForFailure(
						new StyleAction("hupa-validationErrorBorder"))
				.addActionForFailure(fAction), new NotEmptyValidator(display.getToText())
				.addActionForFailure(
						new StyleAction("hupa-validationErrorBorder"))
				.addActionForFailure(fAction));
	}

	public enum Type {
		NEW,
		REPLY,
		REPLY_ALL,
		FORWARD
	}
	
	public interface Display extends WidgetDisplay {
		public HasText getFromText();
		public HasText getToText();
		public HasText getCcText();
		public HasText getBccText();
		public HasText getSubjectText();
		public HasText getMessageText();
		public HasClickHandlers getSendClick();
		public HasEnable getSendEnable();
		public IUploader getUploader();
		public void resetUploader();
		public HasClickHandlers getBackButtonClick();
	}

	@Override
	public Place getPlace() {
		return PLACE;
	}

	@Override
	protected void onBind() {
		registerHandler(eventBus.addHandler(LoadMessagesEvent.TYPE, new LoadMessagesEventHandler() {

			public void onLoadMessagesEvent(LoadMessagesEvent loadMessagesEvent) {
				reset();
			}
			
		}));
		registerHandler(eventBus.addHandler(FolderSelectionEvent.TYPE, new FolderSelectionEventHandler() {

			public void onFolderSelectionEvent(FolderSelectionEvent event) {
				reset();
			}
			
		}));
		display.getUploader().setOnFinishHandler(handler);
		
		registerHandler(display.getSendClick().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				
					if (validator.validate() == false) {
						return;
					}
					SMTPMessage message = new SMTPMessage();
								
					message.setFrom(display.getFromText().getText());
					
					ArrayList<String> to = new ArrayList<String>();
					String[] toRaw = display.getToText().getText().split(",");
					if (toRaw != null) {
						for (int i = 0; i < toRaw.length;i++) {
							String toRecip = toRaw[i].trim();
							if (toRecip.length() > 0) {
								to.add(toRaw[i].trim());
							}
						}
					}
					message.setTo(to);
					
					ArrayList<String> cc = new ArrayList<String>();
					String[] ccRaw = display.getCcText().getText().split(",");
					if (ccRaw != null) {
						for (int i = 0; i < ccRaw.length;i++) {
							String ccRecip = ccRaw[i].trim();
							if (ccRecip.length() > 0) {
								cc.add(ccRaw[i].trim());
							}
						}
					}
					message.setCc(cc);
					
					message.setSubject(display.getSubjectText().getText());
					message.setText(display.getMessageText().getText());

					message.setMessageAttachments(aList);
				
					if (type.equals(Type.NEW)) {
						dispatcher.execute(new SendMessage(user.getSessionId(),message), new SessionAsyncCallback<EmptyResult>(new AsyncCallback<EmptyResult>() {

							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							public void onSuccess(EmptyResult result) {
								reset();
								eventBus.fireEvent(new SentMessageEvent());
							}
							
						}, eventBus,user));
					} else if(type.equals(Type.FORWARD)) {
						dispatcher.execute(new ForwardMessage(user.getSessionId(),message,folder,oldmessage.getUid()), new SessionAsyncCallback<EmptyResult>(new AsyncCallback<EmptyResult>() {

							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							public void onSuccess(EmptyResult result) {
								reset();
								eventBus.fireEvent(new SentMessageEvent());
							}
							
						}, eventBus,user));
					} else if(type.equals(Type.REPLY) || type.equals(Type.REPLY_ALL)) {
					
						boolean replyAll = type.equals(Type.REPLY_ALL);
						dispatcher.execute(new ReplyMessage(user.getSessionId(),message,folder,oldmessage.getUid(),replyAll),new SessionAsyncCallback<EmptyResult>(new AsyncCallback<EmptyResult>() {

							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							public void onSuccess(EmptyResult result) {
								reset();
								eventBus.fireEvent(new SentMessageEvent());
							}
							
						}, eventBus,user));
					
					}
				}
		
			
		}));
		
		registerHandler(display.getBackButtonClick().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new BackEvent());
			}
			
		}));
	}

	private void reset() {
		display.resetUploader();
		display.getUploader().setOnStartHandler(startHandler);
		display.getUploader().setOnFinishHandler(handler);
		display.getBccText().setText("");
		display.getCcText().setText("");
		display.getToText().setText("");
		display.getSubjectText().setText("");
		aList.clear();
		folder = null;
		oldmessage = null;
		type = Type.NEW;
	}

	@Override
	protected void onPlaceRequest(PlaceRequest request) {
		String from = request.getParameter("from", user.getName());
		display.getFromText().setText(from);

		
		String to = request.getParameter("to", null);
		if (to != null) {
			display.getToText().setText(to);
		}
		
		String cc = request.getParameter("cc", null);
		if (cc != null) {
			display.getCcText().setText(cc);
		}
		
		String bcc = request.getParameter("bcc", null);
		if (bcc != null) {
			display.getBccText().setText(bcc);
		}
		
		String subject = request.getParameter("subject", null);
		if (subject != null) {
			display.getSubjectText().setText(subject);
		}
		
		String bodytext = request.getParameter("bodytext", null);
		if (bodytext != null) {
			display.getMessageText().setText(bodytext);
		}
	}

	@Override
	protected void onUnbind() {
		// TODO Auto-generated method stub
		
	}

	public void refreshDisplay() {
		// TODO Auto-generated method stub
		
	}

	public void revealDisplay() {
		// TODO Auto-generated method stub
		
	}
	
	private class EndValueChangeHandler implements ValueChangeHandler<IUploader> {

		public void onValueChange(ValueChangeEvent<IUploader> event) {
			String name = ((Uploader) event.getValue()).getFilename();
			MessageAttachment attachment = new MessageAttachment();
			attachment.setName(name);
			aList.add(attachment);
			display.getSendEnable().setEnabled(true);
		}

	}
	
	private class StartValueChangeHandler implements ValueChangeHandler<IUploader> {

		public void onValueChange(ValueChangeEvent<IUploader> event) {
			display.getSendEnable().setEnabled(false);
		}
		
	}
	
	public void bind(User user, IMAPFolder folder, Message oldmessage, MessageDetails oldDetails, Type type) {
		this.oldmessage = oldmessage;
		this.oldDetails = oldDetails;
		this.folder = folder;
		this.user = user;
		this.type = type;
		
		bind();
		
		display.getFromText().setText(user.getName());

		if (type.equals(Type.FORWARD)) {
			display.getSubjectText().setText("Fwd: " + oldmessage.getSubject());
			display.getMessageText().setText("\n\n-------- Original Message -------\n" );
		} else if (type.equals(Type.REPLY) || type.equals(Type.REPLY_ALL)) {
			display.getSubjectText().setText("Re: " + oldmessage.getSubject());
			
			String oldMessageText = oldDetails.getText();
			StringBuffer messageText = new StringBuffer("\n\n-------- Message -------\n");
			if ( oldMessageText != null) {
				messageText.append(oldMessageText);
			}
			display.getMessageText().setText(messageText.toString());

			if (type.equals(Type.REPLY)) {
				display.getToText().setText(oldmessage.getFrom());
			} else {
				oldmessage.getCc().remove(user.getName());
				display.getCcText().setText(Util.arrayToString(oldmessage.getCc()));
				oldmessage.getTo().remove(user.getName());

				display.getToText().setText(Util.arrayToString(oldmessage.getTo()));

			}
		}else {
			display.getSubjectText().setText("");
			display.getMessageText().setText("");
		}
	}
	
	public void bind(User user, Type type) {
		bind(user,null,null,null, type);
	}
	
}
