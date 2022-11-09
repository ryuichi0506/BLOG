<template>
    <div class="write-container" v-loading.fullscreen.lock="loading">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/upload/study' }">学习</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/upload/anime' }">动漫</el-breadcrumb-item>
            <el-breadcrumb-item>生活</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/upload/music' }">音乐</el-breadcrumb-item>
            <el-breadcrumb-item>投稿</el-breadcrumb-item>
        </el-breadcrumb>
        
        <el-divider></el-divider>

        <el-form ref="lifeFormRef" :model="lifeForm" :rules="lifeFormRules" label-position="top" size="small">
            
            <!-- 封面 -->
            <el-form-item label="封面 (建议使用尺寸比例为16:9的图片,只能上传一张封面)" prop="lifeCover" :inline-message="true">
                
                <div class="selectBox">
                    <input ref="fileInput" id="fileInput" type="file" hidden accept="image/*" @change="getFile">
                    <label for="fileInput" class="selectImage">
                        <font-awesome-icon class="icon" :icon="['fas','cloud-upload-alt']"></font-awesome-icon>
                        <p>选择图片</p>
                    </label>
                     <img v-if="lifeForm.lifeCover" draggable="false" class="el-upload-list__item" :src="lifeForm.lifeCover">
                </div>

            </el-form-item>

            <!-- 标题 -->
            <el-form-item label="标题" prop="lifeTitle" :inline-message="true">
                <el-input v-model="lifeForm.lifeTitle" maxlength="40" show-word-limit></el-input>
            </el-form-item>

            <!-- 正文 -->
            <el-form-item label="正文" prop="lifeContent" :inline-message="true">
                <tinymce-editor ref="editor" v-model="lifeForm.lifeContent"></tinymce-editor>
            </el-form-item>

            <!-- 标签 -->
            <el-form-item label="标签" prop="tags" :inline-message="true">
                <el-tag class="tag" :key="tag" v-for="tag in lifeForm.tags" closable :disable-transitions="false" @close="handleClose(tag)">
                    {{tag}}
                </el-tag>
                <el-input class="input-add-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm" size="small">
                </el-input>
                <el-button class="button-add-tag" v-else @click="showInput" size="small">+ 添加标签</el-button>
            </el-form-item>

            <!-- 提交 -->
            <el-form-item class="upload-btns">
                <el-button type="warning" @click="saveAsDraft">保存为草稿</el-button>
                <el-button type="primary" @click="lifeUpload">发布</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>

import TinymceEditor from '../components/Editor'

export default {
    components: {
        TinymceEditor
    },
    data () {
        return {
            loading: false,
            lifeForm: {
                lifeCover: '',
                lifeTitle: '',
                lifeContent: '',
                lifeUserId: '',
                lifeStatus: '',
                tags: [],
            },
            lifeFormRules: {
                lifeCover: {
                    required: true, 
                    message: '请选择一张图片作为封面', 
                },
                lifeTitle: {
                    required: true, 
                    message: '标题不能为空', 
                    trigger: 'blur'
                },
                lifeContent: {
                    required: true, 
                    message: '正文内容不能为空', 
                    trigger: 'blur'
                },
                tags: {
                    required: true, 
                    message: '请添加至少一个标签', 
                    trigger: 'blur'
                }
            },
            inputVisible: false,
            inputValue: ''
        }
    },
    created() {
        if(this.$route.params.lifeForm){
            this.lifeForm = this.$route.params.lifeForm;
            this.lifeForm.tags = this.lifeForm.tags.split(",");
        }
    },
    methods: {
        getFile(event) {
            const files = event.target.files
            let filetype = files[0].name.split('.')[1];
            if(filetype !== 'jpg' && filetype !== 'png' && filetype !== 'gif' && filetype !== 'jpeg' && filetype !== 'webp'){
                this.$message({
                    type: 'error',
                    message: '图片格式错误!',
                    duration: 2000  
                })
                return false;
            }
            const fileReader = new FileReader()                //内置方法new FileReader()   读取文件
                fileReader.addEventListener('load',() => {
                this.lifeForm.lifeCover = fileReader.result
            })
            fileReader.readAsDataURL(files[0])
        },

        handleClose(tag) {
            this.lifeForm.tags.splice(this.lifeForm.tags.indexOf(tag), 1);
        },

        showInput() {
            if(this.lifeForm.tags.length >= 3){
                this.inputVisible = false;
                this.$notify({
                    title: '提示',
                    type: 'warning',
                    message: '最多只能添加3个标签哦！',
                    duration: 2000,
                });
            } else{
                this.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            }
        },

        handleInputConfirm() {
            let inputValue = this.inputValue;
            if (inputValue) {
                this.lifeForm.tags.push(inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },

        saveAsDraft() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.lifeForm.lifeUserId = user.userId;
            this.lifeForm.lifeStatus = 0;
            let this_ = this;
            this.$refs.lifeFormRef.validate(async valid => {
                if (!valid) return;
                
                this.loading = true; 
                this.$http.post(
                    '/lifeUpload',
                    JSON.stringify(this.lifeForm),
                    {
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    }
                ).then(
                    function (res) {
                        console.log("then res =");
                        console.log(res);
                        this_.$message({
                            type: 'success',
                            message: '已保存至草稿',
                            duration: 2000
                        });
                        this_.$router.push('/draft/life');
                    }
                ).catch(
                    function (res) {
                        console.log("catch res =");
                        console.log(res);
                    }
                ) 
            });
        },

        lifeUpload() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.lifeForm.lifeUserId = user.userId;
            this.lifeForm.lifeStatus = 1;
            let this_ = this;
            this.$refs.lifeFormRef.validate(async valid => {
                if (!valid) return;
                
                this.loading = true; 
                this.$http.post(
                    '/lifeUpload',
                    JSON.stringify(this.lifeForm),
                    {
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    }
                ).then(
                    function (res) {
                        console.log("then res =");
                        console.log(res);
                        this_.$message({
                            type: 'success',
                            message: '发布成功',
                            duration: 2000
                        });
                        this_.$router.push('/home');
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
    .write-container{
        padding: 0 25%;
    }
    .el-tag {
        margin-right: 10px;
    }
    .input-add-tag {
        width: 150px !important;
    }
    .button-add-tag {
        width: 100px !important;
    }

    .selectBox{
        display: flex;
        justify-content: flex-start;
        flex-direction: column;
        .selectImage{
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            width: 80px;
            height: 80px;
            border: 1px solid #eeeeee;
            border-radius: 3px;
            cursor: pointer;
            .icon{
                width: 30px;
                height: 30px;
                color: #acacac;
                justify-items: center;
            }
            p{
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 13px;
                height: 20px;
                margin: 0;
                color: #6d757a;
            }
        }
    }
</style>