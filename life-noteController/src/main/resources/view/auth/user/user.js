new Vue({
    el: '#user',
    data: {
        form: {
            name: '',
            password: '',
        },
        list:[],
        userInfo: [],
        search: '',
        drawer: false,
        direction: 'ltr',
        size: '10%',
        isMenuOpen: true,
        isMenuClose: true,
        dialogTableVisible: false,
        addUserDialog: false,
        editUserDialog: false,
        formLabelWidth: '120px',
        currentPage: 1,
        pageSize:5,
        totalNum: 100,
        rules: {
            name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
            password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            newPassword1: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
            newPassword2: [{ required: true, message: '请确认密码', trigger: 'blur'}]
        },
        editUser: {
            id: "",
            userName: "",
            password: ""
        }
    },
    created() {
        console.log("初始化...");
        this.getUserInfo();
    },
    methods: {
        handleEdit(index, row, str) {
            if(str == 'upd'){
                let url = 'http://localhost:8081/note/auth/updateUser';
                console.log("修改: [index:" + index + ', userName:' +row.userName);
                this.editUser.id = row.id;
                this.form.id = row.id;
                this.editUser.userName = row.userName;
                this.form.name = row.userName;
                this.form.password ='';
                // this.form.newPassword1 ='';
                // this.form.newPassword2 ='';
                //this.editUser.password = row.password;
                this.editUserDialog = true;
            }else if(str == 'del') {
                this.$confirm('删除['+row.userName+']用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let url = 'http://localhost:8081/note/auth/delUser';
                    axios.post(url,'id='+ row.id).then((res) => {
                        console.log(res.data);
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getUserInfo();
                    }).catch(function (error) {
                        console.log(error);
                        this.$message.error('删除失败');
                    });
                }).catch(() => {
                    return false;
                });
            }else{
                console.log("操作有误！");
            }
        },
        handleDelete(index, row) {
            console.log(index, row);
        },
        getUserInfo() {
            axios.get('http://localhost:8081/note/auth/getUsers'
            ).then ((res) => {
                console.log(res.data.data);
                this.userInfo = res.data.data;
                this.getList();
            }).catch(function (error) {
                console.log(error);
                this.$message.error('获取用户信息失败');
            });
        },
        async getUserInfo1() {
            return await axios.get('http://localhost:8081/note/auth/getUsers'
            ).then ((res) => {
                this.userInfo = res.data.data;
            }).catch(function (error) {
                this.$message.error('获取用户信息失败');
            });
        },
        menuClose() {
            console.log('menuClose');
            this.isMenuClose=true;
            this.isMenuOpne=false;
        },
        menuOpen() {
            console.log('menuOpen');
            this.isMenuClose=false;
            this.isMenuOpne=true;
        },
        openAddDialog(){
            this.addUserDialog = true;
            this.form.name='';
            this.form.password='';
        },
        onSubmit(formName) {
            let userName = this.form.name;
            let password = this.form.password;
            if(userName=="" || password==""){
                this.$refs[formName].validate();
                return false;
            }
            let url = 'http://localhost:8081/note/auth/addUser';
            console.log('[用户名: ' + userName + ', 密码: ' + password + '] submit!');
            axios.post(url,
                'userName='+ userName + '&password='+ password
            ).then ((res) => {
                console.log(res.data);
                this.$message({
                    message: '用户添加成功',
                    type: 'success'
                });
                this.addUserDialog = false,
                this.getUserInfo();
            }).catch(function (error) {
                console.log(error);
                this.$message.error('用户添加失败');
            });
        },
        editSubmit(formName) {
            let id = this.form.id;
            let userName = this.form.name;
            let password = this.form.password;
            let password1 = this.form.newPassword1;
            let password2 = this.form.newPassword2;
            console.log('[用户名: ' + userName + ', 密码: ' + password + ', 新密码: ' + password1 + ', 确认新密码: ' + password2+ '] update!');
            //alert("userName="+userName+"password="+password+"password1="+password1+"password2="+password2);
            if(password=="" || password1=="" ||password2=="" ){
                this.$refs[formName].validate();
                return false;
            }
            if(password1 != password2){
                this.$message.error('两次新密码不一致！');
                return false;
            }
            this.check();
            let url = 'http://localhost:8081/note/auth/updateUser';
            axios.post(url,
                'id='+ id + '&userName='+ userName + '&password='+ password2
            ).then((res) => {
                console.log(res.data);
                this.$message({
                    message: '修改成功',
                    type: 'success'
                });
                this.editUserDialog = false;
                this.getUserInfo();
            }).catch(function (error) {
                console.log(error);
                this.$message.error('修改失败');
            });
            console.log("editSubmit");
        },
        check(){
            //alert(this.form.id);
            let id = this.form.id;
            let password =this.form.password;
            //alert(id+"~"+password);
            if(password != ""){
                let url = 'http://localhost:8081/note/auth/checkPwd';
                axios.post(url,
                    'id='+ id + '&password='+ password
                ).then ((res) => {
                    if(res.data.retCode == '200'){
                        // this.$message({
                        //     message: '原密码通过',
                        //     type: 'success'
                        // });
                    }else{
                        this.$message.error('原密码错误');
                        this.form.password='';
                    }
                }).catch(function (error) {
                    console.log(error);
                    this.$message.error(error.toString());
                });
            }
        },
        handleSizeChange(val) {
            this.pageSize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        async getList() {
            await this.getUserInfo1();
            let list = this.userInfo.filter(data => !this.search || data.userName.toLowerCase().includes(this.search.toLowerCase()));
            this.userInfo=list;//重新复制
            list.filter((item, index) => index < this.currentPage * this.pageSize && index >= this.pageSize * (this.currentPage - 1));
            this.totalNum = list.length
        },
        handleSizeChange(val) {
            console.log('每页 ${val} 条');
            this.pageSize = val;
            this.getList()
        },
        handleCurrentChange(val) {
            console.log('当前页: ${val}');
            this.currentPage = val;
            this.getList();
        },
        searchData(){
            this.currentPage = 1;
            this.getList()
        }
    }
});