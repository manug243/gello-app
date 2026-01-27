@file:OptIn(InternalResourceApi::class)

package gello.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/gello.composeapp.generated.resources/"

internal val Res.drawable.appicon: DrawableResource by lazy {
      DrawableResource("drawable:appicon", setOf(
        ResourceItem(setOf(), "${MD}drawable/appicon.png", -1, -1),
      ))
    }

internal val Res.drawable.compose_multiplatform: DrawableResource by lazy {
      DrawableResource("drawable:compose_multiplatform", setOf(
        ResourceItem(setOf(), "${MD}drawable/compose-multiplatform.xml", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("appicon", Res.drawable.appicon)
  map.put("compose_multiplatform", Res.drawable.compose_multiplatform)
}
