<template>
    <div class="container" ref="container">
        <!-- 头部 -->
        <my-header></my-header>

        <!-- 主体 -->
        <main class="main">
            <div class="message-board">

                <div class="no-content" v-if="messageLists == ''">
                    暂时没有用户留言
                </div>

                <el-timeline v-else>
                    <el-timeline-item v-for="message in messageLists" :key="message.messageId" :timestamp="message.messageCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')" placement="top" size="large">
                        <el-card>
                            <div class="messageContainer">
                                <font-awesome-icon :icon="['fas', 'quote-left']" style="color: gray; float: left;"></font-awesome-icon><br>
                                <p class="message">{{message.messageContent}}</p>
                                <font-awesome-icon :icon="['fas', 'quote-right']" style="color: gray; float: right;"></font-awesome-icon>
                                <div class="info">
                                    <router-link :to="/space/ + message.user.userId" target="_blank">
                                        <span class="name">{{message.user.userNickname}}</span>&nbsp;&nbsp;
                                        <img class="avatar" :src="message.user.userAvatar">
                                    </router-link>
                                    <span class="time">{{message.messageCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                                </div>
                            </div>
                            <div class="replyMessageContainer" v-if="message.messageReturnContent">
                                <div class="replyContent">回复: {{message.messageReturnContent}}</div>
                                <div class="info">
                                    <div>
                                        <el-tag size="small" type="danger" effect="dark">管理员</el-tag>
                                        <span class="name">{{message.admin.adminNickname}}</span>&nbsp;&nbsp;
                                        <img class="avatar" :src="message.admin.adminAvatar">
                                    </div>
                                    <span class="time">{{message.messageReturnCreatetime | formatDate('yyyy-MM-dd hh:mm:ss')}}</span>
                                </div>
                            </div>
                        </el-card>
                    </el-timeline-item>
                    
                    <!-- 分页条 -->
                    <el-pagination class="pagination" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="total" layout="total, prev, pager, next, jumper"></el-pagination>
                </el-timeline>

                <!-- 发布留言表单 -->
                <el-row>
                    <el-form ref="messageFormRef" :rules="messageFormRules" :model="messageForm" v-if="model">
                        <el-form-item prop="messageContent">
                            <el-input class="textarea" type="textarea" maxlength="200" show-word-limit v-model="messageForm.messageContent" autocomplete="off" placeholder="写下你的留言吧..."></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" size="small" @click="postMessage">提交</el-button>
                        </el-form-item>
                    </el-form>
                    <div class="no-permission" v-else>
                        <p>登录后才可以留言</p>
                        <router-link to="/login">登录</router-link>/
                        <router-link to="/regist">注册</router-link>
                    </div>
                </el-row>
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
    inject: ['reload'],
    data() {
        return {
            messageForm: {
                messageContent: '',
                messageUserId: ''
            },
            messageFormRules: {
                messageContent: [
                    {
                        required: true,
                        message: '留言不能为空',
                        trigger: 'blur'
                    }
                ]
            },
            messageLists:[],
            model: false,
            query: {
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        if(JSON.parse(window.sessionStorage.getItem('user')) != null) {
            this.model = true;
        } else{
            this.model = false;
        }

        this.getMessages();
    },
    methods: {
        getMessages() {
            let this_ = this;
            this.$http.post(
                '/getMessages',
                JSON.stringify(this.query.pageNum),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("then res =");
                    console.log(res);
                    this_.messageLists = res.data.list;
                    this_.total = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        postMessage() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.messageForm.messageUserId = user.userId;
            let this_ = this;
            this.$refs.messageFormRef.validate(async valid => {
                if (!valid) return; 
                this.$http.post(
                    '/postMessage',
                    JSON.stringify(this.messageForm),
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
                            //发布成功
                            this_.$message({
                                type: 'success',
                                message: res.data.message,
                                duration: 1000
                            });
                            // 刷新当前页面
                            this_.reload();
                            // 回到页面顶部
                            this_.$refs.container.scrollIntoView();
                        } else{
                            //发布失败
                            return this_.$message({
                                type: 'error',
                                message: '发布失败',
                                duration: 1000
                            })
                        }
                        
                    }
                ).catch(
                    function (res) {
                        console.log("catch res =");
                        console.log(res);
                    }
                )
            });
        },
        // 监听页码值改变的事件
        handleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getMessages();
        },
    }
}
</script>

<style lang="less" scoped>
.main{
    // background: url('../assets/images/wallpaper_56.jpg');
    background-image: url('http://localhost:8888/upload/imageSrc/front/wallpaper_56.jpg');
    background-position: center center;
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
}
.message-board{
    padding: 50px 10%;
}
.message-board .no-content{
    display: flex;
    align-items: center;
    justify-content: center;
    color: rgb(100, 100, 100);
}
.message-board .messageContainer .message{
    font-size: 18px;
    margin: 0 20px;
    font-family: "plantc", "Source Han Serif", serif;
}
.message-board .messageContainer .info{
    height: 36px;
    line-height: 36px;
    margin: 20px 0 0;
    color: rgb(30, 30, 30);
    display: flex;
    justify-content: flex-end;
    a{
        display: flex;
        justify-content: flex-end;
        width: fit-content;
        .avatar{
            height: 36px;
            line-height: 36px;
            width: 36px;
            border-radius: 50%;
            margin-left: 5px;
            object-fit: cover;
        }
        .name{
            font-size: 13px;
        }
    }
    a:hover{
        color: rgb(100, 100, 100);
    }
    .time{
        font-size: 13px;
        margin-left: 20px;
    }
}
.message-board .replyMessageContainer{
    padding: 20px 0 0 20px;
    user-select: none;
    .replyMessage{
        font-size: 18px;
        margin: 0 20px;
        font-family: "plantc", "Source Han Serif", serif;
    }
    .info{
        height: 36px;
        line-height: 36px;
        margin: 20px 0 0;
        color: rgb(30, 30, 30);
        display: flex;
        justify-content: flex-end;
        div{
            display: flex;
            justify-content: flex-end;
            align-items: center;
            width: fit-content;
            .el-tag{
                height: 24px;
                line-height: 24px;
                margin-right: 5px;
            }
            .avatar{
                height: 36px;
                line-height: 36px;
                width: 36px;
                border-radius: 50%;
                margin-left: 5px;
                object-fit: cover;
            }
            .name{
                font-size: 13px;
            }
        }
        a:hover{
            color: rgb(100, 100, 100);
        }
        .time{
            font-size: 13px;
            margin-left: 20px;
        }
    }
}
.el-row{
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 50px;
    .no-permission{
        width: 80%;
        height: 100px;
        background-color: rgba(255,255,255,0.9);
        color: rgb(100,100,100);
        font-size: 0.9em;
        border-radius: 4px;
        display: flex;
        justify-content: center;
        align-items: center;
        p{
            margin-right: 20px;
        }
        a{
            width: fit-content;
            color: rgb(30,30,30);
            font-size: 1em;
        }
        a:hover{
            color: rgb(100,100,100);
        }
    }
}

.el-timeline-item__content .el-card{
    background-color: rgba(255,255,255,0.9);
}
.el-form{
    width: 60%;
}
.pagination{
    text-align: center;
    padding: 20px 0 0;
}
</style>
