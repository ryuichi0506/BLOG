<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="messageLists" stripe>
            <el-table-column prop="messageId" label="#" width="50px"></el-table-column>
            <el-table-column label="用户" width="200px">
                <template slot-scope="scope">
                    <div class="user">
                        <img draggable="false" class="userAvatar" :src="scope.row.user.userAvatar">
                        <span>{{scope.row.user.userNickname}}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="messageContent" label="内容"></el-table-column>
            <el-table-column prop="messageReturnContent" label="回复内容"></el-table-column>
            <el-table-column prop="messageCreatetime" label="发布时间" width="200px" :formatter="dateFormat"></el-table-column>
            <el-table-column label="操作" width="210px">
                <template slot-scope="scope">
                    <!-- 回复按钮 -->
                    <el-button type="primary" size="mini" @click="openReplyDialog(scope.row)">回复</el-button>
                    <!-- 删除按钮 -->
                    <el-button type="danger" size="mini" @click="toDeleteMessage(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页条 -->
        <el-pagination @current-change="handleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="total" layout="total, prev, pager, next, jumper"></el-pagination>

        <!-- 添加的对话框 -->
        <el-dialog title="回复留言" :visible.sync="replyDialogVisible" width="40%">
            <!-- 内容主体 -->
            <el-form :model="replyForm" ref="replyFormRef">
                <el-form-item :label="'回复 \'' + replyForm.user.userNickname + '\' 的留言'">
                    <el-input type="textarea" maxlength="200" show-word-limit v-model="replyForm.messageReturnContent" autocomplete="off" placeholder="回复留言..."></el-input>
                </el-form-item>
            </el-form>
            <!-- 按钮区域 -->
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="replyDialogVisible = false">取 消</el-button>
                <el-button type="primary" size="small" @click="reply">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    inject: ['reload'],
    data() {
        return {
            messageLists: [],
            query: {
                pageNum: 1,
                pageSize: 10
            },
            total: 0,
            // 控制回复留言对话框的显示与隐藏
            replyDialogVisible: false,
            replyForm: {
                adminId: '',
                messageId: '',
                messageReturnContent: '',
                user: {}
            }
        }
    },
    created() {
        this.getMessageManage();
    },
    methods: {
        dateFormat(row, column, cellValue, index) {
            let date = new Date(cellValue);
            let y = date.getFullYear();
            let MM = date.getMonth() + 1;
            MM = MM < 10 ? ('0' + MM) : MM;
            let d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            let h = date.getHours();
            h = h < 10 ? ('0' + h) : h;
            let m = date.getMinutes();
            m = m < 10 ? ('0' + m) : m;
            let s = date.getSeconds();
            s = s < 10 ? ('0' + s) : s;
            return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
        },
        getMessageManage() {
            let this_ = this;
            this.$http.post(
                '/getMessageManage', 
                JSON.stringify(this.query),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("res =");
                    console.log(res);
                    this_.messageLists = res.data.list;
                    this_.total = res.data.total;
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        // 监听页码值改变的事件
        handleCurrentChange(newPage) {
            this.query.pageNum = newPage;
            this.getMessageManage();
        },
        // 打开回复留言的对话框
        openReplyDialog(row) {
            this.replyDialogVisible = true;
            this.replyForm.user = row.user;
            this.replyForm.messageId = row.messageId;
            this.replyForm.messageReturnContent = row.messageReturnContent;
            this.replyForm.adminId = JSON.parse(window.sessionStorage.getItem('admin')).adminId;
        },
        // 回复留言
        reply() {
            let this_ = this;
            this.$http.post(
                '/replyMessage', 
                JSON.stringify(this.replyForm),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("res =");
                    console.log(res);
                    this_.$message({
                        type: 'success',
                        message: '回复成功',
                        duration: 1000
                    });
                    this_.reload();
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                    this_.$message({
                        type: 'error',
                        message: '回复失败',
                        duration: 1000
                    });
                    this_.reload();
                }
            )
        },
        // 删除留言
        toDeleteMessage(row){
            console.log(row);
            let this_ = this;
            this.$confirm('确定删除 "' + row.user.userNickname + '"的留言吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this_.$http.post(
                    '/deleteMessage',
                    JSON.stringify(row.messageId),
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
                            message: res.data.message,
                            duration: 2000
                        });
                        this_.reload();
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
    .user{
        display: flex;
        align-items: center;
    }
    .userAvatar{
        height: 40px;
        width: 40px;
        border-radius: 50%;
        object-fit: cover;
        margin-right: 10px;
    }
</style>