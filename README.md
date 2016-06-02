
LastLetterServer
===================================  
对应客户端[LastLetterClient](https://github.com/SimonCherryGZ/LastLetterClient)

LastLetter是一个网上遗言管理项目。这里的“遗言”不具有法律效应，仅仅是让意外身故者有机会留下一些信息给亲友。

使用LastLetter需要留下一个真实有效的邮箱地址。当服务器检测到该用户超过设定期限没有登陆，就会将该用户上传的“遗言”发送到所留的邮箱中。

本服务器已实现如下功能：
  1. 与Android客户端交互。
  2. 通过MySQL管理用户数据。
  3. 通过Quartz实现定时任务调度。
  4. 通过JavaMail发送邮件。


Maven 
-----------------------------------  
	<dependency>
		<groupId>com.google.code.gson</groupId>
		<artifactId>gson</artifactId>
		<version>2.6.2</version>
	</dependency>

	<dependency>
		<groupId>com.sun.mail</groupId>
		<artifactId>javax.mail</artifactId>
		<version>1.5.4</version>
	</dependency>

	<dependency>
		<groupId>org.quartz-scheduler</groupId>
		<artifactId>quartz</artifactId>
		<version>2.2.3</version>
	</dependency>

	<dependency>
        <groupId>org.quartz-scheduler</groupId>
        <artifactId>quartz-jobs</artifactId>
        <version>2.2.3</version>
    </dependency>


> Written with [StackEdit](https://stackedit.io/).
