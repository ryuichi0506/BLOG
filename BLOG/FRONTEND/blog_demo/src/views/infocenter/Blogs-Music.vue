<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="myMusicBlogs" stripe>
            <el-table-column prop="musicId" label="#" width="60px"></el-table-column>
            <el-table-column prop="musicTitle" label="标题"></el-table-column>
            <el-table-column prop="musicCreatetime" label="发布时间" width="200px" :formatter="dateFormat"></el-table-column>
            <el-table-column label="操作" width="210px">
                <template slot-scope="scope">
                    <!-- 编辑按钮 -->
                    <el-button type="warning" size="mini" @click="toEditMusic(scope.row.musicId)">编辑</el-button>
                    <!-- 查看按钮 -->
                    <el-button type="primary" size="mini" @click="toMusicDetail(scope.row.musicId)">查看</el-button>
                    <!-- 删除按钮 -->
                    <el-button type="danger" size="mini" @click="toDeleteMusic(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页条 -->
        <el-pagination v-if="total != 0" @current-change="handleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="total" layout="total, prev, pager, next, jumper"></el-pagination>
    </div>
</template>

<script>
export default {
    inject: ['reload'],
    data() {
        return {
            myMusicBlogs: [],
            query: {
                userId: '',
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        window.sessionStorage.setItem('activePath','/blogs/music');
        this.getMyMusicBlogs();
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
        
        getMyMusicBlogs() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.query.userId = user.userId;
            let this_ = this;
            this.$http.post(
                '/getMyMusicBlogs', 
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
                    this_.myMusicBlogs = res.data.list;
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
            this.getMyMusicBlogs();
        },

        toEditMusic(musicId) {
            let this_ = this;
            this.$http.post(
                '/toEditMusic', 
                JSON.stringify(musicId),
                {
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8'
                    }
                }
            ).then(
                function (res) {
                    console.log("res =");
                    console.log(res);
                    this_.$router.push({
                        name: '/upload/music',
                        params: {
                            musicForm: res.data.data
                        }
                    });
                }
            ).catch(
                function (res) {
                    console.log("catch res =");
                    console.log(res);
                }
            )
        },
        
        toMusicDetail(musicId) {
            let routes = this.$router.resolve({ path: '/music/'+ musicId });
            window.open(routes.href, '_blank');
        },

        toDeleteMusic(row) {
            let this_ = this;
            this.$confirm('确定删除 "' + row.musicTitle + '" 这篇博客吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this_.$http.post(
                    '/deleteMusic',
                    JSON.stringify(row.musicId),
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