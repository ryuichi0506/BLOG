<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="userLists" stripe>
            <el-table-column prop="userId" label="#" width="50px"></el-table-column>
            <el-table-column prop="userGender" label="性别"  width="50px"></el-table-column>
            <el-table-column label="头像" width="70px">
                <template slot-scope="scope">
                    <img draggable="false" class="userAvatar" :src="scope.row.userAvatar">
                </template>
            </el-table-column>
            <el-table-column prop="userName" label="用户名" width="150px"></el-table-column>
            <el-table-column prop="userNickname" label="昵称"></el-table-column>
            <el-table-column prop="userEmail" label="邮箱"></el-table-column>
            <el-table-column prop="userCreatetime" label="创建时间" width="200px" :formatter="dateFormat"></el-table-column>
            <el-table-column label="用户状态" width="80px">
                <template slot-scope="scope">
                    <div v-if="scope.row.userStatus === 0">禁用</div>
                    <div v-if="scope.row.userStatus === 1">可用</div>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="210px">
                <!-- 禁用启用按钮 -->
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" v-if="scope.row.userStatus === 1" @click="changUserStatus(scope.row)">禁用</el-button>
                    <el-button type="success" size="mini" v-else @click="changUserStatus(scope.row)">启用</el-button>
                    <!-- 查看按钮 -->
                    <el-button type="primary" size="mini" :disabled="scope.row.userStatus === 1 ? false : true" @click="toUserSpace(scope.row.userId)">查看</el-button>
                    <!-- 删除按钮 -->
                    <el-button type="danger" size="mini" @click="toDeleteUser(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页条 -->
        <el-pagination @current-change="handleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="total" layout="total, prev, pager, next, jumper"></el-pagination>
    </div>
</template>

<script>
export default {
    inject: ['reload'],
    data() {
        return {
            userLists: [],
            query: {
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        this.getUserManage();
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
        getUserManage() {
            let this_ = this;
            this.$http.post(
                '/getUserManage', 
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
                    this_.userLists = res.data.list;
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
            this.getUserManage();
        },
        // 修改用户状态
        changUserStatus(val) {
            // 将状态 0变为1 1变为0
            val.userStatus = Math.abs(val.userStatus - 1);
            this.$http.post(
                '/changUserStatus', 
                val,
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("res =");
                    console.log(res);
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        // 查看用户主页
        toUserSpace(userId) {
            let routes = this.$router.resolve({ path: '/space/'+ userId });
            window.open(routes.href, '_blank');
        },
        // 删除用户
        toDeleteUser(row) {
            let this_ = this;
            this.$confirm('确定删除 "' + row.userName + '" 用户吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this_.$http.post(
                    '/deleteUser',
                    JSON.stringify(row.userId),
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
    .userAvatar{
        height: 40px;
        width: 40px;
        border-radius: 50%;
        object-fit: cover;
    }
</style>