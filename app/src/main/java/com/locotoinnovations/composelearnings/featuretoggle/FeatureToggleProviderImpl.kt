package com.locotoinnovations.composelearnings.featuretoggle

class FeatureToggleProviderImpl(
    private val launchDarklyFeatureToggleProvider: LaunchDarklyFeatureToggleProvider,
): FeatureToggleProvider {

    override fun isFeatureToggleEnabled(featureToggle: FeatureToggle): Boolean {
        return when(featureToggle) {
            is FeatureToggles.LaunchDarkly -> isLaunchDarklyFeatureEnabled(featureToggle)
            is FeatureToggles.Local -> isLocalFeatureEnabled(featureToggle)
            else -> false // Should not hit this
        }
    }

    /**
     * Checks if a [Local] feature toggle is enabled.
     *
     * If we're on a debug build and have an overridden value or [FeatureToggle.onForDebugBuilds] is true, return value
     * Else return false
     */
    private fun isLocalFeatureEnabled(featureToggle: FeatureToggles.Local): Boolean {
        return getDebugEnabled(featureToggle) ?: false
    }

    /**
     * Checks if a [LaunchDarkly] feature toggle is enabled.
     *
     * If we're on a debug build and have an overridden value or [FeatureToggle.onForDebugBuilds] is true, return value
     * Else return what LD has with a fallback of the [LaunchDarkly.fallbackDefault] value
     */
    private fun isLaunchDarklyFeatureEnabled(featureToggle: FeatureToggles.LaunchDarkly): Boolean {
        return launchDarklyFeatureToggleProvider.isEnabled(featureToggle.key, featureToggle.fallbackDefault)
    }

    /**
     * Returns true if the feature toggle is enabled on debug builds or null to fallback on other checks
     *
     * If release builds, return null
     * Else if the [FeatureToggle] is overridden locally to be on or off, return the overridden value
     * Else if [FeatureToggle.onForDebugBuilds] is enabled, return true
     * Else return null
     */
    private fun getDebugEnabled(featureToggle: FeatureToggle): Boolean? {
        return featureToggle.onForDebugBuilds
    }
}