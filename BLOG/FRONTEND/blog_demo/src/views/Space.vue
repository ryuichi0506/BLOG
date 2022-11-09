<template>
    <div class="container">
        <!-- 头部 -->
        <my-header></my-header>

        <!-- 主体 -->
        <main class="main" v-wechat-title="$route.meta.title"> 
            <div v-if="user">
                <!-- 个人信息 -->
                <div class="head">
                    <div class="info">
                        <img draggable="false" class="avatar" :src="user.userAvatar">
                        <div>
                            <p class="nickname">
                                <span>{{user.userNickname}}</span>&nbsp;
                                <font-awesome-icon v-if="user.userGender === '男'" :icon="['fas', 'mars']" style="color: lightblue;"></font-awesome-icon>
                                <font-awesome-icon v-if="user.userGender === '女'" :icon="['fas', 'venus']" style="color: pink;"></font-awesome-icon>
                            </p>
                            <p class="sign" v-if="user.userSignature">
                                {{user.userSignature}}
                            </p>
                            <p class="sign" v-else>
                                还没有个性签名哦(⊙o⊙)
                            </p>
                        </div>
                    </div>
                </div>

                <div class="content">
                    <el-row class="content-body">
                        <el-col class="left" :span="18">
                            <el-tabs v-model="activeName">
                                <el-tab-pane label="学习" name="study">
                                    <div class="content-container">
                                        <div v-if="studyTotal != 0">
                                            <el-row :gutter="20">
                                                <el-col :span="8" v-for="study in userStudy" :key="study.studyId">
                                                    <el-card class="item" shadow="hover" body-style="padding: 10px">
                                                        <el-image fit="cover" @click="toStudyDetail(study.studyId)" draggable="false" class="cover" :src="study.studyCover"/>
                                                        <p @click="toStudyDetail(study.studyId)" class="title">{{study.studyTitle}}</p>
                                                        <p class="description">{{study.studyDescription}}</p>
                                                        <el-row class="info">
                                                            <el-col :span="14">
                                                                <span>{{study.studyCreatetime | formatDate('yyyy-MM-dd')}}</span>
                                                            </el-col>
                                                            <el-col :span="5">
                                                                <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{study.studyViews}}</span>
                                                            </el-col>
                                                            <el-col :span="5">
                                                                <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{study.studyComments}}</span>
                                                            </el-col>
                                                        </el-row>
                                                    </el-card>
                                                </el-col>
                                            </el-row>

                                            <!-- 分页条 -->
                                            <el-pagination class="pagination" @current-change="studyHandleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="studyTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                                        </div>

                                        <div v-else class="no-content">
                                            <p>还没有内容</p>
                                        </div>
                                    </div>
                                </el-tab-pane>

                                <el-tab-pane label="动漫" name="anime">
                                    <div class="content-container">
                                        <div v-if="animeTotal != 0">
                                            <el-row :gutter="20">
                                                <el-col :span="8" v-for="anime in userAnime" :key="anime.animeId">
                                                    <el-card class="item" shadow="hover" body-style="padding: 10px">
                                                        <el-image fit="cover" @click="toAnimeDetail(anime.animeId)" draggable="false" class="cover" :src="anime.animeCover"/>
                                                        <p @click="toAnimeDetail(anime.animeId)" class="title">{{anime.animeTitle}}</p>
                                                        <p class="description">{{anime.animeDescription}}</p>
                                                        <el-row class="info">
                                                            <el-col :span="14">
                                                                <span>{{anime.animeCreatetime | formatDate('yyyy-MM-dd')}}</span>
                                                            </el-col>
                                                            <el-col :span="5">
                                                                <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{anime.animeViews}}</span>
                                                            </el-col>
                                                            <el-col :span="5">
                                                                <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{anime.animeComments}}</span>
                                                            </el-col>
                                                        </el-row>
                                                    </el-card>
                                                </el-col>
                                            </el-row>

                                            <!-- 分页条 -->
                                            <el-pagination class="pagination" @current-change="animeHandleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="animeTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                                        </div>

                                        <div v-else class="no-content">
                                            <p>还没有内容</p>
                                        </div>
                                    </div>
                                </el-tab-pane>

                                <el-tab-pane label="生活" name="life">
                                    <div class="content-container">
                                        <div v-if="lifeTotal != 0">
                                            <el-row :gutter="20">
                                                <el-col :span="8" v-for="life in userLife" :key="life.lifeId">
                                                    <el-card class="item" shadow="hover" body-style="padding: 10px">
                                                        <el-image fit="cover" @click="toLifeDetail(life.lifeId)" draggable="false" class="cover" :src="life.lifeCover"/>
                                                        <p @click="toLifeDetail(life.lifeId)" class="title">{{life.lifeTitle}}</p>
                                                        <el-row class="info">
                                                            <el-col :span="14">
                                                                <span>{{life.lifeCreatetime | formatDate('yyyy-MM-dd')}}</span>
                                                            </el-col>
                                                            <el-col :span="5">
                                                                <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{life.lifeViews}}</span>
                                                            </el-col>
                                                            <el-col :span="5">
                                                                <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{life.lifeComments}}</span>
                                                            </el-col>
                                                        </el-row>
                                                    </el-card>
                                                </el-col>
                                            </el-row>

                                            <!-- 分页条 -->
                                            <el-pagination class="pagination" @current-change="lifeHandleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="lifeTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                                        </div>

                                        <div v-else class="no-content">
                                            <p>还没有内容</p>
                                        </div>
                                    </div>
                                </el-tab-pane>

                                <el-tab-pane label="音乐" name="music">
                                    <div class="content-container">
                                        <div v-if="musicTotal != 0">
                                            <el-row :gutter="20">
                                                <el-col :span="12" v-for="music in userMusic" :key="music.musicId">
                                                    <el-card class="item" shadow="hover" body-style="padding: 10px">
                                                        <p @click="toMusicDetail(music.musicId)" class="title">{{music.musicTitle}}</p>
                                                        <!-- 音乐播放器 -->
                                                        <my-aplayer :music="music"></my-aplayer>
                                                        
                                                        <p class="description">{{music.musicDescription}}</p>
                                                        <el-row class="info">
                                                            <el-col :span="18">
                                                                <span>{{music.musicCreatetime | formatDate('yyyy-MM-dd')}}</span>
                                                            </el-col>
                                                            <el-col :span="3">
                                                                <span><font-awesome-icon :icon="['fas', 'headphones']"></font-awesome-icon>{{music.musicView}}</span>
                                                            </el-col>
                                                            <el-col :span="3">
                                                                <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{music.musicComments}}</span>
                                                            </el-col>
                                                        </el-row>
                                                    </el-card>
                                                </el-col>
                                            </el-row>

                                            <!-- 分页条 -->
                                            <el-pagination class="pagination" @current-change="musicHandleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="musicTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                                        </div>

                                        <div v-else class="no-content">
                                            <p>还没有内容</p>
                                        </div>
                                    </div>
                                </el-tab-pane>
                            </el-tabs>
                        </el-col>

                        <el-col class="right" :span="6">
                            <div class="tags">
                                <div class="tag-group">
                                    <p class="tags-title">
                                        <font-awesome-icon :icon="['fas', 'tag']" style="color: rgb(50,50,50);"></font-awesome-icon>
                                        标签
                                    </p>
                                    <el-divider></el-divider>
                                    
                                    
                                    <div v-if="userTagsList == ''" class="no-content">
                                        <p>还没有内容</p>
                                    </div>
                                    <div v-else>
                                        <el-tag v-for="(item,index) in userTagsList" :key="index" type="info">
                                            {{ item.tagName }}
                                        </el-tag>
                                    </div>
                                </div>
                            </div>

                            <!-- <div class="archive">
                                <div class="archive-body">
                                    <p class="archive-title">
                                        <font-awesome-icon :icon="['fas', 'archive']" style="color: rgb(50,50,50);"></font-awesome-icon>
                                        归档
                                    </p>
                                    <el-divider></el-divider>
                                    <div class="no-content">
                                        <p>还没有内容</p>
                                    </div>
                                </div>
                            </div> -->
                        </el-col>
                    </el-row>
                </div>
            </div>
            <div v-else class="userNotFound">
                <p>该用户不存在(((φ(◎ロ◎;)φ)))</p>
                <img draggable="false" src="../assets/images/nothing.png">
            </div>
            
        </main>

        <!-- 底部 -->
        <my-footer></my-footer>

        <el-backtop :right="50" :bottom="50">
            <font-awesome-icon :icon="['fas', 'chevron-up']"></font-awesome-icon>
        </el-backtop>
    </div>
