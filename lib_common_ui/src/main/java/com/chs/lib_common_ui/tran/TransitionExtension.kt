/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chs.lib_common_ui.tran

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.transition.platform.Hold
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/** 设置对activity的退出共享元素回调，以实现共享元素转换. */
fun Activity.onTransformationStartContainer() {
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
  setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
  window.sharedElementsUseOverlay = false
}

/** 为活动设置一个enter共享元素回调，以实现共享元素转换。 */
fun Activity.onTransformationEndContainer(
  params: TransformationLayout.Params?
) {
  requireNotNull(
    params) { "TransformationLayout。参数不能为空。检查意图键值是否正确." }
  window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
  ViewCompat.setTransitionName(findViewById<View>(android.R.id.content), params.transitionName)
  setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
  window.sharedElementEnterTransition = params.getMaterialContainerTransform()
  window.sharedElementReturnTransition = params.getMaterialContainerTransform()
}

/** 将退出共享元素回调设置为fragment，以实现共享元素转换. */
fun Fragment.onTransformationStartContainer() {
  exitTransition = Hold()
}

/** 将一个enter共享元素回调设置为fragment，以实现共享元素转换. */
fun Fragment.onTransformationEndContainer(
  params: TransformationLayout.Params?
) {
  requireNotNull(
    params) { "TransformationLayout。参数不能为空。检查意图键值是否正确." }
  sharedElementEnterTransition = params.getMaterialFragmentTransform()
}

/** 向FragmentTransaction添加共享元素转换. */
fun FragmentTransaction.addTransformation(
  transformationLayout: TransformationLayout,
  transitionName: String? = null
): FragmentTransaction {
  if (transitionName != null && transformationLayout.transitionName == null) {
    ViewCompat.setTransitionName(transformationLayout, transitionName)
  }
  addSharedElement(transformationLayout, transformationLayout.transitionName)
  return this
}
