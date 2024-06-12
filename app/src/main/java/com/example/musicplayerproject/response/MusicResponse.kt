package com.example.musicplayerproject.response

import com.google.gson.annotations.SerializedName

data class MusicResponse(

    @field:SerializedName("track")
    val track: List<TrackItem?>? = null
)

data class TrackItem(

    @field:SerializedName("intMusicVidFavorites")
    val intMusicVidFavorites: Any? = null,

    @field:SerializedName("strMood")
    val strMood: Any? = null,

    @field:SerializedName("strTrackLyrics")
    val strTrackLyrics: Any? = null,

    @field:SerializedName("intTotalPlays")
    val intTotalPlays: Any? = null,

    @field:SerializedName("strDescriptionCN")
    val strDescriptionCN: Any? = null,

    @field:SerializedName("idTrack")
    val idTrack: String? = null,

    @field:SerializedName("intScore")
    val intScore: Any? = null,

    @field:SerializedName("intLoved")
    val intLoved: String? = null,

    @field:SerializedName("strMusicVidDirector")
    val strMusicVidDirector: Any? = null,

    @field:SerializedName("strMusicBrainzAlbumID")
    val strMusicBrainzAlbumID: String? = null,

    @field:SerializedName("strTrack3DCase")
    val strTrack3DCase: Any? = null,

    @field:SerializedName("strDescriptionSE")
    val strDescriptionSE: Any? = null,

    @field:SerializedName("strDescriptionJP")
    val strDescriptionJP: Any? = null,

    @field:SerializedName("intMusicVidComments")
    val intMusicVidComments: Any? = null,

    @field:SerializedName("strDescriptionFR")
    val strDescriptionFR: Any? = null,

    @field:SerializedName("strMusicVidScreen2")
    val strMusicVidScreen2: Any? = null,

    @field:SerializedName("strMusicVidScreen1")
    val strMusicVidScreen1: Any? = null,

    @field:SerializedName("strDescriptionNL")
    val strDescriptionNL: Any? = null,

    @field:SerializedName("strMusicVidScreen3")
    val strMusicVidScreen3: Any? = null,

    @field:SerializedName("strDescriptionRU")
    val strDescriptionRU: Any? = null,

    @field:SerializedName("strArtistAlternate")
    val strArtistAlternate: Any? = null,

    @field:SerializedName("strDescriptionNO")
    val strDescriptionNO: Any? = null,

    @field:SerializedName("strDescriptionES")
    val strDescriptionES: Any? = null,

    @field:SerializedName("strGenre")
    val strGenre: String? = null,

    @field:SerializedName("strStyle")
    val strStyle: Any? = null,

    @field:SerializedName("strTheme")
    val strTheme: Any? = null,

    @field:SerializedName("intScoreVotes")
    val intScoreVotes: Any? = null,

    @field:SerializedName("strMusicBrainzID")
    val strMusicBrainzID: String? = null,

    @field:SerializedName("strDescriptionIT")
    val strDescriptionIT: Any? = null,

    @field:SerializedName("idAlbum")
    val idAlbum: String? = null,

    @field:SerializedName("strTrack")
    val strTrack: String? = null,

    @field:SerializedName("idIMVDB")
    val idIMVDB: Any? = null,

    @field:SerializedName("strDescriptionEN")
    val strDescriptionEN: Any? = null,

    @field:SerializedName("strMusicVidCompany")
    val strMusicVidCompany: Any? = null,

    @field:SerializedName("strDescriptionIL")
    val strDescriptionIL: Any? = null,

    @field:SerializedName("strMusicBrainzArtistID")
    val strMusicBrainzArtistID: String? = null,

    @field:SerializedName("intMusicVidDislikes")
    val intMusicVidDislikes: Any? = null,

    @field:SerializedName("strTrackThumb")
    val strTrackThumb: Any? = null,

    @field:SerializedName("strLocked")
    val strLocked: String? = null,

    @field:SerializedName("strMusicVid")
    val strMusicVid: Any? = null,

    @field:SerializedName("intMusicVidLikes")
    val intMusicVidLikes: Any? = null,

    @field:SerializedName("intDuration")
    val intDuration: String? = null,

    @field:SerializedName("intMusicVidViews")
    val intMusicVidViews: Any? = null,

    @field:SerializedName("intTotalListeners")
    val intTotalListeners: Any? = null,

    @field:SerializedName("strDescriptionHU")
    val strDescriptionHU: Any? = null,

    @field:SerializedName("intCD")
    val intCD: Any? = null,

    @field:SerializedName("strArtist")
    val strArtist: String? = null,

    @field:SerializedName("idArtist")
    val idArtist: String? = null,

    @field:SerializedName("strDescriptionPT")
    val strDescriptionPT: Any? = null,

    @field:SerializedName("strDescriptionDE")
    val strDescriptionDE: Any? = null,

    @field:SerializedName("strAlbum")
    val strAlbum: String? = null,

    @field:SerializedName("intTrackNumber")
    val intTrackNumber: String? = null,

    @field:SerializedName("strDescriptionPL")
    val strDescriptionPL: Any? = null,

    @field:SerializedName("idLyric")
    val idLyric: String? = null
)
