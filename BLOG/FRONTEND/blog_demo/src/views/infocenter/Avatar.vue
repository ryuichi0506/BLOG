<template>
    <div class="container">
        <div class="avatar-container">
            <input id="fileInput" type="file" hidden accept="image/*" @change="getFile">
            <label for="fileInput" class="selectAvatar">
                <font-awesome-icon class="icon" :icon="['fas','cloud-upload-alt']"></font-awesome-icon>
                <p>选择头像</p>
            </label>
            <div class="avatarbox">
                <img draggable="false" class="avatar" :src="user.userAvatar">
                <p ref="avatarInfo">当前头像</p>
            </div>
        </div>
        
        <p class="prompt">请选择图片上传: 支持JPG、PNG等格式</p>

        <div class="upload-btn">
            <el-button type="primary" :disabled="uploadBtnValid" @click="updateUserAvatar">上传</el-button>
        </div>
        
    </div>    
</template>

<script>
export default {
    inject: ['reload'],
    data() {
        return {
            user: {},
            uploadBtnValid: true
        }
    },
    created() {
        this.user = JSON.parse(window.sessionStorage.getItem('user'));
    },
    methods: {
        getFile(event) {
            this.$refs.avatarInfo.innerHTML = '预览头像'
            this.uploadBtnValid = false
            const files = event.target.files
            let filetype = files[0].name.split('.')[1];
            if(filetype !== 'jpg' && filetype !== 'png' && filetype !== 'gif' && filetype !== 'jpeg'){
                this.$message({
                    type: 'error',
                    message: '图片格式错误!',
                    duration: 2000  
                })
                return false;
            }
            const fileReader = new FileReader()                //内置方法new FileReader()   读取文件
                fileReader.addEventListener('load',() => {
                this.user.userAvatar = fileReader.result
            })
            fileReader.readAsDataURL(files[0])
            fileReader.onloadend = function (e) {
                this.user.userAvatar = this.result; // this指向当前方法onloadend的作用域
            }
            //到这里后, 选择图片就可以显示出来了
        },

        updateUserAvatar() {
            let this_ = this;
            this.$confirm('确定要修改头像吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this_.$http.post(
                    '/updateUserAvatar',
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
                                duration: 1000
                            });
                            window.sessionStorage.setItem('user', JSON.stringify(res.data.data));
                            this_.reload();
                        } else{
                            // 修改失败
                            return this_.$message({
                                type: 'error',
                                message: '修改失败',
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
            }).catch(() => {
                return    
            });
        }
    }
}
</script>

<style lang="less" scoped>
.avatar-container{
    display: flex;
    justify-content: center;
}

.selectAvatar{
    width: 180px;
    height: 180px;
    background-color: #f1f2f5;
    border-radius: 4px;
    margin-right: 50px;
    cursor: pointer;
    .icon{
        width: 40px;
        height: 40px;
        margin: 60px 70px 0;
        color: #acacac;
        justify-items: center;
    }
    p{
        margin: 0;
        height: 20px;
        text-align: center;
        color: #6d757a;
    }
}

.avatarbox{
    height: 180px;
    width: 180px;
    .avatar{
        height: 120px;
        width: 120px;
        border-radius: 50%;
        margin: 0 30px;
        object-fit: cover;
    }
    p{
        margin: 22px 0;
        font-size: 12px;
        color: rgb(150, 150, 150);
        text-align: center;
    }
}


.prompt{
    margin-top: 30px;
    font-size: 12px;
    color:rgb(100, 100, 100);
    text-align: center;
}
.upload-btn{
    margin-top: 30px;
    display: flex;
    justify-content: center;
    .el-button{
        width: 150px;
    }
}


</style>