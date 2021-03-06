/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.core.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.handler.ShellHandler;

/**
 * Contains methods for handling UI operations on {@link Shell} widgets.
 * 
 * @author Jiri Peterka
 *
 */
public class ShellHandler extends ControlHandler{
	
	private static final Logger log = Logger.getLogger(ShellHandler.class);
	private static ShellHandler instance;
	
	/**
	 * Gets instance of ShellHandler.
	 * 
	 * @return instance of ShellHandler
	 */
	public static ShellHandler getInstance(){
		if(instance == null){
			instance = new ShellHandler();
		}
		return instance;
	}


	/**
	 * Closes specified {@link Shell}.
	 * 
	 * @param swtShell shell to close
	 */
	public void closeShell(final Shell swtShell) {
		if(!isDisposed(swtShell)){
			log.info("Closing shell '" +getText(swtShell)+ "'");
		}
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (swtShell != null && !swtShell.isDisposed()) {
					swtShell.close();
				}
			}
		});
	}

	/**
	 * Focuses on specified {@link Shell}.
	 * 
	 * @param shell shell to focus
	 */
	public void setFocus(final Shell shell) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					shell.forceActive();
					boolean isFocus = shell.forceFocus();
					if (!isFocus) log.warn("Shell is not focused");
				} catch (SWTException e) {
					throw new CoreLayerException("Exception thrown during forcing focus",e);
				}
			}
		});
	}
	
	/**
	 * Maximize shell.
	 * @param swtShell swt shell to maximize
	 */
	public void maximize(final Shell swtShell) {
		log.info("Maximize shell '" +getText(swtShell)+ "'");
		Display.syncExec(new Runnable() {

			public void run() {
				swtShell.setMaximized(true);
			}
		});
	}
	
	/**
	 * Minimize shell.
	 * 
	 * @param swtShell swt shell to minimize
	 */
	public void minimize(final Shell swtShell) {
		log.info("Minimize shell '" +getText(swtShell)+ "'");
		Display.syncExec(new Runnable() {

			public void run() {
				swtShell.setMinimized(true);
			}
		});
	}

	/**
	 * Restore shell.
	 * 
	 * @param swtShell swt shell to restore
	 */
	public void restore(final Shell swtShell) {
		log.info("Restore shell '" +getText(swtShell)+ "'");
		Display.syncExec(new Runnable() {

			public void run() {
				swtShell.setMaximized(false);
				swtShell.setMinimized(false);
			}
		});
	}

	/**
	 * Return true if shell is maximized, false otherwise.
	 *
	 * @param swtShell swt shell
	 * @return true if shell is maximized
	 */
	public boolean isMaximized(final Shell swtShell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			public Boolean run() {
				return swtShell.getMaximized();
			}
		});
	}
	
	/**
	 * Return true if shell is minimized, false otherwise.
	 *
	 * @param swtShell swt shell
	 * @return true if shell is minimized
	 */
	public boolean isMinimized(final Shell swtShell) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			public Boolean run() {
				return swtShell.getMinimized();
			}
		});
	}
	
	/**
	 * Gets text of shell
	 * @param shell to handle
	 * @return text of specified shell
	 */
	public String getText(final Shell shell){
		return Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				return shell.getText();
			}
		});
	}
	
	/**
	 * Returns an array containing all shells which are descendants of the specified shell.
	 * @param shell to get descendants of
	 * @return array of descendants of specified shell
	 */
	public Shell[] getShells(final Shell shell){
		return Display.syncExec(new ResultRunnable<Shell[]>() {

			@Override
			public Shell[] run() {
				return shell.getShells();
			}
		});
	}
	
	/**
	 * Returns all ancestor shells of given swt shell
	 * @param shell swt shell to get parents of
	 * @return list of all parent shells
	 */
	public List<Shell> getAllAncestorShells(final Shell shell){
		List<Shell> allParentShells = new ArrayList<>();
		Composite parent = getParent(shell);
		while(parent != null){
			allParentShells.add(getShell(parent));
			parent = getParent(parent);
		}
		return allParentShells;
		
	}
	
	/**
	 * Returns menu bar of given shell
	 * @param shell to get menu bar of
	 * @return menu bar of given shell
	 */
	public Menu getMenuBar(final Shell shell) {
		log.info("Getting Menu Bar of shell '" + getText(shell) + "'");
		return Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return shell.getMenuBar();
			}
		});
	}
	
	
}
