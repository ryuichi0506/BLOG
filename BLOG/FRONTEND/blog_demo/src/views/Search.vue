<template>
    <div class="container">
        <!-- 头部 -->
        <my-header></my-header>

        <!-- 主体 -->
        <main class="main">
            <el-tabs type="border-card" style="margin: 0 10%;">


                <!-- 按博客查找 -->
                <el-tab-pane>
                    <span slot="label"><font-awesome-icon :icon="['fas', 'blog']" style="margin-right: 5px;"></font-awesome-icon>按博客查找</span>
                    <el-tabs type="border-card">

                        <!-- 按学习分类查找 -->
                        <el-tab-pane>
                            <span slot="label"><font-awesome-icon :icon="['fas', 'book-reader']" style="margin-right: 5px;"></font-awesome-icon>学习</span>
                            <el-row class="notfound" v-if="studyTotal == 0">
                                <p>没有找到相关学习博客 (((φ(◎ロ◎;)φ)))</p>
                                <img draggable="false" src="../assets/images/nothing.png">
                            </el-row>

                            <div v-else>
                                <el-row :gutter="20">
                                    <el-col :span="12" v-for="study in studyList" :key="study.studyId">
                                        <div class="study-card">
                                            <el-row :gutter="10">
                                                <el-col :span="16" class="study">
                                                    <router-link class="title" :to="/study/ + study.studyId" target="_blank">
                                                        {{study.studyTitle}}
                                                    </router-link>
                                                    <p class="description">{{study.studyDescription}}</p>
                                                    <div class="info">
                                                        <router-link :to="/space/ + study.user.userId" target="_blank">
                                                            <img class="avatar" :src="study.user.userAvatar">
                                                            <p class="nickname">{{study.user.userNickname}}</p>
                                                        </router-link>
                                                        <span class="createtime">
                                                            <font-awesome-icon :icon="['fas', 'clock']"></font-awesome-icon>
                                                            {{study.studyCreatetime | formatDate('yyyy-MM-dd')}}
                                                        </span>
                                                        <span class="views">
                                                            <font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>
                                                            {{study.studyViews}}
                                                        </span>
                                                        <span class="comments">
                                                            <font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>
                                                            {{study.studyComments}}
                                                        </span>
                                                    </div>
                                                </el-col>
                                                <el-col :span="8" class="cover">
                                                    <router-link :to="/study/ + study.studyId" target="_blank">
                                                        <img :src="study.studyCover">
                                                    </router-link>
                                                    
                                                </el-col>
                                            </el-row>
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-pagination class="pagination" @current-change="studyHandleCurrentChange" :current-page="studyQueryInfo.pageNum" :page-size="studyQueryInfo.pageSize" :total="studyTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                            </div>
                        </el-tab-pane>

                        <!-- 按动漫分类查找 -->
                        <el-tab-pane>
                            <span slot="label"><font-awesome-icon :icon="['fas', 'ghost']" style="margin-right: 5px;"></font-awesome-icon>动漫</span>
                            <el-row class="notfound" v-if="animeTotal == 0">
                                <p>没有找到相关动漫博客 (((φ(◎ロ◎;)φ)))</p>
                                <img draggable="false" src="../assets/images/nothing.png">
                            </el-row>

                            <div v-else>
                                <el-row :gutter="20">
                                    <el-col :span="12" v-for="anime in animeList" :key="anime.animeId">
                                        <div class="anime-card">
                                            <el-row :gutter="10">
                                                <el-col :span="16" class="anime">
                                                    <router-link class="title" :to="/anime/ + anime.animeId" target="_blank">
                                                        {{anime.animeTitle}}
                                                    </router-link>
                                                    <p class="description">{{anime.animeDescription}}</p>
                                                    <div class="info">
                                                        <router-link :to="/space/ + anime.user.userId" target="_blank">
                                                            <img class="avatar" :src="anime.user.userAvatar">
                                                            <p class="nickname">{{anime.user.userNickname}}</p>
                                                        </router-link>
                                                        <span class="createtime">
                                                            <font-awesome-icon :icon="['fas', 'clock']"></font-awesome-icon>
                                                            {{anime.animeCreatetime | formatDate('yyyy-MM-dd')}}
                                                        </span>
                                                        <span class="views">
                                                            <font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>
                                                            {{anime.animeViews}}
                                                        </span>
                                                        <span class="comments">
                                                            <font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>
                                                            {{anime.animeComments}}
                                                        </span>
                                                    </div>
                                                </el-col>
                                                <el-col :span="8" class="cover">
                                                    <router-link :to="/anime/ + anime.animeId" target="_blank">
                                                        <img :src="anime.animeCover">
                                                    </router-link>
                                                    
                                                </el-col>
                                            </el-row>
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-pagination class="pagination" @current-change="animeHandleCurrentChange" :current-page="animeQueryInfo.pageNum" :page-size="animeQueryInfo.pageSize" :total="animeTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                            </div>
                        </el-tab-pane>

                        <!-- 按生活分类查找 -->
                        <el-tab-pane>
                            <span slot="label"><font-awesome-icon :icon="['fas', 'coffee']" style="margin-right: 5px;"></font-awesome-icon>生活</span>
                            <el-row class="notfound" v-if="lifeTotal == 0">
                                <p>没有找到相关生活博客 (((φ(◎ロ◎;)φ)))</p>
                                <img draggable="false" src="../assets/images/nothing.png">
                            </el-row>

                            <div v-else>
                                <el-row :gutter="20">
                                    <el-col :span="12" v-for="life in lifeList" :key="life.lifeId">
                                        <div class="life-card">
                                            <el-row :gutter="10">
                                                <el-col :span="16" class="life">
                                                    <router-link class="title" :to="/life/ + life.lifeId" target="_blank">
                                                        {{life.lifeTitle}}
                                                    </router-link>
                                                    <div class="info">
                                                        <router-link :to="/space/ + life.user.userId" target="_blank">
                                                            <img class="avatar" :src="life.user.userAvatar">
                                                            <p class="nickname">{{life.user.userNickname}}</p>
                                                        </router-link>
                                                        <span class="createtime">
                                                            <font-awesome-icon :icon="['fas', 'clock']"></font-awesome-icon>
                                                            {{life.lifeCreatetime | formatDate('yyyy-MM-dd')}}
                                                        </span>
                                                        <span class="views">
                                                            <font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>
                                                            {{life.lifeViews}}
                                                        </span>
                                                        <span class="comments">
                                                            <font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>
                                                            {{life.lifeComments}}
                                                        </span>
                                                    </div>
                                                </el-col>
                                                <el-col :span="8" class="cover">
                                                    <router-link :to="/life/ + life.lifeId" target="_blank">
                                                        <img :src="life.lifeCover">
                                                    </router-link>
                                                    
                                                </el-col>
                                            </el-row>
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-pagination class="pagination" @current-change="lifeHandleCurrentChange" :current-page="lifeQueryInfo.pageNum" :page-size="lifeQueryInfo.pageSize" :total="lifeTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                            </div>
                        </el-tab-pane>

                        <!-- 按音乐分类查找 -->
                        <el-tab-pane>
                            <span slot="label"><font-awesome-icon :icon="['fas', 'music']" style="margin-right: 5px;"></font-awesome-icon>音乐</span>
                            <el-row class="notfound" v-if="musicTotal == 0">
                                <p>没有找到相关音乐博客 (((φ(◎ロ◎;)φ)))</p>
                                <img draggable="false" src="../assets/images/nothing.png">
                            </el-row>

                            <div v-else>
                                <el-row>
                                    <el-col :span="24" v-for="music in musicList" :key="music.musicId">
                                        <el-row :gutter="10" class="music-card">
                                            <el-col :span="12" class="music">
                                                <router-link class="title" :to="/music/ + music.musicId" target="_blank">
                                                    {{music.musicTitle}}
                                                </router-link>
                                                <p class="description">{{music.musicDescription}}</p>
                                                <div class="info">
                                                    <router-link :to="/space/ + music.user.userId" target="_blank">
                                                        <img class="avatar" :src="music.user.userAvatar">
                                                        <p class="nickname">{{music.user.userNickname}}</p>
                                                    </router-link>
                                                    <span class="createtime">
                                                        <font-awesome-icon :icon="['fas', 'clock']"></font-awesome-icon>
                                                        {{music.musicCreatetime | formatDate('yyyy-MM-dd')}}
                                                    </span>
                                                    <span class="views">
                                                        <font-awesome-icon :icon="['fas', 'headphones']"></font-awesome-icon>
                                                        {{music.musicView}}
                                                    </span>
                                                    <span class="comments">
                                                        <font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>
                                                        {{music.musicComments}}
                                                    </span>
                                                </div>
                                            </el-col>
                                            <el-col :span="12" class="cover">
                                                <my-aplayer :music="music"></my-aplayer>
                                            </el-col>
                                        </el-row>
                                    </el-col>
                                </el-row>
                                <el-pagination class="pagination" @current-change="musicHandleCurrentChange" :current-page="musicQueryInfo.pageNum" :page-size="musicQueryInfo.pageSize" :total="musicTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </el-tab-pane>

                
                <!-- 按用户查找 -->
                <el-tab-pane>
                    <span slot="label"><font-awesome-icon :icon="['fas', 'user']" style="margin-right: 5px;"></font-awesome-icon>按用户查找</span>

                    <el-row class="notfound" v-if="userTotal == 0">
                        <p>没有找到相关用户 (((φ(◎ロ◎;)φ)))</p>
                        <img draggable="false" src="../assets/images/nothing.png">
                    </el-row>
                    <div v-else>
                        <el-row :gutter="20">
                            <el-col :span="6" v-for="user in userList" :key="user.userId">
                                <div class="user-card">
                                    <div class="avatar">
                                        <img draggable="false" :src="user.userAvatar">
                                    </div>
                                    <div class="info">
                                        <div class="nickname">
                                            <span>{{user.userNickname}}</span>
                                            <font-awesome-icon class="icon" v-if="user.userGender === '男'" :icon="['fas', 'mars']" style="color: lightblue;"></font-awesome-icon>
                                            <font-awesome-icon class="icon" v-if="user.userGender === '女'" :icon="['fas', 'venus']" style="color: pink;"></font-awesome-icon>
                                        </div>
                                        <div class="username">
                                            用户名: <span>{{user.userName}}</span>
                                        </div>
                                        <div class="userspace">
                                            <router-link :to="/space/ + user.userId" target="_blank">
                                                TA的个人主页
                                            </router-link>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                        <el-pagination class="pagination" @current-change="userHandleCurrentChange" :current-page="userQueryInfo.pageNum" :page-size="userQueryInfo.pageSize" :total="userTotal" layout="total, prev, pager, next, jumper"></el-pagination>
                    </div>
                    
                </el-tab-pane>
            </el-tabs>
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
            userQueryInfo: {
                pageNum: 1,
                pageSize: 12,
                query: ''
            },
            studyQueryInfo: {
                pageNum: 1,
                pageSize: 10,
                query: ''
            },
            animeQueryInfo: {
                pageNum: 1,
                pageSize: 10,
                query: ''
            },
            lifeQueryInfo: {
                pageNum: 1,
                pageSize: 10,
                query: ''
            },
            musicQueryInfo: {
                pageNum: 1,
                pageSize: 10,
                query: ''
            },
            userTotal: 0,
            studyTotal: 0,
            animeTotal: 0,
            lifeTotal: 0,
            musicTotal: 0,
            userList: {},
            studyList: {},
            animeList: {},
            lifeList: {},
            musicList: {}
        }
    },
    created() {
        this.findUserByQuery();
        this.findStudyByQuery();
        this.findAnimeByQuery();
        this.findLifeByQuery();
        this.findMusicByQuery();
    },
    methods: {
        findUserByQuery() {
            let this_ = this;
            this.userQueryInfo.query = this.$route.query.query;
            this.$http.post(
                '/findUserByQuery',
                JSON.stringify(this.userQueryInfo),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("获取到的用户list为 =");
                    console.log(res);
                    this_.userList = res.data.list;
                    this_.userTotal = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        findStudyByQuery() {
            let this_ = this;
            this.studyQueryInfo.query = this.$route.query.query;
            this.$http.post(
                '/findStudyByQuery',
                JSON.stringify(this.studyQueryInfo),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("获取到的学习list为 =");
                    console.log(res);
                    this_.studyList = res.data.list;
                    this_.studyTotal = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        findAnimeByQuery() {
            let this_ = this;
            this.animeQueryInfo.query = this.$route.query.query;
            this.$http.post(
                '/findAnimeByQuery',
                JSON.stringify(this.animeQueryInfo),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("获取到的动漫list为 =");
                    console.log(res);
                    this_.animeList = res.data.list;
                    this_.animeTotal = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        findLifeByQuery() {
            let this_ = this;
            this.lifeQueryInfo.query = this.$route.query.query;
            this.$http.post(
                '/findLifeByQuery',
                JSON.stringify(this.lifeQueryInfo),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("获取到的生活list为 =");
                    console.log(res);
                    this_.lifeList = res.data.list;
                    this_.lifeTotal = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        findMusicByQuery() {
            let this_ = this;
            this.musicQueryInfo.query = this.$route.query.query;
            this.$http.post(
                '/findMusicByQuery',
                JSON.stringify(this.musicQueryInfo),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("获取到的生活list为 =");
                    console.log(res);
                    this_.musicList = res.data.list;
                    this_.musicTotal = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        // 监听页码值改变的事件
        userHandleCurrentChange(newPage) {
            this.userQueryInfo.pageNum = newPage;
            this.findUserByQuery();
        },
        studyHandleCurrentChange(newPage) {
            this.studyQueryInfo.pageNum = newPage;
            this.findStudyByQuery();
        },
        animeHandleCurrentChange(newPage) {
            this.animeQueryInfo.pageNum = newPage;
            this.findAnimeByQuery();
        },
        lifeHandleCurrentChange(newPage) {
            this.lifeQueryInfo.pageNum = newPage;
            this.findLifeByQuery();
        },
        musicHandleCurrentChange(newPage) {
            this.musicQueryInfo.pageNum = newPage;
            this.findMusicByQuery();
        }
    }
}
</script>

<style lang="less" scoped>
    .main{
        margin: 20px 0;
    }
    .notfound{
        height: 300px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: rgb(100,100,100);
    }

    .user-card{
        display: flex;
        flex-direction: row;
        height: 80px;
        border: 1px solid #ddd;
        border-radius: 3px;
        padding: 10px;
        margin: 10px 0;
        .avatar{
            display: flex;
            align-items: center;
            img{
                width: 60px;
                height: 60px;
                border-radius: 50%;
            }
        }
        .info{
            width: 100%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            margin-left: 10px;
            .nickname{
                width: fit-content;
                .icon{
                    margin-left: 5px;
                }
            }
            .username{
                padding: 5px 0;
                font-size: 13px;
                font-weight: 100;
                color: #aaa;
            }
            .userspace{
                padding: 5px 0 0;
                display: flex;
                width: 100%;
                a{
                    padding: 5px 0;
                    text-align: center;
                    border: none;
                    border-radius: 3px;
                    font-size: 12px;
                    outline: none;
                    color: #fff;
                    cursor: pointer;
                    background-color: #00aaaa;
                    transition: opacity 0.5s;
                }
                a:hover{
                    opacity: 0.6;
                }
            }
        }
    }

    .study-card{
        display: flex;
        flex-direction: row;
        border: 1px solid #ddd;
        border-radius: 3px;
        height: 120px;
        padding: 10px;
        margin: 10px 0;
        .study{
            height: 100%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            .title{
                width: fit-content;
                margin: 8px 0;
                transition: color 0.3s;
            }
            .title:hover{
                color: #999;
            }
            .description{
                margin: 5px 0;
                font-size: 12px;
                color: #999;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
                -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
                -webkit-line-clamp: 2;  /*文本超出显示几行*/
            }
            .info{
                display: flex;
                flex-direction: row;
                align-items: center;
                margin: 5px 0;
                a{
                    width: fit-content;
                    display: flex;
                    flex-direction: row;
                    align-items: center;
                    .avatar{
                        height: 30px;
                        width: 30px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                    .nickname{
                        margin: 0 5px;
                        font-size: 13px;
                        transition: color 0.3s;
                    }
                    .nickname:hover{
                        color: #999;
                    }
                }
                .createtime{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .views{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .comments{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
            }
        }
        
        .cover{
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            img{
                width: 100%;
                object-fit: cover;
                border-radius: 3px;
            }
        }
    }

    .anime-card{
        display: flex;
        flex-direction: row;
        border: 1px solid #ddd;
        border-radius: 3px;
        height: 120px;
        padding: 10px;
        margin: 10px 0;
        .anime{
            height: 100%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            .title{
                width: fit-content;
                margin: 8px 0;
                transition: color 0.3s;
            }
            .title:hover{
                color: #999;
            }
            .description{
                margin: 5px 0;
                font-size: 12px;
                color: #999;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;   /*作为弹性伸缩盒子模型显示*/
                -webkit-box-orient: vertical;   /*设置伸缩盒子的子元素排列方式--从上到下垂直排列*/
                -webkit-line-clamp: 2;  /*文本超出显示几行*/
            }
            .info{
                display: flex;
                flex-direction: row;
                align-items: center;
                margin: 5px 0;
                a{
                    width: fit-content;
                    display: flex;
                    flex-direction: row;
                    align-items: center;
                    .avatar{
                        height: 30px;
                        width: 30px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                    .nickname{
                        margin: 0 5px;
                        font-size: 13px;
                        transition: color 0.3s;
                    }
                    .nickname:hover{
                        color: #999;
                    }
                }
                .createtime{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .views{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .comments{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
            }
        }
        
        .cover{
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            img{
                width: 100%;
                object-fit: cover;
                border-radius: 3px;
            }
        }
    }

    .life-card{
        display: flex;
        flex-direction: row;
        border: 1px solid #ddd;
        border-radius: 3px;
        height: 120px;
        padding: 10px;
        margin: 10px 0;
        .life{
            height: 100%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            .title{
                width: fit-content;
                margin: 8px 0;
                transition: color 0.3s;
            }
            .title:hover{
                color: #999;
            }
            .info{
                display: flex;
                flex-direction: row;
                align-items: center;
                margin: 5px 0;
                a{
                    width: fit-content;
                    display: flex;
                    flex-direction: row;
                    align-items: center;
                    .avatar{
                        height: 30px;
                        width: 30px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                    .nickname{
                        margin: 0 5px;
                        font-size: 13px;
                        transition: color 0.3s;
                    }
                    .nickname:hover{
                        color: #999;
                    }
                }
                .createtime{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .views{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .comments{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
            }
        }
        
        .cover{
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            img{
                width: 100%;
                object-fit: cover;
                border-radius: 3px;
            }
        }
    }

    .music-card{
        display: flex;
        flex-direction: row;
        border: 1px solid #ddd;
        border-radius: 3px;
        height: 150px;
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        .music{
            height: 100%;
            display: flex;
            justify-content: center;
            flex-direction: column;
            .title{
                width: fit-content;
                margin: 8px 0;
                transition: color 0.3s;
            }
            .title:hover{
                color: #999;
            }
            .description{
                margin: 5px 0;
                font-size: 12px;
                color: #999;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
            }
            .info{
                display: flex;
                flex-direction: row;
                align-items: center;
                margin: 5px 0;
                a{
                    width: fit-content;
                    display: flex;
                    flex-direction: row;
                    align-items: center;
                    .avatar{
                        height: 30px;
                        width: 30px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                    .nickname{
                        margin: 0 5px;
                        font-size: 13px;
                        transition: color 0.3s;
                    }
                    .nickname:hover{
                        color: #999;
                    }
                }
                .createtime{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .views{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
                .comments{
                    margin: 0 10px;
                    font-size: 12px;
                    color: #888;
                }
            }
        }
        
        .cover{
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            .aplayer{
                width: 80%;
            }
        }
    }

    .pagination{
        text-align: center;
    }
</style>