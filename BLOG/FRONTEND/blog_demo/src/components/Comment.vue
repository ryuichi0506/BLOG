<template>
    <div class="comment-container" ref="comment-container">
        <h2 style="color: lightblue">
            <font-awesome-icon :icon="['fas', 'comments']" ></font-awesome-icon> 评论
        </h2>

        <el-row v-if="model" class="postComment">
            <el-col class="post-user" :span="4">
                <div class="userAvatar">
                    <img draggable="false" :src="user.userAvatar">
                </div>
            </el-col>
            <el-col class="post-box" :span="20">
                <el-input type="textarea" maxlength="200" show-word-limit placeholder="留下你的评论吧(ง •_•)ง" v-model="postcomment.commentContent"></el-input>
                <button class="post-btn" @click="postMyComment">发表评论</button>
            </el-col>
        </el-row>
        <el-row v-else class="nopermisson">
            登录后才能进行评论
        </el-row>

        <el-row class="comments" v-for="comment in commentList" :key="comment.commentId">
            <el-col :span="4">
                <div class="userAvatar">
                    <img draggable="false" :src="comment.user.userAvatar">
                </div>
            </el-col>
            <el-col :span="20">
                <div class="userNickname">{{comment.user.userNickname}} :</div>
                <div class="comment">{{comment.commentContent}}</div>
                <div>
                    <span class="posttime">{{comment.commentCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                    <el-popover
                        popper-class="popover"
                        placement="bottom-start"
                        trigger="click">
                        <el-row v-if="model" class="replyComment">
                            <el-col class="reply-user" :span="5">
                                <div class="userAvatar">
                                    <img draggable="false" :src="user.userAvatar">
                                </div>
                                <div class="userNickname">{{user.userNickname}}</div>
                            </el-col>
                            <el-col class="post-box" :span="19">
                                <el-input :placeholder="'@' + comment.user.userNickname" ref="replayArea" type="textarea" maxlength="200" show-word-limit v-model="replyComment.commentContent"></el-input>
                                <button class="post-btn" @click="replyMyComment(comment.commentId, comment.user.userId)">发表评论</button>
                            </el-col>
                        </el-row>
                        <el-row v-else class="nopermisson">
                            登录后才能进行评论
                        </el-row>
                        <span class="reply" slot="reference">回复</span>
                    </el-popover>
                </div>

                <!-- 子评论 -->
                <el-row class="childrenComment" v-for="children in comment.childrenComments" :key="children.commentId">
                    <el-col :span="3">
                        <div class="userAvatar">
                            <img draggable="false" :src="children.user.userAvatar">
                        </div>
                    </el-col>
                    <el-col :span="21">
                        <div class="userNickname">
                            <span>{{children.user.userNickname}}</span>
                            : @<span>{{children.commentObject.userNickname}}</span>
                        </div>
                        <div class="comment">{{children.commentContent}}</div>
                        <div>
                            <span class="posttime">{{children.commentCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                            <el-popover
                                popper-class="popover"
                                placement="bottom-start"
                                trigger="click">
                                <el-row v-if="model" class="replyComment">
                                    <el-col class="reply-user" :span="5">
                                        <div class="userAvatar">
                                            <img draggable="false" :src="user.userAvatar">
                                        </div>
                                        <div class="userNickname">{{user.userNickname}}</div>
                                    </el-col>
                                    <el-col class="post-box" :span="19">
                                        <el-input :placeholder="'@' + children.user.userNickname" ref="replayArea" type="textarea" maxlength="200" show-word-limit v-model="replyComment.commentContent"></el-input>
                                        <button class="post-btn" @click="replyMyComment(children.commentParentId, children.user.userId)">发表评论</button>
                                    </el-col>
                                </el-row>
                                <el-row v-else class="nopermisson">
                                    登录后才能进行评论
                                </el-row>
                                <span class="reply" slot="reference">回复</span>
                            </el-popover>
                        </div>
                    </el-col>
                </el-row>

                <el-divider></el-divider>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    inject: ['reload'],
    data(){
        return {
            postcomment: {
                commentUserId: '',
                commentContent: '',
                commentArticleId: '',
                commentArticleSortId: '',
                commentObjectId: ''
            },
            replyComment: {
                commentUserId: '',
                commentContent: '',
                commentArticleId: '',
                commentArticleSortId: '',
                commentParentId: '',
                commentObjectId: ''
            },
            query: {
                articleId: this.commentArticleId,
                articleSortId: this.commentArticleSortId
            },
            user: {},
            model: true // true用户登录模式 false游客登录模式
        }
    },
    props: ['commentArticleId', 'commentArticleSortId', 'commentObjectId', 'commentList'],
    created(){
        this.user = JSON.parse(window.sessionStorage.getItem('user'));
        if(JSON.parse(window.sessionStorage.getItem('user')) != null) {
            this.model = true;
        } else{
            this.model = false;
        }
    },
    methods: {
        postMyComment(objectId) {
            let this_ = this;
            this.postcomment.commentUserId = this.user.userId;
            this.postcomment.commentArticleId = this.commentArticleId
            this.postcomment.commentArticleSortId = this.commentArticleSortId
            this.postcomment.commentObjectId = this.commentObjectId
            if(this.postcomment.commentContent != ''){
                this.$confirm('确定发布该条评论?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this_.$http.post(
                        '/postMyComment',
                        JSON.stringify(this_.postcomment),
                        {
                            headers: {
                                'Content-Type': 'application/json;charset=UTF-8'
                            }
                        }
                    ).then(
                        function (res) {
                            console.log("then res =");
                            console.log(res);
                            if(res.data.status === 1){
                                this_.$message({
                                    type: 'success',
                                    message: '评论成功',
                                    duration: 2000
                                })
                                this_.reload();
                            } else{
                                this_.$message({
                                    type: 'error',
                                    message: '评论失败',
                                    duration: 2000
                                })
                            }
                        }
                    ).catch(
                        function (res) {
                            console.log("catch res =");
                            console.log(res);
                        }
                    )
                }).catch(() => {
                    return    
                });
            } else{
                this.$notify({
                    title: '提示',
                    type: 'warning',
                    message: '评论内容不能为空!',
                    duration: 2000,
                });
            }
        },
        replyMyComment(id, objectId) {
            let this_ = this;
            this.replyComment.commentUserId = this.user.userId;
            this.replyComment.commentArticleId = this.commentArticleId
            this.replyComment.commentArticleSortId = this.commentArticleSortId
            this.replyComment.commentParentId = id;
            this.replyComment.commentObjectId = objectId;
            if(this.replyComment.commentContent != ''){
                this.$confirm('确定发布该条评论?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this_.$http.post(
                        '/postMyComment',
                        JSON.stringify(this_.replyComment),
                        {
                            headers: {
                                'Content-Type': 'application/json;charset=UTF-8'
                            }
                        }
                    ).then(
                        function (res) {
                            console.log("then res =");
                            console.log(res);
                            if(res.data.status === 1){
                                this_.$message({
                                    type: 'success',
                                    message: '评论成功',
                                    duration: 2000
                                })
                                this_.reload();
                            } else{
                                this_.$message({
                                    type: 'error',
                                    message: '评论失败',
                                    duration: 2000
                                })
                            }
                        }
                    ).catch(
                        function (res) {
                            console.log("catch res =");
                            console.log(res);
                        }
                    )
                }).catch(() => {
                    return    
                });
            } else{
                this.$notify({
                    title: '提示',
                    type: 'warning',
                    message: '评论内容不能为空!',
                    duration: 2000,
                });
            }
        }
    }
}
</script>

