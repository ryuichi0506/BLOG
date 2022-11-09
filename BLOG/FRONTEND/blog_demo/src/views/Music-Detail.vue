<template>
    <div class="container" v-wechat-title="$route.meta.title">
        <my-header></my-header>

        <div class="main">
            <div v-if="blogDetail == null" class="notfound">
                <p>没有找到这篇文章(((φ(◎ロ◎;)φ)))</p>
                <img draggable="false" src="../assets/images/nothing.png">
            </div>

            <div v-else class="music-container">
                <el-row :gutter="30">
                    <!-- 文章 -->
                    <el-col :span="17">
                        <my-aplayer class="music" :music="music"></my-aplayer>

                        <div class="title">{{blogDetail.musicTitle}}</div>

                        <div class="info">
                            <el-tag class="type" size="small" type="success" v-if="blogDetail.musicType === 0">原创</el-tag>
                            <el-tag class="type" size="small" type="danger" v-if="blogDetail.musicType === 1">转载</el-tag>
                            <span class="updatetime" v-if="blogDetail.musicUpdatetime">最后更新于: {{blogDetail.musicUpdatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                            <span class="views">
                                {{blogDetail.musicView}} 人听过
                            </span>
                            <span class="comments">
                                {{blogDetail.musicComments}} 条评论
                            </span>
                        </div>
                        <div class="content" v-html="blogDetail.musicContent"></div>
                        <div class="tags">
                            <el-tag size="small" type="info" v-for="tag in blogDetail.tags.split(',')" :key="tag">
                                {{tag}}
                            </el-tag>
                        </div>
                        <div class="createinfo">
                            <img draggable="false" :src="blogDetail.user.userAvatar">
                            <span class="authornickname">{{blogDetail.user.userNickname}}</span>
                            <span class="createtime">发布于: {{blogDetail.musicCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                        </div>

                        <el-divider></el-divider>

                        <my-comment :commentArticleId="commentArticleId" :commentArticleSortId="commentArticleSortId" :commentObjectId="commentObjectId" :commentList="commentList"></my-comment>
                    </el-col>

                    <!-- 作者卡片 -->
                    <el-col :span="7" class="authorCard">
                        <el-card class="author" shadow="hover">
                            <div class="avatar">
                                <img draggable="false" :src="blogDetail.user.userAvatar">
                            </div>
                            <div class="nickname">
                                <span>{{blogDetail.user.userNickname}}</span>
                                <font-awesome-icon class="icon" v-if="blogDetail.user.userGender === '男'" :icon="['fas', 'mars']" style="color: lightblue;"></font-awesome-icon>
                                <font-awesome-icon class="icon" v-if="blogDetail.user.userGender === '女'" :icon="['fas', 'venus']" style="color: pink;"></font-awesome-icon>
                            </div>
                            <div class="username">
                                <font-awesome-icon class="icon" :icon="['fas', 'user']" style="color: gray;"></font-awesome-icon>
                                <span>{{blogDetail.user.userName}}</span>
                            </div>
                            <div class="email">
                                <font-awesome-icon class="icon" :icon="['fas', 'envelope']" style="color: orange;"></font-awesome-icon>
                                <span>{{blogDetail.user.userEmail}}</span>
                            </div>
                            <div class="signature">
                                <font-awesome-icon class="icon" :icon="['fas', 'signature']" style="color: black;"></font-awesome-icon>
                                <span v-if="blogDetail.user.userSignature">{{blogDetail.user.userSignature}}</span>
                                <span v-else>还没有个性签名哦(⊙o⊙)</span>
                            </div>
                            <div class="more">
                                <router-link :to="/space/ + blogDetail.user.userId" target="_blank">
                                    TA的个人主页
                                </router-link>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
        </div>

        <my-footer></my-footer>

        <el-backtop :right="50" :bottom="50">
            <font-awesome-icon :icon="['fas', 'chevron-up']"></font-awesome-icon>
        </el-backtop>
    </div>
</template>

<script>

export default {
    inject: ['reload'],
    data() {
        return {
            musicId: '',
            // 获取的博客的详情
            blogDetail: {
                user: {},
                tags: '',
            },
            music: {
                musicSrcTitle: '',
                musicArtist: '',
                musicSrc: 'musicSrc',
                musicCover: ''
            },
            // 传给评论组件的4个关键数据
            commentArticleId: '',
            commentArticleSortId: '',
            commentObjectId: '',
            commentList: [],
            // 查询评论的条件
            query: {
                articleId: '',
                articleSortId: ''
            }
        }
    },
    created(){
        this.musicId = this.$route.params.musicId;
        this.musicDetail();
    },
    methods: {
        musicDetail() {
            let this_ = this;
            this.$http.post(
                '/musicDetail',
                JSON.stringify(this.musicId),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.blogDetail = res.data.data;
                    this_.$route.meta.title = this_.blogDetail.musicTitle;

                    this_.commentArticleId = this_.blogDetail.musicId;
                    this_.commentArticleSortId = this_.blogDetail.musicSortId;
                    this_.commentObjectId = this_.blogDetail.user.userId;
                    
                    this_.query.articleId = this_.blogDetail.musicId;
                    this_.query.articleSortId = this_.blogDetail.musicSortId;

                    this_.music.musicSrcTitle = this_.blogDetail.musicSrcTitle;
                    this_.music.musicArtist = this_.blogDetail.musicArtist;
                    this_.music.musicSrc = this_.blogDetail.musicSrc;
                    this_.music.musicCover = this_.blogDetail.musicCover;
                    
                    this_.getComment();
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        getComment() {
            let this_ = this;
            this.$http.post(
                '/getComment',
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
                    this_.commentList = res.data.data;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        }
    }
}
</script>

<style lang="less" scoped>
    .container{
        overflow: hidden;
    }
    .notfound{
        height: 700px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        font-size: 16px;
        color: rgb(100,100,100);
    }
    .music-container{
        padding: 0 10%;
        .el-row{
            overflow: hidden;
            .music{
                padding: 30px 0;
                margin: 0 !important;
                background-color: rgba(0,0,0,0) !important;
                .aplayer-pic{
                    width: 100px !important;
                    height: 100px !important;
                }
                .aplayer-info{
                    height: 100px !important;
                    padding: 5px 15px !important;
                }
            }
            .title{
                font-size: 26px;
                padding: 10px 0;
                font-weight: bold;
            }
            .info{
                padding: 10px 0;
                .type{
                    margin-right: 10px;
                }
                .updatetime{
                    font-size: 12px;
                    color: rgb(150,150,150);
                    margin: 0 15px;
                }
                .views{
                    font-size: 12px;
                    color: rgb(150,150,150);
                    margin-right: 15px;
                }
                .comments{
                    font-size: 12px;
                    color: rgb(150,150,150);
                }
            }
            .content{
                padding: 20px 0;
            }
            .tags{
                padding: 10px 0;
                span{
                    margin-right: 10px;
                }
            }
            .createinfo{
                display: flex;
                justify-content: flex-end;
                align-items: center;
                img{
                    width: 30px;
                    height: 30px;
                    border-radius: 50%;
                    object-fit: cover;
                    margin-right: 5px;
                }
                .authornickname{
                    font-size: 13px;
                    color: rgb(150,150,150);
                    margin-right: 15px;
                }
                .createtime{
                    font-size: 13px;
                    color: rgb(150,150,150);
                }
            }

            // 作者卡片
            .authorCard{
                padding: 30px 0;
                .author{
                    .avatar{
                        display: flex;
                        justify-content: center;
                        img{
                            width: 70px;
                            height: 70px;
                            border-radius: 50%;
                        }
                    }
                    .nickname{
                        text-align: center;
                        padding: 15px 0;
                        font-weight: bolder;
                    }
                    .username{
                        display: flex;
                        font-size: 14px;
                        padding: 15px 0;
                        color: gray;
                        span{
                            margin-left: 5px;
                        }
                    }
                    .email{
                        display: flex;
                        font-size: 14px;
                        padding: 15px 0;
                        span{
                            margin-left: 5px;
                        }
                    }
                    .signature{
                        display: flex;
                        font-size: 14px;
                        padding: 15px 0;
                        line-height: 1.5;
                        span{
                            margin-left: 5px;
                        }
                    }
                    .more{
                        padding: 10px 0;
                        display: flex;
                        justify-content: center;
                        a{
                            padding: 10px 0;
                            text-align: center;
                            border: none;
                            border-radius: 4px;
                            font-size: 13px;
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
            
            
        }
    }

    .icon{
        width: 20px;
        height: 20px;
    }
</style>