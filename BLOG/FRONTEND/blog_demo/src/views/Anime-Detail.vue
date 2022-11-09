<template>
    <div class="container" v-wechat-title="$route.meta.title">
        <my-header></my-header>

        <div class="main">
            <div v-if="blogDetail == null" class="notfound">
                <p>没有找到这篇文章(((φ(◎ロ◎;)φ)))</p>
                <img draggable="false" src="../assets/images/nothing.png">
            </div>

            <div v-else class="anime-container">
                <el-row :gutter="30">
                    
                    <!-- 文章 -->
                    <el-col :span="17">
                        <div class="cover">
                            <img draggable="false" :src="blogDetail.animeCover">
                        </div>
                        <div class="title">{{blogDetail.animeTitle}}</div>
                        <div class="info">
                            <el-tag class="type" size="small" type="success" v-if="blogDetail.animeType === 0">原创</el-tag>
                            <el-tag class="type" size="small" type="danger" v-if="blogDetail.animeType === 1">转载</el-tag>
                            <span class="updatetime" v-if="blogDetail.animeUpdatetime">最后更新于: {{blogDetail.animeUpdatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                            <span class="views">
                                {{blogDetail.animeViews}} 人浏览
                            </span>
                            <span class="comments">
                                {{blogDetail.animeComments}} 条评论
                            </span>
                        </div>
                        <div class="content" v-html="blogDetail.animeContent"></div>
                        <div class="tags">
                            <el-tag size="small" type="info" v-for="tag in blogDetail.tags.split(',')" :key="tag">
                                {{tag}}
                            </el-tag>
                        </div>
                        <div class="createinfo">
                            <img draggable="false" :src="blogDetail.user.userAvatar">
                            <span class="authornickname">{{blogDetail.user.userNickname}}</span>
                            <span class="createtime">发布于: {{blogDetail.animeCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
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
            animeId: '',
            // 获取的博客的详情
            blogDetail: {
                user: {},
                tags: ''
            },
            // 传给评论组件的3个关键数据
            commentArticleId: '',
            commentArticleSortId: '',
            commentObjectId: '',
            commentList: [],
            // 查询评论的条件
            query: {
                articleId: '',
                articleSortId: ''
            },
        }
    },
    created(){
        this.animeId = this.$route.params.animeId;
        this.animeDetail();
    },
    methods: {
        animeDetail() {
            let this_ = this;
            this.$http.post(
                '/animeDetail',
                JSON.stringify(this.animeId),
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
                    this_.$route.meta.title = this_.blogDetail.animeTitle;

                    this_.commentArticleId = this_.blogDetail.animeId;
                    this_.commentArticleSortId = this_.blogDetail.animeSortId;
                    this_.commentObjectId = this_.blogDetail.user.userId;
                    
                    this_.query.articleId = this_.blogDetail.animeId;
                    this_.query.articleSortId = this_.blogDetail.animeSortId;
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
    .anime-container{
        padding: 0 10%;
        .el-row{
            overflow: hidden;
            .cover{
                padding: 30px 0 10px;
                display: flex;
                justify-content: center;
                img{
                    width: 100%;
                    height: 481px;
                    object-fit: cover;
                    border-radius: 4px; 
                }          
            }
            .title{
                font-size: 30px;
                font-weight: bold;
                padding: 10px 0;
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
                        padding: 10px 0;
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