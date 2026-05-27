package com.example;

import stencyl.app.ext.OptionsAddon;
import stencyl.app.ext.OptionsPanel;
import stencyl.core.ext.app.AppExtension;
import stencyl.core.lib.IProject;
import stencyl.sw.app.ext.ExtensionCP;
import stencyl.sw.app.main.Menu;
import stencyl.sw.app.main.Menu.ExtensionMenuAddon;

import javax.swing.*;
import java.awt.*;

public class SampleExtension extends AppExtension
{
	/*
	 * Happens when the extension is loaded, including when StencylWorks launches.
	 *
	 * Avoid doing anything time-intensive in here, or it will slow down launch.
	 */
	@Override
	public void onLoad()
	{
		super.onLoad();

		/*
		 * Add an options menu to the Extension Control Panel
		 */
		getAddons().setAddon(ExtensionCP.EXTENSION_CP_OPTIONS_ADDONS,
				(OptionsAddon) SampleExtension.this::onOptions);

		/*
		 * Add a menu item to the Extensions menu
		 */
		getAddons().setAddon(Menu.EXTENSIONS_MENU_ADDON, new ExtensionMenuAddon(this) {
			@Override
			protected void actionPerformed() {
				System.out.println("SampleExtension : Menu Item Action Performed");
			}
		});
	}

	/*
	 * Happens when the extension is unloaded.
	 *
	 * Usually used to save things out.
	 */
	@Override
	public void onUnload()
	{
		super.onUnload();
		System.out.println("SampleExtension : Unloaded");
	}

	/*
	 * Happens when a game is saved.
	 */
	@Override
	public void onGameSave(IProject project)
	{
		System.out.println("SampleExtension : Saved");
	}

	/*
	 * Happens when a game is opened.
	 */
	@Override
	public void onGameOpened(IProject project)
	{
		System.out.println("SampleExtension : Opened");
	}

	/*
	 * Happens when a game is closed.
	 */
	@Override
	public void onGameClosed(IProject project)
	{
		System.out.println("SampleExtension : Closed");
	}

	private OptionsPanel onOptions()
	{
		System.out.println("SampleExtension : Options");
		
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
				text.setText("" + properties.get("name"));
				check.setSelected(Boolean.parseBoolean("" + properties.get("choc")));
				dropdown.setSelectedItem(properties.get("loc"));
			}
			
			/*
			 * Use this to save the form data out.
			 * All you need to do is place the properties into preferences.
			 */
			@Override
			public void onPressedOK()
			{
				properties.put("name", text.getText());
				properties.put("choc", check.isSelected());
				properties.put("loc", dropdown.getSelectedItem());
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
}