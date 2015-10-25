package com.mysite.example;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import stencyl.sw.ext.GameExtension;
import stencyl.sw.ext.OptionsPanel;

public class SampleGameExtension extends GameExtension
{
	private static final Logger log = Logger.getLogger(SampleGameExtension.class);
	
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
	 * Happens when a game that this extension is enabled for is closed.
	 */
	@Override
	public void onGameWithDataClosed()
	{
		log.info("SampleExtension : Closed Game " + getGame().getName());
	}

	/*
	 * Happens when a game that this extension is enabled for is opened.
	 */
	@Override
	public void onGameWithDataOpened()
	{
		log.info("SampleExtension : Opened Game " + getGame().getName());
		
		int openCount = readIntGameProp("opened", 0);
		putGameProp("opened", ++openCount);
		
		log.info(String.format("This game has been opened %d times with SampleExtension enabled.", openCount));
	}

	/*
	 * Happens when a game that this extension is enabled for is saved.
	 */
	@Override
	public void onGameWithDataSaved()
	{
		log.info("SampleExtension : Saved Game " + getGame().getName());
	}

	/*
	 * Happens when this extension is enabled for a game.
	 */
	@Override
	public void onInstalledForGame()
	{
		log.info("SampleExtension : Installed for Game " + getGame().getName());
	}

	/*
	 * Happens when this extension is disabled for a game.
	 */
	@Override
	public void onUninstalledForGame()
	{
		log.info("SampleExtension : Uninstalled for Game " + getGame().getName());
	}

	/*
	 * Happens when a game is being opened, and the internalVersion of
	 * this extension has been increased since the game was last opened.
	 */
	@Override
	public void updateFromVersion(int version)
	{
		log.info("SampleExtension : Updating from version " + version + " for Game " + getGame().getName());
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