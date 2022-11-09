<template>
    <div class="write-container" v-loading.fullscreen.lock="loading">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/upload/study' }">学习</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/upload/anime' }">动漫</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/upload/life' }">生活</el-breadcrumb-item>
            <el-breadcrumb-item>音乐</el-breadcrumb-item>
            <el-breadcrumb-item>投稿</el-breadcrumb-item>
        </el-breadcrumb>

        <el-divider></el-divider>

        <el-form ref="musicFormRef" :model="musicForm" :rules="musicFormRules" label-position="top" size="small">

            <!-- 博客类型(转载/原创) -->
            <el-form-item label="原创/转载 (如转载请在文中标注转载来源)" prop="musicType" :inline-message="true">
                <el-radio v-model="musicForm.musicType" :label="0">原创</el-radio>
                <el-radio v-model="musicForm.musicType" :label="1">转载</el-radio>
            </el-form-item>

            <!-- 标题 -->
            <el-form-item label="标题" prop="musicTitle" :inline-message="true">
                <el-input maxlength="40" show-word-limit v-model="musicForm.musicTitle"></el-input>
            </el-form-item>
            
            <!-- 歌曲标题 -->
            <el-form-item label="歌曲标题 (title)" prop="musicSrcTitle" :inline-message="true">
                <el-input maxlength="40" show-word-limit v-model="musicForm.musicSrcTitle"></el-input>
            </el-form-item>

            <!-- 歌曲作者 -->
            <el-form-item label="歌手/演奏者/艺术家 (artist)" prop="musicArtist" :inline-message="true">
                <el-input v-model="musicForm.musicArtist"></el-input>
            </el-form-item>

            <!-- 歌曲封面 -->
            <el-form-item label="歌曲封面 (建议使用尺寸比例为1:1的图片,只能上传一张封面)" prop="musicCover" :inline-message="true">
                <el-upload class="upload-demo" accept="image/*" action="#" :limit="1" :http-request="imgRequest" list-type="picture">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
            </el-form-item>

            <!-- 歌曲链接 -->
             <el-form-item label="歌曲链接 (只能上传音频格式文件)" prop="musicSrc" :inline-message="true">
                <el-upload class="upload-demo" accept="audio/*" action="#" :limit="1" :http-request="musicRequest" :before-upload="beforeMusicUpload">
                    <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
            </el-form-item>

            <!-- 正文 -->
            <el-form-item label="正文" prop="musicContent" :inline-message="true">
                <tinymce-editor ref="editor" v-model="musicForm.musicContent"></tinymce-editor>
            </el-form-item>

            <!-- 概要 -->
            <el-form-item label="概要" prop="musicDescription" :inline-message="true">
                <el-input type="textarea" maxlength="200" show-word-limit autocomplete="off" placeholder="一段话简单概括一下你的文章吧" v-model="musicForm.musicDescription"></el-input>
            </el-form-item> 

            <!-- 标签 -->
            <el-form-item label="标签" prop="tags" :inline-message="true">
                <el-tag class="tag" :key="tag" v-for="tag in musicForm.tags" closable :disable-transitions="false" @close="handleClose(tag)">
                    {{tag}}
                </el-tag>
                <el-input class="input-add-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm" size="small">
                </el-input>
                <el-button class="button-add-tag" v-else @click="showInput" size="small">+ 添加标签</el-button>
            </el-form-item>

            <!-- 提交 -->
            <el-form-item class="upload-btns">
                <el-button type="warning" @click="saveAsDraft">保存为草稿</el-button>
                <el-button type="primary" @click="musicUpload">发布</el-button>
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
            musicForm: {
                musicType: '',
                musicTitle: '',
                musicSrcTitle: '',
                musicArtist: '',
                musicCover: '',
                musicSrc: '',
                musicContent: '',
                musicDescription: '',
                musicUserId: '',
                musicStatus: '',
                tags: [],
            },
            musicFormRules: {
                musicType: {
                    required: true, 
                    message: '请选择一张图片作为封面', 
                },
                musicTitle: {
                    required: true, 
                    message: '标题不能为空', 
                    trigger: 'blur'
                },
                musicSrcTitle: {
                    required: true, 
                    message: '歌曲标题不能为空', 
                    trigger: 'blur'
                },
                musicArtist: {
                    required: true, 
                    message: '歌手/演奏者/艺术家不能为空', 
                    trigger: 'blur'
                },
                musicCover: {
                    required: true, 
                    message: '歌曲封面不能为空', 
                    trigger: 'blur'
                },
                musicSrc: {
                    required: true, 
                    message: '歌曲连接不能为空', 
                    trigger: 'blur'
                },
                musicContent: {
                    required: true, 
                    message: '正文内容不能为空', 
                    trigger: 'blur'
                },
                musicDescription: {
                    required: true, 
                    message: '概要不能为空', 
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
        if(this.$route.params.musicForm){
            this.musicForm = this.$route.params.musicForm;
            this.musicForm.tags = this.musicForm.tags.split(",");
        }
    },
    methods: {
        handleClose(tag) {
            this.musicForm.tags.splice(this.musicForm.tags.indexOf(tag), 1);
        },

        showInput() {
            if(this.musicForm.tags.length >= 3){
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
                this.musicForm.tags.push(inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },

        handleUpload(files, fileList) {
            this.$notify({
                title: '提示',
                type: 'warning',
                message: '最多只能上传一张封面图片',
                duration: 2000,
            });
        },

        beforeMusicUpload(file) {
            console.log(file);
            if(file.type !== 'audio/mpeg' && file.type !== 'audio/x-flac' && file.type !== 'audio/wav'){
                this.$message({
                    type: 'error',
                    message: '上传歌曲链接格式错误!',
                    duration: 2000  
                })
                return false;
            } else{
                return true;
            }
        },

        imgRequest(data) {
            let this_ = this;
            let rd = new FileReader(); // 创建文件读取对象
            let file = data.file;
            rd.readAsDataURL(file); // 文件读取装换为base64类型
            rd.onloadend = function (e) {
                this_.musicForm.musicCover = this.result; // this指向当前方法onloadend的作用域
            }
        },

        musicRequest(data) {
            let this_ = this;
            let rd = new FileReader(); // 创建文件读取对象
            let file = data.file;
            rd.readAsDataURL(file); // 文件读取装换为base64类型
            rd.onloadend = function (e) {
                this_.musicForm.musicSrc = this.result; // this指向当前方法onloadend的作用域
            }
        },

        // 保存为草稿
        saveAsDraft() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.musicForm.musicUserId = user.userId;
            this.musicForm.musicStatus = 0;
            let this_ = this;
            this.$refs.musicFormRef.validate(async valid => {
                if (!valid) return; 
                
                this.loading = true; 
                this.$http.post(
                    '/musicUpload',
                    JSON.stringify(this.musicForm),
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
                        this_.$router.push('/draft/music');
                    }
                ).catch(
                    function (res) {
                        console.log("catch res =");
                        console.log(res);
                    }
                )
            });
        },

        // 发布
        musicUpload() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.musicForm.musicUserId = user.userId;
            this.musicForm.musicStatus = 1;
            let this_ = this;
            this.$refs.musicFormRef.validate(async valid => {
                if (!valid) return; 
                
                this.loading = true; 
                this.$http.post(
                    '/musicUpload',
                    JSON.stringify(this.musicForm),
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
</style>