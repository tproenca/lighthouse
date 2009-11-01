package edu.uci.lighthouse.model.io;

import java.io.IOException;

import org.dom4j.DocumentException;

import edu.uci.lighthouse.model.jpa.JPAUtilityException;

public interface IPersistence {

	public void save() throws IOException;
	
	public void save(String fileName) throws IOException;
	
	public void load() throws DocumentException, JPAUtilityException;
	
	public void load(String fileName) throws DocumentException, JPAUtilityException;
	
}