package com.example.zebracoffee.presentation.coffeeShops.map.clustering

import com.example.zebracoffee.data.modelDto.Schedule
import com.example.zebracoffee.data.modelDto.ShopImage
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ClusteringData(
    val itemPosition: LatLng,
    val itemTitle: String,
    val itemSnippet: String,
    val itemZIndex: Float,
    val address: String,
    val blocked: Boolean,
    val card_account_id: Int,
    val cash_account_id: Int,
    val city: String,
    val contacts: String,
    val cover: String,
    val distance: Double,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val open_from: String,
    val open_until: String,
    val pager_active: Boolean,
    val rating: Double,
    val schedule: Schedule,
    val shop_images: List<ShopImage>,
    val tis_token: String,
    val warehouse_address: String,
    val warehouse_name: String,
    val two_gis_link: String
) : ClusterItem {
    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet

    fun getZIndex(): Float =
        itemZIndex
}