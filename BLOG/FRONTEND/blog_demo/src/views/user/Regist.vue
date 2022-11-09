<template>
    <div class="box">
        <div class="regist_box">
        <!-- 注册表单区域 -->
            <el-form ref="registFormRef" :model="registForm" :rules="registFormRules" class="regist_form">
                <el-form-item>
                    <el-divider>
                        <span style="font-size: 20px;">注&nbsp;册</span>
                    </el-divider>
                </el-form-item>
                <!-- 用户名 -->
                <el-form-item prop="userName">
                    <el-input v-model="registForm.userName" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
                </el-form-item>
                <!-- 昵称 -->
                <el-form-item prop="userNickname">
                    <el-input v-model="registForm.userNickname" prefix-icon="el-icon-user" placeholder="昵称"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="userPassword">
                    <el-input v-model="registForm.userPassword" prefix-icon="el-icon-lock" type="password" placeholder="密码"></el-input>
                </el-form-item>
                <!-- 邮箱 -->
                <el-form-item prop="userEmail">
                    <el-input v-model="registForm.userEmail" prefix-icon="el-icon-message" placeholder="邮箱" @keyup.enter.native="regist"></el-input>
                </el-form-item>
                <!-- 登录链接 -->
                <router-link class="link_login" to="/login">已有帐号？直接登录>></router-link>
                <!-- 按钮 -->
                <el-button type="primary" class="regist_btn" @click="regist">用户注册</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data () {

        // 用户名是否存在校验规则
        var checkUserNameIsExist = (rule, value, callback) => {
            let this_ = this;
            this.$http.post(
                '/checkUserNameIsExist',
                JSON.stringify(value),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    if(res.data.status === 1){
                        callback();
                    } else{
                        callback(new Error(res.data.message));
                    }
                }
            ).catch(
                function (res) {}
            )
        };

        return {
            registForm: {
                userName: '',
                userNickname: '',
                userPassword: '',
                userEmail: ''
            },
            registFormRules: {
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
                    },
                    {
                        validator: checkUserNameIsExist,
                        trigger: 'blur'
                    }
                ],
                userNickname: [
                    {
                        required: true,
                        message: '请输入昵称',
                        trigger: 'blur'
                    },
                    {
                        min: 4,
                        max: 12,
                        message: '昵称长度在4-12个字符之间',
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
                ],
                userEmail: [
                    {
                        required: true,
                        message: '请输入邮箱地址',
                        trigger: 'blur'
                    },
                    {
                        type: 'email',
                        message: '请输入正确的邮箱地址',
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
        regist() {
            let this_ = this;
            this.$refs.registFormRef.validate(async valid => {
                if (!valid) return; 
                this.$http.post(
                    '/regist',
                    JSON.stringify(this.registForm),
                    {
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    }
                ).then(
                    function (res) {
                        if(res.data.status === 1){
                            this_.$message({
                                type: 'success',
                                message: res.data.message,
                                duration: 2000
                            });
                            this_.$router.push('/login');
                        } else{
                            this_.$message({
                                type: 'error',
                                message: res.data.message,
                                duration: 2000
                            });
                        }
                    }
                ).catch(
                    function (res) {}
                )
            });
        }
    }
}
</script>

<style lang="less" scoped>
    .box {
        position: absolute;
        width: 400px;
        height: 420px;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }

    .regist_box {
        width: 100%;
        height: 100%;
        background-color: #fff;
        border-radius: 4px;
        position: absolute;
    }
    .regist_form {
        position: absolute;
        width: 100%;
        padding: 0 30px;
        box-sizing: border-box;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }
    .link_login {
        display: inline-block;
        margin: 0 0 15px;
        color: #00a1d6;
        font-size: 14px;
        text-decoration: none;

    }
    .regist_btn {
        width: 100%;
        margin: 0;
        button {
            width: 100%;
        }
    }
</style>
