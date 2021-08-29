package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

final class OdtUtils {

	private OdtUtils() {
		throw new UnsupportedOperationException();
	}

	static String loadFile(File file) throws IOException, SAXException, ParserConfigurationException {
		Document document;

		// Parse input stream to XML document
		try (ZipFile odt = new ZipFile(file);
				InputStream contentStream = odt.getInputStream(odt.getEntry("content.xml"))) {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(contentStream);
		}

		// Get just text nodes
		final List<Node> textNodes = getTextNodes(document, new ArrayList<Node>());

		// Parse text into lines
		String text = "";
		for (int i = 0; i < textNodes.size(); i++) {
			text += textNodes.get(i).getTextContent();
			if (i < textNodes.size() - 1) {
				text += "\n";
			}
		}

		return text;
	}

	static String export(String text, File file) throws Exception {
		// Make sure file has correct extension
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".odt")) {
			filePath += ".odt";
		}

		// Split text into lines
		final String[] lines = text.split("\n");

		// Create empty ODF document
		try (OdfTextDocument document = OdfTextDocument.newTextDocument()) {
			// Add lines to document
			document.addText(lines[0]);
			for (int i = 1; i < lines.length; i++) {
				document.newParagraph(lines[i]);
			}
			// Save document to file
			document.save(filePath);
		}

		// Return file name
		return filePath.substring(filePath.lastIndexOf("\\") + 1);
	}

	// Gets just text nodes
	private static List<Node> getTextNodes(Node node, List<Node> textNodes) {
		// Add node if it is a paragraph or heading
		if ("text:p".equals(node.getNodeName()) || "text:h".equals(node.getNodeName())) {
			textNodes.add(node);
		}
		// Get child nodes
		final NodeList childNodes = node.getChildNodes();
		// Recursively call getTextNodes on child nodes
		for (int i = 0; i < childNodes.getLength(); i++) {
			getTextNodes(childNodes.item(i), textNodes);
		}

		return textNodes;
	}

}
