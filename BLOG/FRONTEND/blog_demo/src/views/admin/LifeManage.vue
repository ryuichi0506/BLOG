<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="lifeBlogs" stripe>
            <el-table-column prop="lifeId" label="#" width="50px"></el-table-column>
            <el-table-column prop="user.userNickname" label="作者" width="150px"></el-table-column>
            <el-table-column prop="lifeTitle" label="标题"></el-table-column>
            <el-table-column prop="lifeUpdatetime" label="更新时间" width="200px" :formatter="dateFormat"></el-table-column>
            <el-table-column label="状态" width="50px">
                <template slot-scope="scope">
                    <div v-if="scope.row.lifeStatus === 0">禁用</div>
                    <div v-if="scope.row.lifeStatus === 1">可用</div>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="210px">
                <!-- 禁用启用按钮 -->
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" v-if="scope.row.lifeStatus === 1" @click="changLifeStatus(scope.row)">禁用</el-button>
                    <el-button type="success" size="mini" v-else @click="changLifeStatus(scope.row)">启用</el-button>
                    <!-- 查看按钮 -->
                    <el-button type="primary" size="mini" :disabled="scope.row.lifeStatus === 1 ? false : true" @click="toLifeDetail(scope.row.lifeId)">查看</el-button>
                    <!-- 删除按钮 -->
                    <el-button type="danger" size="mini" @click="toDeleteLife(scope.row)">删除</el-button>
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
            lifeBlogs: [],
            query: {
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        this.getLifeManage();
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
        getLifeManage() {
            let this_ = this;
            this.$http.post(
                '/getLifeManage', 
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
                    this_.lifeBlogs = res.data.list;
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
            this.getLifeManage();
        },
        // 修改博客状态
        changLifeStatus(val) {
            // 将状态 0变为1 1变为0
            val.lifeStatus = Math.abs(val.lifeStatus - 1);
            this.$http.post(
                '/changLifeStatus', 
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
        // 查看生活详情
        toLifeDetail(lifeId) {
            let routes = this.$router.resolve({ path: '/life/'+ lifeId });
            window.open(routes.href, '_blank');
        },
        // 删除生活博客
        toDeleteLife(row) {
            let this_ = this;
            this.$confirm('确定删除 "' + row.lifeTitle + '" 这篇博客吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this_.$http.post(
                    '/deleteLife',
                    JSON.stringify(row.lifeId),
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

<style>

</style>