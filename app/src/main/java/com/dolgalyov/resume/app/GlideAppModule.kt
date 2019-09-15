package com.dolgalyov.resume.app

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun isManifestParsingEnabled() = false
}