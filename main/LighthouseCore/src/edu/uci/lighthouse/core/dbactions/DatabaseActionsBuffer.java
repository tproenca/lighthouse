/*******************************************************************************
 * Copyright (c) {2009,2011} {Software Design and Collaboration Laboratory (SDCL)
 *				, University of California, Irvine}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    {Software Design and Collaboration Laboratory (SDCL)
 *	, University of California, Irvine} 
 *			- initial API and implementation and/or initial documentation
 *******************************************************************************/ 
package edu.uci.lighthouse.core.dbactions;

import java.util.LinkedList;

import edu.uci.lighthouse.core.util.WorkbenchUtility;
import edu.uci.lighthouse.model.io.IPersistable;

public class DatabaseActionsBuffer extends LinkedList<IDatabaseAction> implements IPersistable {

	private static final long serialVersionUID = -5094074685177488970L;
	
	private static final String filename = "lighthouse_buffer.bin";
	
	private transient static DatabaseActionsBuffer instance;
	
	private DatabaseActionsBuffer() {
		// TODO (tproenca): Make this a Singleton class. Implications: check the IPersistable service, since it uses reflection to load the objects.
//		instance = this;
	}
	
	@Override
	public synchronized IDatabaseAction peek() {
		return super.peek();
	}

	@Override
	public synchronized IDatabaseAction poll() {
		return super.poll();
	}

	@Override
	public synchronized boolean offer(IDatabaseAction e) {
		if (e instanceof IPeriodicDatabaseAction && exists((IPeriodicDatabaseAction)e)) {
			return false;
		}
		return super.offer(e);
	}
	
	@Override
	public String getFileName() {
		return WorkbenchUtility.getMetadataDirectory() + filename;
	}
	
	private boolean exists(IPeriodicDatabaseAction periodicAction){
		for (IDatabaseAction action: this) {
			if (action instanceof IPeriodicDatabaseAction) {
				if (action.getClass().equals(periodicAction.getClass())){
					return true;
				}
			}
		}
		return false;
	}
	
	public static DatabaseActionsBuffer getInstance(){
		if (instance == null) {
			instance = new DatabaseActionsBuffer();
		}
		return instance;
	}
	
	// readResolve method to preserve singleton property
	private Object readResolve() {
		return getInstance();
	}
}
