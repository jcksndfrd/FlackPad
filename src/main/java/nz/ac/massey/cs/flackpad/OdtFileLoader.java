package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class OdtFileLoader {

	OdtFileLoader() {
	}

	String loadFile(File file) throws IOException {

		@SuppressWarnings("resource")
		ZipFile odt = new ZipFile(file);
		InputStream contentStream = odt.getInputStream(odt.getEntry("content.xml"));

		Document document;
		String text = "";

		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(contentStream);
		} catch (ParserConfigurationException | SAXException e) {
			throw new IOException();
		}

		List<Node> textNodes = getTextNodes(document, new ArrayList<Node>());

		for (int i = 0; i < textNodes.size(); i++) {
			text += textNodes.get(i).getTextContent();
			if (i < textNodes.size() - 1) {
				text += System.getProperty("line.separator");
			}
		}

		return text;
	}

	private static List<Node> getTextNodes(Node node, List<Node> textNodes) {
		if (node.getNodeName() == "text:p" || node.getNodeName() == "text:h") {
			textNodes.add(node);
		}

		NodeList childNodes = node.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			getTextNodes(childNodes.item(i), textNodes);
		}

		return textNodes;
	}

}
