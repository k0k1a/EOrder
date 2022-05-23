### MBG使用说明
- 直接run Gen即可生成代码

- com.cdu.lys.graduation.generate.Gen类是MBG自动生成Mapper代码类，生成的类在此包的dao、entity下，Mapper文件在resource/generate目录下。
- 将生成的代码分别拷到com.cdu.lys.graduation.dao和com.cdu.lys.graduation.entity下，Mapper文件拷到resource/mappers，然后删除生成目录下的代码。
- 修改数据库时重新运行生成。
- 扩展Mapper类写在ext包下。