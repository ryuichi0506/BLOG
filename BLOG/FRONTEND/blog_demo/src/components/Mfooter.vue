<template>
    <footer class="footer">
        <el-row class="bottom">
            <el-col :span="8">
                <div class="links">
                    <p class="link-title">链接</p>
                    <ul>
                        <li class="link-item">
                            <a href="https://cn.vuejs.org/" target="_blank">Vue</a>
                        </li>
                        <li class="link-item">
                            <a href="https://www.tiny.cloud/features" target="_blank">TinyMCE</a>
                        </li>
                        <li class="link-item">
                            <a href="https://element.eleme.cn" target="_blank">Element-UI</a>
                        </li>
                        <li class="link-item">
                            <a href="https://github.com/SevenOutman/vue-aplayer" target="_blank">Vue-APlayer</a>
                        </li>
                        <li class="link-item">
                            <a href="https://fa5.dashgame.com/" target="_blank">Font Awesome</a>
                        </li>
                    </ul>
                </div>
            </el-col>
            <el-col :span="8">
                <div class="about">
                    <p class="about-title">关于</p>
                    <p class="about-item">
                        <br>
                        采采卷耳，不盈顷筐。<br>
                        嗟我怀人，寘彼周行。
                    </p>
                </div>
            </el-col>
            <el-col :span="8">
                <div class="social">
                    <p class="social-title">社交</p>
                    <el-popover trigger="hover" placement="top" width="150" class="social-item">
                        <img src="../assets/images/qq.jpg" width="150px" height="150px">
                        <el-button slot="reference" circle>
                            <font-awesome-icon :icon="['fab', 'qq']"></font-awesome-icon>
                        </el-button>
                    </el-popover>
                    <el-popover trigger="hover" placement="top" width="150" class="social-item">
                        <img src="../assets/images/wechat.jpg" width="150px" height="150px">
                        <el-button slot="reference" circle>
                            <font-awesome-icon :icon="['fab', 'weixin']"></font-awesome-icon>
                        </el-button>
                    </el-popover>
                    <el-popover trigger="hover" placement="top" content="RyuichiTamako@outlook.com" class="social-item">
                        <el-button slot="reference" circle>
                            <font-awesome-icon :icon="['fas', 'envelope']"></font-awesome-icon>
                        </el-button>
                    </el-popover>
                </div>
            </el-col>
        </el-row>
        <p class="copyright">
            &copy;DESIGNED BY 王学铭 16130214
        </p>
    </footer>
</template>

<script>
export default {

}
</script>

<style lang="less" scoped>
/* 底部css */
.footer{
    position: relative;
    background-color: #eef3f5;
    width: 100%;
    bottom: 0;
    height: 270px;
    user-select: none;
}
.footer .bottom{
    padding: 20px 150px;
}
.footer .bottom .links{
    text-align: center;
}
.footer .bottom .links .link-title{
    color: #000;
    font-weight: bold;
    font-size: 16px;
}
.footer .bottom .links ul{
    padding: 0;
    list-style: none;
}
.footer .bottom .links ul .link-item{
    height: 26px;
    line-height: 26px;
}
.footer .bottom .links ul .link-item a{
    color: rgb(30, 30, 30);
    text-decoration: none;
    font-size: 14px;
}
.footer .bottom .links ul .link-item a:hover{
    color: rgb(100, 100, 100);
    text-decoration: none;
    font-size: 14px;
}
.footer .bottom .about{
    text-align: center;
}
.footer .bottom .about .about-title{
    color: #000;
    font-weight: bold;
    font-size: 16px;
}
.footer .bottom .about .about-item{
    padding: 0 20px;
    line-height: 1.8;
}
.footer .bottom .social{
    text-align: center;
}
.footer .bottom .social .social-title{
    color: #000;
    font-weight: bold;
    font-size: 16px;
}
.footer .bottom .social .social-item{
    padding: 5px;
}

.copyright{
    position: absolute;
    bottom: 0;
    width: 100%;
    margin: 10px 0;
    text-align: center;
    font-size: 14px;
    color: #000;
    font-family: Arial, Helvetica, sans-serif;
}
</style>