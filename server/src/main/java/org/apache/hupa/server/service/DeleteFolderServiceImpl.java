<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> remove both of gwt-representer and gwt-dispatch dependencies, add license headers to all new files
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

<<<<<<< HEAD
package org.apache.hupa.server.service;

import javax.mail.Folder;
import javax.mail.MessagingException;
<<<<<<< HEAD
=======
=======
>>>>>>> remove both of gwt-representer and gwt-dispatch dependencies, add license headers to all new files
package org.apache.hupa.server.service;

import javax.mail.Folder;
>>>>>>> delete and rename folder
=======
>>>>>>> re-add server unit tests

import org.apache.hupa.shared.data.GenericResultImpl;
import org.apache.hupa.shared.domain.DeleteFolderAction;
import org.apache.hupa.shared.domain.GenericResult;
import org.apache.hupa.shared.domain.ImapFolder;
import org.apache.hupa.shared.domain.User;
<<<<<<< HEAD
<<<<<<< HEAD
import org.apache.hupa.shared.exception.HupaException;
=======
>>>>>>> delete and rename folder
=======
import org.apache.hupa.shared.exception.HupaException;
>>>>>>> re-add server unit tests

import com.sun.mail.imap.IMAPStore;

public class DeleteFolderServiceImpl extends AbstractService implements DeleteFolderService {

	@Override
<<<<<<< HEAD
<<<<<<< HEAD
	public GenericResult delete(DeleteFolderAction action) throws HupaException, MessagingException {
=======
	public GenericResult delete(DeleteFolderAction action) throws Exception {
>>>>>>> delete and rename folder
=======
	public GenericResult delete(DeleteFolderAction action) throws HupaException, MessagingException {
>>>>>>> re-add server unit tests
		User user = getUser();
		ImapFolder folder = action.getFolder();
		IMAPStore store = cache.get(user);

		Folder f = store.getFolder(folder.getFullName());

		// close the folder if its open
		if (f.isOpen()) {
			f.close(false);
		}

		// recursive delete the folder
		if (f.delete(true)) {
			logger.info("Successfully delete folder " + folder + " for user " + user);
			return new GenericResultImpl();
		} else {
<<<<<<< HEAD
<<<<<<< HEAD
			throw new HupaException("Unable to delete folder " + folder + " for user " + user);
=======
			throw new Exception("Unable to delete folder " + folder + " for user " + user);
>>>>>>> delete and rename folder
=======
			throw new HupaException("Unable to delete folder " + folder + " for user " + user);
>>>>>>> re-add server unit tests
		}
	}

}
