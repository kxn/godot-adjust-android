package org.comman.godot.plugin.adjust;

import com.adjust.sdk.AdjustAttribution;

import org.godotengine.godot.Dictionary;

public class GodotAdjustUtil {
    static Dictionary adjustAttributonToDictionary(AdjustAttribution attribution) {
        Dictionary ret = new Dictionary();
        ret.put("trackerToken", attribution.trackerToken);
        ret.put("trackerName", attribution.trackerName);
        ret.put("network", attribution.network);
        ret.put("campaign", attribution.campaign);
        ret.put("adgroup", attribution.adgroup);
        ret.put("creative", attribution.creative);
        ret.put("clickLabel", attribution.clickLabel);
        ret.put("adid", attribution.adid);
        ret.put("costType", attribution.costType);
        ret.put("costAmount", attribution.costAmount);
        ret.put("costCurrency", attribution.costCurrency);
        ret.put("fbInstallReferrer", attribution.fbInstallReferrer);
        return ret;
    }
}
