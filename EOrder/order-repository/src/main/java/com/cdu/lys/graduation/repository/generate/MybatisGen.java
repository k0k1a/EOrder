package com.cdu.lys.graduation.repository.generate;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/1/3 21:24
 */
public class MybatisGen {
    public static void main(String[] args) {

        try {
            System.out.println("startgenerator ...");

            List<String> warnings = new ArrayList<>();

            boolean overwrite = true;

            File configFile = new File(MybatisGen.class.getResource("/generatorConfig.xml").getFile());

            ConfigurationParser cp = new ConfigurationParser(warnings);


            Configuration config = cp.parseConfiguration(configFile);

            DefaultShellCallback callback = new DefaultShellCallback(overwrite);

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            myBatisGenerator.generate(null);

            System.out.println("endgenerator!");

        } catch (IOException e) {
            e.printStackTrace();

        } catch (XMLParserException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();

        } catch (InvalidConfigurationException e) {

            e.printStackTrace();

        }

    }
}
