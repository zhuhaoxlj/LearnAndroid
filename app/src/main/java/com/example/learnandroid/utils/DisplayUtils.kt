/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.learnandroid.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun getDisplayMetrics() = LocalContext.current.resources.displayMetrics

// Float 扩展
@Composable
fun Float.dpToPx(): Int = (this * getDisplayMetrics().density + 0.5f).toInt()
@Composable
fun Float.pxToDp(): Dp = (this / getDisplayMetrics().density + 0.5f).dp
@Composable
fun Float.spToPx(): Int = (this * getDisplayMetrics().scaledDensity + 0.5f).toInt()
@Composable
fun Float.pxToSp(): Int = (this / getDisplayMetrics().scaledDensity + 0.5f).toInt()

// Int 扩展（可选）
@Composable
fun Int.dpToPx(): Int = toFloat().dpToPx()
@Composable
fun Int.pxToDp(): Dp = toFloat().pxToDp()
@Composable
fun Int.spToPx(): Int = toFloat().spToPx()
@Composable
fun Int.pxToSp(): Int = toFloat().pxToSp()
