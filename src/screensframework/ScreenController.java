/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */ 

package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import distribution.Brain;
import distribution.TextTransfer;
import exceptions.MinValueToBigException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Fredrik Johansson
 */
public class ScreenController implements Initializable {
    
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button calcButton;
    @FXML private TextField keyTextField;
    @FXML private TextField roofTextField;
    @FXML private AnchorPane overlay_AnchorPane;
    @FXML private TextField sumTextField;
    @FXML private Label calcStatusLabel;
    @FXML private Label errorLabel;
    
    private final TextTransfer textTransfer = new TextTransfer();
    private double keyValue = 0;
    private double sumValue = 0;
    private double roofValue = 0;
    private boolean keyOk = false;
    private boolean sumOk = false;
    private boolean roofOk = false;
    
    private static final String CUSTOM = "-fx-border-color: #588adb;"
            							+ "-fx-border-width: 1;"
        								+ "-fx-background-radius: 0px;"
        								+ "-fx-background-color: #ecf7ff;"
        								+ "-fx-focus-color: #3f75f3;";
    private static final String RED = "-fx-border-color: red;"
										+ "-fx-border-width: 1;"
										+ "-fx-background-radius: 0px;"
										+ "-fx-background-color: #ecf7ff;"
										+ "-fx-focus-color: #3f75f3;";
    private static final String GREEN = "-fx-border-color: green;"
										+ "-fx-border-width: 1;"
										+ "-fx-background-radius: 0px;"
										+ "-fx-background-color: #ecf7ff;"
										+ "-fx-focus-color: #3f75f3;";
    @FXML
    void sumEnter(ActionEvent event) {
    	keyTextField.requestFocus();
    }
    
    @FXML
    void distEnter(ActionEvent event) {    	
    	if(!calcButton.isDisable()){
    		calcButton.requestFocus();
    		calculate(null);
    	}
    }
    
    @FXML
    void roofEnter(KeyEvent event) {
    	if(!calcButton.isDisable()){
    		calcButton.requestFocus();
    		calculate(null);
    	}
    }
    
    @FXML
    void roofTyped(KeyEvent event) {
    	try {
			roofValue = Double.valueOf(roofTextField.getText());
			roofTextField.setStyle(GREEN);
			roofOk = true;
		} catch (NumberFormatException  e) {
			roofTextField.setStyle(RED);
			roofOk = false;
		}
		if((keyOk || roofOk) && sumOk){
        	calcButton.setDisable(false);
        }else{
        	calcButton.setDisable(true);
        }
    }
    
    @FXML
    void keyTyped(KeyEvent event) {
		try {
			keyValue = Double.valueOf(keyTextField.getText());
			keyTextField.setStyle(GREEN);
			keyOk = true;
		} catch (NumberFormatException  e) {
			keyTextField.setStyle(RED);
			keyOk = false;
		}
		if((keyOk || roofOk) && sumOk){
        	calcButton.setDisable(false);
        }else{
        	calcButton.setDisable(true);
        }
    }
    
    @FXML
    void sumTyped(KeyEvent event) {
    	try {
			sumValue = Double.valueOf(sumTextField.getText());
			sumTextField.setStyle(GREEN);
			sumOk = true;
		} catch (NumberFormatException  e) {
			sumTextField.setStyle(RED);
			sumOk = false;
		}
		if((keyOk || roofOk)  && sumOk){
        	calcButton.setDisable(false);
        }else{
        	calcButton.setDisable(true);
        }
    }
    
    @FXML
    private void calculate(ActionEvent event) {
    	String clipBoardContent	= this.textTransfer.getClipboardContents();
    	
    	try{
    		if(this.keyOk){
    			clipBoardContent = Brain.calculateKey(clipBoardContent, this.keyValue, this.sumValue);	
    		}else if(this.roofOk){
    			clipBoardContent = Brain.calculateRoof(clipBoardContent, this.roofValue, this.sumValue);
    		}
    		this.textTransfer.setClipboardContents(clipBoardContent);
        	this.calcStatusLabel.setText("Calculation complete!");
        	this.errorLabel.setText("");
    	}catch (NumberFormatException e){
    		this.calcStatusLabel.setText("Could not calculate.");
    		this.errorLabel.setText("Please review the copied values.");
    	}catch (MinValueToBigException e){
    		this.calcStatusLabel.setText("Could not calculate.");
    		this.errorLabel.setText("The minimum value is too big.");
    	}catch (Exception e){
    		this.calcStatusLabel.setText("Could not calculate.");
    		this.errorLabel.setText("Please review the copied values.");
    	}
    	this.overlay_AnchorPane.setVisible(true);
    }

