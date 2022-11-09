<template>
    <div class="container">
        <el-form ref="userRef" :model="user" :rules="userRules" label-position="left" label-width="80px" size="medium">
            <img class="avatar" :src="user.userAvatar">
            <el-form-item label="id">
                <p>{{user.userId}}</p>
            </el-form-item>
            <el-form-item label="用户名">
                <p>{{user.userName}}</p>
            </el-form-item>
            <el-form-item prop="userNickname" label="昵称">
                <el-input v-model="user.userNickname"></el-input>
            </el-form-item>
            <el-form-item prop="userGender" label="性别">
                <el-radio v-model="user.userGender" label="男">男</el-radio>
                <el-radio v-model="user.userGender" label="女">女</el-radio>
            </el-form-item>
            <el-form-item prop="userEmail" label="邮箱">
                <el-input v-model="user.userEmail"></el-input>
            </el-form-item>
            <el-form-item prop="userSignature" label="个性签名">
                <el-input type="textarea" v-model="user.userSignature" maxlength="40" show-word-limit placeholder="写下你的个性签名叭- ( ゜- ゜)つロ"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="updateUserInfo">确认修改</el-button>
            </el-form-item>
        </el-form>
    </div>    
</template>

<script>
export default {
    inject: ['reload'],
    data(){
        return {
            user: {},
            userRules: {
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
        this.user = JSON.parse(window.sessionStorage.getItem('user'));
    },
    methods: {
        updateUserInfo() {
            let this_ = this;
            this.$refs.userRef.validate(async valid => {
                if (!valid) return; 
                this_.$confirm('确定修改个人信息吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this_.$http.post(
                        '/updateUserInfo',
                        JSON.stringify(this.user),
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
                                // 修改成功
                                this_.$message({
                                    type: 'success',
                                    message: '修改成功',
                                    duration: 2000
                                });
                                window.sessionStorage.setItem('user', JSON.stringify(res.data.data));
                                this_.reload();
                            } else{
                                // 修改失败
                                return this_.$message({
                                    type: 'error',
                                    message: '修改失败',
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
            });
        }
    }
}
</script>

<style lang="less" scoped>
    .container .el-form{
        padding: 10px 20%;
        width: 500px;
        p{
            margin: 0;
            font-size: 13px;
            color: rgb(100,100,100);
        }
        .avatar{
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 50%;
            margin-bottom: 10px;
        }
    }
</style>