@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package com.swe.studyplanner.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi

private object CommonDrawable0 {
  public val compose_multiplatform: DrawableResource by 
      lazy { init_compose_multiplatform() }
}

@InternalResourceApi
internal fun _collectCommonDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("compose_multiplatform", CommonDrawable0.compose_multiplatform)
}

public val Res.drawable.compose_multiplatform: DrawableResource
  get() = CommonDrawable0.compose_multiplatform

private fun init_compose_multiplatform(): DrawableResource = org.jetbrains.compose.resources.DrawableResource(
  "drawable:compose_multiplatform",
    setOf(
      org.jetbrains.compose.resources.ResourceItem(setOf(), "composeResources/com.swe.studyplanner.resources/drawable/compose-multiplatform.xml", -1, -1),
    )
)
