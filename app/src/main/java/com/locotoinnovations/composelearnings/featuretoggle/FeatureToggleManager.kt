package com.locotoinnovations.composelearnings.featuretoggle

object FeatureToggleManagerInstance {

    private lateinit var featureToggleProvider: FeatureToggleProvider

    fun init(featureToggleProvider: FeatureToggleProvider) {
        this.featureToggleProvider = featureToggleProvider
    }

    fun isFeatureToggleEnabled(featureToggle: FeatureToggle): Boolean {
        return featureToggleProvider.isFeatureToggleEnabled(featureToggle)
    }
}

/**
 * Interface that defines the methods that a Feature Toggle provider must implement
 */
interface FeatureToggleProvider {
    /**
     * Returns where or not a feature toggle is enabled.
     */
    fun isFeatureToggleEnabled(featureToggle: FeatureToggle): Boolean
}

data class FeatureToggleUser(
    val userServerId: String,
    val emailAddress: String,
    val name: String?,
)

/**
 * Interface that defines the methods
 * that a Feature Toggle must implement
 */
interface FeatureToggle {
    /**
     * Key value for the feature toggle
     */
    val key: String

    /**
     * Default value to enable/disable the toggle on debug builds
     */
    val onForDebugBuilds: Boolean

    /**
     * Returns whether the feature toggle is enabled or not
     */
    fun isEnabled(): Boolean
}