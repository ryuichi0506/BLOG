<template>
    <div class="container">
        <my-header></my-header>
        
        <main class="main">
            <div class="head">
                <p>ANIME</p>
            </div>
            <div class="content">
                <el-row :gutter="20" style="padding: 10px 10%">
                    <el-col :span="8" v-for="anime in animeItems" :key="anime.animeId">
                        <el-card class="anime-item" body-style="padding: 15px" shadow="hover">
                            <el-image lazy fit="cover" class="cover" draggable="false" @click="toAnimeDetail(anime.animeId)" :src="anime.animeCover"/>
                            <p class="title" @click="toAnimeDetail(anime.animeId)">{{anime.animeTitle}}</p>
                            <p class="description">{{anime.animeDescription}}</p>
                            <p class="tag">
                                <el-tag size="small" type="warning" effect="dark" v-for="tag in anime.tags.split(',')" :key="tag">{{tag}}</el-tag>
                            </p>
                            <p class="info">
                                <el-row>
                                    <el-col :span="16" class="avatar">
                                        <router-link :to="/space/ + anime.user.userId" target="_blank">
                                            <img :src="anime.user.userAvatar">
                                            <div style="float: right">
                                                <p :title="anime.user.userNickname">{{anime.user.userNickname}}</p>
                                                <p>{{anime.animeCreatetime | formatDate('yyyy-MM-dd')}}</p>
                                            </div>
                                        </router-link>
                                    </el-col>
                                    <el-col :span="4" class="views">
                                        <span><font-awesome-icon :icon="['fas', 'eye']"></font-awesome-icon>{{anime.animeViews}}</span>
                                    </el-col>
                                    <el-col :span="4" class="comments">
                                        <span><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon>{{anime.animeComments}}</span>
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
            animeItems: [],
            query: {
                pageNum: 1,
                pageSize: 12
            },
            total: 0
        }
    },
    created() {
        this.getAnimeSort();
    },
    methods: {
        getAnimeSort() {
            let this_ = this;
            this.$http.post(
                '/getAnimeSort',
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
                    this_.animeItems = res.data.list
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
            this.getStudyManage();
        },
        toAnimeDetail(animeId) {
            let routes = this.$router.resolve({ path: '/anime/'+ animeId });
            window.open(routes.href, '_blank');
        }
    }
}
</script>

<style lang="less" scoped>
    .main .head{
        width: 100%;
        height: 400px;
        // background-color: #ff4444;
        // background-image: url('../../assets/images/wallpaper_04.jpg');
        background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_04.jpg');
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
    .main .content .anime-item{
        margin: 10px 0;
    }
    .main .content .anime-item .cover{
        width: 100%;
        height: 200px;
        border-radius: 3px;
        object-fit: cover;
        cursor: pointer;
    }
    .main .content .anime-item .title{
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
    .main .content .anime-item .title:hover{
        color: rgb(150, 150, 150);
    }
    .main .content .anime-item .description{
        height: 25px;
        font-size: 14px;
        margin: 10px 0;
        line-height: 1.5;
        color: rgb(120, 120, 120);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .main .content .anime-item .tag{
        height: 24px;
        span{
            margin-right: 10px;
        }
    }
    .main .content .anime-item .info{
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