package org.scanl.plugins.tsdetect.common;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PluginResourceBundle {
	//The locations of all the internationalization
	private static final String BUNDLE_INSPECTION = "I18n.inspection.text";
	private static final String BUNDLE_UI = "I18n.ui.text";

	private static Map<String, Reference<ResourceBundle>> bundles = new HashMap<>();


	private PluginResourceBundle() {
	}


	public static String message(@NotNull Type type, @NotNull String key, @NotNull Object... params) {
		return AbstractBundle.message(getBundleInspection(type), key, params);
	}

	/**
	 * Gets the inspection
	 * @param type Either Inspection or UI, which bundle is being searched for
	 * @return the bundle that is needed
	 */
	private static ResourceBundle getBundleInspection(Type type) {
		ResourceBundle bundle;

		if (type == Type.INSPECTION) {
			if (!bundles.containsKey(BUNDLE_INSPECTION)) {
				bundle = ResourceBundle.getBundle(BUNDLE_INSPECTION);
				bundles.put(BUNDLE_INSPECTION, new SoftReference<>(bundle));
			}
			return bundles.get(BUNDLE_INSPECTION).get();
		} else {
			if (!bundles.containsKey(BUNDLE_UI)) {
				bundle = ResourceBundle.getBundle(BUNDLE_UI);
				bundles.put(BUNDLE_UI, new SoftReference<>(bundle));
			}
			return bundles.get(BUNDLE_UI).get();
		}


	}

	public enum Type {
		INSPECTION,
		UI
	}
}
