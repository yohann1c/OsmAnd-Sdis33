package net.osmand.plus.views.mapwidgets.configure.buttons;

import static net.osmand.aidlapi.OsmAndCustomizationConstants.MAP_3D_HUD_ID;
import static net.osmand.plus.quickaction.ButtonAppearanceParams.BIG_SIZE_DP;
import static net.osmand.plus.quickaction.ButtonAppearanceParams.ROUND_RADIUS_DP;
import static net.osmand.plus.quickaction.ButtonAppearanceParams.TRANSPARENT_ALPHA;
import static net.osmand.plus.settings.enums.Map3DModeVisibility.HIDDEN;
import static net.osmand.plus.settings.enums.Map3DModeVisibility.VISIBLE;
import static net.osmand.plus.views.OsmandMapTileView.DEFAULT_ELEVATION_ANGLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.osmand.plus.OsmandApplication;
import net.osmand.plus.R;
import net.osmand.plus.quickaction.ButtonAppearanceParams;
import net.osmand.plus.settings.backend.ApplicationMode;
import net.osmand.plus.settings.backend.preferences.CommonPreference;
import net.osmand.plus.settings.enums.Map3DModeVisibility;
import net.osmand.plus.views.controls.maphudbuttons.ButtonPositionSize;

public class Map3DButtonState extends MapButtonState {

	private final CommonPreference<Long> positionPref;
	private final CommonPreference<Map3DModeVisibility> visibilityPref;

	private float elevationAngle = DEFAULT_ELEVATION_ANGLE;


	public Map3DButtonState(@NonNull OsmandApplication app) {
		super(app, MAP_3D_HUD_ID);

		this.visibilityPref = addPreference(settings.registerEnumStringPreference("map_3d_mode_visibility", VISIBLE, Map3DModeVisibility.values(), Map3DModeVisibility.class)).makeProfile().cache();
		this.positionPref = addPreference(settings.registerLongPreference(id + "_position", -1)).makeProfile().cache();
	}

	@NonNull
	@Override
	public String getName() {
		return app.getString(R.string.map_3d_mode_action);
	}

	@NonNull
	@Override
	public String getDescription() {
		return app.getString(R.string.map_3d_mode_action_descr);
	}

	@Override
	public int getDefaultLayoutId() {
		return R.layout.map_3d_button;
	}

	@Override
	public boolean isEnabled() {
		return getVisibility() != HIDDEN;
	}

	public float getElevationAngle() {
		return elevationAngle;
	}

	public void setElevationAngle(float angle) {
		elevationAngle = angle;
	}

	@NonNull
	public Map3DModeVisibility getVisibility() {
		return visibilityPref.get();
	}

	@NonNull
	public Map3DModeVisibility getVisibility(@NonNull ApplicationMode mode) {
		return visibilityPref.getModeValue(mode);
	}

	@NonNull
	@Override
	public CommonPreference<Map3DModeVisibility> getVisibilityPref() {
		return visibilityPref;
	}

	@Nullable
	@Override
	public CommonPreference<Long> getPositionPref() {
		return positionPref;
	}

	@NonNull
	@Override
	public ButtonAppearanceParams createDefaultAppearanceParams() {
		String iconName = isFlatMapMode() ? "ic_action_3d" : "ic_action_2d";
		return new ButtonAppearanceParams(iconName, BIG_SIZE_DP, TRANSPARENT_ALPHA, ROUND_RADIUS_DP);
	}

	public boolean isFlatMapMode() {
		return app.getOsmandMap().getMapView().getElevationAngle() == DEFAULT_ELEVATION_ANGLE;
	}

	@Nullable
	@Override
	public ButtonPositionSize getButtonPositionSize() {
		ButtonAppearanceParams params = createAppearanceParams();
		int size = (params.getSize() / 8) + 1;
		return new ButtonPositionSize(getId(), size, false, false).setMoveVertical().setMoveHorizontal();
	}
}