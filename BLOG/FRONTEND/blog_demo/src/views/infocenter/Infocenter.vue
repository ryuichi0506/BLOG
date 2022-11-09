<template>
    <el-container>
        <!-- 头部 -->
        <el-header class="header">
            <el-row class="top">
                <el-col :span="24">
                    <el-menu class="navbar" :default-active="$route.path" mode="horizontal" background-color="#fff" text-color="#000" active-text-color="#ff0000" 
                    router 
                    menu-trigger="click">
                        <el-menu-item index="/home">首页</el-menu-item>
                        <el-menu-item index="/messageboard">留言板</el-menu-item>
                        <el-menu-item v-if="model" :index="/space/ + user.userId" @click="toMyspace">个人主页</el-menu-item>
                        <el-menu-item v-else index="/space">个人主页</el-menu-item>
                        <el-submenu index="4" class="type">
                            <template slot="title">分类</template>
                            <el-menu-item index="/study" class="type-item">学习&nbsp;·&nbsp;Study</el-menu-item>
                            <el-menu-item index="/anime" class="type-item">动漫&nbsp;·&nbsp;Anime</el-menu-item>
                            <el-menu-item index="/life" class="type-item">生活&nbsp;·&nbsp;Life</el-menu-item>
                            <el-menu-item index="/music" class="type-item">音乐&nbsp;·&nbsp;Music</el-menu-item>
                        </el-submenu>
                        <el-menu-item class="write" index="/upload/study">投稿</el-menu-item>

                        <ul class="avatar">
                            <li>
                                <el-input type="text" style="width: 200px; margin-right: 50px;" placeholder="搜索博客、用户..." size="small" v-model="query" @keyup.enter.native="search">
                                    <font-awesome-icon style="margin-right: 5px; cursor: pointer;" slot="suffix" :icon="['fas', 'search']" @click="search"></font-awesome-icon>
                                </el-input>
                            </li>
                            <li v-if="model">
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
                            <div v-else>
                                <li><router-link to="/login">登录</router-link></li>
                                <li><router-link to="/regist">注册</router-link></li>
                            </div>
                        </ul>
                    </el-menu>
                </el-col>
            </el-row>
        </el-header>


        <el-container class="mid">
            <!-- 侧边栏 -->
            <el-aside :width="isCollapse ? '60px' : '200px'">

                <!-- 侧边栏缩进按钮 -->
                <div class="toggle-button" @click="toggleCollapse" title="侧边栏收起/展开">
                    |||
                </div>

                <el-menu class="aside-menu" :unique-opened="true" :default-active="$route.path" :collapse-transition="false" :collapse="isCollapse" router>

                    <!-- 个人信息 -->
                    <el-submenu index="1">
                        <template slot="title">
                            <font-awesome-icon class="icon" :icon="['fas','user-cog']"></font-awesome-icon>
                            <span>个人</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/info">
                                <font-awesome-icon class="icon" :icon="['fas','user']"></font-awesome-icon>
                                个人信息
                            </el-menu-item>
                            <el-menu-item index="/avatar">
                            <font-awesome-icon class="icon" :icon="['fas','user-circle']"></font-awesome-icon>
                                我的头像
                            </el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>

                    <!-- 我的博客 -->
                    <el-submenu index="2">
                        <template slot="title">
                            <font-awesome-icon class="icon" :icon="['fas','blog']"></font-awesome-icon>
                            <span>博客</span>
                        </template>
                        <el-menu-item index="/blogs/study">
                            <font-awesome-icon class="icon" :icon="['fas','book-reader']"></font-awesome-icon>
                            学习
                        </el-menu-item>
                        <el-menu-item index="/blogs/anime">
                            <font-awesome-icon class="icon" :icon="['fas','ghost']"></font-awesome-icon>
                            动漫
                        </el-menu-item>
                        <el-menu-item index="/blogs/life">
                            <font-awesome-icon class="icon" :icon="['fas','coffee']"></font-awesome-icon>
                            生活
                        </el-menu-item>
                        <el-menu-item index="/blogs/music">
                            <font-awesome-icon class="icon" :icon="['fas','music']"></font-awesome-icon>
                            音乐
                        </el-menu-item>
                    </el-submenu>

                    <!-- 草稿箱 -->
                    <el-submenu index="/draft">
                        <template slot="title">
                            <font-awesome-icon class="icon" :icon="['fas','box-open']"></font-awesome-icon>
                            <span>草稿</span>
                        </template>
                        <el-menu-item index="/draft/study">
                            <font-awesome-icon class="icon" :icon="['fas','book-reader']"></font-awesome-icon>
                            学习
                        </el-menu-item>
                        <el-menu-item index="/draft/anime">
                            <font-awesome-icon class="icon" :icon="['fas','ghost']"></font-awesome-icon>
                            动漫
                        </el-menu-item>
                        <el-menu-item index="/draft/life">
                            <font-awesome-icon class="icon" :icon="['fas','coffee']"></font-awesome-icon>
                            生活
                        </el-menu-item>
                        <el-menu-item index="/draft/music">
                            <font-awesome-icon class="icon" :icon="['fas','music']"></font-awesome-icon>
                            音乐
                        </el-menu-item>
                    </el-submenu>

                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
export default {
    inject: ['reload'],
    data() {
        return {
            user: {},
            activeIndex: '5',
            // 是否折叠
            isCollapse: false,
            model: true, // true用户登录模式 false游客登录模式
            query: ''
        }
    },
    created() {
        this.user = JSON.parse(window.sessionStorage.getItem('user'));
        if(JSON.parse(window.sessionStorage.getItem('user')) != null) {
            this.model = true;
        } else{
            this.model = false;
        }
    },
    methods: {
        handleCommand(command) {
            if(command === '/login'){
                window.sessionStorage.clear();
                this.$router.push(command);
                this.$message.info({
                    type: 'info',
                    message: '退出成功',
                    duration: 1000
                });
            }else{
                // 如果目标路由与当前路由一致，则不进行跳转
                if(command == this.$route.path){
                    return
                }else{
                    this.$router.push(command);
                }
            }
        },
        // 点击按钮切换侧边栏的折叠与展开
        toggleCollapse() {
            this.isCollapse = !this.isCollapse;
        },
        toMyspace(){
            this.reload();
        },
        search() {
            console.log(this.query);
            if(this.query === '') {
                if(this.$route.path === '/search'){
                    this.$router.push({ name: '/search'});
                    this.reload();
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
    .el-container{
        height: 100%;
        overflow: hidden;
    }
    .el-aside{
        overflow: hidden;
        height: 100%;
        border-right: 1px solid #e6e6e6 !important;
        border-left: 1px solid #e6e6e6 !important;
        user-select: none;
    }
    .toggle-button {
        background-color: #ffffff;
        color: #111111;
        font-size: 10px;
        line-height: 30px;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }
    .icon{
        width: 20px;
        margin-right: 5px;
    }
    .el-menu{
        border-right: none;
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


    .mid{
        margin: 0 10%;
    }
</style>