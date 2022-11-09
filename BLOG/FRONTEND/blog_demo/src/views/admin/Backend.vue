<template>
    <div class="container">
        <el-container>
            <el-header>
                <p class="title">多人博客系统后台管理</p>
                <div class="adminInfo">
                    <p class="adminName">{{admin.adminNickname}}</p>
                    <el-dropdown placement="bottom" @command="handleCommand">
                        <div class="adminAvatar">
                            <img draggable="false" :src="admin.adminAvatar">
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item icon="el-icon-switch-button" command="/logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </div>
                    </el-dropdown>
                </div>
                
            </el-header>
            <el-container>
                <el-aside :width="isCollapse ? '60px' : '200px'">
                    <!-- 侧边栏缩进按钮 -->
                    <div class="toggle-button" @click="toggleCollapse" title="侧边栏收起/展开">
                        |||
                    </div>
                    
                    <el-menu
                        :unique-opened="true"
                        background-color="#D3DCE6"
                        text-color="#111111"
                        router
                        :default-active="$route.path" :collapse-transition="false" :collapse="isCollapse">
                        <el-submenu index="1">
                            <template slot="title">
                                <font-awesome-icon class="icon" :icon="['fas','blog']"></font-awesome-icon>
                                <span>博客</span>
                            </template>
                            <el-menu-item-group title="分类">
                                <el-menu-item index="/backend/blog/study">
                                    <font-awesome-icon class="icon" :icon="['fas','book-reader']"></font-awesome-icon>
                                    学习
                                </el-menu-item>
                                <el-menu-item index="/backend/blog/anime">
                                    <font-awesome-icon class="icon" :icon="['fas','ghost']"></font-awesome-icon>
                                    动漫
                                </el-menu-item>
                                <el-menu-item index="/backend/blog/life">
                                    <font-awesome-icon class="icon" :icon="['fas','coffee']"></font-awesome-icon>
                                    生活
                                </el-menu-item>
                                <el-menu-item index="/backend/blog/music">
                                    <font-awesome-icon class="icon" :icon="['fas','music']"></font-awesome-icon>
                                    音乐
                                </el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                        <el-submenu index="2">
                            <template slot="title">
                                <font-awesome-icon class="icon" :icon="['fas','users']"></font-awesome-icon>
                                <span slot="title">用户</span>
                            </template>
                            <el-menu-item index="/backend/user">
                                 <font-awesome-icon class="icon" :icon="['fas','user-cog']"></font-awesome-icon>
                                用户管理
                            </el-menu-item>
                        </el-submenu>
                        <el-submenu index="3">
                            <template slot="title">
                                <font-awesome-icon class="icon" :icon="['fas','tags']"></font-awesome-icon>
                                <span slot="title">标签</span>
                            </template>
                            <el-menu-item index="/backend/tag">
                                 <font-awesome-icon class="icon" :icon="['fas','hashtag']"></font-awesome-icon>
                                标签管理
                            </el-menu-item>
                        </el-submenu>
                        <el-submenu index="4">
                            <template slot="title">
                                <font-awesome-icon class="icon" :icon="['fas','comments']"></font-awesome-icon>
                                <span slot="title">留言</span>
                            </template>
                            <el-menu-item index="/backend/message">
                                 <font-awesome-icon class="icon" :icon="['fas','comment-alt']"></font-awesome-icon>
                                留言管理
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>

                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 管理员对象
            admin: {},
            // 侧边栏是否折叠
            isCollapse: false,
        }
    },
    created() {
        this.admin = JSON.parse(window.sessionStorage.getItem('admin'));
    },
    methods: {
        handleCommand(command) {
            if(command === '/logout'){
                window.sessionStorage.clear();
                this.$router.push('/admin');
                this.$message.info({
                    type: 'info',
                    message: '退出成功',
                    duration: 1000
                });
            }
        },
        // 点击按钮切换侧边栏的折叠与展开
        toggleCollapse() {
            this.isCollapse = !this.isCollapse;
        },
    }
}
</script>

<style lang="less" scoped>
    .container{
        height: 100%;
    }
    .el-container{
        height: 100%;
        .el-header{
            height: 100%;
            padding: 0 200px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: #B3C0D1;
            user-select: none;
            .title{
                font-size: 28px;
                font-weight: bold;
                color: #fff;
                margin: 0;
                text-shadow: 5px 5px 5px rgba(0,0,0,0.3);
            }
            .adminInfo{
                display: flex;
                justify-content: center;
                align-items: center;
                .adminName{
                    font-size: 14px;
                    color: #fff;
                    margin: 0 15px 0 0;
                }
                .adminAvatar{
                    display: flex;
                    align-items: center;
                    cursor: pointer;
                    img{
                        width: 45px;
                        height: 45px;
                        border-radius: 50%;
                        object-fit: cover;
                    }
                }
            }
            
        }
        .el-container{
            height: 100%;
            .el-aside{
                height: 100%;
                user-select: none;
                overflow: hidden;
                background-color: #D3DCE6;
                .toggle-button {
                    background-color: #D3DCE6;
                    color: #111111;
                    font-size: 10px;
                    line-height: 30px;
                    text-align: center;
                    letter-spacing: 2px;
                    cursor: pointer;
                }
                .el-menu{
                    overflow: hidden;
                    
                }
            }
            .el-main{
                background-color: #fff;
                height: 100%;
            }
        }
    }
    .icon{
        width: 20px;
        margin-right: 5px;
    }
</style>