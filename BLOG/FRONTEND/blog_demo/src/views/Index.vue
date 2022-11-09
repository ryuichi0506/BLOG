<template>
    <div id="app" class="container">
        <!-- <div class="title">基于SSM的多人博客系统</div> -->
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    
}
</script>

<style lang="less" scoped>
    .container{
        height: 100%;
        // background-image: url('../assets/images/wallpaper_42.jpg');
        background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_42.jpg');
        background-size: cover;
        background-repeat: no-repeat;
    }

    // .title{
    //     display: flex;
    //     justify-content: center;
    //     position: absolute;
    //     top: 10%;
    //     width: 100%;
    //     font-size: 30px;
    //     font-family: YouYuan;
    //     color: #ffffff;
    //     text-shadow: 2px 2px 5px #ffffff;
    // }
</style>
