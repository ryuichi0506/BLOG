<template>
    <div class="tinymce-editor">
        <editor v-model="myValue"
        :init="init"
        :disabled="disabled"
        @onClick="onClick">
        </editor>
    </div>
</template>

<script>
import tinymce from '../../node_modules/tinymce/tinymce'
import Editor from '../../node_modules/@tinymce/tinymce-vue'
import '../../node_modules/tinymce/themes/silver'
// 编辑器插件plugins
// 更多插件参考：https://www.tiny.cloud/docs/plugins/
import '../../node_modules/tinymce/plugins/image'// 插入上传图片
import '../../node_modules/tinymce/plugins/table'// 表格
import '../../node_modules/tinymce/plugins/lists'// 列表
import '../../node_modules/tinymce/plugins/wordcount'// 字数统计
import '../../node_modules/tinymce/plugins/fullscreen'// 全屏
import '../../node_modules/tinymce/plugins/codesample'// 代码块
import '../../node_modules/tinymce/plugins/preview'// 预览
import '../../node_modules/tinymce/plugins/link'// 链接
import '../../node_modules/tinymce/plugins/nonbreaking' // 非空换行

export default {
    components: {
        Editor
    },
    props: {
        value: {
            type: String,
            default: ''
        },
        // 基本路径，默认为空根目录，如果你的项目发布后的地址为目录形式，
        // 即abc.com/tinymce，baseUrl需要配置成tinymce，不然发布后资源会找不到
        baseUrl: {
            type: String,
            default: ''
        },
        disabled: {
            type: Boolean,
            default: false
        },
        plugins: {
            type: [String, Array],
            default: 'lists image table fullscreen codesample preview nonbreaking link'
        },
        toolbar: {
            type: [String, Array],
            default: 'undo redo | formatselect | bold underline italic strikethrough | blockquote codesample | forecolor | alignleft alignright aligncenter alignjustify | bullist numlist | link image table preview fullscreen'
        }
    },
    data () {
        return {
            init: {
                language_url: `${this.baseUrl}/tinymce/langs/zh_CN.js`,
                language: 'zh_CN',
                skin_url: `${this.baseUrl}/tinymce/skins/ui/oxide`,
                content_css: `${this.baseUrl}/tinymce/skins/content/default/content.css`,
                height: 600,
                plugins: this.plugins,
                toolbar: this.toolbar,
                branding: false,
                menubar: false,
                nonbreaking_force_tab: true,
                // 此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
                // 如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
                images_upload_handler: (blobInfo, success, failure) => {
                    // const img = 'data:image/jpeg;base64,' + blobInfo.base64()
                    // success(img)
                    this.handlerImageUpload(blobInfo, success, failure);
                }
            },
            myValue: this.value
        }
    },
    mounted () {
        tinymce.init({})
    },
    
    methods: {
        // 添加相关的事件，可用的事件参照文档=> https://github.com/tinymce/tinymce-vue => All available events

        // blob转base64
        blobToDataURL(blob, callback) {
            var a = new FileReader();
            a.onload = function (e) { callback(e.target.result); }
            a.readAsDataURL(blob);
        },
        handlerImageUpload(blobInfo, success, failure) {
            this.blobToDataURL(blobInfo.blob(), image => {
                this.$http.post(
                    '/imageUpload',
                    JSON.stringify(image),
                    {
                        headers: {
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    }
                ).then(res => {
                    console.log("then=");
                    console.log(res);
                    success(res.data.data)
                }).catch(res => {
                    console.log("catch=");
                    console.log(res);
                    failure('error')
                })
            })
        }
    },
    watch: {
        value (newValue) {
            this.myValue = newValue
        },
        myValue (newValue) {
            this.$emit('input', newValue)
        }
    }
}


</script>