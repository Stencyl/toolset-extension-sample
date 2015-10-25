package com.mysite.example;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import stencyl.core.lib.Game;
import stencyl.sw.ext.BaseExtension;
import stencyl.sw.ext.OptionsPanel;

public class SampleExtension extends BaseExtension
{
	private static final Logger log = Logger.getLogger(SampleExtension.class);
	
	/*
	 * Happens when StencylWorks launches. 
	 * 
	 * Avoid doing anything time-intensive in here, or it will
	 * slow down launch.
	 */
	@Override
	public void onStartup()
	{
		super.onStartup();
		
		isInMenu = true;
		menuName = "Extension Name";
		
		isInGameCenter = true;
		gameCenterName = "Extension Name";
	}
	
	/*
	 * Happens when the extension is told to display.
	 * 
	 * May happen multiple times during the course of the app. 
	 * 
	 * A good way to handle this is to make your extension a singleton.
	 */
	@Override
	public void onActivate()
	{
		log.info("SampleExtension : Activated");
	}
	
	/*
	 * Happens when StencylWorks closes.
	 *  
	 * Usually used to save things out.
	 */
	@Override
	public void onDestroy()
	{
		log.info("SampleExtension : Destroyed");
	}
	
	/*
	 * Happens when a game is saved.
	 */
	@Override
	public void onGameSave(Game game)
	{
		log.info("SampleExtension : Saved");
	}
	
	/*
	 * Happens when a game is opened.
	 */
	@Override
	public void onGameOpened(Game game)
	{
		log.info("SampleExtension : Opened");
	}

	/*
	 * Happens when a game is closed.
	 */
	@Override
	public void onGameClosed(Game game)
	{
		log.info("SampleExtension : Closed");
	}
	
	
	@Override
	protected boolean hasOptions()
	{
		return true;
	}
	
	/*
	 * Happens when the user requests the Options dialog for your extension.
	 * 
	 * You need to provide the form. We wrap it in a dialog.
	 */
	@Override
	public OptionsPanel onOptions()
	{
		log.info("SampleExtension : Options");
		
		return new OptionsPanel()
		{
			JTextField text;
			JCheckBox check;
			JComboBox<?> dropdown;
			
			/*
			 * Construct the form.
			 * 
			 * We provide a simple way to construct forms without
			 * knowing Swing (Java's GUI library).
			 */
			@Override
			public void init()
			{
				startForm();
				addHeader("Options");
				text = addTextfield("Name:");
				check = addCheckbox("Do you like chocolate?");
				dropdown = addDropdown("Where are you from?", new String[] {"Americas", "Europe", "Asia", "Other"});
				endForm();
				
				//Set the form's values
				text.setText(readStringProp("name", ""));
				check.setSelected(readBoolProp("choc", false));
				dropdown.setSelectedItem(readStringProp("loc", "Americas"));
			}
			
			/*
			 * Use this to save the form data out.
			 * All you need to do is place the properties into preferences.
			 */
			@Override
			public void onPressedOK()
			{
				putProp("name", text.getText());
				putProp("choc", check.isSelected());
				putProp("loc", dropdown.getSelectedItem());
			}

			@Override
			public void onPressedCancel()
			{
			}

			@Override
			public void onShown()
			{
			}
		};
	}
	
	/*
	 * Happens when the extension is first installed.
	 */
	@Override
	public void onInstall()
	{
		log.info("SampleExtension : Install");
	}
	
	/*
	 * Happens when the extension is uninstalled.
	 * 
	 * Clean up files.
	 */
	@Override
	public void onUninstall()
	{
		log.info("SampleExtension : Uninstall");
	}
}