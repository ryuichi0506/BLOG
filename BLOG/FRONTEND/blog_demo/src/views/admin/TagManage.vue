<template>
    <div class="container">
        <!-- 用户列表 -->
        <el-table :data="tagLists" stripe>
            <el-table-column prop="tagId" label="#" width="50px"></el-table-column>
            <el-table-column prop="tagName" label="标签"></el-table-column>
            <!-- <el-table-column label="操作" width="210px">
                <template slot-scope="scope">
                    <el-button type="danger" size="mini">删除</el-button>
                </template>
            </el-table-column> -->
        </el-table>

        <!-- 分页条 -->
        <el-pagination @current-change="handleCurrentChange" :current-page="query.pageNum" :page-size="query.pageSize" :total="total" layout="total, prev, pager, next, jumper"></el-pagination>
    </div>
</template>

<script>
export default {
    data() {
        return {
            tagLists: [],   
            query: {
                pageNum: 1,
                pageSize: 10
            },
            total: 0
        }
    },
    created() {
        this.getTagManage();
    },
    methods: {
        getTagManage() {
            let this_ = this;
            this.$http.post(
                '/getTagManage', 
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
                    this_.tagLists = res.data.list;
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
            this.getTagManage();
        }
    }
}
</script>

<style>

</style>