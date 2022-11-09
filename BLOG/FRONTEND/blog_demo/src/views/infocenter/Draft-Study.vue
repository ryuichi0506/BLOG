<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="myStudyBlogsInDraft" stripe>
            <el-table-column prop="studyId" label="#" width="60px"></el-table-column>
            <el-table-column prop="studyTitle" label="标题"></el-table-column>
            <el-table-column prop="studyCreatetime" label="发布时间" width="200px" :formatter="dateFormat"></el-table-column>
            <el-table-column label="操作" width="210px">
                <template slot-scope="scope">
                    <!-- 编辑按钮 -->
                    <el-button type="warning" size="mini" @click="toEditStudy(scope.row.studyId)">编辑</el-button>
                    <!-- 删除按钮 -->
                    <el-button type="danger" size="mini" @click="toDeleteStudy(scope.row)">删除</el-button>
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
            myStudyBlogsInDraft: [],
            query: {
                userId: '',
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        this.getMyStudyBlogsInDraft();
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
        getMyStudyBlogsInDraft() {
            let user = JSON.parse(window.sessionStorage.getItem('user'));
            this.query.userId = user.userId;
            let this_ = this;
            this.$http.post(
                '/getMyStudyBlogsInDraft', 
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
                    this_.myStudyBlogsInDraft = res.data.list;
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
            this.getMyStudyBlogsInDraft();
        },

        toEditStudy(studyId) {
            let this_ = this;
            this.$http.post(
                '/toEditStudy', 
                JSON.stringify(studyId),
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
                        name: '/upload/study',
                        params: {
                            studyForm: res.data.data
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

<style>

</style>