</template>

<script>
export default {
    data() {
        return {
            activeName: 'study',
            user: {},
            userTagsList: [],
            userStudy: {},
            userAnime: {},
            userLife: {},
            userMusic: {},
            query: {
                userId: '',
                pageNum: 1,
                pageSize: 0
            },
            studyTotal: 0,
            animeTotal: 0,
            lifeTotal: 0,
            musicTotal: 0
        }
    }, 
    created() {
        this.query.userId = this.$route.params.userId;
        this.getUser();
        this.getUserTags();
        this.getUserStudy();
        this.getUserAnime();
        this.getUserLife();
        this.getUserMusic();
    },
    methods: {
        getUser() {
            let this_ = this;
            this.$http.post(
                '/getUser',
                JSON.stringify(this.query.userId),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.user = res.data.data;
                    this_.$route.meta.title = res.data.data.userNickname + ' 的个人主页';
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getUserTags() {
            let this_ = this;
            this.$http.post(
                '/getUserTags',
                JSON.stringify(this.query.userId),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("获取到的用户标签为 =");
                    console.log(res);
                        this_.userTagsList = res.data.data;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getUserStudy() {
            let this_ = this;
            this.$http.post(
                '/getUserStudy',
                JSON.stringify(this.query),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.userStudy = res.data.list;
                    this_.studyTotal = res.data.total;
                    this_.query.pageSize = res.data.pageSize;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getUserAnime() {
            let this_ = this;
            this.$http.post(
                '/getUserAnime',
                JSON.stringify(this.query),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.userAnime = res.data.list;
                    this_.animeTotal = res.data.total;
                    this_.query.pageSize = res.data.pageSize;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getUserLife() {
            let this_ = this;
            this.$http.post(
                '/getUserLife',
                JSON.stringify(this.query),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.userLife = res.data.list;
                    this_.lifeTotal = res.data.total;
                    this_.query.pageSize = res.data.pageSize;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getUserMusic() {
            let this_ = this;
            this.$http.post(
                '/getUserMusic',
                JSON.stringify(this.query),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.userMusic = res.data.list;
                    this_.musicTotal = res.data.total;
                    this_.query.pageSize = res.data.pageSize;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        // 监听学习页页码值改变的事件
        studyHandleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getUserStudy();
        },
        // 监听动漫页页码值改变的事件
        animeHandleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getUserAnime();
        },
        // 监听生活页页码值改变的事件
        lifeHandleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getUserLife();
        },
        // 监听音乐页页码值改变的事件
        musicHandleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getUserMusic();
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
        }
    }
}
</script>

<style lang="less" scoped>
    .main{
        background-color: #f5f7fa;
        padding: 0 0 20px;
    }
    .main .userNotFound{
        height: 700px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        font-size: 16px;
        color: rgb(100,100,100);
    }
    .main .head{
        position: relative;
        height: 250px;
        margin: 0 10%;
        background-image: url('../assets/images/head.jpg');
        background-repeat: no-repeat;
        background-size: cover;
        box-shadow: 5px 5px 5px rgba(0,0,0,0.1);
        border-radius: 3px;
        user-select: none;
        .info{
            position: absolute;
            bottom: 0;
            height: 100px;
            display: flex;
            align-items: center;
            .avatar{
                height: 70px;
                width: 70px;
                border-radius: 50%;
                padding: 0 15px 0;
                object-fit: cover;
            }
            div{
                height: 70px;
                .nickname{
                    color: #fff;
                    font-weight: 600;
                    font-size: 20px;
                    margin: 10px 0;
                }
                .sign{
                    color: #fff;
                    font-size: 14px;
                    margin: 10px 0;
                    font-family: Hiragino Sans GB,Microsoft YaHei,Arial,sans-serif;
                }
            }
        }
    }
    .main .content{
        padding: 20px 10%;
    }
    .main .content .content-body{
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        .left{
            padding-right: 10px;
            .el-tabs{
                background-color: #fff;
                border-radius: 3px;
                box-shadow: 5px 5px 5px rgba(0,0,0,0.1);
            }
        }
        .right{
            padding-left: 10px;
            .tags{
                background-color: #fff;
                border-radius: 3px;
                box-shadow: 5px 5px 5px rgba(0,0,0,0.1);
                margin-bottom: 20px;
                .tag-group{
                    min-height: 284px;
                    padding: 15px;
                    .tags-title{
                        height: 23px;
                        font-size: 16px;
                        margin: 0 10px 10px;
                        color: rgb(50,50,50);
                    }
                    .el-divider{
                        margin: 10px 0 15px;
                        color: rgb(50,50,50);
                    }
                    .no-content{
                        height: 235px;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        p{
                            font-size: 14px;
                            color: rgb(100,100,100);
                        }
                    }
                    .el-tag{
                        user-select: none;
                        margin: 5px;
                        height: 26px;
                        line-height: 24px;
                    }
                }
            }
            // .archive{
            //     background-color: #fff;
            //     border-radius: 3px;
            //     // box-shadow: 5px 5px 5px rgba(0,0,0,0.1);
            //     .archive-body{
            //         min-height: 137px;
            //         padding: 15px;
            //         .archive-title{
            //             height: 23px;
            //             font-size: 16px;
            //             margin: 0 10px 10px;
            //             color: rgb(50,50,50);
            //         }
            //         .el-divider{
            //             margin: 10px 0 15px;
            //             color: rgb(50,50,50);
            //         }
            //         .no-content{
            //             height: 60px;
            //             display: flex;
            //             align-items: center;
            //             justify-content: center;
            //             p{
            //                 font-size: 14px;
            //                 color: rgb(100,100,100);
            //             }
            //         }
            //         .el-tag{
            //             margin: 5px;
            //             cursor: pointer;
            //         }
            //     }
            // }
        }
    }
    .main .content .content-body .content-container{
        padding: 0 20px;
        min-height: 250px;
        .item{
            margin: 5px 0;
        }
        .cover{
            width: 100%;
            height: 131px;
            object-fit: cover;
            cursor: pointer;
        }
        .title{
            margin: 10px 0;
            font-size: 16px;
            color: rgb(30, 30, 30);
            cursor: pointer;
            transition: color 0.5s;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .title:hover{
            color: rgb(120,120,120);
        }
        .description{
            height: 20px;
            margin: 15px 0;
            font-size: 13px;
            color: rgb(120,120,120);
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .info{
            font-size: 13px;
            color: rgb(120,120,120);
            :nth-last-child(1), :nth-last-child(2){
                text-align: center;
            }
            svg{
                margin-right: 5px;
            }
        }
        .no-content{
            height: 250px;
            display: flex;
            align-items: center;
            justify-content: center;
            p{
                font-size: 14px;
                color: rgb(100,100,100);
            }
        }
    }

    .pagination{
        text-align: center;
        padding: 20px 0;
    }
   
</style>