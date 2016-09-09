# dingqukuaidi
第一次提交
自贸区APP__BUG总结：
 	1：cardView item颜色无法改变的原因：通过inflate方法产生时一定要注意上下文环境
	2：activity之间回调requestCode不对的原因是：startActivityForResult()方法一定要放在
	   activity中不能放在fragement中
        3：xRecyclerView：使用该控件时如果需要添加header header外部不要嵌套布局
			  而且每添加一个header 在getAdapterPosition都要减去header的数量才能点击正			  确的item位置
