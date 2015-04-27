package com.tibbers.util.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SaxErrorHandler implements ErrorHandler {

	@Override
	public void warning(SAXParseException ex) throws SAXException {
		throw ex;

	}

	@Override
	public void error(SAXParseException ex) throws SAXException {
		throw ex;

	}

	@Override
	public void fatalError(SAXParseException ex) throws SAXException {
		throw ex;

	}

}
