package org.comman.godot.plugin.adjust;

import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.OnAttributionChangedListener;

public class GodotAdjust extends GodotPlugin implements OnAttributionChangedListener  {

	private boolean pendingResume = false;
	private boolean initAdjustCalled = false;
	public GodotAdjust(Godot godot) {
		super(godot);
	}

	@Override
	public void onMainPause() {
		Adjust.onPause();
	}

	@Override
	public void onMainResume()
	{
		if (!initAdjustCalled) {
			pendingResume = true;
			return;
		}
		Adjust.onResume();
	}

	@UsedByGodot
	public void initAdjust(String appToken, String environment) {
		if (initAdjustCalled) {
			return;
		}
		AdjustConfig config = new AdjustConfig(getActivity(), appToken, environment);
		config.setOnAttributionChangedListener(this);
		Adjust.onCreate(config);
		initAdjustCalled = true;
		if (pendingResume) {
			pendingResume = false;
			Adjust.onResume();
		}
	}


	@UsedByGodot
	public void setOfflineMode(boolean mode) {
		Adjust.setOfflineMode(mode);
	}


	@UsedByGodot
	public Dictionary getAttribution() {
		return GodotAdjustUtil.adjustAttributonToDictionary(Adjust.getAttribution());
	}

	@Override
	public void onAttributionChanged(AdjustAttribution adjustAttribution) {
		emitSignal("attribution_changed", GodotAdjustUtil.adjustAttributonToDictionary(adjustAttribution));
	}

	@UsedByGodot
	public String getAdid() {
		return Adjust.getAdid();
	}


	@UsedByGodot
	public void trackEvent(String eventId, String orderId) {
		AdjustEvent adjustEvent = new AdjustEvent(eventId);
		if (!Objects.equals(orderId, ""))
			adjustEvent.setOrderId(orderId);
		Adjust.trackEvent(adjustEvent);
	}

	@UsedByGodot
	public void trackRevenue(String eventId, double revenue, String currency, String orderId) {
		AdjustEvent adjustEvent = new AdjustEvent(eventId);
		adjustEvent.setRevenue(revenue,currency);
		if (!Objects.equals(orderId, ""))
			adjustEvent.setOrderId(orderId);
		Adjust.trackEvent(adjustEvent);
	}

	// TODO: Event tracking status signals

	@NonNull
	@Override
	public String getPluginName() {
		return "GodotAdjust";
	}

	@NonNull
	@Override
	public List<String> getPluginMethods() {
		return Arrays.asList("initAdjust", "setOfflineMode", "getAttribution", "getAdid");
	}


	@NonNull
	@Override
	public Set<SignalInfo> getPluginSignals() {
		Set<SignalInfo> signals = new ArraySet<>();
		signals.add(new SignalInfo("attribution_changed", Dictionary.class));
		return signals;
	}

}
