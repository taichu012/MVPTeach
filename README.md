# MVPTeach
登录为业务场景对比非MVP和MVP设计的例子

#taichu补充：
1.原作者通过很简单的举例来说明MVP的含义，有无MVP对比区别，好坏mvp来体现使用得当与否的区别；非常好；
2.这里推荐一个package组织层次（见mvp包）
  * “login”对应app的一个页面呈现（不是一个功能），举例：登录页面
  * bean目录：存放相关用到的数据结构，俗称bean，也叫POJO或entity；
  * imp目录：体现mvp核心的地方，其中分为3层：
    ** LoginView（XxxView层）：是对应login页面的显示，即view的展现，包含各类元素，输入框等；
    ** LoginPresenter（XxxPresenter层）：表示view地下隐藏的逻辑，比如登录，登录成功，失败调用进度条等；
       他非常核心，纪要调节view的展现（比如进度条等），又要执行manger的逻辑；
       概念区分：view一般是初始化的静态展现；presenter是动态的，有一定状态的所有展现的逻辑联系和表达；
    ** LoginManager（XxxManager层）：管理层，其实是功能集合，被presenter调用来完成view无关的操作；
    MVP作用：通过三层，将view纯（静态）显示，presenter动态显示和功能交织，manager纯功能实现，三者解耦合，
             这样view和实现层都可单独替换，重构，优化和独立维护。
    MVP难点：抽象出纯的功能函数manager层，以及presenter的撰写；很容易出现mvpbad中的假三层，虽然是三层，但
             是没有隔离实现和view。
  * itf目录：其实单纯实现MVP已经实现了三层隔离解耦合，三层是3个java文件可单独替换，但是如果再用接口，那么
             三层就还能提供多种最终实现，让sprint等框架来配置，代码也更清晰漂亮和灵活！
3.根据以上分析，taichu认为用名称“VCAD：view/controller/action/data”更合理，见vcad包
