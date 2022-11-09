<template>
    <div class="container">
        <!-- 头部 -->
        <my-header></my-header>

        <!-- 主体 -->
        <main class="main">
            <!-- 轮播图 -->
            <div class="carousel">
                <el-carousel :interval="5000" type="card" height="350px" trigger="click">
                    <el-carousel-item v-for="(item,index) in carousels" :key="index">
                        <div v-if="item.studyId">
                            <div class="carousel-title">{{item.studyTitle}}</div>
                            <img class="carousel-pic" @click="toStudyDetail(item.studyId)" draggable="false" :src="item.studyCover"/>
                        </div>
                        <div v-else-if="item.animeId">
                            <div class="carousel-title">{{item.animeTitle}}</div>
                            <img class="carousel-pic" @click="toAnimeDetail(item.animeId)" draggable="false" :src="item.animeCover"/>
                        </div>
                    </el-carousel-item>
                </el-carousel>
            </div>

            <!-- 留言板 -->
            <div class="message">
                <el-row class="message-background"></el-row>
                <el-row class="message-body">
                    <div v-if="messageItem" class="message-container">
                            <font-awesome-icon :icon="['fas', 'quote-left']" style="color: white; float: left;"></font-awesome-icon><br>
                            <p class="m-message">{{messageItem.messageContent}}</p>
                            <font-awesome-icon :icon="['fas', 'quote-right']" style="color: white; float: right;"></font-awesome-icon>
                            <div class="m-message-author-container">
                                <router-link :to="/space/ + messageItem.user.userId" target="_blank">
                                    <img draggable="false" :src="messageItem.user.userAvatar">
                                    <p>{{messageItem.user.userNickname}}</p>
                                </router-link>
                            </div>
                            
                            <p class="m-message-subtime">
                                {{messageItem.messageCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}
                            </p>
                        </div>
                        <div v-else class="no-content">
                            没有最新留言
                        </div>
                </el-row>
                <!-- <el-row style="padding: 10px 10%;">
                    <el-col :span="13">
                        <img draggable="false" class="message-board" src="../assets/images/onepiece.jpg">
                    </el-col>
                    <el-col :span="11">
                        <div v-if="messageItem" class="message-container">
                            <font-awesome-icon :icon="['fas', 'quote-left']" style="color: darkgray; float: left;"></font-awesome-icon><br>
                            <p class="m-message">{{messageItem.messageContent}}</p>
                            <font-awesome-icon :icon="['fas', 'quote-right']" style="color: darkgray; float: right;"></font-awesome-icon>
                            <div class="m-message-author-container">
                                <router-link :to="/space/ + messageItem.user.userId" target="_blank">
                                    <img draggable="false" :src="messageItem.user.userAvatar">
                                    <p>{{messageItem.user.userNickname}}</p>
                                </router-link>
                            </div>
                            
                            <p class="m-message-subtime">
                                {{messageItem.messageCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}
                            </p>
                        </div>
                        <div v-else class="no-content">
                            没有最新留言
                        </div>
                    </el-col>
                </el-row> -->
            </div>

            <!-- 学习区 -->
            <div class="study">
                <el-row class="study-title">
                    <p>学习</p>
                    <!-- <el-divider><span class="type-title">学&nbsp;习</span></el-divider> -->
                </el-row>

                <el-row :gutter="20" style="padding: 10px 10%">
                    <el-col :span="6" v-for="study in studyItems" :key="study.studyId">
                        <el-card class="study-item" shadow="hover">
                            <el-image lazy fit="cover" draggable="false" @click="toStudyDetail(study.studyId)" class="cover" :src="study.studyCover" />
                            <p class="title" @click="toStudyDetail(study.studyId)" :title="study.studyTitle">{{study.studyTitle}}</p>
                            <p class="description">{{study.studyDescription}}</p>
                            <p class="tag">
                                <el-tag size="small" type="danger" effect="dark" v-for="tag in study.tags.split(',')" :key="tag">{{tag}}</el-tag>
                            </p>
                            <p class="info">
                                <el-row>
                                    <el-col :span="14" class="avatar">
                                        <router-link :to="/space/ + study.user.userId" target="_blank">
                                            <img :src="study.user.userAvatar">
                                            <div>
                                                <p :title="study.user.userNickname">{{study.user.userNickname}}</p>
                                                <p>{{study.studyCreatetime | formatDate('yyyy-MM-dd')}}</p>
                                            </div>
                                        </router-link>
                                    </el-col>
                                    <el-col :span="5" class="views">
                                        <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{study.studyViews}}</span>
                                    </el-col>
                                    <el-col :span="5" class="comments">
                                        <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{study.studyComments}}</span>
                                    </el-col>
                                </el-row>
                            </p>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row style="padding: 0 10%;">
                    <div class="more-container">
                        <router-link to="/study" target="_blank" class="more" style="background-color: #f56c6c">
                            查看更多
                            <font-awesome-icon class="icon" :icon="['fas','book-reader']"></font-awesome-icon>
                        </router-link>
                    </div>
                </el-row>
            </div>

            <!-- 动漫区 -->
            <div class="anime">
                <el-row class="anime-title">
                    <p>动漫</p>
                    <!-- <el-divider><span class="type-title">动&nbsp;漫</span></el-divider> -->
                </el-row>

                <el-row :gutter="20" style="padding: 10px 10%">
                    <el-col :span="6" v-for="anime in anineItems" :key="anime.animeId">
                        <el-card class="anime-item" body-style="padding: 15px" shadow="hover">
                            <el-image lazy fit="cover" draggable="false" @click="toAnimeDetail(anime.animeId)" class="cover" :src="anime.animeCover" />
                            <p class="title" @click="toAnimeDetail(anime.animeId)" :title="anime.animeTitle">{{anime.animeTitle}}</p>
                            <p class="description">{{anime.animeDescription}}</p>
                            <p class="tag">
                                <el-tag size="small" type="warning" effect="dark" v-for="tag in anime.tags.split(',')" :key="tag">{{tag}}</el-tag>
                            </p>
                            <p class="info">
                                <el-row>
                                    <el-col :span="14" class="avatar">
                                        <router-link :to="/space/ + anime.user.userId" target="_blank">
                                            <img :src="anime.user.userAvatar">
                                            <div>
                                                <p :title="anime.user.userNickname">{{anime.user.userNickname}}</p>
                                                <p>{{anime.animeCreatetime | formatDate('yyyy-MM-dd')}}</p>
                                            </div>
                                        </router-link>
                                    </el-col>
                                    <el-col :span="5" class="views">
                                        <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{anime.animeViews}}</span>
                                    </el-col>
                                    <el-col :span="5" class="comments">
                                        <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{anime.animeComments}}</span>
                                    </el-col>
                                </el-row>
                            </p>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row style="padding: 0 10%;">
                    <div class="more-container">
                        <router-link to="/anime" target="_blank" class="more" style="background-color: #e6a23c">
                            查看更多
                            <font-awesome-icon class="icon" :icon="['fas','ghost']"></font-awesome-icon>
                        </router-link>
                    </div>
                </el-row>
            </div>

            <!-- 生活区 -->
            <div class="life">
                <el-row class="life-title">
                    <p>生活</p>
                    <!-- <el-divider><span class="type-title">生&nbsp;活</span></el-divider> -->
                </el-row>
                
                <el-row :gutter="20" style="padding: 10px 10%">
                    <el-col :span="8" v-for="life in lifeItems" :key="life.lifeId">
                        <el-card class="life-item" body-style="padding: 15px" shadow="hover">
                            <el-image lazy fit="cover" draggable="false" @click="toLifeDetail(life.lifeId)" class="cover" :src="life.lifeCover"/>
                            <p class="title" @click="toLifeDetail(life.lifeId)" :title="life.lifeTitle">{{life.lifeTitle}}</p>
                            <p class="tag">
                                <el-tag size="small" type="success" effect="dark" v-for="tag in life.tags.split(',')" :key="tag">{{tag}}</el-tag>
                            </p>
                            <p class="info">
                                <el-row>
                                    <el-col :span="16" class="avatar">
                                        <router-link :to="/space/ + life.user.userId" target="_blank">
                                            <img :src="life.user.userAvatar">
                                            <div>
                                                <p>{{life.user.userNickname}}</p>
                                                <p>{{life.lifeCreatetime | formatDate('yyyy-MM-dd')}}</p>
                                            </div>
                                        </router-link>
                                    </el-col>
                                    <el-col :span="4" class="views">
                                        <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{life.lifeViews}}</span>
                                    </el-col>
                                    <el-col :span="4" class="comments">
                                        <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{life.lifeComments}}</span>
                                    </el-col>
                                </el-row>
                            </p>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row style="padding: 0 10%;">
                    <div class="more-container">
                        <router-link to="/life" target="_blank" class="more" style="background-color: #67c23a">
                            查看更多
                            <font-awesome-icon class="icon" :icon="['fas','coffee']"></font-awesome-icon>
                        </router-link>
                    </div>
                </el-row>
            </div>

            <!-- 音乐区 -->
            <div class="music">
                <el-row class="music-title">
                    <p>音乐</p>
                    <!-- <el-divider><span class="type-title">音&nbsp;乐</span></el-divider> -->
                </el-row>

                <el-row :gutter="20" style="padding: 10px 10%">
                    <el-col :span="8" v-for="music in musicItems" :key="music.musicId">
                        <el-card class="music-item" body-style="padding: 20px" shadow="hover">
                            <p class="title" @click="toMusicDetail(music.musicId)" :title="music.musicTitle">{{music.musicTitle}}</p>
                            <!-- 音乐播放器 -->
                            <my-aplayer :music="music"></my-aplayer>


                            <p class="description">{{music.musicDescription}}</p>
                            <p class="tag">
                                <el-tag size="small" effect="dark" v-for="tag in music.tags.split(',')" :key="tag">{{tag}}</el-tag>
                            </p>
                            <p class="info">
                                <el-row>
                                    <el-col :span="16" class="avatar">
                                        <router-link :to="/space/ + music.user.userId" target="_blank">
                                            <img :src="music.user.userAvatar">
                                            <div>
                                                <p>{{music.user.userNickname}}</p>
                                                <p>{{music.musicCreatetime | formatDate('yyyy-MM-dd')}}</p>
                                            </div>
                                        </router-link>
                                    </el-col>
                                    <el-col :span="4" class="views">
                                        <span><font-awesome-icon :icon="['fas', 'headphones']"></font-awesome-icon>{{music.musicView}}</span>
                                    </el-col>
                                    <el-col :span="4" class="comments">
                                        <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{music.musicComments}}</span>
                                    </el-col>
                                </el-row>
                            </p>
                        </el-card>
                    </el-col>
                </el-row>

                <el-row style="padding: 0 10%;">
                    <div class="more-container">
                        <router-link to="/music" target="_blank" class="more" style="background-color: #409eff">
                            查看更多
                            <font-awesome-icon class="icon" :icon="['fas','music']"></font-awesome-icon>
                        </router-link>
                    </div>
                </el-row>
            </div>
        </main>

        <!-- 底部 -->
        <my-footer></my-footer>
        
        
        
        <el-backtop :right="50" :bottom="50">
            <font-awesome-icon :icon="['fas', 'chevron-up']"></font-awesome-icon>
        </el-backtop>
        <el-backtop :right="50" :bottom="50">
            <font-awesome-icon :icon="['fas', 'chevron-up']"></font-awesome-icon>
        </el-backtop>
    </div>
