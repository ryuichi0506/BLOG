<template>
    <div class="container">
        <my-header></my-header>
        
        <main class="main">
            <div class="head">
                <p>MUSIC</p>
            </div>

            <div class="content">
                <el-row :gutter="20" style="padding: 10px 10%">
                    <el-col :span="8" v-for="music in musicItems" :key="music.musicId">
                        <el-card class="music-item" body-style="padding: 20px" shadow="hover">
                            <p class="title" @click="toMusicDetail(music.musicId)">{{music.musicTitle}}</p>
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
                                            <div style="float: right">
                                                <p :title="music.user.userNickname">{{music.user.userNickname}}</p>
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

                <!-- 分页条 -->
                <el-pagination class="pagination" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="total" layout="total, prev, pager, next, jumper"></el-pagination>
            </div>

        </main>

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
            musicItems: [],
            query: {
                pageNum: 1,
                pageSize: 12
            },
            total: 0
        }
    },
    created() {
        this.getMusicSort();
    },
    methods: {
        getMusicSort() {
            let this_ = this;
            this.$http.post(
                '/getMusicSort',
                JSON.stringify(this.query),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("学习 res =");
                    console.log(res);
                    this_.musicItems = res.data.list
                    this_.total = res.data.total
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        // 监听页码值改变的事件
        handleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getMusicSort();
        },
        toMusicDetail(musicId) {
            let routes = this.$router.resolve({ path: '/music/'+ musicId });
            window.open(routes.href, '_blank');
        },
    }
}
</script>

<style lang="less" scoped>
    .main .head{
        width: 100%;
        height: 400px;
        // background-color: #ff4444;
        // background-image: url('../../assets/images/wallpaper_73.jpg');
        background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_73.jpg');
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-size: 100%;
        background-position: center center;
        display: flex;
        align-items: center;
        justify-content: center;
        user-select: none;
        p{
            margin: 0;
            color: #fff;
            font-size: 70px;
            font-weight: 900;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
    }
    .main .content{
        width: 100%;
        overflow: hidden;
        .pagination{
            padding: 20px 0;
            text-align: center;
        }
    }
    .main .content .music-item{
        margin: 10px 0;
    }
    .main .content .music-item .cover{
        width: 100%;
        height: 200px;
        border-radius: 3px;
        object-fit: cover;
    }
    .main .content .music-item .title{
        height: 30px;
        font-size: 18px;
        margin: 0 0 10px 0;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        cursor: pointer;
        color: rgb(30, 30, 30);
        transition: color 0.2s linear;
    }
    .main .content .music-item .title:hover{
        color: rgb(150, 150, 150);
    }
    .main .content .music-item .description{
        height: 25px;
        font-size: 14px;
        margin: 10px 0;
        line-height: 1.5;
        color: rgb(120, 120, 120);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .main .content .music-item .tag{
        height: 24px;
        span{
            margin-right: 10px;
        }
    }
    .main .content .music-item .info{
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
    
</style>