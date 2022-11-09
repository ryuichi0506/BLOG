<template>
    <div class="box">
        <div class="login_box">
            <!-- 登录表单区域 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
                <el-form-item>
                    <el-divider><span style="font-size: 20px;">登&nbsp;录</span></el-divider>
                </el-form-item>
                <!-- <el-form-item>
                    <p style="font-size: 20px; text-align: center; margin: 0">登&nbsp;录</p>
                </el-form-item> -->
                <!-- 用户名 -->
                <el-form-item prop="userName">
                    <el-input v-model="loginForm.userName" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="userPassword">
                    <el-input v-model="loginForm.userPassword" prefix-icon="el-icon-lock" type="password" placeholder="密码" @keyup.enter.native="login"></el-input>
                </el-form-item>
                <!-- 注册链接 -->
                <router-link class="link_regist" to="/regist">还没帐号？点此注册>></router-link>
                <!-- 按钮 -->
                <el-form-item class="login_btns">
                    <el-button type="info" @click="visitorLogin">游客登录</el-button>
                    <el-button type="primary" @click="login">用户登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {
            loginForm: {
                userName: '',
                userPassword: ''
            },
            visitor: {
                visitorId: 1,
                visitorName: '友人A',
                visitorAvatar: 'http://localhost:8888/upload/imageSrc/user/tx.jpg'
            },
            // 登录表单的验证规则对象
            loginFormRules: {
                userName: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    },
                    {
                        min: 6,
                        max: 12,
                        message: '用户名长度在6-12个字符之间',
                        trigger: 'blur'
                    }
                ],
                userPassword: [
                    {
                        required: true,
                        message: '请输入密码',
                        trigger: 'blur'
                    },
                    {
                        min: 6,
                        max: 16,
                        message: '密码长度在6-16个字符之间',
                        trigger: 'blur'
                    }
                ]
            }
        }
    },
    created() {
        window.sessionStorage.removeItem('visitor');
    },
    methods: {
        // 游客身份登录
        visitorLogin () {

            window.sessionStorage.setItem('visitor', JSON.stringify(this.visitor));
            this.$router.push('/home');


            // let this_ = this;
            // this.$http.post(
            //     '/visitorLogin',
            //     JSON.stringify(this.visitorLoginForm),
            //     {
            //         headers: {
            //             'Content-Type': 'application/json;charset=UTF-8'
            //         }
            //     }
            // ).then(
            //     function (res) {
            //         console.log("then res =");
            //         console.log(res);
            //         if(res.data.status === 1){
            //             //登录成功
            //             this_.$message({
            //                 type: 'info',
            //                 message: '游客登录',
            //                 duration: 2000
            //             });
            //             // 跳转到主页
            //             window.sessionStorage.setItem('visitor', JSON.stringify(res.data.data));
            //             this_.$router.push('/home');
            //         } else{
            //             //登录失败
            //             return this_.$message({
            //                 type: 'error',
            //                 message: '登录失败',
            //                 duration: 2000
            //             })
            //         }
            //     }
            // ).catch(
            //     function (res) {
            //         console.log("catch res =");
            //         console.log(res);
            //     }
            // )
        },

        // 用户登录
        login() {
            let this_ = this;
            this.$refs.loginFormRef.validate(async valid => {
                if (!valid) return; 
                this.$http.post(
                    '/login',
                    JSON.stringify(this.loginForm),
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
                            //登录成功
                            this_.$message({
                                type: 'success',
                                message: res.data.message,
                                duration: 2000
                            });
                            // 跳转到主页
                            window.sessionStorage.setItem('user', JSON.stringify(res.data.data));
                            this_.$router.push('/home');
                        } else{
                            //登录失败
                            return this_.$message({
                                type: 'error',
                                message: res.data.message,
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
            });
        }
    }
}
</script>

<style lang="less" scoped>
    .box{
        position: absolute;
        width: 400px;
        height: 300px;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }

    .login_box{
        width: 100%;
        height: 100%;
        background-color: #fff;
        border-radius: 5px;
        position: absolute;
        // background-color: rgba(0,0,0,0.1);
    }
    .login_form{
        position: absolute;
        width: 100%;
        padding: 0 30px;
        box-sizing: border-box;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }
    .link_regist{
        display: inline-block;
        margin: 0 0 15px;
        color: #00a1d6;
        font-size: 14px;
        text-decoration: none;
    }
    .login_btns{
        width: 100%;
        margin: 0;
        button{
             &:first-child{
                width: 48%;
                float: left;
            }
            &:last-child{
                width: 48%;
                float: right;
            }
        }

    }
</style>
