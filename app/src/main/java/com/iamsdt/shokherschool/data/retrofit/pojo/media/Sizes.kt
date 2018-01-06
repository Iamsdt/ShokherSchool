package com.iamsdt.shokherschool.data.retrofit.pojo.media

import com.google.gson.annotations.SerializedName

data class Sizes(@SerializedName("trusted-shop-thumbnail")
                 val trustedShopThumbnail: TrustedShopThumbnail?,
                 @SerializedName("trusted-shop-archive")
                 val trustedShopArchive: TrustedShopArchive?,
                 val thumbnail: Thumbnail?,
                 @SerializedName("trusted-shop-single")
                 val trustedShopSingle: TrustedShopSingle?,
                 val medium: Medium?,
                 val full: Full?)