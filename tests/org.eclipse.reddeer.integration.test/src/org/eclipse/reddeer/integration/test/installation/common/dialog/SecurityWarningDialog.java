/*******************************************************************************
 * Copyright (c) 2020 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/

package org.eclipse.reddeer.integration.test.installation.common.dialog;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;

public class SecurityWarningDialog extends DefaultShell {

	public static final String HEADER = "Security Warning";
	
	public SecurityWarningDialog () {
		super(HEADER);
	}
	
	public void ok() {
		new OkButton(this).click();
		new WaitWhile(new ShellIsAvailable(this));
	}
	
	public static boolean isAvailable() {
		return (new ShellIsAvailable(HEADER)).test();
	}
}