    @FXML
    private void hideOverlay(MouseEvent event) {
    	this.overlay_AnchorPane.setVisible(false);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert calcButton != null : "fx:id=\"calcButton\" was not injected: check your FXML file 'Main.fxml'.";
        assert calcStatusLabel != null : "fx:id=\"calcStatusLabel\" was not injected: check your FXML file 'Main.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'Main.fxml'.";
        assert keyTextField != null : "fx:id=\"keyTextField\" was not injected: check your FXML file 'Main.fxml'.";
        assert overlay_AnchorPane != null : "fx:id=\"overlay_AnchorPane\" was not injected: check your FXML file 'Main.fxml'.";
        assert sumTextField != null : "fx:id=\"sumTextField\" was not injected: check your FXML file 'Main.fxml'.";
        assert roofTextField != null : "fx:id=\"roofTextField\" was not injected: check your FXML file 'Main.fxml'.";
        
        ChangeListener<Boolean> onFocusKey = new ChangeListener<Boolean>(){
        	
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	if (!newPropertyValue){
		    		try {
		    			keyValue = Double.valueOf(keyTextField.getText());
		    			keyTextField.setStyle(GREEN);
		    			keyOk = true;
		    		} catch (NumberFormatException  e) {
		    			keyTextField.setStyle(RED);
		    			keyOk = false;
		    		}
		    		if((keyOk || roofOk) && sumOk){
		            	calcButton.setDisable(false);
		            }else{
		            	calcButton.setDisable(true);
		            }
		    		
		        }
		    }
		};
		
		ChangeListener<Boolean> onFocusSum = new ChangeListener<Boolean>()
        		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue){		            
		    		try {
		    			sumValue = Double.valueOf(sumTextField.getText());
		    			sumTextField.setStyle(GREEN);
		    			sumOk = true;
		    		} catch (NumberFormatException  e) {
		    			sumTextField.setStyle(RED);
		    			sumOk = false;
		    		}
		    		if((keyOk || roofOk) && sumOk){
		            	calcButton.setDisable(false);
		            }else{
		            	calcButton.setDisable(true);
		            }
		        }
		    }
		    
		};
		
		ChangeListener<Boolean> onFocusRoof = new ChangeListener<Boolean>(){
        	
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		    	if (!newPropertyValue){
		    		try {
		    			roofValue = Double.valueOf(roofTextField.getText());
		    			roofTextField.setStyle(GREEN);
		    			roofOk = true;
		    		} catch (NumberFormatException  e) {
		    			roofTextField.setStyle(RED);
		    			roofOk = false;
		    		}
		    		if((keyOk || roofOk) && sumOk){
		            	calcButton.setDisable(false);
		            }else{
		            	calcButton.setDisable(true);
		            }
		    		
		        }
		    }
		};
		
		this.keyTextField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	        	String s = keyTextField.getText().replaceAll("," , ".");
	        	try{
	                s =  s.substring(0, 3);
	        	}catch(IndexOutOfBoundsException e){
	        		
	        	}
	        	keyTextField.setText(s);
	        	
	        	if(s.length() == 0){
	        		roofTextField.setDisable(false);
	        		roofTextField.setStyle(RED);
	        		
	        	}else{
	        		roofTextField.setDisable(true);
	        		roofTextField.setStyle(CUSTOM);
	        		
	        	}	            
	        }
	    });
		
		this.roofTextField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	        	if(newValue.length() == 0){
	        		keyTextField.setDisable(false);
	        		keyTextField.setStyle(RED);
	        		
	        	}else{
	        		keyTextField.setDisable(true);
	        		keyTextField.setStyle(CUSTOM);
	        		
	        	}
	        }
	    });
		
        this.keyTextField.focusedProperty().addListener(onFocusKey);
        this.sumTextField.focusedProperty().addListener(onFocusSum);
        this.roofTextField.focusedProperty().addListener(onFocusRoof);
        
        
	}
    
}
