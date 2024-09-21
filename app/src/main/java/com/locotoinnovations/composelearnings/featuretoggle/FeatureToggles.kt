package com.locotoinnovations.composelearnings.featuretoggle

object FeatureToggles {

    enum class LaunchDarkly(
       override val key: String,
       override val onForDebugBuilds: Boolean,
       internal val fallbackDefault: Boolean = false,
       internal val includeInToggleList: Boolean = true,
    ): FeatureToggle {
        /**
         * Features
         */
        SAMPLE_FEATURE(key = "sample-feature", onForDebugBuilds = false);

        companion object {
            fun getToggleList(): List<LaunchDarkly> = entries.filter { it.includeInToggleList }
        }

        override fun isEnabled(): Boolean {
            return FeatureToggleManagerInstance.isFeatureToggleEnabled(this)
        }
    }

    enum class Local(
        override val key: String,
        override val onForDebugBuilds: Boolean,
        internal val includeInToggleList: Boolean = true,
    ): FeatureToggle {
        /**
         * Features
         */
        SAMPLE_FEATURE(key = "sample_feature", onForDebugBuilds = false);

        companion object {
            fun getToggleList(): List<Local> = entries.filter { it.includeInToggleList }
        }

        override fun isEnabled(): Boolean {
            return FeatureToggleManagerInstance.isFeatureToggleEnabled(this)
        }
    }
}