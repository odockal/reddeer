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
package org.eclipse.reddeer.eclipse.wst.server.ui.cnf;

import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.adaptable.RedDeerAdaptable;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.wst.server.ui.editor.ServerEditor;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.ModifyModulesDialog;
import org.eclipse.reddeer.swt.api.TreeItem;

/**
 * Represents a server on {@link ServersView2}. Contains both, the server data
 * (name, state, status) and operations that can be invoked on server (Start,
 * Stop, Delete etc.). For operations that can be invoked on project added to
 * server see {@link ServerModule}
 * 
 * @author Lucia Jelinkova, mlabuda@redhat.com
 * 
 */
public interface Server extends RedDeerAdaptable<Server> {

	/**
	 * Gets underlying tree item
	 * @return tree item representing server
	 */
	public TreeItem getTreeItem();
	
	/**
	 * Returns true if underlying tree item is not null and is not disposed.
	 *
	 * @return true if treeItem is not null or is not disposed, false otherwise
	 */
	public boolean isValid();
	
	/**
	 * Returns a server label.
	 * 
	 * @return Server label
	 */
	public ServerLabel getLabel();
	
	/**
	 * Selects the server.
	 */
	public void select();
	
	/**
	 * Opens the server editor.
	 * 
	 * @return Server editor
	 */
	public ServerEditor open();
	
	/**
	 * Starts the server.
	 */
	public void start();
	
	/**
	 * Stops the server.
	 */
	public void stop();
	
	/**
	 * Restarts the server.
	 */
	public void restart();
	
	/**
	 * Restarts the server in debug.
	 */
	public void restartInDebug();
	
	/**
	 * Restarts the server in profile.
	 */
	public void restartInProfile();
	
	/**
	 * Debugs the server.
	 */
	public void debug();
	
	/**
	 * Profiles the server.
	 */
	public void profile();
	
	/**
	 * Publishes the server.
	 */
	public void publish();
	
	/**
	 * Cleans the server.
	 */
	public void clean();
	
	/**
	 * Deletes the server. The server is not stop first.
	 */
	public void delete();
	
	/**
	 * Deletes the server. You can specify whether the server is stop first.
	 * 
	 * @param stopFirst
	 *            Indicates whether the server is stop first.
	 */
	public void delete(boolean stopFirst);
	
	/**
	 * Gets timeout of server for changing its state.
	 * It is a time period to wait until a server get to the specified state.
	 * It consist of time to change current state to temporarily state, if there is any
	 * and of time to get to the final state. Other words, time out is whole time to get from 
	 * initial state to final state.
	 * 
	 * @return TimePeriod to time out if server does not get to final state in the given timeout
	 */
	public TimePeriod getServerStateChangeTimeout();
	
	/**
	 * Sets timeout of server for changing its state.
	 * It is a time period to wait until a server get to the specified state.
	 * It consist of time to change current state to temporarily state, if there is any
	 * and of time to get to the final state. Other words, time out is whole time to get from 
	 * initial state to final state.
	 *
	 * @param timeout timeout to wait from getting server from initial state to desired state
	 */
	public void setServerStateChangeTimeout(TimePeriod timeout);
	
	/**
	 * Gets server timeout for publishing.
	 * 
	 * @return TimePeriod to time out if server did not finished publish in the timeout
	 */
	public TimePeriod getServerPublishTimeout();
	
	/**
	 * Sets timeout for publishing to server.
	 * 
	 * @param timeout time period to time out if server did not finished publish in the timeout
	 */
	public void setServerPublishTimeout(TimePeriod timeout);
	
	/**
	 * Opens a dialog for adding/removing modules.
	 * 
	 * @return Dialog for adding/removing modules
	 */
	public ModifyModulesDialog addAndRemoveModules();
	
	/**
	 * Activates server view where this server belongs to.
	 */
	public void activate();
	
	/**
	 * Gets a module with a given name.
	 * 
	 * @param name
	 *            Module name
	 * @return Module
	 */
	public ServerModule getModule(String name);
	
	/**
	 * Gets a module with a given name and given type.
	 * 
	 * @param clazz class type of server module
	 * @param name
	 *            Module name
	 * @return Module
	 */
	public <T extends ServerModule> T getModule(Class<T> clazz, String name);
	
	/**
	 * Gets a module of given type and matching a matcher.
	 * 
	 * @param clazz class type of server module
	 * @param stringMatcher
	 *            matcher to match against module name.
	 * @return Module
	 */
	public <T extends ServerModule> T getModule(Class<T> clazz, Matcher<String> stringMatcher);
	
	/**
	 * Gets a module matching a matcher.
	 * 
	 * @param stringMatcher
	 *            matcher to match against module name.
	 * @return Module
	 */
	public ServerModule getModule(Matcher<String> stringMatcher);
	
	/**
	 * Gets a list of modules.
	 * 
	 * @return List of modules
	 */
	public List<ServerModule> getModules();
	
	/**
	 * Gets a list of modules of given type.
	 * 
	 * @param clazz class type of server module
	 * @return List of modules
	 */
	public <T extends ServerModule>  List<T> getModules(Class<T> clazz);
	
	
}
