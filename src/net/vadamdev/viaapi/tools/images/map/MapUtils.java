package net.vadamdev.viaapi.tools.images.map;

import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapUtils {
    public static void clearMapRenderers(MapView map) {
        for (MapRenderer mapRenderer : map.getRenderers()) map.removeRenderer(mapRenderer);
    }
}
