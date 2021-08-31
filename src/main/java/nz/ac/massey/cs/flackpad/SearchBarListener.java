package nz.ac.massey.cs.flackpad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchBarListener implements ActionListener, FocusListener {

	private final SearchBar searchBar;
	
	public SearchBarListener(SearchBar searchBar) {
		this.searchBar = searchBar;
	}

	@Override
	public void actionPerformed(ActionEvent thisEvent) {		
		if (thisEvent.getSource().equals(searchBar.getFindClose())) {
			searchBar.hideFindBar();
		}
		else if (thisEvent.getSource().equals(searchBar.getFindField())) {
			searchBar.findText();
		}
		else if (thisEvent.getSource().equals(searchBar.getFindButton())) {
			searchBar.findText();
		}
		else if (thisEvent.getSource().equals(searchBar.getReplaceField())) {
			searchBar.replaceText();
		}
		else if (thisEvent.getSource().equals(searchBar.getReplaceButton())) {
			searchBar.replaceText();
		}
	}

	@Override
	public void focusGained(FocusEvent thisEvent) {
		if (thisEvent.getSource().equals(searchBar.getFindField())) {
			searchBar.setFindField();
		}
		else if (thisEvent.getSource().equals(searchBar.getReplaceField())) {
			searchBar.setReplaceField();
		}
		
	}

	@Override
	public void focusLost(FocusEvent thisEvent) {
		if (thisEvent.getSource().equals(searchBar.getFindField())) {
			searchBar.resetFindField();
		}	
		else if (thisEvent.getSource().equals(searchBar.getReplaceField())) {
			searchBar.resetReplaceField();
		}
	}

}
