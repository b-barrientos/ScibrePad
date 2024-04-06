package com.example.scribepad.utils

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Pixel 5", device = "spec:shape=Normal,width=1080,height=2400,unit=px,dpi=480", showSystemUi = false)
@Preview(name = "Samsung Galaxy S21 Ultra", device = "spec:shape=Normal,width=1440,height=3200,unit=px,dpi=515", showSystemUi = false)
@Preview(name = "Foldable", device = Devices.FOLDABLE, showSystemUi = false)
annotation class DevicePreviews