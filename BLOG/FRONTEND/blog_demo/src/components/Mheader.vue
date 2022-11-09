<template>
    <!-- 头部 -->
    <header class="header">
        <el-row class="top">
            <el-col :span="24">
                <el-menu class="navbar" :default-active="$route.path" mode="horizontal" 
                background-color="#fff" 
                text-color="#000" active-text-color="#ff0000" 
                router 
                menu-trigger="click">
                    <el-menu-item index="/home">首页</el-menu-item>
                    <el-menu-item index="/messageboard">留言板</el-menu-item>
                    <el-menu-item v-if="model == 1" :index="/space/ + user.userId" @click="toMyspace">个人主页</el-menu-item>
                    <el-menu-item v-else index="/space">个人主页</el-menu-item>
                    <el-submenu index="4" class="type">
                        <template slot="title">分类</template>
                        <el-menu-item index="/study" class="type-item">学习&nbsp;·&nbsp;Study</el-menu-item>
                        <el-menu-item index="/anime" class="type-item">动漫&nbsp;·&nbsp;Anime</el-menu-item>
                        <el-menu-item index="/life" class="type-item">生活&nbsp;·&nbsp;Life</el-menu-item>
                        <el-menu-item index="/music" class="type-item">音乐&nbsp;·&nbsp;Music</el-menu-item>
                    </el-submenu>
                    <el-menu-item index="/upload/study" class="write">投稿</el-menu-item>


                    <ul class="avatar">
                        <li>
                            <el-input type="text" style="width: 200px; margin-right: 50px;" placeholder="搜索博客、用户..." size="small" v-model="query" @keyup.enter.native="search">
                                <font-awesome-icon style="margin-right: 5px; cursor: pointer;" slot="suffix" :icon="['fas', 'search']" @click="search"></font-awesome-icon>
                            </el-input>
                        </li>
                        <li v-if="model === 0">
                            <p class="nickname">{{admin.adminNickname}}</p>
                            <el-dropdown placement="bottom" @command="handleCommand">
                                <img draggable="false" :src="admin.adminAvatar"/>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item icon="el-icon-switch-button" command="/login">登录</el-dropdown-item>
                                    <el-dropdown-item icon="el-icon-s-opportunity" command="/regist">注册</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </li>
                        <li v-else-if="model === 1">
                            <p class="nickname">{{user.userNickname}}</p>
                            <el-dropdown placement="bottom" @command="handleCommand">
                                <img draggable="false" :src="user.userAvatar"/>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="/info">个人信息</el-dropdown-item>
                                    <el-dropdown-item command="/blogs/study">我的博客</el-dropdown-item>
                                    <el-dropdown-item command="/login">退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </li>
                        <li v-else-if="model === 2">
                            <p class="nickname">{{visitor.visitorName}}</p>
                            <el-dropdown placement="bottom" @command="handleCommand">
                                <img draggable="false" :src="visitor.visitorAvatar"/>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item icon="el-icon-switch-button" command="/login">登录</el-dropdown-item>
                                    <el-dropdown-item icon="el-icon-s-opportunity" command="/regist">注册</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </li>
                    </ul>
                </el-menu>
            </el-col>
        </el-row>
    </header>
</template>

<script>
export default {
    inject: ['reload'],
    data() {
        return {
            user: {},
            visitor: {},
            admin: {},
            activeIndex: '',
            model: 0, // 0管理员 1用户 2游客
            query: ''
        }
    },
    created() {
        this.activeIndex = window.sessionStorage.getItem('activeIndex');
        this.user = JSON.parse(window.sessionStorage.getItem('user'));
        this.visitor = JSON.parse(window.sessionStorage.getItem('visitor'));
        this.admin = JSON.parse(window.sessionStorage.getItem('admin'));
        if(JSON.parse(window.sessionStorage.getItem('admin')) != null) {
            this.model = 0;
        } else if(JSON.parse(window.sessionStorage.getItem('user')) != null){
            this.model = 1;
        } else if(JSON.parse(window.sessionStorage.getItem('visitor')) != null){
            this.model = 2;
        }
    },
    methods: {
        handleCommand(command) {
            if(command === '/login'){
                window.sessionStorage.clear();
                this.$router.push(command);
                this.$message({
                    type: 'info',
                    message: '退出成功',
                    duration: 1000
                });
            }else{
                this.$router.push(command);
            }
        },
        toMyspace() {
            this.reload();
        },
        search() {
            if(this.query === '') {
                if(this.$route.path === '/search'){
                    
                } else{
                    let routes = this.$router.resolve({ path: '/search' });
                    window.open(routes.href, '_blank');
                }
            } else{
                if(this.$route.path === '/search'){
                    this.$router.push({ name: '/search' , query: { query: this.query }});
                    this.reload();
                } else{
                    let routes = this.$router.resolve({ name: '/search' , query: { query: this.query }});
                    window.open(routes.href, '_blank');
                }
            }
        }
    }
}
</script>

<style lang="less" scoped>
/* 头部css */
.container{
    width: 100%;
    box-sizing: border-box;
}
.header{
    z-index: 1;
    padding: 0;
    user-select: none;
}
.header .top{
    padding: 0 10%;
    border-bottom: 1px solid #e6e6e6;
}
.header .top .navbar{
    border: none;
}
.header .top .navbar .write{
    background-color: #ff8888 !important;
    color: #fff !important;
    border-color: #ff8888 !important;
    margin-left: 5px;
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;
    padding: 0 25px;
}
.header .top .avatar{
    margin: 0;
    float: right;
    list-style: none;
    outline: none;
}
.header .top .avatar li{
    display: flex;
    align-items: center;
    height: 60px;
    line-height: 60px;
    float: left;
    padding: 0 10px;
}
.header .top .avatar li a{
    text-decoration: none;
    font-size: 13px;
    color: #303133;
    margin: 0;
}
.header .top .avatar li .el-dropdown{
    height: 60px;
    line-height: 60px;
    display: flex;
    align-items: center;
}
.header .top .avatar li .el-dropdown img{
    width: 48px;
    height: 48px;
    line-height: 48px;
    border-radius: 50%;
    outline: none;
    cursor: pointer;
    object-fit: cover;
}
.header .top .avatar li .nickname{
    font-size: 15px;
    margin: 0;
    margin-right: 10px;
}
.type-item{
    height: 44px !important;
    line-height: 44px !important;
    padding: 0 20px !important;
}
</style>