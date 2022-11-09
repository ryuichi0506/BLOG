<template>
    <div class="box">
        <div class="login_box">
            <!-- 登录表单区域 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
                <el-form-item>
                    <el-divider><span style="font-size: 20px;">后&nbsp;台&nbsp;登&nbsp;录</span></el-divider>
                </el-form-item>
                <!-- 用户名 -->
                <el-form-item prop="adminUsername">
                    <el-input v-model="loginForm.adminUsername" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="adminPassword">
                    <el-input v-model="loginForm.adminPassword" prefix-icon="el-icon-lock" type="password" placeholder="密码" @keyup.enter.native="login"></el-input>
                </el-form-item>
                <!-- 按钮 -->
                <el-button type="primary" class="login_btn" @click="login">管理员登录</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data () {
        return {
            loginForm: {
                adminUsername: '',
                adminPassword: ''
            },
            // 登录表单的验证规则对象
            loginFormRules: {
                adminUsername: [
                    {
                        required: true,
                        message: '请输入用户名',
                        trigger: 'blur'
                    },
                    {
                        min: 2,
                        max: 8,
                        message: '用户名长度在2-8个字符之间',
                        trigger: 'blur'
                    }
                ],
                adminPassword: [
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
        let admin = window.sessionStorage.getItem('admin');
        if(admin != null){
            this.$router.push('/backend');
        }
    },
    methods: {
        login() {
            let this_ = this;
            this.$refs.loginFormRef.validate(async valid => {
                if (!valid) return; 
                this.$http.post(
                    '/adminLogin',
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
                        // const admin = res.data.data;
                        if(res.data.status === 1){
                            //登录成功
                            this_.$message({
                                type: 'success',
                                message: '登录成功',
                                duration: 2000
                            });
                            window.sessionStorage.setItem('admin', JSON.stringify(res.data.data));
                            this_.$router.push('/backend');
                            // 跳转到后台,并将获取到的值传递到后台
                            // this_.$router.push({
                            //     name: '/backend',
                            //     params: {
                            //         admin
                            //     }
                            // });
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
        border-radius: 4px;
        position: absolute;
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
    .login_btn {
        width: 100%;
        margin: 10px 0 0 0;
        button {
            width: 100%;
        }
    }
</style>
