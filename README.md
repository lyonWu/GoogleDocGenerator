1.0 version
实现了自动生成表格功能，格式为component-case-link格式表格。

1.1 version
添加断点模式，支持因意外发生中断，从而可以从中断位置开始重新启动脚本，同时支持片段截取（截取方式为数字模式）。

1.2 version
添加自由模式，支持跳跃式选择模式，实现根据component name片段截取方式。

1.3 version
将断点模式的截取方式更改为根据name的截取方式

1.4 version
在默认模式中，添加重做功能，在因网速原因中断时并不会强制中断脚本，而尝试重做1次并继续运行后续脚本。
添加 mutiple-build model（连续作业模式）。

1.5 version
更改连续作业模式workflow，使用并发遍历代替串行遍历。
更改重做机制，在当前component抛出异常时，先跳过当前component并记录，之后在每个build-num作业中，重做一次。
增加GUI操作界面

1.6 version
加入GUI日志页面，改进freecase与multiple参数输入模式（以","取代"/"，同时也不需要最后一个符号了）
