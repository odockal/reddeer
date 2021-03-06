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
package org.eclipse.reddeer.jface.test.window;

import static org.junit.Assert.*;

import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.jface.exception.JFaceLayerException;
import org.junit.After;
import org.junit.Test;

public class WindowTest {
	
	@After
	public void closeShells(){
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
	}
	

	@Test
	public void openableEclipseWindow(){
		RedDeerEclipseWindow ew = new RedDeerEclipseWindow();
		assertNull(ew.getShell());
		assertFalse(ew.isOpen());
		ew.open();
		assertNotNull(ew.getShell());//getShell() should not be null because open will set it
		assertTrue(ew.isOpen());
	}
	
	@Test
	public void openableEclipseWindowCustomAction(){
		RedDeerEclipseWindow ew = new RedDeerEclipseWindow();
		assertNull(ew.getShell());
		assertFalse(ew.isOpen());
		CustomWindowOpenable customAction = new CustomWindowOpenable();
		ew.setOpenAction(customAction);
		ew.open();
		assertNotNull(ew.getShell());//getShell() should not be null because open will set it
		assertTrue(ew.isOpen());
		assertTrue(customAction.numOfCalls == 1); //action was called
		
		ew.getShell().close();
		ew.setOpenAction(null);
		ew.open();
		assertNotNull(ew.getShell());//getShell() should not be null because open will set it
		assertFalse(ew.getShell().isDisposed());
		assertTrue(ew.isOpen());
		assertTrue(customAction.numOfCalls == 1); //action was not called
		
		
	}
	
	@Test
	public void openEclipseWindow(){
		new DefaultToolItem(new WorkbenchShell(),"openEclipseWindowTitleTooltip").click();
		RedDeerEclipseWindow ew = new RedDeerEclipseWindow();
		assertNull(ew.getShell());
		assertTrue(ew.isOpen()); //window was opened before
		assertNotNull(ew.getShell());//getShell() should not be null because isOpen will set it
		ew.open(); //finds out that window is already open and wont fail
		assertTrue(ew.isOpen());
	}
	
	@Test
	public void openEclipseWindow1(){
		new DefaultToolItem(new WorkbenchShell(),"openEclipseWindowTitleTooltip").click();
		RedDeerEclipseWindow ew = new RedDeerEclipseWindow();
		assertNull(ew.getShell());
		ew.open(); //finds out that window is already open and wont fail
		assertNotNull(ew.getShell());//getShell() should not be null because open will set it
		assertTrue(ew.isOpen());
	}
	
	@Test
	public void eclipseWindowIsOpen(){
		new DefaultToolItem(new WorkbenchShell(),"openEclipseWindowTitleTooltip").click();
		RedDeerEclipseWindowWithMatchers ew = new RedDeerEclipseWindowWithMatchers();
		assertTrue(ew.isOpen());
		ew.getShell().close(); 
		assertFalse(ew.isOpen());
		
		new DefaultToolItem(new WorkbenchShell(),"openEclipseWindowTitleTooltip").click();
		RedDeerEclipseWindowWithText ewText = new RedDeerEclipseWindowWithText();
		assertTrue(ewText.isOpen());
		assertNotNull(ewText.getShell());
		ewText.getShell().close();
		assertFalse(ewText.isOpen());
		
	}
	
	@Test(expected=JFaceLayerException.class)
	public void openActionNotDefined(){
		RedDeerEclipseWindowNotDefined ew = new RedDeerEclipseWindowNotDefined();
		ew.open();
	}
	
	@Test(expected=JFaceLayerException.class)
	public void openActionNotDefinedIsOpen(){
		RedDeerEclipseWindowNotDefined ew = new RedDeerEclipseWindowNotDefined();
		ew.isOpen();
	}

	
	
	class RedDeerEclipseWindowNotDefined extends AbstractWindow {

		@Override
		public Openable getDefaultOpenAction() {
			return null;
		}
		
	}
	
	class RedDeerEclipseWindowWithMatchers extends AbstractWindow {
		
		public RedDeerEclipseWindowWithMatchers() {
			super(new WithTextMatcher("Eclipse Window"));
		}

		@Override
		public Openable getDefaultOpenAction() {
			return null;
		}
		
	}
	
	class RedDeerEclipseWindowWithText extends AbstractWindow {
		
		public RedDeerEclipseWindowWithText() {
			super("Eclipse Window");
		}

		@Override
		public Openable getDefaultOpenAction() {
			return null;
		}
		
	}
	
	
	class RedDeerEclipseWindow extends AbstractWindow {

		@Override
		public Openable getDefaultOpenAction() {
			return new WindowOpenable();
		}
		
	}
	
	class WindowOpenable extends Openable {
		
		public WindowOpenable() {
			super(new WithTextMatcher("Eclipse Window"));
		}

		@Override
		public void run() {
			new DefaultToolItem(new WorkbenchShell(),"openEclipseWindowTitleTooltip").click();
			
		}
		
	}
	
	class CustomWindowOpenable extends Openable {
		
		public int numOfCalls = 0;
		
		public CustomWindowOpenable() {
			super(new WithTextMatcher("Eclipse Window"));
		}

		@Override
		public void run() {
			new DefaultToolItem(new WorkbenchShell(),"openEclipseWindowTitleTooltip").click();
			numOfCalls++;
		}
		
	}
}
