/*
 * @(#)JSeshMain.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package jsesh.jhotdraw;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import jsesh.Version;
import jsesh.editor.actions.text.EditorCartoucheAction;
import jsesh.editor.actions.text.EditorShadeAction;
import jsesh.hieroglyphs.CompositeHieroglyphsManager;
import jsesh.hieroglyphs.DefaultHieroglyphicFontManager;
import jsesh.hieroglyphs.HieroglyphFamily;
import jsesh.hieroglyphs.ManuelDeCodage;
import jsesh.mdcDisplayer.preferences.DrawingSpecificationsImplementation;
import jsesh.swing.utils.ImageIconFactory;

import org.jhotdraw_7_6.app.Application;
import org.qenherkhopeshef.guiFramework.AppStartup;
import org.qenherkhopeshef.guiFramework.splash.SplashMessageText;
import org.qenherkhopeshef.jhotdrawChanges.QenherOSXApplication;
import org.qenherkhopeshef.jhotdrawChanges.QenherOSXLikeApplication;

/**
 * JSeshMain class.
 * 
 * @author Serge Rosmorduc.
 * @version $Id: JSeshMain.java 604 2010-01-09 12:00:29Z rawcoder $
 */
public class JSeshMain extends AppStartup<JSeshApplicationStartingData> {

	public final static String NAME = "JSesh";
	public final static String COPYRIGHT = "JSesh is CeCiLL Software (GPL-compatible) written by S. Rosmorduc";

	private String args[];

	@Override
	public JSeshApplicationStartingData initApplicationData() {
		// Pre-load a number of objects so that they are ready when graphic
		// stuff starts.
		JSeshApplicationStartingData data = new JSeshApplicationStartingData();
		new DrawingSpecificationsImplementation();
		DefaultHieroglyphicFontManager.getInstance();
		preloadHieroglyphicIcons();
		return data;
	}

	private void preloadHieroglyphicIcons() {
		List<HieroglyphFamily> families = CompositeHieroglyphsManager
				.getInstance().getFamilies();
		for (int i = 0; i < families.size(); i++) {
			HieroglyphFamily family = families.get(i);
			for (String code : ManuelDeCodage.getInstance()
					.getBasicGardinerCodesForFamily(family.getCode())) {
				ImageIconFactory.buildGlyphImage(code);
			}
		}
		EditorCartoucheAction.preloadCartoucheIcons();
		EditorShadeAction.preloadIcons();

	}

	@Override
	public void startApplication(JSeshApplicationStartingData data) {
		// Force creation of the hieroglyphic font manager and loading of the
		// fonts (do it elsewhere).
		JSeshApplicationModel applicationModel = new JSeshApplicationModel();
		applicationModel.setCopyright(COPYRIGHT);
		applicationModel.setName(NAME);
		applicationModel.setVersion(Version.getVersion());

		// We use setViewClass method instead of setViewClassName
		// Because we will probably move the code...

		applicationModel.setViewClass(JSeshView.class);

		Application app;
		if (System.getProperty("os.name").toLowerCase().startsWith("mac os x")) {
			app = new QenherOSXApplication();
			//app= new QenherOSXLikeApplication();
		} else if (System.getProperty("os.name").toLowerCase()
				.startsWith("win")) {
			// app = new QenherOSXApplication();
			app = new QenherOSXLikeApplication();
		} else {
			app = new QenherOSXLikeApplication();
			// app= new QenherOSXApplication();
		}
		app.setModel(applicationModel);
		app.launch(args);
	}

	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {
		JSeshMain jseshMain = new JSeshMain();
		jseshMain.args = args;
		jseshMain.setSplashPicture("/jseshResources/images/splash.png");
		SplashMessageText message = new SplashMessageText(50, 172, "Version "
				+ Version.getVersion());
		message.setColor(Color.WHITE);
		jseshMain.addSplashMessage(message);
		jseshMain.setTickTimer();
		jseshMain.run();
	}

}
