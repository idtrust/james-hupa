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

package org.apache.hupa.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class __ComposePanel extends Composite {

	@UiField protected SimplePanel composeHeaderContainer;
	@UiField protected SimpleLayoutPanel composeContentContainer;
	@UiField protected SimplePanel composeStatusContainer;

	public __ComposePanel() {
		initWidget(binder.createAndBindUi(this));
	}

	interface __ComposePanelUiBinder extends
			UiBinder<DockLayoutPanel, __ComposePanel> {
	}

	private static __ComposePanelUiBinder binder = GWT
			.create(__ComposePanelUiBinder.class);

	public AcceptsOneWidget getComposeHeaderContainer() {
		return new AcceptsOneWidget() {
			@Override
			public void setWidget(IsWidget w) {
				composeHeaderContainer.setWidget(Widget.asWidgetOrNull(w));
			}
		};
	}

	public AcceptsOneWidget getComposeContentContainer() {
		return new AcceptsOneWidget() {
			@Override
			public void setWidget(IsWidget w) {
				composeContentContainer.setWidget(Widget.asWidgetOrNull(w));
			}
		};
	}

	public AcceptsOneWidget getComposeStatusContainer() {
		return new AcceptsOneWidget() {
			@Override
			public void setWidget(IsWidget w) {
				composeStatusContainer.setWidget(Widget.asWidgetOrNull(w));
			}
		};
	}

}