<style lang="less" scoped>
    .comment-container{
        .postComment{
            padding: 0 0 20px 0;
            .post-user{
                .userAvatar{
                    display: flex;
                    justify-content: center;
                    margin-bottom: 10px;
                    img{
                        width: 50px;
                        height: 50px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                }
            }
            .post-box{
                .post-btn{
                    float: right;
                    width: 150px;
                    margin-top: 10px;
                    padding: 8px;
                    border: none;
                    border-radius: 4px;
                    background-color: rgb(94, 184, 214);
                    color: #fff;
                    cursor: pointer;
                    outline: none;
                    transition: opacity 0.5s;
                }
                .post-btn:hover{
                    opacity: 0.8;
                }
            }
        }
        .nopermisson{
            padding: 100px 0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            color: rgb(150,150,150);
        }
        // 父评论
        .comments{
            padding: 10px 0;
            .userAvatar{
                display: flex;
                justify-content: center;
                img{
                    width: 50px;
                    height: 50px;
                    border-radius: 50%;
                    object-fit: cover;
                }
            }
            .userNickname{
                font-size: 13px;
                font-weight: bold;
                color: #00aaaa;
            }
            .comment{
                font-size: 14px;
                padding: 10px 0;
            }
            .posttime{
                font-size: 13px;
                color: rgb(100,100,100);
                margin-right: 15px;
            }
            .reply{
                font-size: 13px;
                color: rgb(100,100,100);
                margin-right: 15px;
                cursor: pointer;
            }
            .reply:hover{
                color: rgb(50,50,50);
            }
            // 子评论
            .childrenComment{
                padding: 25px 0 0;
                .userAvatar{
                    display: flex;
                    justify-content: center;
                    img{
                        width: 40px;
                        height: 40px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                }
                .userNickname{
                    font-size: 13px;
                    font-weight: bold;
                    color: #00aaaa;
                }
                .comment{
                    font-size: 14px;
                    padding: 10px 0;
                }
                .posttime{
                    font-size: 13px;
                    color: rgb(100,100,100);
                    margin-right: 15px;
                }
            }
        }
    }
</style>