</template>

<script>
export default {
    data () {
        return {
            carousels: [],
            messageItem: {
                user: {}
            },
            studyItems: [],
            anineItems: [],
            lifeItems: [],
            musicItems: [],
        }
    },
    created() {
        this.getTopStudy();
        this.getTopAnime();
        this.getNewMessage();
        this.getStudy();
        this.getAnime();
        this.getLife();
        this.getMusic();
    },
    methods: {
        getTopStudy() {
            let this_ = this;
            this.$http.get(
                '/getTopStudy'
            ).then(
                function (res) {
                    console.log("学习置顶 res =");
                    console.log(res);
                    if(res.data.data.length > 0){
                        for(let i = 0; i < res.data.data.length; i++){
                            this_.carousels.push(res.data.data[i]);
                        }
                    }
                    console.log('轮播图:');
                    console.log(this_.carousels);
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getTopAnime() {
            let this_ = this;
            this.$http.get(
                '/getTopAnime'
            ).then(
                function (res) {
                    console.log("动漫置顶 res =");
                    console.log(res);
                    if(res.data.data.length > 0){
                        for(let i = 0; i < res.data.data.length; i++){
                            this_.carousels.push(res.data.data[i]);
                        }
                    }
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getNewMessage() {
            let this_ = this;
            this.$http.get(
                '/getNewMessage'
            ).then(
                function (res) {
                    console.log("最新留言 res =");
                    console.log(res);
                    this_.messageItem = res.data.data
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getStudy() {
            let this_ = this;
            this.$http.get(
                '/getStudy'
            ).then(
                function (res) {
                    console.log("学习 res =");
                    console.log(res);
                    this_.studyItems = res.data.data
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getAnime() {
            let this_ = this;
            this.$http.get(
                '/getAnime'
            ).then(
                function (res) {
                    console.log("动漫 res =");
                    console.log(res);
                    this_.anineItems = res.data.data
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getLife() {
            let this_ = this;
            this.$http.get(
                '/getLife'
            ).then(
                function (res) {
                    console.log("生活 res =");
                    console.log(res);
                    this_.lifeItems = res.data.data
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getMusic() {
            let this_ = this;
            this.$http.get(
                '/getMusic'
            ).then(
                function (res) {
                    console.log("音乐 res =");
                    console.log(res);
                    this_.musicItems = res.data.data
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        toStudyDetail(studyId) {
            let routes = this.$router.resolve({ path: '/study/'+ studyId });
            window.open(routes.href, '_blank');
        },
        toAnimeDetail(animeId) {
            let routes = this.$router.resolve({ path: '/anime/'+ animeId });
            window.open(routes.href, '_blank');
        },
        toLifeDetail(lifeId) {
            let routes = this.$router.resolve({ path: '/life/'+ lifeId });
            window.open(routes.href, '_blank');
        },
        toMusicDetail(musicId) {
            let routes = this.$router.resolve({ path: '/music/'+ musicId });
            window.open(routes.href, '_blank');
        },

    }
}
</script>

<style lang="less" scoped>
.carousel{
    padding: 15px 0;
    background-color: rgb(250, 250, 250);
}
.carousel-pic{
    width: 100%;
    height: 400px;
    object-fit: cover;
    border-radius: 3px;
    overflow: hidden;
}
.carousel-title{
    position: absolute;
    color: #fff;
    text-shadow: 0 1px 5px rgb(0,0,0);
    width: 80%;
    left: 50%;
    transform: translate(-50%);
    text-align: center;
    bottom: 0;
    margin: 25px 0;
    font-size: 19px;
    font-family: "plantc", "Source Han Serif", serif;
    font-weight: bold;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

// 留言板
.message{
    height: 250px;
    user-select: none;
    background-color: rgb(252, 252, 252);
    display: flex;
    align-items: center;
    .el-row{
        width: 100%;
    }
}

// 新添加
.message .message-background{
    height: 100%;
    // background-image: url('../assets/images/wallpaper_69.jpg');
    background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_69.jpg');
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: center center;
    filter: grayscale(25%);
}
.message .message-body{
    position: absolute;
    padding: 0 30%;
    color: white;
}
// 新添加

.message .message-board{
    border-radius: 4px;
    overflow: hidden;
    margin: 0 20px;
    object-fit: cover;
}
.message .message-container{
    height: 210px;
    margin: 0;
    padding: 20px;
}
.message .no-content{
    height: 250px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 16px;
    color: white;
}
.m-message{
    height: 100px;
    color: white;
    font-family: "plantc", "Source Han Serif", serif;
    text-shadow: 0 1px 5px rgb(0,0,0);
    font-weight: bold;
    font-size: 22px;
    line-height: 1.5;
    // font-family: 微软雅黑 LIGHT;
    padding: 0 20px;
    margin: 0;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
    -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
    -webkit-line-clamp: 3;  /*文本超出显示几行*/
}
.m-message-author-container{
    width: 100%;
    height: 40px;
    line-height: 40px;
    margin: 30px 0 0;
    display: flex;
    justify-content: flex-end;
    a{
        width: fit-content;
        display: flex;
        align-self: center;
        flex-direction: row;
        color: white;
        font-family: "plantc", "Source Han Serif", serif;
        text-shadow: 0 1px 5px rgb(0,0,0);
        img{
            height: 40px;
            width: 40px;
            border-radius: 50%;
            margin: 0 10px 0 0;
        }
        p{
            font-size: 15px;
            margin: 0;
            font-weight: bold;
        }
    }
}
.m-message-subtime{
    float: right;
    color: white;
    font-size: 14px;
    letter-spacing: 1px;
    font-family: "plantc", "Source Han Serif", serif;
    text-shadow: 0 1px 5px rgb(0,0,0);
    font-weight: bold;
    padding: 10px 0;
    margin: 0;
}

// 学习
.study{
    padding: 20px 0;
    overflow: hidden;
}
.study .study-title{
    margin: 0 10%;
    height: 200px;
    // background-image: url('../assets/images/wallpaper_03.jpg');
    background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_03.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    background-position: center center;
    background-attachment: fixed;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    p{
        color: white;
        font-size: 50px;
        font-weight: 600;
        font-family: "plantc", "Source Han Serif", serif;
    }
}
.study .study-item{
    margin: 10px 0;
}
.study .study-item .cover{
    width: 100%;
    height: 145px;
    border-radius: 3px;
    object-fit: cover;
    cursor: pointer;
}
.study .study-item .title{
    height: 30px;
    font-size: 18px;
    margin: 10px 0;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;
    color: rgb(30, 30, 30);
    transition: color 0.2s linear;
}
.study .study-item .title:hover{
    color: rgb(150, 150, 150);
}
.study .study-item .description{
    height: 25px;
    font-size: 13px;
    margin: 10px 0;
    line-height: 1.5;
    color: rgb(120, 120, 120);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.study .study-item .tag{
    height: 24px;
    margin: 10px 0;
    span{
        margin-right: 10px;
    }
}
.study .study-item .info{
    height: 36px;
    line-height: 36px;
    color: #707070;
    user-select: none;
    margin-bottom: 0;
    .avatar{
        a{
            width: fit-content;
            display: flex;
            color: rgb(30, 30, 30);
            img{
                height: 36px;
                width: 36px;
                border-radius: 50%;
                float: left;
            }
            div{
                float: right;
                p{
                    margin: 0;
                    font-size: 13px;
                    height: 18px;
                    line-height: 18px;
                    display: flex;
                    padding-left: 10px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
                    -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
                    -webkit-line-clamp: 1;  /*文本超出显示几行*/
                }
            }
        }
        a:hover{
            color: rgb(100, 100, 100);
        }
    }
    .views{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    .comments{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    
}


// 动漫
.anime{
    padding: 20px 0;
    overflow: hidden;
}
.anime .anime-title{
    margin: 0 10%;
    height: 200px;
    // background-image: url('../assets/images/wallpaper_04.jpg');
    background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_04.jpg');
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: center center;
    background-attachment: fixed;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    p{
        color: white;
        font-size: 50px;
        font-weight: 600;
        font-family: "plantc", "Source Han Serif", serif;
    }
}
.anime .anime-item{
    margin: 10px 0;
}
.anime .anime-item .cover{
    width: 100%;
    height: 145px;
    border-radius: 3px;
    object-fit: cover;
    cursor: pointer;
}
.anime .anime-item .title{
    height: 25px;
    font-size: 18px;
    margin: 10px 0;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;
    color: rgb(30, 30, 30);
    transition: color 0.2s linear;
}
.anime .anime-item .title:hover{
    color: rgb(150, 150, 150);
}
.anime .anime-item .description{
    height: 20px;
    font-size: 13px;
    margin: 10px 0;
    line-height: 1.5;
    color: rgb(120, 120, 120);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.anime .anime-item .tag{
    height: 24px;
    margin: 10px 0;
    span{
        margin-right: 10px;
    }
}
.anime .anime-item .info{
    height: 36px;
    line-height: 36px;
    color: #707070;
    user-select: none;
    margin-bottom: 0;
    .avatar{
        a{
            width: fit-content;
            display: flex;
            color: rgb(30, 30, 30);
            img{
                height: 36px;
                width: 36px;
                border-radius: 50%;
                float: left;
            }
            div{
                float: right;
                p{
                    margin: 0;
                    font-size: 12px;
                    height: 18px;
                    line-height: 18px;
                    padding-left: 10px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
                    -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
                    -webkit-line-clamp: 1;  /*文本超出显示几行*/
                }
            }
        }
        a:hover{
            color: rgb(100, 100, 100);
        }
    }
    .views{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    .comments{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    
}

// 生活
.life{
    padding: 20px 0;
    overflow: hidden;
}
.life .life-title{
    margin: 0 10%;
    height: 200px;
    // background-image: url('../assets/images/wallpaper_05.jpg');
    background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_05.jpg');
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: center center;
    background-attachment: fixed;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    p{
        color: white;
        font-size: 50px;
        font-weight: 600;
        font-family: "plantc", "Source Han Serif", serif;
    }
}
.life .life-item{
    margin: 10px 0;
}
.life .life-item .cover{
    width: 100%;
    height: 200px;
    border-radius: 3px;
    object-fit: cover;
    cursor: pointer;
}
.life .life-item .title{
    height: 25px;
    font-size: 18px;
    margin: 10px 0;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;
    color: rgb(30, 30, 30);
    transition: color 0.2s linear;
}
.life .life-item .title:hover{
    color: rgb(150, 150, 150);
}
.life .life-item .tag{
    height: 24px;
    margin: 10px 0;
    span{
        margin-right: 10px;
    }
}
.life .life-item .info{
    height: 36px;
    line-height: 36px;
    color: #707070;
    user-select: none;
    margin-bottom: 0;
    .avatar{
        a{
            width: fit-content;
            display: flex;
            color: rgb(30, 30, 30);
            img{
                height: 36px;
                width: 36px;
                border-radius: 50%;
                float: left;
            }
            div{
                float: right;
                p{
                    margin: 0;
                    font-size: 12px;
                    height: 18px;
                    line-height: 18px;
                    padding-left: 10px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
                    -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
                    -webkit-line-clamp: 1;  /*文本超出显示几行*/
                }
            }
        }
        a:hover{
            color: rgb(100, 100, 100);
        }
    }
    .views{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    .comments{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    
}


// 音乐
.music{
    padding: 20px 0;
    overflow: hidden;
}
.music .music-title{
    margin: 0 10%;
    height: 200px;
    // background-image: url('../assets/images/wallpaper_73.jpg');
    background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_73.jpg');
    background-repeat: no-repeat;
    background-size: 100%;
    background-position: center center;
    background-attachment: fixed;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
    p{
        color: white;
        font-size: 50px;
        font-weight: 600;
        font-family: "plantc", "Source Han Serif", serif;
    }
}
.music .music-item{
    margin: 10px 0;
}
.music .music-item .title{
    height: 25px;
    margin: 0 0 15px 0;
    font-size: 18px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    cursor: pointer;
    color: rgb(30, 30, 30);
    transition: color 0.2s linear;
}
.music .music-item .title:hover{
    color: rgb(150, 150, 150);
}
.music .music-item .description{
    height: 20px;
    font-size: 13px;
    margin: 10px 0;
    line-height: 1.5;
    color: rgb(120, 120, 120);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.music .music-item .tag{
    height: 24px;
    margin: 10px 0;
    span{
        margin-right: 10px;
    }
}
.music .music-item .info{
    height: 36px;
    line-height: 36px;
    color: #707070;
    user-select: none;
    margin-bottom: 0;
    .avatar{
        a{
            width: fit-content;
            display: flex;
            color: rgb(30, 30, 30);
            img{
                height: 36px;
                width: 36px;
                border-radius: 50%;
                float: left;
            }
            div{
                float: right;
                p{
                    margin: 0;
                    font-size: 12px;
                    height: 18px;
                    line-height: 18px;
                    padding-left: 10px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
                    -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
                    -webkit-line-clamp: 1;  /*文本超出显示几行*/
                }
            }
        }
        a:hover{
            color: rgb(100, 100, 100);
        }
    }
    .views{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    .comments{
        display: flex;
        justify-content: center;
        font-size: 13px;
        font-family: 微软雅黑;
        svg{
            margin-right: 5px;
        }
    }
    
}




// 分类的标题
.type-title{
    color: rgb(100, 100, 100);
    font-size: 30px;
    font-family: youyuan;
}


// 查看更多按钮
.more-container{
    width: 100%;
    display: flex;
    justify-content: center;
}
.more{
    width: 250px;
    padding: 10px 0;
    border: none;
    border-radius: 4px;
    color: #fff;
    text-align: center;
    font-size: 14px;
    letter-spacing: 2px;
    outline: none;
    cursor: pointer;
    transition: opacity 0.5s linear;
}
.more:hover{
    opacity: 0.6;
}


</style>
