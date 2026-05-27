package com.example;

import org.apache.log4j.Logger;
import stencyl.app.ext.PageAddon.ExtensionPageAddon;
import stencyl.core.ext.GameExtension;
import stencyl.core.ext.engine.ExtensionInstanceManager.FormatUpdateSubmitter;
import stencyl.sw.app.center.GameLibrary;

import javax.swing.*;

public class SampleGameExtension extends GameExtension
{
	private static final Logger log = Logger.getLogger(SampleGameExtension.class);

	/*
	 * Happens when this extension is loaded in a project.
	 */
	@Override
	protected void onLoad()
	{
		log.info("SampleGameExtension : Loaded for game " + getProject().getName());

		super.onLoad();

		int openCount = readIntGameProp("opened", 0);
		putGameProp("opened", ++openCount);

		log.info(String.format("SampleGameExtension has been loaded/reloaded %d times in this game.", openCount));

		/*
		 * Add a page to the Extensions listing in the dashboard
		 */
		owner().getAddons().setAddon(GameLibrary.DASHBOARD_SIDEBAR_PAGE_ADDONS, new ExtensionPageAddon(this) {
			@Override
			public JPanel getPage() {
				return null;
			}
		});
	}

	/*
	 * Happens when this extension is unloaded from a project.
	 */
	@Override
	protected void onUnload()
	{
		log.info("SampleGameExtension : Unloaded for game " + getProject().getName());
		super.onUnload();
	}

	/*
	 * Happens when a game that this extension is enabled for is saved.
	 */
	@Override
	protected void onSave()
	{
		super.onSave();
		log.info("SampleGameExtension : Saved Game " + getProject().getName());
	}

	/*
	 * Happens when this extension is enabled for a game.
	 */
	@Override
	protected void onInstalled()
	{
		super.onInstalled();
		log.info("SampleGameExtension : Installed for Game " + getProject().getName());
	}

	/*
	 * Happens when this extension is disabled for a game.
	 *
	 * Note that this is not guaranteed to be called, for example if the
	 * extension is deleted outside Stencyl.
	 */
	@Override
	protected void onUninstalled()
	{
		super.onUninstalled();
		log.info("SampleGameExtension : Uninstalled for Game " + getProject().getName());
	}

	/*
	 * Happens when a game is being opened, and the internalVersion of
	 * this extension has been increased since the game was last opened.
	 *
	 * If this extension needs to perform a collaborative update with other
	 * extensions, use the formatUpdateQueue.
	 */
	@Override
	public void updateFromVersion(int fromVersion, FormatUpdateSubmitter formatUpdateQueue)
	{
		log.info("SampleGameExtension : Updating from version " + fromVersion + " for Game " + getProject().getName());
	}
}
