package edu.asu;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Document parseXMLFile(String _fileName){
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(_fileName);
			return dom;
		} catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch(SAXException se){
			se.printStackTrace();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}
	public static String getXMLElement(Node XML, String tagName){
		return getXMLNodes(XML, "Name").item(0).getFirstChild().getNodeValue();  //needs to be broken down and given try blocks		
	}
	public static NodeList getXMLNodes(Node XML, String NodeType){
		Element XMLData = (Element)XML;
		return XMLData.getElementsByTagName("Name");  //needs a try catch
	}
}
