<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="studyBlogs" stripe>
            <el-table-column prop="studyId" label="#" width="50px"></el-table-column>
            <el-table-column prop="user.userNickname" label="作者" width="150px"></el-table-column>
            <el-table-column prop="studyTitle" label="标题"></el-table-column>
            <el-table-column prop="studyUpdatetime" label="更新时间" width="200px" :formatter="dateFormat"></el-table-column>
            <el-table-column label="类型" width="50px">
                <template slot-scope="scope">
                    <div v-if="scope.row.studyType === 0">原创</div>
                    <div v-if="scope.row.studyType === 1">转载</div>
                </template>
            </el-table-column>
            <el-table-column label="状态" width="50px">
                <template slot-scope="scope">
                    <div v-if="scope.row.studyStatus === 0">禁用</div>
                    <div v-if="scope.row.studyStatus === 1">可用</div>
                </template>
            </el-table-column>
            <el-table-column label="是否置顶" width="80px">
                <template slot-scope="scope">
                    <el-switch :active-value="1" :inactive-value="0" v-model="scope.row.studyTopStatus" @change="changStudyTopStatus(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="210px">
                <!-- 禁用启用按钮 -->
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" v-if="scope.row.studyStatus === 1" @click="changStudyStatus(scope.row)">禁用</el-button>
                    <el-button type="success" size="mini" v-else @click="changStudyStatus(scope.row)">启用</el-button>
                    <!-- 查看按钮 -->
                    <el-button type="primary" size="mini" :disabled="scope.row.studyStatus === 1 ? false : true" @click="toStudyDetail(scope.row.studyId)">查看</el-button>
                    <!-- 删除按钮 -->
                    <el-button type="danger" size="mini" @click="toDeleteStudy(scope.row)">删除</el-button>
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
            studyBlogs: [],
            query: {
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        this.getStudyManage();
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
        getStudyManage() {
            let this_ = this;
            this.$http.post(
                '/getStudyManage', 
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
                    this_.studyBlogs = res.data.list;
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
            this.getStudyManage();
        },
        // 修改置顶状态
        changStudyTopStatus(val) {
            this.$http.post(
                '/changStudyTopStatus', 
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
        // 修改博客状态
        changStudyStatus(val) {
            // 将状态 0变为1 1变为0
            val.studyStatus = Math.abs(val.studyStatus - 1);
            this.$http.post(
                '/changStudyStatus', 
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
        // 查看博客详情
        toStudyDetail(studyId) {
            let routes = this.$router.resolve({ path: '/study/'+ studyId });
            window.open(routes.href, '_blank');
        },
        // 删除学习博客
        toDeleteStudy(row) {
            let this_ = this;
            this.$confirm('确定删除 "' + row.studyTitle + '" 这篇博客吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this_.$http.post(
                    '/deleteStudy',
                    JSON.stringify(row.studyId),
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

</style>