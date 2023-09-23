extends Node

var adjust_instance

const SANDBOX = "sandbox"
const PRODUCTION = "production"

func _ready():
	if Engine.has_singleton("GodotAdjust"):
		print("find adjust plugin")
		adjust_instance = Engine.get_singleton("GodotAdjust")
		adjust_instance.initAdjust("{MyAppAdjustId}", SANDBOX)
		adjust_instance.connect("attribution_changed", on_attribution_changed)
		# pass a string for event dedup for second parameter, empty string if no need to dedup
		adjust_instance.trackEvent("{MyAdjustEvent}", "")
		adjust_instance.trackRevenue("{MyAdjustEvent}", 0.1, "USD", "")
	else:
		print("No godot adjust exists")	

func on_attribution_changed(att:Dictionary):
	print(att)

