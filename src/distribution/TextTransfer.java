package distribution;
//import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

public final class TextTransfer implements ClipboardOwner {

	/**
   * Empty implementation of the ClipboardOwner interface.
   */
	@Override
	public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable contents) {
     //do nothing
   }

  /**
  * Place a String on the clipboard, and make this class the
  * owner of the Clipboard's contents.
  */
  public void setClipboardContents(String aString){
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent content = new ClipboardContent();
    content.putString(aString);
    clipboard.setContent(content);
  }

  /**
  * Get the String residing on the clipboard.
  *
  * @return any text found on the Clipboard; if none found, return an
  * empty String.
  */
  public String getClipboardContents() {
    String result = "";
    Clipboard clipboard = Clipboard.getSystemClipboard();

    boolean hasTransferableText =
    	      (clipboard != null) &&
    	      clipboard.hasContent(DataFormat.PLAIN_TEXT)
    	    ;
    if(hasTransferableText){
    	result = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
    }
    return result;
  }
} 