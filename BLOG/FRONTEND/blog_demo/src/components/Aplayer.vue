<template>
    <!-- 音乐播放器 -->
    <aplayer
        :volume="0.5" 
        :music="{
            title: music.musicSrcTitle,
            artist: music.musicArtist,
            src: music.musicSrc,
            pic: music.musicCover
        }"
    />
</template>

<script>
import Aplayer from '../../node_modules/vue-aplayer'

export default {
    components: {
        Aplayer
    },
    props: [ 'music' ]
}
</script>

<style>

</style>