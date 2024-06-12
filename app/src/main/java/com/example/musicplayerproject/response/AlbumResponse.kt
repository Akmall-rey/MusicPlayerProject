package com.example.musicplayerproject.response

import com.google.gson.annotations.SerializedName

data class AlbumResponse(

    @field:SerializedName("album")
    val album: List<AlbumItem?>? = null
)

data class AlbumItem(

    @field:SerializedName("strMood")
    val strMood: String? = null,

    @field:SerializedName("strArtistStripped")
    val strArtistStripped: String? = null,

    @field:SerializedName("strDiscogsID")
    val strDiscogsID: String? = null,

    @field:SerializedName("strDescriptionCN")
    val strDescriptionCN: Any? = null,

    @field:SerializedName("strWikipediaID")
    val strWikipediaID: String? = null,

    @field:SerializedName("strAlbumCDart")
    val strAlbumCDart: String? = null,

    @field:SerializedName("strAllMusicID")
    val strAllMusicID: String? = null,

    @field:SerializedName("strAlbumThumbBack")
    val strAlbumThumbBack: String? = null,

    @field:SerializedName("intScore")
    val intScore: String? = null,

    @field:SerializedName("strMusicMozID")
    val strMusicMozID: String? = null,

    @field:SerializedName("strLabel")
    val strLabel: String? = null,

    @field:SerializedName("strAlbum3DThumb")
    val strAlbum3DThumb: String? = null,

    @field:SerializedName("intLoved")
    val intLoved: String? = null,

    @field:SerializedName("strLyricWikiID")
    val strLyricWikiID: Any? = null,

    @field:SerializedName("strAlbum3DFace")
    val strAlbum3DFace: String? = null,

    @field:SerializedName("strLocation")
    val strLocation: Any? = null,

    @field:SerializedName("intSales")
    val intSales: String? = null,

    @field:SerializedName("strDescriptionSE")
    val strDescriptionSE: Any? = null,

    @field:SerializedName("strAlbumThumbHQ")
    val strAlbumThumbHQ: Any? = null,

    @field:SerializedName("strDescriptionJP")
    val strDescriptionJP: Any? = null,

    @field:SerializedName("strAlbumSpine")
    val strAlbumSpine: String? = null,

    @field:SerializedName("strDescriptionFR")
    val strDescriptionFR: String? = null,

    @field:SerializedName("strDescriptionNL")
    val strDescriptionNL: Any? = null,

    @field:SerializedName("strDescriptionRU")
    val strDescriptionRU: Any? = null,

    @field:SerializedName("strDescriptionNO")
    val strDescriptionNO: Any? = null,

    @field:SerializedName("strDescriptionES")
    val strDescriptionES: String? = null,

    @field:SerializedName("strReleaseFormat")
    val strReleaseFormat: String? = null,

    @field:SerializedName("strGenre")
    val strGenre: String? = null,

    @field:SerializedName("strStyle")
    val strStyle: String? = null,

    @field:SerializedName("intScoreVotes")
    val intScoreVotes: String? = null,

    @field:SerializedName("strTheme")
    val strTheme: Any? = null,

    @field:SerializedName("strMusicBrainzID")
    val strMusicBrainzID: String? = null,

    @field:SerializedName("strAlbumThumb")
    val strAlbumThumb: String? = null,

    @field:SerializedName("strDescriptionIT")
    val strDescriptionIT: Any? = null,

    @field:SerializedName("strRateYourMusicID")
    val strRateYourMusicID: String? = null,

    @field:SerializedName("idAlbum")
    val idAlbum: String? = null,

    @field:SerializedName("strAlbum3DCase")
    val strAlbum3DCase: String? = null,

    @field:SerializedName("strDescriptionEN")
    val strDescriptionEN: String? = null,

    @field:SerializedName("strAmazonID")
    val strAmazonID: Any? = null,

    @field:SerializedName("strAlbumStripped")
    val strAlbumStripped: String? = null,

    @field:SerializedName("strDescriptionIL")
    val strDescriptionIL: Any? = null,

    @field:SerializedName("strMusicBrainzArtistID")
    val strMusicBrainzArtistID: String? = null,

    @field:SerializedName("strGeniusID")
    val strGeniusID: Any? = null,

    @field:SerializedName("intYearReleased")
    val intYearReleased: String? = null,

    @field:SerializedName("strLocked")
    val strLocked: String? = null,

    @field:SerializedName("strSpeed")
    val strSpeed: String? = null,

    @field:SerializedName("strDescriptionHU")
    val strDescriptionHU: Any? = null,

    @field:SerializedName("strReview")
    val strReview: String? = null,

    @field:SerializedName("strArtist")
    val strArtist: String? = null,

    @field:SerializedName("idArtist")
    val idArtist: String? = null,

    @field:SerializedName("strWikidataID")
    val strWikidataID: String? = null,

    @field:SerializedName("strItunesID")
    val strItunesID: Any? = null,

    @field:SerializedName("strDescriptionPT")
    val strDescriptionPT: String? = null,

    @field:SerializedName("idLabel")
    val idLabel: String? = null,

    @field:SerializedName("strDescriptionDE")
    val strDescriptionDE: Any? = null,

    @field:SerializedName("strBBCReviewID")
    val strBBCReviewID: String? = null,

    @field:SerializedName("strAlbum")
    val strAlbum: String? = null,

    @field:SerializedName("strAlbum3DFlat")
    val strAlbum3DFlat: String? = null,

    @field:SerializedName("strDescriptionPL")
    val strDescriptionPL: Any? = null
)
