package nz.ac.massey.cs.flackpad;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tika.Tika;

class FileMime {
	
	private final Map<String, String> mimeMap = new HashMap<>();
	private final Tika tika = new Tika();
	
	FileMime() {
		setMimeMap();
	}

	private void setMimeMap() {
		mimeMap.put("text/x-actionscript", "text/actionscript");
		mimeMap.put("text/x-asm", "text/asm");
		mimeMap.put("text/x-bbcode", "text/bbcode");
		mimeMap.put("text/x-csrc", "text/c");
		mimeMap.put("text/x-clojure", "text/clojure");
		mimeMap.put("text/x-c++src", "text/cpp");
		mimeMap.put("text/x-csharp", "text/cs");
		mimeMap.put("text/css", "text/css");
		mimeMap.put("text/x-pascal", "text/delphi");
		mimeMap.put("text/xml-dtd", "text/dtd");
		mimeMap.put("text/x-fortran", "text/fortran");
		mimeMap.put("text/x-groovy", "text/groovy");
		mimeMap.put("text/html", "text/html");
		mimeMap.put("text/x-java-source", "text/java");
		mimeMap.put("application/javascript", "text/javascript");
		mimeMap.put("application/json", "text/json");
		mimeMap.put("text/x-jsp", "text/jsp");
		mimeMap.put("application/x-latex", "text/latex");
		mimeMap.put("text/x-common-lisp", "text/lisp");
		mimeMap.put("text/x-lua", "text/lua");
		mimeMap.put("application/vx+xml", "text/mxml");
		mimeMap.put("text/plain", "text/plain");
		mimeMap.put("text/x-perl", "text/perl");
		mimeMap.put("text/x-php", "text/php");
		mimeMap.put("text/x-java-properties", "text/properties");
		mimeMap.put("text/x-python", "text/python");
		mimeMap.put("text/x-ruby", "text/ruby");
		mimeMap.put("application/x-sas", "text/sas");
		mimeMap.put("text/x-scala", "text/scala");
		mimeMap.put("text/x-sql", "text/sql");
		mimeMap.put("text/x-tcl", "text/tcl");
		mimeMap.put("text/x-vbdotnet", "text/vb");
		mimeMap.put("application/x-bat", "text/bat");
		mimeMap.put("application/xml", "text/xml");
	}

	String getFileMime(File file) {
		String fileMime = null;
		
		// Detect MIME
		if (file != null) {
			try {
				fileMime = tika.detect(file);
			} catch (IOException e) {
				// Unable to detect MIME
				fileMime = null;
			}
		}
		
		// Return MIME
		return fileMime;
	}
	
	String getFileStyle(File file) {
		String fileStyle = "text/plain";
		
		// Get MIME
		final String fileMime = getFileMime(file);
		
		// Convert to style
		if (mimeMap.containsKey(fileMime)) {
			fileStyle = mimeMap.get(fileMime);
		}
		
		// Return style
		return fileStyle;
	}

}
