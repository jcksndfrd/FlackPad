package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.Tika;

class FileMIME {
	
	private Map<String, String> MIME_MAP = new HashMap<String, String>();
	private Tika tika = new Tika();
	
	FileMIME() {
		setMIMEMap();
	}

	private void setMIMEMap() {
		MIME_MAP.put("text/x-actionscript", "text/actionscript");
		MIME_MAP.put("text/x-asm", "text/asm");
		MIME_MAP.put("text/x-bbcode", "text/bbcode");
		MIME_MAP.put("text/x-csrc", "text/c");
		MIME_MAP.put("text/x-clojure", "text/clojure");
		MIME_MAP.put("text/x-c++src", "text/cpp");
		MIME_MAP.put("text/x-csharp", "text/cs");
		MIME_MAP.put("text/css", "text/css");
		MIME_MAP.put("text/x-pascal", "text/delphi");
		MIME_MAP.put("text/xml-dtd", "text/dtd");
		MIME_MAP.put("text/x-fortran", "text/fortran");
		MIME_MAP.put("text/x-groovy", "text/groovy");
		MIME_MAP.put("text/html", "text/html");
		MIME_MAP.put("text/x-java-source", "text/java");
		MIME_MAP.put("application/javascript", "text/javascript");
		MIME_MAP.put("application/json", "text/json");
		MIME_MAP.put("text/x-jsp", "text/jsp");
		MIME_MAP.put("application/x-latex", "text/latex");
		MIME_MAP.put("text/x-common-lisp", "text/lisp");
		MIME_MAP.put("text/x-lua", "text/lua");
		MIME_MAP.put("application/vx+xml", "text/mxml");
		MIME_MAP.put("text/plain", "text/plain");
		MIME_MAP.put("text/x-perl", "text/perl");
		MIME_MAP.put("text/x-php", "text/php");
		MIME_MAP.put("text/x-java-properties", "text/properties");
		MIME_MAP.put("text/x-python", "text/python");
		MIME_MAP.put("text/x-ruby", "text/ruby");
		MIME_MAP.put("application/x-sas", "text/sas");
		MIME_MAP.put("text/x-scala", "text/scala");
		MIME_MAP.put("text/x-sql", "text/sql");
		MIME_MAP.put("text/x-tcl", "text/tcl");
		MIME_MAP.put("text/x-vbdotnet", "text/vb");
		MIME_MAP.put("application/x-bat", "text/bat");
		MIME_MAP.put("application/xml", "text/xml");
	}

	String getFileMIME(File file) {
		if (file == null) {
			return null;
		}
		try {
			return tika.detect(file);
		} catch (IOException e) {
			System.out.println("FileMIME\\getFileMIME() - Error: Could not detect file MIME");
			return null;
		}
	}
	
	String getFileStyle(File file) {
		String fileMIME = getFileMIME(file);
		
		if (MIME_MAP.containsKey(fileMIME)) {
			return MIME_MAP.get(fileMIME);
		} else {
			return "text/plain";
		}
	}

}
